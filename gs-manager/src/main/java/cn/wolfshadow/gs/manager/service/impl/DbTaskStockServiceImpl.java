package cn.wolfshadow.gs.manager.service.impl;

import cn.wolfshadow.gs.common.entity.TaskStockEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.manager.service.DbTaskStockService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 任务相关股票信息文档服务实现类
 */
@Service
public class DbTaskStockServiceImpl extends MongoDbOperator<TaskStockEntity> implements DbTaskStockService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public TaskStockEntity insert(TaskStockEntity data) {
        try {
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }
    @Override
    public TaskStockEntity insert(String urlId, String stockCode, String stockName) {
        TaskStockEntity entity = new TaskStockEntity();
        entity.setUrlId(urlId);
        entity.setStockCode(stockCode);
        entity.setStockName(stockName);
       return insert(entity);
    }
    @Override
    @Transactional
    public int insertBatch(List<TaskStockEntity> taskStocks){
        try {
            return super.insertBatch(taskStocks,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  0;
        }
    }

    @Override
    public boolean update(TaskStockEntity data) {
        try {
            return super.update(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  false;
        }
    }
    @Override
    public boolean update(String id, String urlId, String stockCode, String stockName) {
        TaskStockEntity entity = new TaskStockEntity();
        entity.setId(id);
        if (!StringUtils.isEmpty(urlId)) entity.setUrlId(urlId);
        if (!StringUtils.isEmpty(stockCode)) entity.setStockCode(stockCode);
        if (!StringUtils.isEmpty(stockName)) entity.setStockName(stockName);
        return update(entity);
    }

    @Override
    public boolean delete(TaskStockEntity data) {
        return super.delete(data,mongoTemplate);
    }
    @Override
    public boolean delete(String id) {
        TaskStockEntity entity = new TaskStockEntity();
        entity.setId(id);
        return delete(entity);
    }
    @Override
    public boolean remove(String id) {
        TaskStockEntity entity = new TaskStockEntity();
        entity.setId(id);
        entity.setValid(DATA_INVALID);
        return update(entity);
    }
    @Override
    public boolean removeBatch(String urlId) {
        //修改条件
        Query query = new Query(Criteria.where("urlId").is(urlId));

        //修改内容
        Update update = new Update();
        update.set("valid",DATA_INVALID);

        //修改
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, TaskStockEntity.class);

        return updateResult.getMatchedCount() > 0;
    }

    @Override
    public List<TaskStockEntity> listByUrlId(String urlId) {
        Query query = new Query(Criteria.where("urlId").is(urlId));
        return  mongoTemplate.find(query,TaskStockEntity.class);
    }


    @Override
    public TaskStockEntity get(String pk) {
        return super.get(pk,mongoTemplate);
    }

    @Override
    public List<TaskStockEntity> list(TaskStockEntity condition) {
        return super.list(condition,mongoTemplate);
    }


    public boolean addStock2Draft(String urlId, String stockCode, String stockName){
          return  insert(urlId,stockCode,stockName) != null;
    }
}

