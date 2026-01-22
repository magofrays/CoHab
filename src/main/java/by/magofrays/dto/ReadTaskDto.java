package by.magofrays.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class ReadTaskDto {
    UUID taskId;
    String taskName;
    String description;
    UUID createdBy;
    UUID issuedTo;
    LocalDate createdDate;
    LocalDate dueDate;
}
