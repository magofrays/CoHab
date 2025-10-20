package by.magofrays.security;

import by.magofrays.dto.AccessDto;
import by.magofrays.security.MemberPrincipal;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JwtToPrincipalMapper {
    public MemberPrincipal convert(DecodedJWT jwt){
        return MemberPrincipal.builder()
                .id(UUID.fromString(jwt.getSubject()))
                .username(jwt.getClaim("u").asString())
                .accesses(extractAuthoritiesFromJwt(jwt)).build();
    }

    private List<AccessDto> extractAuthoritiesFromJwt(DecodedJWT jwt){
        var claim = jwt.getClaim("a");
        if(claim.isNull() || claim.isMissing()){
            return List.of();
        }
        return claim.asList(AccessDto.class);
    }
}
