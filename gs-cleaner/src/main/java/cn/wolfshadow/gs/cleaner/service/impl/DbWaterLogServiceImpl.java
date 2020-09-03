package cn.wolfshadow.gs.cleaner.service.impl;

import cn.wolfshadow.gs.cleaner.service.DbWaterLogService;
import cn.wolfshadow.gs.common.entity.WaterLog;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}

