package by.magofrays.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateInvitationRequest(
    @NotNull
    UUID familyId,
    @Min(0)
    @Max(15)
    Integer numMembers,
    @Future
    @NotNull
    LocalDateTime expiresAt
){}
