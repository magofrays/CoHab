package by.magofrays.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateFamilyDto {
    @NotBlank
    private String familyName;
    private UUID createdBy;
}
