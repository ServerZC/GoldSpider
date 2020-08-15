package cn.wolfshadow.gs.common.config;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

@Data
@ConfigurationProperties("cn.wolfshadow.system.manager")
@Component
@Validated
public class SystemManagerConfig {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String role;

    @Email
    private String email;
}
