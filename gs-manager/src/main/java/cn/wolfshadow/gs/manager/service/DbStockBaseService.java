package cn.wolfshadow.gs.manager.service;

import cn.wolfshadow.gs.common.entity.StockBaseEntity;
import cn.wolfshadow.gs.common.service.DbOperable;

public interface DbStockBaseService extends DbOperable<StockBaseEntity> {

    StockBaseEntity insert(String stockCode, String stockName,
                  String industryNo, String industryName);

    boolean update(String id, String stockCode, String stockName,
                   String industryNo, String industryName);
}
