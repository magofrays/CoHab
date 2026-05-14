package by.magofrays.dto.request;

import jakarta.validation.constraints.NotBlank;


public record InvitationRequest (
    @NotBlank
    String code
){}
