package cn.wolfshadow.gs.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="sms_phone_num")
public class SmsPhoneNumEntity {
    @Id
    private String id;

    private String smsType;//发送短信类型
    private String phoneNum;//手机号
    private int valid = 1;//是否可用，默认为1

    private String md5;//数据唯一保证
}
