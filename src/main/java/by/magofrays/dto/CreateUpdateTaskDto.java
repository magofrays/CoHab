package by.magofrays.dto;

import by.magofrays.validation.InFamily;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    UUID familyId;
    UUID issuedTo;
    @Future
    LocalDate dueDate;
}
