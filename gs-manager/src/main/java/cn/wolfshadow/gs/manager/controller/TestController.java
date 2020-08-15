package cn.wolfshadow.gs.manager.controller;

import cn.wolfshadow.gs.common.entity.StockBaseEntity;
import cn.wolfshadow.gs.common.entity.TaskStockEntity;
import cn.wolfshadow.gs.common.entity.TaskUrlEntity;
import cn.wolfshadow.gs.manager.dto.TaskDraftDto;
import cn.wolfshadow.gs.manager.service.DbStockBaseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "API - test",description = "test")
@RestController
public class TestController {

    @Autowired
    private DbStockBaseService dbStockBaseService;

    @ApiOperation(value = "查询车辆接口", notes = "此接口描述xxxxxxxxxxxxx<br/>xxxxxxx<br>值得庆幸的是这儿支持html标签<hr/>", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vno", value = "车牌", required = false,
                    dataType = "string", paramType = "query", defaultValue = "辽A12345"),
            @ApiImplicitParam(name = "page", value = "page", required = false,
                    dataType = "Integer", paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "count", value = "count", required = false,
                    dataType = "Integer", paramType = "query",defaultValue = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @GetMapping("test")
    public String test(){
        StockBaseEntity base = new StockBaseEntity();
        base.setId("5f24099260129e0156e05ed1");
        base.setStockCode("123456");
        base.setStockName("京东方222");
        dbStockBaseService.update(base);
        return "success.";
    }

    @GetMapping("json")
    public TaskDraftDto getJson(){
        TaskDraftDto dto = new TaskDraftDto();
        TaskUrlEntity urlEntity = new TaskUrlEntity();
        urlEntity.setUrl("http://basic.10jqka.com.cn/%s/finance.html");
        List<TaskStockEntity> list = new ArrayList<>();
        TaskStockEntity entity1 = new TaskStockEntity();
        TaskStockEntity entity2 = new TaskStockEntity();
        entity1.setStockCode("000001");
        entity2.setStockCode("000002");
        entity1.setStockName("东方财富");
        entity2.setStockName("同花顺");
        list.add(entity1);
        list.add(entity2);
        dto.setTaskUrl(urlEntity);
        dto.setTaskStocks(list);
        return dto;
    }
}
