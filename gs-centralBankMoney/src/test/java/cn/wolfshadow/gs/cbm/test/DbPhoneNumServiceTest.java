package cn.wolfshadow.gs.cbm.test;

import cn.wolfshadow.gs.cbm.service.DbPhoneNumService;
import cn.wolfshadow.gs.common.entity.SmsPhoneNumEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DbPhoneNumServiceTest {
    @Autowired
    private DbPhoneNumService phoneNumService;


    @Test
    public void testInsert(){
        SmsPhoneNumEntity entity = new SmsPhoneNumEntity();
        entity.setPhoneNum("18381357800");
        entity.setSmsType("total");//injection  weepage  total
        phoneNumService.insert(entity);
    }

    @Test
    public void testList(){
        List<SmsPhoneNumEntity> tatal = phoneNumService.list("total");
        System.err.println(tatal.size());
    }
}
