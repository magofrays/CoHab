package by.magofrays.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginResponse {
    String token;
    String refreshToken;
    String expiresAt;
}
