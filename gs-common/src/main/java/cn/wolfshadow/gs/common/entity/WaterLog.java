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
    private String noticeDate;//央行放水日期（精确到天）
    private Long noticeTime;//公告时间
    private int water;//流入到市场的资金（亿）
    private int method;//放水方式：-2，正回购；-1，发债；0，逆回购；1，中期借贷便利（MLF）
    private String timeLimit;//期限
    private String endDate;//到期日期（精确到天）
    private Long endTime;//到期时间
    private String interestRate;//中标利率/参考收益率
    private String content;//用于存放央妈公告正文
    private Date createTime;//创建时间

    private String md5;//同一天相同方式相同资金相同期限视为同一条数据，不可重复  md5(noticeTime+method+water+timeLimit+interestRate)


}
