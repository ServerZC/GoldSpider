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

@Api(value = "API - StockAnalyse",description = "股票数据分析")
@RestController
@RequestMapping("stock")
public class StockAnalyseController {

    @Autowired
    private DbStockValueAnalysisService stockValueAnalysisService;


    @ApiOperation(value = "根据既定的策略获取股票数据", notes = "", response = String.class)
    @PostMapping("analyse")
    @ResponseBody
    public List<StockValueAnalysisEntity> analyse(){
        //解析数据
        List<StockValueAnalysisEntity> list = stockValueAnalysisService.listHighQuality();
        return list;
    }
}
