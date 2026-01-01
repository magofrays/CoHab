package by.magofrays.dto;

import by.magofrays.entity.Family;
import by.magofrays.validation.Unique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateFamilyDto {
    @NotBlank
    @Unique(entityClass = Family.class, fieldName = "familyName", message = "Это имя семьи уже занято!")
    private String familyName;
    //todo check for number of created families
    private UUID createdBy;
}
