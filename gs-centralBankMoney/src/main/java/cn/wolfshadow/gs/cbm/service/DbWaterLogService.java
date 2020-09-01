package cn.wolfshadow.gs.cbm.service;


import cn.wolfshadow.gs.common.entity.WaterLog;
import cn.wolfshadow.gs.common.service.DbOperable;

import java.util.List;

public interface DbWaterLogService extends DbOperable<WaterLog> {

    int insertBatch(List<WaterLog> taskStocks);

    WaterLog saveNotice(String title, String noticeTime, int water, int method, String timeLimit, String interestRate, String content);
}
