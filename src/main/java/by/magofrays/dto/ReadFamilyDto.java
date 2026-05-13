package by.magofrays.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class ReadFamilyDto {
    UUID id;
    String familyName;
    UUID createdBy;
}
