package cn.wolfshadow.gs.manager.service;

import cn.wolfshadow.gs.common.entity.TaskUrlEntity;
import cn.wolfshadow.gs.common.service.DbOperable;

import java.util.List;

public interface DbTaskUrlService extends DbOperable<TaskUrlEntity> {

    TaskUrlEntity insert(String url);

    boolean update(String id, String url);

    boolean delete(String id);

    List<TaskUrlEntity> listUndisposed();

    boolean changeStatus(String urlId, String status);
}
