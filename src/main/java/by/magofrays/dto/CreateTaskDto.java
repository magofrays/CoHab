package by.magofrays.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateTaskDto {
    @NotBlank
    String taskName;
    String description;
    UUID createdBy;
    //todo check same family
    UUID issuedTo;
    //todo check time
    LocalDate dueDate;
}
