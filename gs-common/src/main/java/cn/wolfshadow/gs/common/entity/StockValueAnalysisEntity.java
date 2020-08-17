package cn.wolfshadow.gs.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 股票价值分析实体类
 */
@Data
@Document(collection="stock_value_analysis")
public class StockValueAnalysisEntity <D>{

    @Id
    private String id;

    private String stockCode;
    private String stockName;

    private List<D> annualAnalysisData;//年度分析数据
}
