package by.magofrays.dto;

import by.magofrays.entity.Family;
import by.magofrays.validation.Unique;
import lombok.Value;

import java.util.UUID;

@Value
public class ReadFamilyDto {
    UUID id;
    String familyName;
    UUID createdBy;
}
