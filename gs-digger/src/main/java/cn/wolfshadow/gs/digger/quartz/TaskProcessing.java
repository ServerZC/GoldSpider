package cn.wolfshadow.gs.digger.quartz;

import cn.wolfshadow.gs.common.entity.TaskListEntity;
import cn.wolfshadow.gs.digger.core.GoldSpider;
import cn.wolfshadow.gs.digger.service.DbTaskListService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class TaskProcessing extends QuartzJobBean {

    @Value("${cn.wolfshadow.system.quartz.task-count}")
    private int taskCount = 1;//获取任务数量，默认为1
    @Value("${cn.wolfshadow.system.quartz.html-save-path}")
    private String htmlSavePath = "./html";


    @Autowired
    private DbTaskListService taskListService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("task creating...");
        startJob();
    }

    private void startJob(){
        //1、获取任务
        List<TaskListEntity> tasks = taskListService.listUndisposed(taskCount);
        if (tasks == null || tasks.isEmpty()) return;

        //2、遍历并爬取网页文件
        GoldSpider spider = GoldSpider.getInstance();
        for (TaskListEntity entity : tasks){
            String url = entity.getUrl();
            if (StringUtils.isEmpty(url)) continue;
            boolean success = spider.crawl(url,htmlSavePath);
            if (!success) continue;
            taskListService.finish(entity);
        }


    }
}
