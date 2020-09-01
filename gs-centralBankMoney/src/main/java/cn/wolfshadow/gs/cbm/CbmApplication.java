package cn.wolfshadow.gs.cbm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CbmApplication {
    public static void main(String[] args) {
        SpringApplication.run(CbmApplication.class);
    }
}
