package by.magofrays.mapper;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.RegistrationDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.entity.Member;
import by.magofrays.entity.PersonalInfo;
import by.magofrays.security.MemberPrincipal;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Mapper(componentModel = "spring", uses = {PersonalInfoMapper.class, FamilyMapper.class, AccessMapper.class})
public abstract class MemberMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "personalInfoDto", source = "personalInfo")
    @Mapping(target = "familyDtos", source = "families")
    @Mapping(target = "accesses", source = "accesses")
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
    protected void generateUuids(@MappingTarget Member member) {
        member.setId(UUID.randomUUID());
        if (member.getPersonalInfo() != null) {
            member.getPersonalInfo().setId(UUID.randomUUID());
        }
    }
}