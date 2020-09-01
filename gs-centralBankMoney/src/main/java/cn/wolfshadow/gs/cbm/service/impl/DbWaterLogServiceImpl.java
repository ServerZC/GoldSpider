package cn.wolfshadow.gs.cbm.service.impl;

import cn.wolfshadow.gs.cbm.service.DbWaterLogService;
import cn.wolfshadow.gs.common.entity.WaterLog;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.common.util.DateUtil;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 股票价值分析实现类
 */
@Service
public class DbWaterLogServiceImpl extends MongoDbOperator<WaterLog> implements DbWaterLogService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public WaterLog insert(WaterLog data) {
        try {
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }

    @Override
    @Transactional
    public int insertBatch(List<WaterLog> taskStocks){
        try {
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
        waterLog.setDate(DateUtil.getDateStr(date,DateUtil.YYYY_MM_DD));
        waterLog.setMethod(method);
        waterLog.setContent(content);
        waterLog.setWater(water);
        waterLog.setInterestRate(interestRate);

        parseCoreData(waterLog,date,timeLimit);

        insert(waterLog);

        return waterLog;
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

}

