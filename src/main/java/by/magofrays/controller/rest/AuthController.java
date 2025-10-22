package by.magofrays.controller.rest;

import by.magofrays.dto.LoginResponse;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.security.JwtIssuer;
import by.magofrays.security.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated SmallMemberDto loginMemberDto){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginMemberDto.getUsername(), loginMemberDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        var principal = (MemberPrincipal) auth.getPrincipal();
        var token = jwtIssuer.issue(principal);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    @PostMapping("/registration") // TODO
    public LoginResponse registration(@RequestBody @Validated SmallMemberDto loginMemberDto){
        return LoginResponse.builder().build();
    }
}
