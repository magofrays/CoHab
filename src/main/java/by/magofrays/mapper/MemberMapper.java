package by.magofrays.mapper;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.entity.Member;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Mapper(componentModel = "spring", uses = {PersonalInfoMapper.class, FamilyMapper.class})
public abstract class MemberMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "personalInfoDto", source = "personalInfo")
    @Mapping(target = "familyDto", source = "family")
    public abstract ReadMemberDto toDto(Member member);

    @Mapping(target = "personalInfo", source = "personalInfoDto")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    public abstract Member forCreate(SmallMemberDto createMemberDto, PersonalInfoDto personalInfoDto);

    @AfterMapping
    protected void generateUuids(@MappingTarget Member member) {
        member.setUuid(UUID.randomUUID());
        if (member.getPersonalInfo() != null) {
            member.getPersonalInfo().setUuid(UUID.randomUUID());
        }
    }

    @AfterMapping
    protected void createHashPassword(@MappingTarget Member member, SmallMemberDto createMemberDto) {
        member.setPassword(passwordEncoder.encode(createMemberDto.getPassword()));
    }
}