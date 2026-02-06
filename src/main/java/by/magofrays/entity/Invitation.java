package by.magofrays.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Invitation {
    private String invitationCode;
    private UUID familyId;
    private Integer numMembers; // сколько человек приглашаем
    private LocalDateTime expiresAt; // сколько живем
}
