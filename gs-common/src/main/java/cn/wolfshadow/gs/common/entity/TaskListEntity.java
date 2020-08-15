package cn.wolfshadow.gs.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="task_list")
public class TaskListEntity {

    @Id
    private String id;

    private String url;
    //@Builder.Default
    private String status = "0";//任务状态：0，未处理；1，处理中；2，已完成

}
