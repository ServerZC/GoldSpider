package cn.wolfshadow.gs.cleaner.quartz;

import lombok.Data;
import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@ConfigurationProperties("cn.wolfshadow.system.quartz.job-manager")
@Component
@Data
public class JobManager {

    //@NonNull
    private int startCleanSeconds = 60;//间隔时间，默认60秒，避免未配置参数时报错


    @Bean
    public JobDetail buildExcelCleanJob(){
        return  JobBuilder.newJob(ExcelCleanJob.class)
                .withIdentity("ExcelCleanJob","group_clean")
                .storeDurably()
                .build();
    }
    @Bean
    public JobDetail buildHtmlCleanJob(){
        return  JobBuilder.newJob(HtmlCleanJob.class)
                .withIdentity("HtmlCleanJob","group_clean")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger buildExcelCleanTrigger(){
        return  buildTrigger(startCleanSeconds,buildExcelCleanJob(), "ExcleCleanTrigger", "group_clean");
    }
    @Bean
    public Trigger buildHtmlCleanTrigger(){
        return  buildTrigger(startCleanSeconds,buildHtmlCleanJob(), "HtmlCleanTrigger", "group_clean");
    }


    private Trigger buildTrigger(int seconds, JobDetail jobDetail,
                                 String triggerName, String groupName){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(seconds)
                .repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName,groupName)
                .withSchedule(scheduleBuilder)
                .forJob(jobDetail)
                .build();

        return  trigger;
    }


}
