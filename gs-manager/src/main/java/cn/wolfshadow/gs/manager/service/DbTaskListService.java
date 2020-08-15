package cn.wolfshadow.gs.manager.service;


import cn.wolfshadow.gs.common.entity.TaskListEntity;
import cn.wolfshadow.gs.common.service.DbOperable;

public interface DbTaskListService extends DbOperable<TaskListEntity> {
    TaskListEntity insert(String url);

    boolean update(String id, String url);

    boolean delete(String id);

    void creatJob();
}
