package by.magofrays.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class InvitationRequest {
    @NotBlank
    String code;
}
