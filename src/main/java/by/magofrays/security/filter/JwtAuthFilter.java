package by.magofrays.security.filter;

import by.magofrays.security.JwtDecoder;
import by.magofrays.security.JwtToPrincipalMapper;
import by.magofrays.security.MemberPrincipalAuthToken;
import by.magofrays.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalMapper jwtToPrincipalMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("=== Before JWT filter ===");
        log.debug("Current auth: {}", SecurityContextHolder.getContext().getAuthentication());

        var memberOptional = extractTokenFromRequest(request);
        if(memberOptional.isPresent()){

            memberOptional.map(jwtDecoder::decode) // ты меня заебал уже блять
                    .map(jwtToPrincipalMapper::convert)
                    .map(MemberPrincipalAuthToken::new)
                    .ifPresent(auth -> {
                        log.debug("Setting auth: {}", auth.getName());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
            log.debug("=== After filter chain ===");
            log.debug("Current auth: {}", SecurityContextHolder.getContext().getAuthentication());
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        var token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}