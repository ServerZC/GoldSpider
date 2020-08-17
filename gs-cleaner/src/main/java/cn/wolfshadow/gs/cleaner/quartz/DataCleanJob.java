package cn.wolfshadow.gs.cleaner.quartz;

import cn.wolfshadow.gs.cleaner.core.DataCleaner;
import cn.wolfshadow.gs.cleaner.service.DbStockValueAnalysisService;
import cn.wolfshadow.gs.common.entity.TaskListEntity;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class DataCleanJob extends QuartzJobBean {

    @Value("${cn.wolfshadow.system.quartz.file-count}")
    private int fileCount = 1;//一次处理的文件数目，默认为1
    @Value("${cn.wolfshadow.system.quartz.html-save-path}")
    private String htmlSavePath;


    @Autowired
    private DbStockValueAnalysisService stockValueAnalysisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //System.out.println("task creating...");
        startJob();
    }

    private void startJob(){
        DataCleaner dataCleaner = DataCleaner.getInstance();
        if (dataCleaner.isWorking()) return;

        dataCleaner.work(htmlSavePath,fileCount);

    }
}
