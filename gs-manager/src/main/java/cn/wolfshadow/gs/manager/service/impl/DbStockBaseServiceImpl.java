package cn.wolfshadow.gs.manager.service.impl;

import cn.wolfshadow.gs.common.entity.StockBaseEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.manager.service.DbStockBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 基础股票信息文档服务实现类
 */
@Service
public class DbStockBaseServiceImpl extends MongoDbOperator<StockBaseEntity> implements DbStockBaseService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public StockBaseEntity insert(StockBaseEntity data) {
        try {
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }
    @Override
    public StockBaseEntity insert(String stockCode, String stockName,
                         String industryNo, String industryName) {
        StockBaseEntity entity = new StockBaseEntity();
        entity.setStockCode(stockCode);
        entity.setStockName(stockName);
       if (!StringUtils.isEmpty(industryNo)) entity.setIndustryNo(industryNo);
       if (!StringUtils.isEmpty(industryName)) entity.setIndustryName(industryName);
       return insert(entity);
    }

    @Override
    public boolean update(StockBaseEntity data) {
        try {
            return super.update(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  false;
        }
    }
    @Override
    public boolean update(String id, String stockCode, String stockName,
                          String industryNo, String industryName) {
        StockBaseEntity entity = new StockBaseEntity();
        entity.setId(id);
        if (!StringUtils.isEmpty(stockCode)) entity.setStockCode(stockCode);
        if (!StringUtils.isEmpty(stockName)) entity.setStockName(stockName);
        if (!StringUtils.isEmpty(industryNo)) entity.setIndustryNo(industryNo);
        if (!StringUtils.isEmpty(industryName)) entity.setIndustryName(industryName);
        return update(entity);
    }

    @Override
    public boolean delete(StockBaseEntity data) {
        return super.delete(data,mongoTemplate);
    }

    @Override
    public StockBaseEntity get(String pk) {
        return super.get(pk,mongoTemplate);
    }

    @Override
    public List<StockBaseEntity> list(StockBaseEntity condition) {
        return super.list(condition,mongoTemplate);
    }


}

