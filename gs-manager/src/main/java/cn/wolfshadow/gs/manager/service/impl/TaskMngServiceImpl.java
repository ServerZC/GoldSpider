package cn.wolfshadow.gs.manager.service.impl;

import cn.wolfshadow.gs.common.TaskStatusEnum;
import cn.wolfshadow.gs.common.entity.TaskStockEntity;
import cn.wolfshadow.gs.common.entity.TaskUrlEntity;
import cn.wolfshadow.gs.common.util.JsonUtil;
import cn.wolfshadow.gs.manager.dto.TaskDraftDto;
import cn.wolfshadow.gs.manager.service.TaskMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskMngServiceImpl implements TaskMngService {

    @Autowired
    private DbTaskUrlServiceImpl taskUrlService;
    @Autowired
    private DbTaskStockServiceImpl taskStockService;

    @Override
    @Transactional
    public boolean insert(String json) {
        TaskDraftDto taskDraftDto = (TaskDraftDto)JsonUtil.json2Object(json, TaskDraftDto.class);
        TaskUrlEntity taskUrl = taskDraftDto.getTaskUrl();
        List<TaskStockEntity> taskStocks = taskDraftDto.getTaskStocks();
        if (taskUrl == null || taskStocks == null || taskStocks.isEmpty()) return false;

        //1、插入任务url表
        TaskUrlEntity urlEntity = taskUrlService.insert(taskUrl);
        String urlId = urlEntity.getId();

        //2、插入任务stock表
        for(TaskStockEntity entity : taskStocks) entity.setUrlId(urlId);
        
        int i = taskStockService.insertBatch(taskStocks);
        if (i < 1) return false;

        return  true;
    }

    @Override
    public boolean addStock2Draft(String urlId, String stockCode, String stockName){
        //1、查看任务是否是草稿
        TaskUrlEntity urlEntity = taskUrlService.get(urlId);
        String status = urlEntity.getStatus();
        if (!TaskStatusEnum.DRAFT.valueEquals(status)) return false;
        //2、添加股票
        return  taskStockService.addStock2Draft(urlId,stockCode,stockName);
    }

    @Override
    public boolean active(String urlId){
        return taskUrlService.changeStatus(urlId, TaskStatusEnum.UNDISPOSED.getStatus());
    }
}
