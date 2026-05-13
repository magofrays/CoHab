package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Family family;

    @Builder.Default
    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "issuedTo")
    private List<Task> issuedTasks = new ArrayList<>();

    private LocalDateTime addedAt;
}
