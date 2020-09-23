package cn.wolfshadow.gs.cleaner.cotroller;

import cn.wolfshadow.gs.cleaner.core.DataCleaner;
import cn.wolfshadow.gs.cleaner.service.DbStockValueAnalysisService;
import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "API - excel",description = "excel文件解析等功能")
@RestController
@RequestMapping("excel")
public class ExcelCleanController {

    @Value("${cn.wolfshadow.system.quartz.excel-save-path}")
    private String excelSavePath;

    @Autowired
    private DataCleaner dataCleaner;
    @Autowired
    private DbStockValueAnalysisService stockValueAnalysisService;


    @ApiOperation(value = "解析指定目录下的excel文件，保存有价值的数据到DB", notes = "", response = String.class)
    @PostMapping("clean")
    @ResponseBody
    public void clean(){
        //解析数据
        List<StockValueAnalysisEntity> list = dataCleaner.cleanExcel(excelSavePath);
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
