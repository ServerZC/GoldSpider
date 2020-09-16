package cn.wolfshadow.gs.cbm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aliyun.sms")
public class SmsConfig {
    private String regionId;
    private String accessKeyId;
    private String accessSecret;
    private String sysDomain;
    private String sysVersion;
    private String singName;
    private String templateCodeInjection;
    private String templateCodeWeepage;
    private String templateCodeTotal;


}
