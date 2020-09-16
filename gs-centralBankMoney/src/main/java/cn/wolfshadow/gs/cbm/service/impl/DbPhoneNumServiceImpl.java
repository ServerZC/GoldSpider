package cn.wolfshadow.gs.cbm.service.impl;

import cn.wolfshadow.gs.cbm.service.DbPhoneNumService;
import cn.wolfshadow.gs.common.entity.SmsPhoneNumEntity;
import cn.wolfshadow.gs.common.service.impl.MongoDbOperator;
import cn.wolfshadow.gs.common.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Service
public class DbPhoneNumServiceImpl extends MongoDbOperator<SmsPhoneNumEntity> implements DbPhoneNumService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public SmsPhoneNumEntity insert(SmsPhoneNumEntity data) {
        try {
            setMd5(data);
            return super.insert(data,mongoTemplate);
        } catch (IllegalAccessException e) {
            return  null;
        }
    }

    @Override
    public boolean update(SmsPhoneNumEntity data) {
        return false;
    }

    @Override
    public boolean delete(SmsPhoneNumEntity data) {
        return false;
    }

    @Override
    public SmsPhoneNumEntity get(String pk) {
        return null;
    }

    @Override
    public List<SmsPhoneNumEntity> list(SmsPhoneNumEntity condition) {
        return null;
    }

    @Override
    public List<SmsPhoneNumEntity> list(String type) {
        Query query = new Query(Criteria.where("smsType").is(type).and("valid").is(1));
        List<SmsPhoneNumEntity> list = mongoTemplate.find(query, SmsPhoneNumEntity.class);
        return list;
    }

    private void setMd5(SmsPhoneNumEntity entity){
        String phoneNum = entity.getPhoneNum();
        String smsType = entity.getSmsType();

        String md5 = null;
        try {
            md5 = Md5Util.md5(phoneNum+smsType);
        } catch (NoSuchAlgorithmException e) {
            log.error("md5 error! str");
        }
        entity.setMd5(md5);
    }
}
