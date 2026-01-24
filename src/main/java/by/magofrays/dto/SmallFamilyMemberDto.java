package by.magofrays.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SmallFamilyMemberDto { // when we know what family and know roles(for example for taskLoading)
    UUID id; // id from FamilyMember
    String username;
    PersonalInfoDto personalInfo;
    private LocalDateTime addedAt;
}
