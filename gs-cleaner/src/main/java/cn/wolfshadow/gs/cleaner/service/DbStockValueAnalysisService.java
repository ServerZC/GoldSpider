package cn.wolfshadow.gs.cleaner.service;


import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import cn.wolfshadow.gs.common.service.DbOperable;

import java.util.List;

public interface DbStockValueAnalysisService extends DbOperable<StockValueAnalysisEntity> {

    int insertBatch(List<StockValueAnalysisEntity> taskStocks);

    List<StockValueAnalysisEntity> listHighQuality();
}
