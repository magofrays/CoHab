package by.magofrays.security;

import by.magofrays.security.filter.JwtAuthFilter;
import by.magofrays.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthService authService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity security) {
        security.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(session ->  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .securityMatcher("/**");
        return security.build();
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(HttpSecurity security){
        AuthenticationManagerBuilder builder = security.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(authService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}
