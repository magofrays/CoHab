package by.magofrays.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue
    private UUID id;

    private String username;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
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
