package cn.wolfshadow.gs.cleaner.core;

import cn.wolfshadow.gs.cleaner.service.DbStockValueAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

@Component
public class FileDataCleaner {

    @Value("${cn.wolfshadow.system.quartz.file-batch-count}")
    private int fileBatchCount = 1;//一次处理的文件批数(相同股票代码的为同一批次)，默认为1
    @Value("${cn.wolfshadow.system.quartz.html-save-path}")
    private String htmlSavePath;
    @Value("${cn.wolfshadow.system.quartz.excel-save-path}")
    private String excelSavePath;

    @Autowired
    private DbStockValueAnalysisService stockValueAnalysisService;


    public boolean cleanExcel(){
        if (StringUtils.isEmpty(excelSavePath)) return false;
        if (fileBatchCount < 1) fileBatchCount = 1;
        File file = new File(excelSavePath);
        if (!file.exists()) return false;
        String[] names = file.list();
        return false;
    }

}
