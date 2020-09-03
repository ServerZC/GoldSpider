package cn.wolfshadow.gs.cbm.quartz;

import cn.wolfshadow.gs.cbm.core.WebPageParser;
import cn.wolfshadow.gs.cbm.service.DbWaterLogService;
import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import cn.wolfshadow.gs.common.entity.WaterLog;
import cn.wolfshadow.gs.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
//@Component
public class DailyJob extends QuartzJobBean {

    @Autowired
    private DbWaterLogService waterLogService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        TriggerKey key = trigger.getKey();
        String group = key.getGroup();
        String name = key.getName();
        startJob();
        log.info("ExcelCleanJob task started, trigger group={}, name={}",group,name);
    }

    private void startJob(){
        //当天已经读取过的公告忽略
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        String dateStr = DateUtil.getDateStr(time, DateUtil.YYYY_MM_DD);//当天的日期
        String date = null;//数据库中最新日期

                //从DB获取最新一条数据
        WaterLog newestOne = waterLogService.getNewestOne();
        if (newestOne != null){
            date = newestOne.getDate();
        }

        if (dateStr.equals(date)) return;


        /**
         * @Description
         * 访问列表页面获取公告列表（有反爬虫机制，需要带cookie浏览）
         * 解析最新一条公告
         * 如果发布日期是今天则继续
         * 保存到DB
         * @Author wolfshadow.cn
         * @Date  2020/9/3 17:24
         * @Param []
         * @return void
         */

        List<WaterLog> newNotice = WebPageParser.getNewNotice();
        if (newNotice == null || newNotice.isEmpty()) return;

        String noticeDate = newNotice.get(0).getDate();

        if (!dateStr.equals(noticeDate)) return; //不是今天的公告忽略

        waterLogService.insertBatch(newNotice);

        //TODO 计算


    }
}
