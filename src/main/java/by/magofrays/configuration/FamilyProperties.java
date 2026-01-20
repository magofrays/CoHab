package by.magofrays.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("family")
public class FamilyProperties {
    private String adminRoleName;
    private Integer adminRoleValue;
    private String userRoleName;
    private Integer userRoleValue;
}
