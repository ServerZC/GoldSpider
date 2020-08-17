package cn.wolfshadow.gs.cleaner.core;

import cn.wolfshadow.gs.common.util.FileUtil;
import cn.wolfshadow.gs.common.util.HttpUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.UUID;

/**
 * 目前需求暂不需要用到爬虫框架，仅是单一html页面数据获取
 * 单例爬虫类，延迟加载
 */
@Setter
@Getter
public class DataCleaner {

    private boolean working = false;//是否正在工作

    private DataCleaner(){

    }

    private static class InnerCreator{
        private static final DataCleaner INSTANCE = new DataCleaner();
    }

    public static DataCleaner getInstance(){
        return  InnerCreator.INSTANCE;
    }



    public void work(String directory, int max) {
        try {
            if (working) return;
            //1、设置工作状态
            working = true;
            //2、从指定目录获取文件
            int count = 0;
            File file = new File(directory);
            if (!file.exists()) return;
            String[] fileNames = file.list();
            for(String fileName  : fileNames){
                File htmlFile = FileUtil.getFile(directory,fileName);
                if (!htmlFile.exists()) continue;
                Document parse = Jsoup.parse(htmlFile, FileUtil.CHARACTER_SET_GBK);
                Element body = parse.body();
                Element cwzbTable = parse.getElementById("cwzbTable");
                Element cwzbTable1 = body.getElementById("cwzbTable");
                Elements scrollContainer = parse.getElementsByClass("scroll_container");
                scrollContainer.get(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            working = false;
        }
    }


}
