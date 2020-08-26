package cn.wolfshadow.gs.cleaner.quartz;

import cn.wolfshadow.gs.cleaner.core.DataCleaner;
import cn.wolfshadow.gs.cleaner.service.DbStockValueAnalysisService;
import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import cn.wolfshadow.gs.common.entity.TaskListEntity;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class ExcelCleanJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(ExcelCleanJob.class);

    @Autowired
    private DataCleaner dataCleaner;
    @Autowired
    private DbStockValueAnalysisService stockValueAnalysisService;

    @Value("${cn.wolfshadow.system.quartz.file-batch-count}")
    private int fileBatchCount = 1;//一次处理的文件批数(相同股票代码的为同一批次)，默认为1
    @Value("${cn.wolfshadow.system.quartz.excel-save-path}")
    private String excelSavePath;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        TriggerKey key = trigger.getKey();
        String group = key.getGroup();
        String name = key.getName();
        startJob();
        logger.info("ExcelCleanJob task started, trigger group={}, name={}",group,name);
    }

    private void startJob(){
        //解析数据
        List<StockValueAnalysisEntity> list = dataCleaner.cleanExcel(excelSavePath, fileBatchCount);
        if (list == null || list.isEmpty()) return;
        //保存数据
        int i = stockValueAnalysisService.insertBatch(list);
        if (i > 0){
            //删除文件
            for(StockValueAnalysisEntity entity : list){
                String stockCode = entity.getStockCode();
                dataCleaner.removeFile(stockCode);
            }
        }
    }
}
