package cn.wolfshadow.gs.manager.service;

import cn.wolfshadow.gs.common.entity.TaskStockEntity;
import cn.wolfshadow.gs.common.service.DbOperable;

import java.util.List;

public interface DbTaskStockService extends DbOperable<TaskStockEntity> {

    TaskStockEntity insert(String urlId, String stockCode, String stockName);

    int insertBatch(List<TaskStockEntity> taskStocks);

    boolean update(String id, String urlId, String stockCode, String stockName);

    boolean delete(String id);

    boolean remove(String id);

    boolean removeBatch(String urlId);

    List<TaskStockEntity> listByUrlId(String id);
}
