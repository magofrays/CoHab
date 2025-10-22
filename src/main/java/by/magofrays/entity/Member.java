package by.magofrays.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private UUID uuid;

    private String username;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private PersonalInfo personalInfo;

    private String password;
    @ManyToOne
    private Family family;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Access> accesses;

    @OneToMany
    private List<Task> createdTasks;

}
