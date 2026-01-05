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

    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "issuedTo")
    private List<Task> issuedTasks;

    public Map<UUID, List<Access>> getFamilyAccesses() {
        Map<UUID, List<Access>> accesses = new HashMap<>();

        for (FamilyMember familyMember : familyMembers) {
            if (familyMember.getFamily() != null && familyMember.getRoles() != null) {
                UUID familyId = familyMember.getFamily().getId();
                List<Access> roleAccesses = familyMember.getRoles().stream().map(Role::getAccessList).flatMap(List::stream).toList();
                if (!roleAccesses.isEmpty()) {
                    accesses.put(familyId, roleAccesses);
                }
            }
        }
        return accesses;
    }
}
