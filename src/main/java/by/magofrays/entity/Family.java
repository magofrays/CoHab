package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Family {
    @Id
    private UUID uuid;
    private String familyName;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Member> members;

    public void addMember(Member member){
        member.setFamily(this);
        members.add(member);
    }
}
