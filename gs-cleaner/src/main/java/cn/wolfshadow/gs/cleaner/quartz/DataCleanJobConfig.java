package cn.wolfshadow.gs.cleaner.quartz;

import lombok.Data;
import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@ConfigurationProperties("cn.wolfshadow.system.quartz")
@Component
@Data
public class DataCleanJobConfig {

    //@NonNull
    private int startCleanSeconds = 60;//间隔时间，默认60秒，避免未配置参数时报错

    @Bean
    public JobDetail buildJobDetail(){
        return  JobBuilder.newJob(DataCleanJob.class).withIdentity("DataCleanJob").storeDurably().build();
    }

    @Bean
    public Trigger buildTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(startCleanSeconds)
                .repeatForever();
        return  TriggerBuilder.newTrigger().forJob(buildJobDetail())
                .withIdentity("DataCleanJob")
                .withSchedule(scheduleBuilder)
                .build();
    }



}
