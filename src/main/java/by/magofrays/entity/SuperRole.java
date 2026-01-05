package by.magofrays.entity;

import org.springframework.security.core.GrantedAuthority;

public enum SuperRole implements GrantedAuthority {
    BAD_USER, // when user is not confirmed
    USER,
    GOD;

    @Override
    public String getAuthority() {
        return name();
    }
}
