package by.magofrays.dto;

import lombok.Data;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Value
@Data
public class AccessDto implements GrantedAuthority {
    UUID uuid;
    String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
