package cn.wolfshadow.gs.cleaner.config;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@PropertySource("classpath:config/stockValueData.yaml")
@ConfigurationProperties("excle.data")
@Data
public class ExcelDataConfig {

    private int numLine; //获取多少行数据
    private Main main = new Main();
    private Benefit benefit = new Benefit();
    private Debt debt = new Debt();

    /**
     * 主要指标
     */
    @Setter
    @Getter
    public class Main{
        private String file; //文件名（如：000001_${file}.xls）
        private String revenue;//营收（亿）
        private String revenueIncrease;//营收增长
        private String grossProfitMargin;//销售毛利率
        private String retainedProfits; //净利润(元)
        private String retainedProfitsIncrease; //净利润同比增长率
    }
    /**
     * 利润表
     */
    @Setter
    @Getter
    public class Benefit{
        private String file; //文件名（如：000001_${file}.xls）
        private String researchFee;//研发费用
    }
    /**
     * 资产负债表
     */
    @Setter
    @Getter
    public class Debt{
        private String file; //文件名（如：000001_${file}.xls）
        private String receiveable;//应收账款
    }

}
