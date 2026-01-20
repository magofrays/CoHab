package by.magofrays.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ReadFamilyMemberDto {
    UUID id;
    String username;
    PersonalInfoDto personalInfoDto;
    ReadFamilyDto familyDto;
    List<RoleDto> roleDtos;
    private LocalDateTime addedAt;
}
