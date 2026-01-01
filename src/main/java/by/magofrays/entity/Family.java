package by.magofrays.entity;

import jakarta.persistence.*;
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
public class Family {
    @Id
    private UUID id;
    private String familyName;
    @ManyToMany
    @JoinTable(
            name = "family_members",
            joinColumns = @JoinColumn(name = "family_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    @OneToOne
    private Member createdBy;

    public void addMember(Member member){
        member.getFamilies().add(this);
        members.add(member);
    }
}
