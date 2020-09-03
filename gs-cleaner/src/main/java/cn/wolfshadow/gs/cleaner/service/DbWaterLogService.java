package cn.wolfshadow.gs.cleaner.service;


import cn.wolfshadow.gs.common.entity.WaterLog;
import cn.wolfshadow.gs.common.service.DbOperable;

import java.util.List;

public interface DbWaterLogService extends DbOperable<WaterLog> {

    int insertBatch(List<WaterLog> taskStocks);
}
