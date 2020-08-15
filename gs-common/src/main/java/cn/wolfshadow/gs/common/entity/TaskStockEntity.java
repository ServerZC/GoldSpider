package cn.wolfshadow.gs.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="task_stock")
public class TaskStockEntity {

    @Id
    private String id;

    private String urlId;
    private String stockCode;
    private String stockName;
    private String valid = "1"; //数据是否有效：0，1

}
