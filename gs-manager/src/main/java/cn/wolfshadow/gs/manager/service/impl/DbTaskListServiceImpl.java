package cn.wolfshadow.gs.manager.service.impl;

import cn.wolfshadow.gs.common.enums.TaskStatusEnum;
import cn.wolfshadow.gs.common.entity.TaskListEntity;
import cn.wolfshadow.gs.common.entity.TaskStockEntity;
import cn.wolfshadow.gs.common.entity.TaskUrlEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.manager.service.DbTaskListService;
import cn.wolfshadow.gs.manager.service.DbTaskStockService;
import cn.wolfshadow.gs.manager.service.DbTaskUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务相关URL文档服务实现类
 */
@Service
public class DbTaskListServiceImpl extends MongoDbOperator<TaskListEntity> implements DbTaskListService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DbTaskUrlService taskUrlService;
    @Autowired
    private DbTaskStockService taskStockService;

    @Override
    public TaskListEntity insert(TaskListEntity data) {
        try {
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }
    @Override
    public TaskListEntity insert(String url) {
        TaskListEntity entity = new TaskListEntity();
        entity.setUrl(url);
        //entity.setStatus();
       return insert(entity);
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
    public boolean update(String id, String url) {
        TaskListEntity entity = new TaskListEntity();
        entity.setId(id);
        if (!StringUtils.isEmpty(url)) entity.setUrl(url);
        return update(entity);
    }

    @Override
    public boolean delete(TaskListEntity data) {
        return super.delete(data,mongoTemplate);
    }
    @Override
    public boolean delete(String id) {
        TaskListEntity entity = new TaskListEntity();
        entity.setId(id);
        return delete(entity);
    }

    @Override
    @Transactional
    public void creatJob() {
        //1、获取待处理任务url
        List<TaskUrlEntity> urlEntities = taskUrlService.listUndisposed();
        for(TaskUrlEntity entity : urlEntities){
            creatJob(entity);
        }
    }
    @Transactional
    public void creatJob(TaskUrlEntity urlEntity){
        String status = urlEntity.getStatus();
        if(!TaskStatusEnum.UNDISPOSED.valueEquals(status)) return;
        String id = urlEntity.getId();
        String url = urlEntity.getUrl();
        List<TaskStockEntity> stocks = taskStockService.listByUrlId(id);
        List<TaskListEntity> tasks = new ArrayList<>();
        for(TaskStockEntity entity : stocks){
            TaskListEntity task = new TaskListEntity();
            task.setUrl(String.format(url,entity.getStockCode()));
            tasks.add(task);
        }
        try {
            int i = insertBatch(tasks, mongoTemplate);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        taskUrlService.changeStatus(id, TaskStatusEnum.FINISHED.getStatus());
    }


    @Override
    public TaskListEntity get(String pk) {
        return super.get(pk,mongoTemplate);
    }

    @Override
    public List<TaskListEntity> list(TaskListEntity condition) {
        return super.list(condition,mongoTemplate);
    }


}

