package cn.wolfshadow.gs.cbm.service.impl;

import cn.wolfshadow.gs.cbm.config.SmsConfig;
import cn.wolfshadow.gs.cbm.service.DbPhoneNumService;
import cn.wolfshadow.gs.cbm.service.SmsService;
import cn.wolfshadow.gs.common.entity.SmsPhoneNumEntity;
import cn.wolfshadow.gs.message.util.SmsUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private DbPhoneNumService phoneNumService;
    @Autowired
    private SmsConfig smsConfig;

    @Override
    public void sendMessageTotal(int water) {
        List<SmsPhoneNumEntity> entities = phoneNumService.list("total");
        String templateCode = smsConfig.getTemplateCodeTotal();
        List<String> phoneNums = new ArrayList<>();

        if (entities.isEmpty()) return;
        entities.stream().forEach(entity -> {
            phoneNums.add(entity.getPhoneNum());
        });

        send(phoneNums,templateCode,water);
    }

    @Override
    public void sendMessageInjection(int water) {
        List<SmsPhoneNumEntity> entities = phoneNumService.list("injection");
        String templateCode = smsConfig.getTemplateCodeInjection();
        List<String> phoneNums = new ArrayList<>();

        if (entities.isEmpty()) return;
        entities.stream().forEach(entity -> {
            phoneNums.add(entity.getPhoneNum());
        });

        send(phoneNums,templateCode,water);
    }

    @Override
    public void sendMessageWeepage(int water) {
        List<SmsPhoneNumEntity> entities = phoneNumService.list("weepage");
        String templateCode = smsConfig.getTemplateCodeWeepage();
        List<String> phoneNums = new ArrayList<>();

        if (entities.isEmpty()) return;
        entities.stream().forEach(entity -> {
            phoneNums.add(entity.getPhoneNum());
        });

        send(phoneNums,templateCode,water);
    }

    @SneakyThrows
    private void send(List<String> phoneNums, String templateCode, int water){
        //阿里云验证码要求小于6位数
        String message = water > 999999 ? "999999" : water+"";

        SmsUtil.sendSms(phoneNums, smsConfig.getRegionId(),smsConfig.getAccessKeyId(),smsConfig.getAccessSecret(),
                smsConfig.getSysDomain(),smsConfig.getSysVersion(),smsConfig.getSingName(),
                templateCode, message);
    }

}
