package cn.wolfshadow.gs.manager.quartz;

import lombok.Data;
import lombok.NonNull;
import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
@ConfigurationProperties("cn.wolfshadow.system.quartz")
@Component
@Data
public class TaskCreatorConfig {

    //@NonNull
    private int taskCreateSeconds = 60;//创建任务启动周期（秒），默认60秒，避免未配置参数时报错

    @Bean
    public JobDetail buildJobDetail(){
        return  JobBuilder.newJob(TaskCreator.class).withIdentity("TaskCreator").storeDurably().build();
    }

    @Bean
    public Trigger buildTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(taskCreateSeconds)
                .repeatForever();
        return  TriggerBuilder.newTrigger().forJob(buildJobDetail())
                .withIdentity("TaskCreator")
                .withSchedule(scheduleBuilder)
                .build();
    }



}
