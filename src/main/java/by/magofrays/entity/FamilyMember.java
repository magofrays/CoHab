package by.magofrays.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMember {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Family family;

    @Builder.Default
    @ManyToMany
    private List<Role> roles = new ArrayList<>();
    private LocalDateTime addedAt;
}
