package cn.wolfshadow.gs.cleaner.base;

import lombok.Data;

@Data
public class StockExcelFileInfo {
    private String stockCode;//股票代码
    private String mainYear;//主要指标（年）
    private String benefitear;//利润表（年）
    private String debtYear;//资产负债表（年）

    public StockExcelFileInfo(String stockCode){
        this.stockCode = stockCode;
    }
}
