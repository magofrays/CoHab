package by.magofrays.dto.response;

import by.magofrays.entity.SuperRole;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReadMemberDto(
        UUID id,
        String username,
        PersonalInfoDto personalInfo,
        SuperRole superRole
) {
}
