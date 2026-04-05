package by.magofrays.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Notification {
    private String from;
    private UUID recipient;
    private String message;
    @Builder.Default
    private Instant createdAt = Instant.now();
}
