package by.magofrays.service;
import by.magofrays.dto.*;
import by.magofrays.entity.FamilyMember;
import by.magofrays.entity.Member;
import by.magofrays.entity.PersonalInfo;
import by.magofrays.entity.SuperRole;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.FamilyMapper;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final FamilyMapper familyMapper;


    public ReadMemberDto createMember(RegistrationDto registrationDto){
        Member member = memberMapper.forCreate(registrationDto);
        member.setSuperRole(SuperRole.BAD_USER);
        personalInfoRepository.save(member.getPersonalInfo());
        memberRepository.save(member);
        return memberMapper.toDto(member);
    }

    public List<ReadFamilyDto> findMemberFamilies(UUID memberId){
        var member = memberRepository
                .findById(memberId).orElseThrow(() ->  new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ memberId +" не существует."));
        return member.getFamilyMembers().stream().map(FamilyMember::getFamily).map(familyMapper::toDto).toList();
    }

    public boolean memberHasFamily(UUID memberID){
        return !memberRepository
                .findById(memberID)
                .map(Member::getFamilyMembers).map(List::isEmpty)
                .orElseThrow(() ->  new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ memberID +" не существует."));
    }

    public Optional<ReadMemberDto> findByUsername(String username){
        return memberRepository.findByUsername(username).map(memberMapper::toDto);
    }


}
