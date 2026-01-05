package by.magofrays.dto;

import by.magofrays.entity.Access;
import by.magofrays.entity.Role;
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
    List<Role> roles;
    List<Access> accessList;
    private LocalDateTime addedAt;
}
