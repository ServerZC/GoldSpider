package cn.wolfshadow.gs.common.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="task_url")
public class TaskUrlEntity {

    @Id
    private String id;

    private String url;
    //@Builder.Default
    private String status = "-1";//任务状态：-1，草稿；0，未处理；1，处理中；2，已完成

}
