package by.magofrays.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("user")
@Data
public class UserProperties {
    private Integer maxFamilies;
}
