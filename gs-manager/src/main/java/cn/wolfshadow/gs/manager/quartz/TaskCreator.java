package cn.wolfshadow.gs.manager.quartz;

import cn.wolfshadow.gs.common.entity.TaskListEntity;
import cn.wolfshadow.gs.manager.service.DbTaskListService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class TaskCreator extends QuartzJobBean {

    @Autowired
    private DbTaskListService taskListService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("task creating...");
        taskListService.creatJob();
    }
}
