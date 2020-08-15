package cn.wolfshadow.gs.manager.service;

import org.springframework.transaction.annotation.Transactional;

public interface TaskMngService {

    boolean insert(String json);

    boolean addStock2Draft(String urlId, String stockCode, String stockName);

    boolean active(String urlId);
}
