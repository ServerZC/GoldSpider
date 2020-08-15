package cn.wolfshadow.gs.digger.service;


import cn.wolfshadow.gs.common.entity.TaskListEntity;
import cn.wolfshadow.gs.common.service.DbOperable;

import java.util.List;

public interface DbTaskListService extends DbOperable<TaskListEntity> {
    boolean update(String id, String url);

    boolean delete(String id);

    List<TaskListEntity> listUndisposed(int count);

    boolean finish(String id);

    boolean finish(TaskListEntity entity);
}
