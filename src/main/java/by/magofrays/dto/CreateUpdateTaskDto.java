package by.magofrays.dto;

import by.magofrays.validation.InFamily;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@InFamily(memberField = "issuedTo", familyField = "familyId", message = "Пользователь не существует, либо назначенный пользователь не состоит в этой семье")
public class CreateUpdateTaskDto {
    UUID taskId;
    @NotBlank
    String taskName;
    String description;
    UUID createdBy;
    @NotNull
    @org.hibernate.validator.constraints.UUID
    UUID familyId;
    //todo check same family
    UUID issuedTo;
    //todo check time
    LocalDate dueDate;
}
