package cn.wolfshadow.gs.cbm.quartz;

import lombok.Data;
import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@ConfigurationProperties("cn.wolfshadow.system.quartz")
@Component
@Data
public class JobManager {

    private String cron = "* 22 9 ? * 2-6 *";


    @Bean
    public JobDetail buildDailyJob(){
        return  JobBuilder.newJob(DailyJob.class)
                .withIdentity("DailyJob","cbm")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger buildTrigger(){

        cron = "* 0/10 * ? * 2-6 *";
        cron = "0/30 * * ? * 2-6 *";

        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(cron);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("央妈放水公告查看触发器Trigger","cbm")
                .withSchedule(builder)
                .forJob(buildDailyJob())
                .build();

        return trigger;
    }


}
