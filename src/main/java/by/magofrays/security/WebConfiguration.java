package by.magofrays.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final CorsProperties corsProperties;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration registration = registry.addMapping("/**");
        if (corsProperties.getAllowedOrigins() != null) {
            registration.allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]));
        }
        if (corsProperties.getAllowCredentials() != null) {
            registration.allowCredentials(corsProperties.getAllowCredentials());
        }
        if (corsProperties.getAllowedMethods() != null) {
            registration.allowedMethods(corsProperties.getAllowedMethods().toArray(new String[0]));
        }
        if (corsProperties.getAllowedHeaders() != null) {
            registration.allowedHeaders(corsProperties.getAllowedHeaders().toArray(new String[0]));
        }
        if (corsProperties.getMaxAge() != null) {
            registration.maxAge(corsProperties.getMaxAge());
        }
    }

}