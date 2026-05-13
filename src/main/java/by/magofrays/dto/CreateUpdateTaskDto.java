package by.magofrays.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.sql.Update;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateUpdateTaskDto {
    @NotNull(groups = {Update.class})
    UUID taskId;
    @NotBlank(message = "Название задачи должно быть не пустое!")
    String taskName;
    String description;
    UUID createdBy;
    @NotNull
    UUID familyId;
    UUID issuedTo;
    @Future(message = "Дедлайн должен быть дальше текущего времени!")
    LocalDateTime dueDate;
}
