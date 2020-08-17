package cn.wolfshadow.gs.cleaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CleanerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CleanerApplication.class);
    }
}
