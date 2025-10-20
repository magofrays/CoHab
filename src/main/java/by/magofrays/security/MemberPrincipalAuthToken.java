package by.magofrays.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;


public class MemberPrincipalAuthToken extends AbstractAuthenticationToken {

    private final MemberPrincipal memberPrincipal;

    public MemberPrincipalAuthToken(MemberPrincipal memberPrincipal) {
        super(memberPrincipal.getAuthorities());
        this.memberPrincipal = memberPrincipal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return memberPrincipal;
    }
}
