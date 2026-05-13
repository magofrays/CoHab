package by.magofrays.mapper;

import by.magofrays.dto.ReadFamilyMemberDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.RegistrationDto;
import by.magofrays.dto.SmallFamilyMemberDto;
import by.magofrays.entity.FamilyMember;
import by.magofrays.entity.Member;
import by.magofrays.entity.PersonalInfo;
import by.magofrays.security.MemberPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {PersonalInfoMapper.class, FamilyMapper.class, RoleMapper.class})
public abstract class MemberMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "username", source = "member.username")
    @Mapping(target = "personalInfo", source = "member.personalInfo")
    public abstract ReadFamilyMemberDto toDto(FamilyMember familyMember);

    @Mapping(target = "username", source = "member.username")
    @Mapping(target = "personalInfo", source = "member.personalInfo")
    public abstract SmallFamilyMemberDto toSmallDto(FamilyMember familyMember);

    @Mapping(target = "personalInfo", source = "personalInfo")
    public abstract ReadMemberDto toDto(Member member);

    @Mapping(target = "personalInfo", ignore = true)
    public abstract ReadMemberDto memberDto(MemberPrincipal principal);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registrationDto.getPassword()))")
    public abstract Member toEntity(RegistrationDto registrationDto);

    public PersonalInfo mapPersonalInfo(RegistrationDto registrationDto) {
        return PersonalInfo.builder()
                .firstname(registrationDto.getFirstname())
                .lastname(registrationDto.getLastname())
                .birthDate(registrationDto.getBirthDate())
                .build();
    }
}