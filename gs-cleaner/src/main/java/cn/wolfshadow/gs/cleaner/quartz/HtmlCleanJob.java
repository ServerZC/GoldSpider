package cn.wolfshadow.gs.cleaner.quartz;

import cn.wolfshadow.gs.cleaner.core.DataCleaner;
import cn.wolfshadow.gs.cleaner.service.DbStockValueAnalysisService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class HtmlCleanJob extends QuartzJobBean {

    @Value("${cn.wolfshadow.system.quartz.file-batch-count}")
    private int fileBatchCount = 1;//一次处理的文件批数(相同股票代码的为同一批次)，默认为1
    @Value("${cn.wolfshadow.system.quartz.html-save-path}")
    private String htmlSavePath;

    @Autowired
    private DbStockValueAnalysisService stockValueAnalysisService;
    @Autowired
    private DataCleaner dataCleaner;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("HtmlCleanJob task creating...");
        //startJob();
    }

    private void startJob(){
        dataCleaner.cleanHtml(htmlSavePath,fileBatchCount);

    }
}
