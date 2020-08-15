package cn.wolfshadow.gs.manager.controller;

import cn.wolfshadow.gs.common.entity.StockBaseEntity;
import cn.wolfshadow.gs.manager.service.DbStockBaseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api(value = "API - stock",description = "股票基础信息维护")
@RestController
@RequestMapping("stock")
public class StockMngController {

    @Autowired
    private DbStockBaseService dbStockBaseService;

    @ApiOperation(value = "股票基础信息插入接口", notes = "此接口可新增单个股票基础信息<br/>", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stockCode", value = "股票代码", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stockName", value = "股票名称", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "industryNo", value = "行业编号", required = false,
                    dataType = "string", paramType = "query",defaultValue = "00"),
            @ApiImplicitParam(name = "industryName", value = "行业名称", required = false,
                    dataType = "string", paramType = "query",defaultValue = "未分类")
    })
    @PostMapping("")
    @ResponseBody
    public StockBaseEntity insert(@NotNull(message = "stockCode不能为空") String stockCode,
                                  @NotNull(message = "stockName")String stockName,
                                  String industryNo, String industryName){
        return dbStockBaseService.insert(stockCode,stockName,industryNo,industryName);
    }

    @ApiOperation(value = "股票基础信息插入接口", notes = "此接口可新增单个股票基础信息<br/>", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一ID", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stockCode", value = "股票代码", required = false,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stockName", value = "股票名称", required = false,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "industryNo", value = "行业编号", required = false,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "industryName", value = "行业名称", required = false,
                    dataType = "string", paramType = "query")
    })
    @PutMapping("")
    @ResponseBody
    public Object update(@NotNull(message = "id不能为空")String id,
                                  String stockCode,String stockName,
                                  String industryNo, String industryName){
        return dbStockBaseService.update(id,stockCode,stockName,industryNo,industryName);
    }
}
