package cn.wolfshadow.gs.manager.dto;

import cn.wolfshadow.gs.common.entity.TaskStockEntity;
import cn.wolfshadow.gs.common.entity.TaskUrlEntity;
import lombok.Data;

import java.util.List;

@Data
public class TaskDraftDto {
    TaskUrlEntity taskUrl;
    List<TaskStockEntity> taskStocks;
}
