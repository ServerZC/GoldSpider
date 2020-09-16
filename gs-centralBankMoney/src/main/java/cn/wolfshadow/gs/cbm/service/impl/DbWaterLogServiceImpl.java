package cn.wolfshadow.gs.cbm.service.impl;

import cn.wolfshadow.gs.cbm.service.DbWaterLogService;
import cn.wolfshadow.gs.common.entity.WaterLog;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.common.util.DateUtil;
import cn.wolfshadow.gs.common.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * 股票价值分析实现类
 */
@Service
@Slf4j
public class DbWaterLogServiceImpl extends MongoDbOperator<WaterLog> implements DbWaterLogService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public WaterLog insert(WaterLog data) {
        try {
            setMd5(data);
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }

    @Override
    @Transactional
    public int insertBatch(List<WaterLog> taskStocks){
        try {
            setMd5(taskStocks);
            return super.insertBatch(taskStocks,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  0;
        }
    }

    @Override
    public WaterLog saveNotice(String title, String noticeTime, int water, int method, String timeLimit, String interestRate, String content) {
        WaterLog waterLog = new WaterLog();
        waterLog.setTitle(title);
        Date date = DateUtil.toDate(noticeTime);
        waterLog.setNoticeTime(date.getTime());
        waterLog.setNoticeDate(DateUtil.getDateStr(date,DateUtil.YYYY_MM_DD));
        waterLog.setMethod(method);
        waterLog.setContent(content);
        waterLog.setWater(water);
        waterLog.setInterestRate(interestRate);

        parseCoreData(waterLog,date,timeLimit);

        insert(waterLog);

        return waterLog;
    }

    @Override
    public WaterLog getNewestOne() {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("noticeTime")));
        query.limit(1);
        List<WaterLog> list = mongoTemplate.find(query, WaterLog.class);
        if (list.isEmpty()) return new WaterLog();
        return list.get(0);
    }

    @Override
    public int sumWaterNow(long now) {
        Query query = new Query(Criteria.where("endTime").gt(now));
        List<WaterLog> waterLogs = mongoTemplate.find(query, WaterLog.class);
        if (waterLogs == null || waterLogs.isEmpty()) return 0;
        int total = 0;
        for(WaterLog waterLog : waterLogs){
            int water = waterLog.getWater();
            int method = waterLog.getMethod();
            //放水方式：-2，正回购；-1，发债；0，逆回购；1，中期借贷便利（MLF）
            if (method == 0 || method == 1){
                total += water;
            }else{
                total -= water;
            }
        }
        return total;
    }

    @Override
    public boolean update(WaterLog data) {
        try {
            return super.update(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  false;
        }
    }

    @Override
    public boolean delete(WaterLog data) {
        return false;
    }

    @Override
    public WaterLog get(String pk) {
        return null;
    }

    @Override
    public List<WaterLog> list(WaterLog condition) {
        return null;
    }


    /**
     * @Description 获取当天央行公告
     * @Author wolfshadow.cn
     * @Date  2020/9/16 16:24
     * @Param [dateStr]
     * @return java.util.List<cn.wolfshadow.gs.common.entity.WaterLog>
     */

    @Override
    public List<WaterLog> listTodayNotice(String dateStr){
        Query query = new Query(Criteria.where("noticeDate").is(dateStr));
        List<WaterLog> waterLogs = mongoTemplate.find(query, WaterLog.class);
        return waterLogs;
    }


    /**
     * @Description 获取当天到期的公告
     * @Author wolfshadow.cn
     * @Date  2020/9/16 16:24
     * @Param [dateStr]
     * @return java.util.List<cn.wolfshadow.gs.common.entity.WaterLog>
     */

    @Override
    public List<WaterLog> listTodayExpireNotice(String dateStr){
        Query query = new Query(Criteria.where("endDate").is(dateStr));
        List<WaterLog> waterLogs = mongoTemplate.find(query, WaterLog.class);
        return waterLogs;
    }



    /**
     * @Description 转换期限、资金数据
     * @Author wolfshadow.cn
     * @Date  2020/9/1 10:36
     * @Param [waterLog, noticeTime, text]
     * @return void
     */
    private void parseCoreData(WaterLog waterLog, Date noticeTime, String text){
        if (text.endsWith("亿") || text.endsWith("亿元")){
            double doubleValue = Double.parseDouble(text.replaceAll("亿[元,]", ""));
            int intValue = (int)Math.round(doubleValue);//四舍五入取整
            waterLog.setWater(intValue);
        }else if (text.endsWith("天") || text.endsWith("年") || text.endsWith("月")){
            int day = 0;
            if (text.endsWith("天")){
                day = Integer.parseInt(text.replace("天", ""));
            }else if (text.endsWith("年")){
                //
                day = Integer.parseInt(text.replace("年", ""));
                day *= 365;
            }else if (text.endsWith("月")){
                String month = text.replaceAll("[个,]月", "");
                //day = NumberConvertEnum.getNumberByWord(month);
                day = 30 * Integer.parseInt(month);
            }
            Date endDate = DateUtil.add(noticeTime, day);
            waterLog.setEndDate(DateUtil.getDateStr(endDate,DateUtil.YYYY_MM_DD));
            waterLog.setTimeLimit(text);
        }else {
            waterLog.setInterestRate(text);
        }
    }

    private void setMd5(WaterLog waterLog){
        String noticeDate = waterLog.getNoticeDate();
        int method = waterLog.getMethod();
        int water = waterLog.getWater();
        String timeLimit = waterLog.getTimeLimit();
        String interestRate = waterLog.getInterestRate();
        String md5 = null;
        try {
            md5 = Md5Util.md5(noticeDate+method+water+timeLimit+interestRate);
        } catch (NoSuchAlgorithmException e) {
            log.error("md5 error! str");
        }
        waterLog.setMd5(md5);
    }
    private void setMd5(List<WaterLog> logs){
        for(WaterLog log : logs) setMd5(log);
    }

}

