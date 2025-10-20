package by.magofrays.security;

import by.magofrays.dto.AccessDto;
import by.magofrays.dto.ReadMemberDto;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import com.auth0.jwt.JWT;
@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(MemberPrincipal principal){
        return JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("n", principal.getUsername())
                .withClaim("a", principal.getAuthorities())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}
