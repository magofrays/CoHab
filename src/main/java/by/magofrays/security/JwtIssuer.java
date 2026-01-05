package by.magofrays.security;


import by.magofrays.dto.ReadMemberDto;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private final ObjectMapper objectMapper;
    private final JwtProperties properties;

    @SneakyThrows
    public String issue(MemberPrincipal principal){
        var accesses = principal.getFamilyAccesses();
        var familyAccessesJson = accesses == null ? null : objectMapper.writeValueAsString(accesses);
        return JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(Duration.of(properties.getExpiresAt(), ChronoUnit.SECONDS)))
                .withClaim("username", principal.getUsername())
                .withClaim("superRole", principal.getAuthorities())
                .withClaim("familyAccesses", familyAccessesJson)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }


}
