package by.magofrays.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MarkCheckTaskRequest(
        @NotNull UUID taskId,
        Boolean taskMarked,
        Boolean taskChecked) {
}
