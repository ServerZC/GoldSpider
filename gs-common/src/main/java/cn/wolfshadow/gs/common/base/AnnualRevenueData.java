package cn.wolfshadow.gs.common.base;

import lombok.Data;

/**
 * 年度营收数据
 */
@Data
public class AnnualRevenueData {

    private int year;//年份
    private double revenue;//营收（亿）
    private int revenueIncrease;//营收增长
    private int grossProfitMargin;//销售毛利率
    private double receiveable;//应收账款
    private int receiveableRadio;//账款比例
    private double researchFee;//研发费用
}
