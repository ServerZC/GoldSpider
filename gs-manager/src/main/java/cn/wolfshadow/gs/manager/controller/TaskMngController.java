package cn.wolfshadow.gs.manager.controller;

import cn.wolfshadow.gs.common.entity.StockBaseEntity;
import cn.wolfshadow.gs.manager.service.DbStockBaseService;
import cn.wolfshadow.gs.manager.service.DbTaskUrlService;
import cn.wolfshadow.gs.manager.service.TaskMngService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api(value = "API - task",description = "任务管理")
@RestController
@RequestMapping("task")
public class TaskMngController {

    @Autowired
    private TaskMngService taskMngService;

    @ApiOperation(value = "创建任务草稿", notes = "任务草稿包括不完整的url地址和股票列表，真实的任务由定时任务来组装", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskDraft", value = "任务草稿", required = true,
                    dataType = "string", paramType = "query")
    })
    @PostMapping("draft")
    @ResponseBody
    public boolean draft(@NotNull(message = "taskDraft不能为空") String taskDraft){
        return taskMngService.insert(taskDraft);
    }

    @ApiOperation(value = "添加任务股票信息", notes = "此接口可新增单个股票基础信息到草稿任务", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "urlId", value = "任务URL对应ID", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stockCode", value = "股票代码", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "stockName", value = "股票名称", required = false,
                    dataType = "string", paramType = "query")
    })
    @PostMapping("stock")
    @ResponseBody
    public boolean stock(@NotNull(message = "urlId不能为空")String urlId,
                        @NotNull(message = "stockCode不能为空")String stockCode,
                                  String stockName){

        return taskMngService.addStock2Draft(urlId,stockCode,stockName);
    }

    @ApiOperation(value = "激活任务", notes = "激活任务不允许再修改，激活后等待其他进程来处理。", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "urlId", value = "任务URL对应ID", required = true,
                    dataType = "string", paramType = "query")
    })
    @PutMapping("active")
    @ResponseBody
    public boolean active(@NotNull(message = "urlId不能为空") String urlId){
        return taskMngService.active(urlId);
    }
}
