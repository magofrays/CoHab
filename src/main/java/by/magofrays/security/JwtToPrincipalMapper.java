package by.magofrays.security;

import by.magofrays.entity.Access;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtToPrincipalMapper {
    private final ObjectMapper objectMapper;

    public MemberPrincipal convert(DecodedJWT jwt){
        return MemberPrincipal.builder()
                .id(UUID.fromString(jwt.getSubject()))
                .username(jwt.getClaim("username").asString())
                .familyAccesses(extractAuthoritiesFromJwt(jwt)).build();
    }

    private Map<UUID, List<Access>> extractAuthoritiesFromJwt(DecodedJWT jwt) {
        try {
            String familyAccessesJson = jwt.getClaim("familyAccesses").asString();

            if (familyAccessesJson == null || familyAccessesJson.isEmpty()) {
                return Map.of();
            }

            return objectMapper.readValue(familyAccessesJson,
                    new TypeReference<>() {
                    });

        } catch (Exception ex) {
            log.error("Error extracting authorities from JWT: {}", ex.getMessage());
            return Map.of();
        }
    }
}
