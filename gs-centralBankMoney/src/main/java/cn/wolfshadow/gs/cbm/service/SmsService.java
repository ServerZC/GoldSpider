package cn.wolfshadow.gs.cbm.service;

public interface SmsService {

    void sendMessageTotal(int water);
    void sendMessageInjection(int water);
    void sendMessageWeepage(int water);
}
