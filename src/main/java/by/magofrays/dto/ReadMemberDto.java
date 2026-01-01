package by.magofrays.dto;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class ReadMemberDto {
    UUID id;
    String username;
    PersonalInfoDto personalInfoDto;
    List<ReadFamilyDto> familyDtos;
    List<AccessDto> accesses;
}
