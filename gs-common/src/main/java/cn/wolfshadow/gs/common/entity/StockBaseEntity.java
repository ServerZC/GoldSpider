package cn.wolfshadow.gs.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="stock_base")
public class StockBaseEntity {

    @Id
    private String id;

    private String stockCode;
    private String stockName;
    private String industryNo;
    private String industryName;


}
