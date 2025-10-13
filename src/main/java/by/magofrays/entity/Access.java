package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;

@Entity
public class Access implements GrantedAuthority {
    @Id
    private UUID uuid;
    private String name;
    @ManyToMany(mappedBy = "accesses")
    private List<Member> memberList;

    @Override
    public String getAuthority() {
        return name;
    }
}
