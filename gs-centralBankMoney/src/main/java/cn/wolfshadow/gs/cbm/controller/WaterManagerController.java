package cn.wolfshadow.gs.cbm.controller;

import cn.wolfshadow.gs.cbm.service.DbWaterLogService;
import cn.wolfshadow.gs.common.entity.WaterLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "API - water",description = "放水公告维护")
@RestController
@RequestMapping("water")
public class WaterManagerController {

    @Autowired
    private DbWaterLogService waterLogService;

    @ApiOperation(value = "手动新增央行放水公告数据", notes = "此接口可手动新增单个放水公告<br/>", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "公告标题", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "noticeTime", value = "公告时间(精确时间)", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "water", value = "流入到市场的资金（亿）", required = true,
                    dataType = "int", paramType = "query",defaultValue = "00"),
            @ApiImplicitParam(name = "method", value = "放水方式：-2，正回购；-1，发债；0，逆回购；1，中期借贷便利（MLF）", required = true,
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "timeLimit", value = "期限", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interestRate", value = "中标利率", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "央妈公告正文", required = true,
                    dataType = "string", paramType = "query")
    })
    @PostMapping("")
    @ResponseBody
    public WaterLog insert(String title, String noticeTime, int water,
                           int method, String timeLimit, String interestRate, String content){
        return waterLogService.saveNotice(title,noticeTime,water,method,timeLimit,interestRate,content);
    }


}
