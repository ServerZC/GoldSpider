package cn.wolfshadow.gs.cbm.quartz;

import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DailyJob extends QuartzJobBean {

    //假cookie
    private String fakeCookie = "d7734ad36fe55f94811e5b0f59aae6bdd5adb97e87bd7f33a3639776baa2cbe76bbd54b50cdc0cb3f7f20254304b4c1ac0e36fe93adc4e956ec5cebf0110cb30d65a90bdb6767cd656b6122f0c7d7822";
    private String listUrl = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125213/125431/125475/17081/index1.html";
    private String cookieUrl = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125213/125431/125475/17081/index1.html";

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

        //todo 访问列表页面获取公告列表（有反爬虫机制，需要带cookie浏览）

        //获取最新一条公告，如果发布日期是今天则继续

        //解析当日公告页面，保存到DB


    }
}
