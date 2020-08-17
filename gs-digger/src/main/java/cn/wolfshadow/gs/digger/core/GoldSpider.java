package cn.wolfshadow.gs.digger.core;

import cn.wolfshadow.gs.common.util.FileUtil;
import cn.wolfshadow.gs.common.util.HttpUtil;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * 目前需求暂不需要用到爬虫框架，仅是单一html页面数据获取
 * 单例爬虫类，延迟加载
 */
public class GoldSpider {

    private GoldSpider(){

    }

    private static class InnerCreator{
        private static final GoldSpider INSTANCE = new GoldSpider();
    }

    public static GoldSpider getInstance(){
        return  InnerCreator.INSTANCE;
    }


    @SneakyThrows
    public boolean crawl(String url, String savePath){
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(savePath)) return false;
        File file = new File(savePath);
        if (!file.exists()) file.mkdirs();
        UUID uuid = UUID.randomUUID();
        String path = FileUtil.getFilePath(savePath,uuid+".html");
        file = new File(path);
        //FileInputStream fis = new FileInputStream(file);

        //TODO 暂未用到爬虫框架，直接读取url对应字节流
        InputStream stream = HttpUtil.getStream(url);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] temp = new byte[1024];
        while (stream.read(temp) != -1){
            fos.write(temp);
        }
        fos.flush();
        fos.close();
        stream.close();

        return true;
    }


}
