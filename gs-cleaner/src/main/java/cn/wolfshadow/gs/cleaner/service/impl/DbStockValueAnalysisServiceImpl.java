package cn.wolfshadow.gs.cleaner.service.impl;

import cn.wolfshadow.gs.cleaner.service.DbStockValueAnalysisService;
import cn.wolfshadow.gs.common.TaskStatusEnum;
import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 股票价值分析实现类
 */
@Service
public class DbStockValueAnalysisServiceImpl extends MongoDbOperator<StockValueAnalysisEntity> implements DbStockValueAnalysisService {

    @Autowired
    private MongoTemplate mongoTemplate;


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

}

