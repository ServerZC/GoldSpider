package cn.wolfshadow.gs.cleaner.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties("stock.strategy")
@Data
public class StockStrategyConfig {
    private Map<String, String> general; //普通策略：直接通过字段值来判断
    private Map<String, String> special; //特殊策略：需要通过字段计算出需要的值再进行判断

}
