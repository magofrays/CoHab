package by.magofrays.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private UUID id;

    private String username;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private PersonalInfo personalInfo;

    private String password;

    @ManyToMany(mappedBy = "members")
    @Builder.Default
    private List<Family> families = new ArrayList<>();;

    @OneToOne(mappedBy = "createdBy")
    private Family createdFamily;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_accesses",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id")
    )
    @Builder.Default
    private List<Access> accesses = new ArrayList<>();;

    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "issuedTo")
    private List<Task> issuedTasks;
}
