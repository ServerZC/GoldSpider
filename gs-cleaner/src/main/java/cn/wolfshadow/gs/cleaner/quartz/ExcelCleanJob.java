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
public class ExcelCleanJob extends QuartzJobBean {

    @Autowired
    private DataCleaner dataCleaner;

    @Value("${cn.wolfshadow.system.quartz.file-batch-count}")
    private int fileBatchCount = 1;//一次处理的文件批数(相同股票代码的为同一批次)，默认为1
    @Value("${cn.wolfshadow.system.quartz.excel-save-path}")
    private String excelSavePath;


    @Autowired
    private DbStockValueAnalysisService stockValueAnalysisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //System.out.println("ExcelCleanJob task creating...");
        startJob();
    }

    private void startJob(){
        dataCleaner.cleanExcel(excelSavePath,fileBatchCount);
    }
}
