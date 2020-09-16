package cn.wolfshadow.gs.cbm.service;

import cn.wolfshadow.gs.common.entity.SmsPhoneNumEntity;
import cn.wolfshadow.gs.common.service.DbOperable;

import java.util.List;

public interface DbPhoneNumService extends DbOperable<SmsPhoneNumEntity> {
    List<SmsPhoneNumEntity> list(String type);
}
