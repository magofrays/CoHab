package by.magofrays.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Family {
    @Id
    private UUID id;

    private String familyName;

    @OneToMany(mappedBy = "family")
    @Builder.Default
    private List<FamilyMember> members = new ArrayList<>();

    @ManyToOne
    private Member createdBy;

    public void addMember(FamilyMember familyMember){
        familyMember.setAddedAt(LocalDateTime.now());
        members.add(familyMember);
    }
}
