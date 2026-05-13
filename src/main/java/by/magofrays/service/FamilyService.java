package by.magofrays.service;

import by.magofrays.configuration.FamilyProperties;
import by.magofrays.configuration.UserProperties;
import by.magofrays.dto.CreateFamilyDto;
import by.magofrays.dto.CreateInvitation;
import by.magofrays.dto.ReadFamilyMemberDto;
import by.magofrays.entity.*;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.FamilyMapper;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.repository.FamilyMemberRepository;
import by.magofrays.repository.FamilyRepository;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static by.magofrays.exception.ErrorCode.BAD_REQUEST;
import static by.magofrays.exception.ErrorCode.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final MemberMapper memberMapper;
    private final UserProperties userProperties;
    private final FamilyProperties familyProperties;
    private final FamilyMapper familyMapper;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final ConcurrentHashMap<String, Invitation> invitationMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, String> familyInvitationMap = new ConcurrentHashMap<>();
    private final NotificationService notificationService;

    public List<ReadFamilyMemberDto> getFamilyMembersByMemberId(UUID familyId) {
        return familyMemberRepository.findByFamily_Id(familyId).stream().map(memberMapper::toDto).toList();
    }

    public Invitation createInvitation(@Validated CreateInvitation request) {
        log.debug("Member: {} trying create invitation for family: {}", request.getMemberId(), request.getFamilyId());
        if (familyInvitationMap.containsKey(request.getFamilyId())) {
            log.debug("Invitation for family: {} already exists", request.getFamilyId());
            return invitationMap.get(
                    familyInvitationMap.get(request.getFamilyId())
            );
        }
        String code = RandomStringUtils.secure().nextAlphanumeric(8).toUpperCase();
        var invitation = Invitation.builder()
                .invitationCode(code)
                .numMembers(request.getNumMembers())
                .expiresAt(
                        request.getExpiresAt()
                )
                .familyId(request.getFamilyId())
                .build();
        invitationMap.put(code, invitation);
        familyInvitationMap.put(request.getFamilyId(), code);
        log.info("Created invitation for family: {}", request.getFamilyId());
        var familyMember = familyMemberRepository.findById(request.getMemberId()).get();
        notificationService.sendNotificationFamily("create-invitation",
                "%s создал код-приглашение".formatted(familyMember.getMember().getUsername()),
                this.getClass().getName(),
                familyMember.getFamily(),
                familyMember.getMember().getId());
        return invitation;
    }


    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
    public void cleaningInvitationMap() {
        log.debug("Cleaning invitation map");
        invitationMap.entrySet().iterator().forEachRemaining(entry -> {
            if (entry.getValue().getExpiresAt().isBefore(LocalDateTime.now())) {
                invitationMap.remove(entry.getKey());
                familyInvitationMap.remove(entry.getValue().getFamilyId());
                var family = familyRepository.findById(entry.getValue().getFamilyId()).get();
                notificationService.sendNotificationFamily("delete-invitation",
                        "Код приглашение в семью был удален",
                        this.getClass().getName(),
                        family,
                        null);
            }
        });
    }

    @Transactional
    public ReadFamilyMemberDto getIntoFamilyByInvitation(String invitationCode, UUID memberId) {
        log.info("Member: {} using invitation code: {}", memberId, invitationCode);
        var invitation = invitationMap.get(invitationCode);
        if (invitation == null) {
            log.warn("Invitation code: {}, does not exist ", invitationCode);
            throw new BusinessException(NOT_FOUND, "Кода приглашения: " + invitationCode + " не существует, либо он прекратил свое действие!");
        }
        if (invitation.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("Invitation code: {} is outdated", invitationCode);
            throw new BusinessException(NOT_FOUND, "Кода приглашения: " + invitationCode + " не существует, либо он прекратил свое действие!");
        }
        if (familyMemberRepository.memberInFamily(memberId, invitation.getFamilyId())) {
            log.warn("Member: {} is already in family", memberId);
            throw new BusinessException(BAD_REQUEST, "Пользователь уже состоит в этой семье!");
        }
        var family = familyRepository.findById(invitation.getFamilyId()).orElseThrow(
                () -> new BusinessException(NOT_FOUND, "Такой семьи не существует!")
        );
        var member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(NOT_FOUND, "Такого пользователя не существует!"));
        if (invitation.getNumMembers() == 1) {
            invitationMap.remove(invitationCode);
            familyInvitationMap.remove(invitation.getFamilyId());
        } else {
            invitation.setNumMembers(invitation.getNumMembers() - 1);
            invitationMap.put(invitationCode, invitation);
        }
        var familyMember = addMemberToFamily(family, member);
        log.info("Member: {} added in family: {}", memberId, invitation.getFamilyId());
        notificationService.sendNotificationFamily("invite-member",
                "%s вступил в семью по коду-приглашению".formatted(member.getUsername()),
                getClass().getName(),
                family,
                member.getId()
        );
        return memberMapper.toDto(familyMember);
    }

    @Transactional
    public ReadFamilyMemberDto createFamily(CreateFamilyDto createFamilyDto) {
        log.debug("Member: {} trying create family", createFamilyDto.getCreatedBy());
        var family = familyMapper.toEntity(createFamilyDto);
        var owner = memberRepository.findById(createFamilyDto.getCreatedBy()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: " + createFamilyDto.getCreatedBy() + " не существует."));
        if (owner.getFamilyMembers().size() > userProperties.getMaxFamilies() && owner.getSuperRole().equals(SuperRole.USER)) {
            log.debug("Member: {} can not create family, because has max families", createFamilyDto.getCreatedBy());
            throw new BusinessException(BAD_REQUEST, "Пользователь не может создать более " + userProperties.getMaxFamilies() + " семей!");
        }
        family = familyRepository.save(family);
        var roles = createBaseRoles(family);
        log.info("Member: {} created family: {}", createFamilyDto.getCreatedBy(), family.getId());
        var ownerFamilyMember = addMemberToFamily(family, owner);
        ownerFamilyMember.getRoles().add(roles.getFirst());
        notificationService.sendNotificationFamily("create-family",
                "Семья была создана",
                getClass().getName(),
                family,
                null);
        return memberMapper.toDto(ownerFamilyMember);
    }

    @Transactional
    public FamilyMember addMemberToFamily(Family family, Member member) {
        log.debug("Adding user: {} to family: {}", member.getId(), family.getId());
        var familyMember = FamilyMember.builder()
                .family(family)
                .member(member)
                .build();
        var role = roleRepository.findByNameAndFamily_Id(familyProperties.getUserRoleName(),
                        family.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Отсутствует основная роль: " + familyProperties.getUserRoleName()));
        familyMember.getRoles().add(role);
        member.getFamilyMembers().add(familyMember);
        family.addMember(familyMember);
        return familyMemberRepository.save(familyMember);
    }


    private List<Role> createBaseRoles(Family family) {
        log.debug("Creating base roles for family: {}", family.getId());
        Role admin = roleRepository.save(Role.builder()
                .family(family)
                .name(familyProperties.getAdminRoleName())
                .accessList(
                        List.of(
                                Access.values()
                        )
                )
                .value(familyProperties.getAdminRoleValue()).build());
        Role member = roleRepository.save(Role.builder()
                .family(family)
                .name(familyProperties.getUserRoleName())
                .value(familyProperties.getUserRoleValue())
                .accessList(
                        List.of(
                                Access.CREATE_TASK,
                                Access.ASSIGN_TASK,
                                Access.SHOW_MEMBERS,
                                Access.GENERATE_INVITE_LINK
                        )
                ).build());
        notificationService.sendNotificationFamily("create-role",
                "Базовые роли %s, %s были созданы".formatted(member.getName(), admin.getName()),
                getClass().getName(),
                family,
                null
        );
        return List.of(admin, member);
    }
}
