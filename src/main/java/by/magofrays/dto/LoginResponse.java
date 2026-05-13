package by.magofrays.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    String token;
    String refreshToken;
    String expiresAt;
}
