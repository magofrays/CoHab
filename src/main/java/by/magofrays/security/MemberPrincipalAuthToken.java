package by.magofrays.security;
import org.springframework.security.authentication.AbstractAuthenticationToken;


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
