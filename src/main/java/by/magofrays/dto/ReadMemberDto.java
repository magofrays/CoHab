package by.magofrays.dto;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class ReadMemberDto {
    UUID uuid;
    String username;
    PersonalInfoDto personalInfoDto;
    FamilyDto familyDto;
    List<AccessDto> accesses;
}
