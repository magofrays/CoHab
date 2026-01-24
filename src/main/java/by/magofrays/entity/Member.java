package by.magofrays.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private UUID id;

    private String username;

    @OneToOne(fetch = FetchType.LAZY)
    private PersonalInfo personalInfo;

    private String password;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<FamilyMember> familyMembers = new ArrayList<>();

    private SuperRole superRole;

    @OneToMany(mappedBy = "createdBy")
    @Builder.Default
    private List<Family> createdFamilies = new ArrayList<>();
}
