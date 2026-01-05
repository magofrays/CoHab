package by.magofrays.mapper;

import by.magofrays.dto.*;
import by.magofrays.entity.FamilyMember;
import by.magofrays.entity.Member;
import by.magofrays.entity.PersonalInfo;
import by.magofrays.entity.Role;
import by.magofrays.security.MemberPrincipal;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Mapper(componentModel = "spring", uses = {PersonalInfoMapper.class, FamilyMapper.class})
public abstract class MemberMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected PersonalInfoMapper personalInfoMapper;

    @Mapping(target = "personalInfoDto", ignore = true)
    @Mapping(target = "familyDto", source = "family")
    @Mapping(target = "accessList", ignore = true)
    public abstract ReadFamilyMemberDto toDto(FamilyMember member);

    @Mapping(target = "personalInfoDto", source = "personalInfo")
    public abstract ReadMemberDto toDto(Member member);

    @Mapping(target = "personalInfoDto", ignore = true)
    public abstract ReadMemberDto memberDto(MemberPrincipal principal);

    @Mapping(target = "personalInfo", expression = "java(mapPersonalInfo(registrationDto))")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registrationDto.getPassword()))")
    public abstract Member forCreate(RegistrationDto registrationDto);

    protected PersonalInfo mapPersonalInfo(RegistrationDto registrationDto) {
        return PersonalInfo.builder()
                .firstname(registrationDto.getFirstname())
                .lastname(registrationDto.getLastname())
                .birthDate(registrationDto.getBirthDate())
                .build();
    }

    @AfterMapping
    protected void convertRolesToAccesses(@MappingTarget ReadFamilyMemberDto familyMemberDto, @Context FamilyMember familyMember){
        familyMemberDto.setAccessList(familyMember.getRoles().stream().map(Role::getAccessList).flatMap(List::stream).toList());
        familyMemberDto.setPersonalInfoDto(personalInfoMapper.toDto(familyMember.getMember().getPersonalInfo()));
    }


    @AfterMapping
    protected void generateUuids(@MappingTarget Member member) {
        member.setId(UUID.randomUUID());
        if (member.getPersonalInfo() != null) {
            member.getPersonalInfo().setId(UUID.randomUUID());
        }
    }
}