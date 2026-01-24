package by.magofrays.dto;
import by.magofrays.entity.Access;
import by.magofrays.entity.Role;
import by.magofrays.entity.SuperRole;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ReadMemberDto {
    UUID id;
    String username;
    PersonalInfoDto personalInfo;
    SuperRole superRole;
}
