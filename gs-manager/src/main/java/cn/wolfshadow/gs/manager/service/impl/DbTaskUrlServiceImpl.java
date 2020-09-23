package cn.wolfshadow.gs.manager.service.impl;

import cn.wolfshadow.gs.common.enums.TaskStatusEnum;
import cn.wolfshadow.gs.common.entity.TaskUrlEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.manager.service.DbTaskUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 任务相关URL文档服务实现类
 */
@Service
public class DbTaskUrlServiceImpl extends MongoDbOperator<TaskUrlEntity> implements DbTaskUrlService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public TaskUrlEntity insert(TaskUrlEntity data) {
        try {
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }
    @Override
    public TaskUrlEntity insert(String url) {
        TaskUrlEntity entity = new TaskUrlEntity();
        entity.setUrl(url);
        //entity.setStatus();
       return insert(entity);
    }

    @Override
    public boolean update(TaskUrlEntity data) {
        try {
            return super.update(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  false;
        }
    }
    @Override
    public boolean update(String id, String url) {
        TaskUrlEntity entity = new TaskUrlEntity();
        entity.setId(id);
        if (!StringUtils.isEmpty(url)) entity.setUrl(url);
        return update(entity);
    }

    @Override
    public boolean delete(TaskUrlEntity data) {
        return super.delete(data,mongoTemplate);
    }
    @Override
    public boolean delete(String id) {
        TaskUrlEntity entity = new TaskUrlEntity();
        entity.setId(id);
        return delete(entity);
    }

    @Override
    public List<TaskUrlEntity> listUndisposed() {
        Query query = new Query(Criteria.where("status").is(TaskStatusEnum.UNDISPOSED.getStatus()));
        return  mongoTemplate.find(query, TaskUrlEntity.class);
    }


    @Override
    public TaskUrlEntity get(String pk) {
        return super.get(pk,mongoTemplate);
    }

    @Override
    public List<TaskUrlEntity> list(TaskUrlEntity condition) {
        return super.list(condition,mongoTemplate);
    }


    @Override
    public boolean changeStatus(String urlId, String status){
        if (!TaskStatusEnum.contains(status)) return false;
        TaskUrlEntity urlEntity = new TaskUrlEntity();
        urlEntity.setId(urlId);
        urlEntity.setStatus(status);
        return update(urlEntity);
    }
}

