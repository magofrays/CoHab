package by.magofrays.controller;

import by.magofrays.dto.LoginResponse;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.RegistrationDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.security.JwtDecoder;
import by.magofrays.security.JwtIssuer;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtIssuer jwtIssuer;
    private final JwtDecoder jwtDecoder;
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated SmallMemberDto loginMemberDto){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginMemberDto.getUsername(), loginMemberDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        var principal = (MemberPrincipal) auth.getPrincipal();
        var token = jwtIssuer.issue(principal);
        Instant expiresAt = jwtDecoder.decode(token).getExpiresAt().toInstant();
        return LoginResponse.builder()
                .token(token)
                .expiresAt(expiresAt.toString())
                .build();
    }

    @PostMapping("/registration")
    public LoginResponse registration(@RequestBody @Validated RegistrationDto registrationDto){
        var member = memberService.createMember(registrationDto);
        var principal = MemberPrincipal.builder()
                .id(member.getId())
                .username(member.getUsername())
                .accesses(member.getAccesses())
                .build();
        var token = jwtIssuer.issue(principal);
        return LoginResponse.builder().token(token).build();
    }

    @PostMapping("/isAuthenticated")
    public void isAuthenticated(){}



}
