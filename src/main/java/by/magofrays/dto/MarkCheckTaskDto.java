package by.magofrays.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class MarkCheckTaskDto {
    private UUID memberId;
    @NotNull
    @org.hibernate.validator.constraints.UUID
    private UUID taskId;
    private Boolean taskMarked;
    private Boolean taskChecked;
}
