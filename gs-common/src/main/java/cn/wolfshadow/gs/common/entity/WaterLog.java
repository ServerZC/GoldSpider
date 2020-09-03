package cn.wolfshadow.gs.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection="water_log")
public class WaterLog {
    @Id
    private String id;

    private String title;//公告标题
    private String date;//央行放水时间（精确到天）
    private Long noticeTime;//公告时间
    private int water;//流入到市场的资金（亿）
    private int method;//放水方式：-2，正回购；-1，发债；0，逆回购；1，中期借贷便利（MLF）
    private String timeLimit;//期限
    private String endDate;//到期日期
    private String interestRate;//中标利率
    private String content;//用于存放央妈公告正文


}
