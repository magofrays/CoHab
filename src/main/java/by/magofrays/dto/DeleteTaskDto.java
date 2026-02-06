package by.magofrays.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DeleteTaskDto {
    @NotNull
    private UUID taskId;
    @NotNull
    private UUID familyId;
}
