package cn.wolfshadow.gs.digger.service.impl;

import cn.wolfshadow.gs.common.TaskStatusEnum;
import cn.wolfshadow.gs.common.entity.TaskListEntity;
import cn.wolfshadow.gs.common.entity.TaskUrlEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.digger.service.DbTaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 任务相关URL文档服务实现类
 */
@Service
public class DbTaskListServiceImpl extends MongoDbOperator<TaskListEntity> implements DbTaskListService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public TaskListEntity insert(TaskListEntity data) {
        return null;
    }

    @Override
    public boolean update(TaskListEntity data) {
        try {
            return super.update(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  false;
        }
    }

    @Override
    public boolean delete(TaskListEntity data) {
        return false;
    }

    @Override
    public TaskListEntity get(String pk) {
        return null;
    }

    @Override
    public List<TaskListEntity> list(TaskListEntity condition) {
        return null;
    }

    @Override
    public boolean update(String id, String url) {
        TaskListEntity entity = new TaskListEntity();
        entity.setId(id);
        if (!StringUtils.isEmpty(url)) entity.setUrl(url);
        return update(entity);
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<TaskListEntity> listUndisposed(int count) {
        Query query = new Query(Criteria.where("status").is("0"));
        query.limit(count);
        return  mongoTemplate.find(query, TaskListEntity.class);
    }

    @Override
    public boolean finish(String id){
        if (StringUtils.isEmpty(id)) return false;
        TaskListEntity entity = new TaskListEntity();
        entity.setId(id);
        entity.setStatus(TaskStatusEnum.FINISHED.getStatus());
        return update(entity);
    }

    @Override
    public boolean finish(TaskListEntity entity){
        return finish(entity.getId());
    }
}

