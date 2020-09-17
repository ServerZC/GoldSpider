package cn.wolfshadow.gs.cbm.quartz;

import cn.wolfshadow.gs.cbm.core.WebPageParser;
import cn.wolfshadow.gs.cbm.service.DbWaterLogService;
import cn.wolfshadow.gs.cbm.service.SmsService;
import cn.wolfshadow.gs.common.entity.WaterLog;
import cn.wolfshadow.gs.common.util.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
//@Component
public class DailyJob extends QuartzJobBean {

    @Autowired
    private DbWaterLogService waterLogService;
    @Autowired
    private SmsService smsService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        TriggerKey key = trigger.getKey();
        String group = key.getGroup();
        String name = key.getName();
        startJob();
        log.info("ExcelCleanJob task started, trigger group={}, name={}",group,name);
    }

    @SneakyThrows
    private void startJob(){
        //当天已经读取过的公告忽略
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        String dateStr = DateUtil.getDateStr(time, DateUtil.YYYY_MM_DD);//当天的日期
        long now = time.getTime();
        String date = null;//数据库中最新日期

        //从DB获取最新一条数据
        WaterLog newestOne = waterLogService.getNewestOne();
        if (newestOne != null){
            date = newestOne.getNoticeDate();
        }

        if (dateStr.equals(date)) return;
        Long lastTimeInDb = newestOne.getNoticeTime();



        /**
         * @Description
         * 访问列表页面获取公告列表（有反爬虫机制，需要带cookie浏览）
         * 解析最新一页的所有公告
         * 如果发布日期大于数据库中最新时间则继续
         * 保存到DB
         */
        List<WaterLog> newNotice = WebPageParser.getNewNotice(lastTimeInDb);
        if (newNotice != null && !newNotice.isEmpty()) waterLogService.insertBatch(newNotice);

        //String noticeDate = newNotice.get(0).getNoticeDate();
        //if (!dateStr.equals(noticeDate)) return; //不是今天的公告忽略

        //获取当天央行公告
        List<WaterLog> todayInjection = waterLogService.listTodayNotice(dateStr);

        //获取当天到期的公告
        List<WaterLog> expireNotice = waterLogService.listTodayExpireNotice(dateStr);

        //综合计算当日市场上的资金是减少还是增加
        AtomicInteger todayWater = new AtomicInteger();
        todayInjection.stream().forEach(waterLog -> {
            int method = waterLog.getMethod();
            int water = waterLog.getWater();
            //放水方式：-2，正回购；-1，发债；0，逆回购；1，中期借贷便利（MLF）
            if (method == -2 || method == -1){
                todayWater.addAndGet(-water);
            }else if (method == 0 || method == 1){
                todayWater.addAndGet(water);
            }
        });
        expireNotice.stream().forEach(waterLog -> {
            int method = waterLog.getMethod();
            int water = waterLog.getWater();
            //放水方式：-2，正回购；-1，发债；0，逆回购；1，中期借贷便利（MLF）
            if (method == -2 || method == -1){
                todayWater.addAndGet(water);
            }else if (method == 0 || method == 1){
                todayWater.addAndGet(-water);
            }
        });

        int value = todayWater.get();
        if (value > 0){
            //市场资金增加
            smsService.sendMessageInjection(value);
        }else if (value < 0){
            //市场资金减少
            smsService.sendMessageWeepage(-value);
        }

        // 计算市场上的总资金
        int total = waterLogService.sumWaterNow(now);
        //log.info("水量：{}",total);
        //发短信：1分钟内短信发送条数不超过1条（阿里云个人用户被限制）
        Thread.sleep(70 * 1000);
        smsService.sendMessageTotal(total);


    }


}
