package by.magofrays.dto;

import by.magofrays.validation.InFamily;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@InFamily(memberField = "memberId", familyField = "familyId")
public class CreateInvitation {
    private UUID memberId;
    @NotNull
    private UUID familyId;
    @Min(0)
    @Max(15)
    private Integer numMembers;
    @Future
    @NotNull
    private LocalDateTime expiresAt;
}
