package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private UUID id;
    private String name;
    private Integer value; // to understand hierarchy

    @ManyToOne
    Family family;

    @Builder.Default
    private List<Access> accessList = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    private List<FamilyMember> familyMembers = new ArrayList<>();
}
