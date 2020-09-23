package cn.wolfshadow.gs.cleaner.service.impl;

import cn.wolfshadow.gs.cleaner.config.StockStrategyConfig;
import cn.wolfshadow.gs.cleaner.service.DbStockValueAnalysisService;
import cn.wolfshadow.gs.common.base.AnnualRevenueData;
import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 股票价值分析实现类
 */
@Service
@Slf4j
public class DbStockValueAnalysisServiceImpl extends MongoDbOperator<StockValueAnalysisEntity> implements DbStockValueAnalysisService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StockStrategyConfig stockStrategyConfig;


    @Override
    public StockValueAnalysisEntity insert(StockValueAnalysisEntity data) {
        try {
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }

    @Override
    @Transactional
    public int insertBatch(List<StockValueAnalysisEntity> taskStocks){
        try {
            return super.insertBatch(taskStocks,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  0;
        }
    }

    @Override
    public List<StockValueAnalysisEntity> listHighQuality() {
        List<StockValueAnalysisEntity> result = new ArrayList<>();
        //库中所有数据
        List<StockValueAnalysisEntity> all = mongoTemplate.findAll(StockValueAnalysisEntity.class);

        //获取策略
        Map<String, String> general = stockStrategyConfig.getGeneral();
        Map<String, String> special = stockStrategyConfig.getSpecial();

        //遍历股票数据并过滤
        all.stream().forEach(entity -> {
            boolean isOk = true;
            //普通策略过滤
            for (Map.Entry<String, String> entry : general.entrySet()) {
                if (!checkGeneralStrategy(entity,entry.getKey(),entry.getValue())) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                for (Map.Entry<String, String> entry : special.entrySet()) {
                    if (!checkSpecialStrategy(entity,entry.getKey(),entry.getValue())) {
                        isOk = false;
                        break;
                    }

                }
            }

            if (isOk) result.add(entity);
        });

        return result;
    }

    @Override
    public boolean update(StockValueAnalysisEntity data) {
        try {
            return super.update(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  false;
        }
    }

    @Override
    public boolean delete(StockValueAnalysisEntity data) {
        return false;
    }

    @Override
    public StockValueAnalysisEntity get(String pk) {
        return null;
    }

    @Override
    public List<StockValueAnalysisEntity> list(StockValueAnalysisEntity condition) {
        return null;
    }

    private boolean checkGeneralStrategy(StockValueAnalysisEntity entity, String strategyKey, String strategyValue){
        String[] split = strategyValue.split("\\s");
        if (split.length != 3) return false;

        AtomicInteger counter = new AtomicInteger();
        List<AnnualRevenueData> analysisData = entity.getAnnualAnalysisData();
        analysisData.stream().forEach(data -> {
            //反射获取data属性值
            try {
                Field field = data.getClass().getDeclaredField(strategyKey);
                field.setAccessible(true);
                //Field field = data.getClass().getField(strategyKey);
                Object fieldValue = field.get(data);
                int value = Integer.parseInt(fieldValue.toString());
                if ("gt".equals(split[0])){
                    if (value > Integer.parseInt(split[1])) counter.getAndIncrement();
                }else if ("lt".equals(split[0])){
                    if (value < Integer.parseInt(split[1])) counter.getAndIncrement();
                }
            } catch (NoSuchFieldException e) {
                log.error("反射获取股票财报数据出现异常！");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                log.error("反射获取股票财报数据出现异常！");
                e.printStackTrace();
            }
        });

        return counter.intValue() >= Integer.parseInt(split[2]);
    }
    private boolean checkSpecialStrategy(StockValueAnalysisEntity entity, String strategyKey, String strategyValue){
        String[] split = strategyValue.split("\\s");
        if (split.length != 3) return false;
        List<AnnualRevenueData> analysisData = entity.getAnnualAnalysisData();
        Collections.reverse(analysisData);//因为数据是按年度逆序的，所以需要倒置
        switch (strategyKey){
            //研发费用增长
            case "researchFeeIncrease":
                Double lastValue = null;
                int counter = 0;
                for (AnnualRevenueData data : analysisData) {
                    double researchFee = data.getResearchFee();
                    if (lastValue != null){
                        if ("gt".equals(split[0])){
                            if (researchFee - lastValue > Double.parseDouble(split[1])) counter++;
                        }else if ("lt".equals(split[0])){
                            if (researchFee - lastValue < Double.parseDouble(split[1])) counter++;
                        }
                    }
                    lastValue = researchFee;
                }
                return counter >= Integer.parseInt(split[2]);
            default:
               return false;
        }
    }

}

