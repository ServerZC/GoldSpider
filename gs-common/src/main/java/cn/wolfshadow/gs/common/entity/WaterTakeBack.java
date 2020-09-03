package cn.wolfshadow.gs.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection="water_take_back")
public class WaterTakeBack {

    @Id
    private String id;

    private String date;//资金回收时间
    private int water;//从市场回收的资金
    private int method;//放水方式：0，逆回购；1，中期借贷便利（MLF）
}
