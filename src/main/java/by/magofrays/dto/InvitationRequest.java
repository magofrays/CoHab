package by.magofrays.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class InvitationRequest {
    @NotBlank
    String code;
}
