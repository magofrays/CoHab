package by.magofrays.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateUpdateTaskDto {
    UUID taskId;
    @NotBlank
    String taskName;
    String description;
    UUID createdBy;
    String familyName;
    //todo check same family
    UUID issuedTo;
    //todo check time
    LocalDate dueDate;
}
