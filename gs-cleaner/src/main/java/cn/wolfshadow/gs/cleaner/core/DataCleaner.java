package cn.wolfshadow.gs.cleaner.core;

import cn.wolfshadow.gs.cleaner.base.StockExcelFileInfo;
import cn.wolfshadow.gs.common.util.ExcelUtil;
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
import java.util.*;

/**
 *
 * 单例数据清洗类，延迟加载
 */
//@Setter
//@Getter
public class DataCleaner {

    public static final int PROCESS_FILE_MAX = 10;//一次处理文件的最大数目

    //private boolean working = false;//是否正在工作

    private DataCleaner(){

    }

    private static class InnerCreator{
        private static final DataCleaner INSTANCE = new DataCleaner();
    }

    public static DataCleaner getInstance(){
        return  InnerCreator.INSTANCE;
    }



    public void cleanHtml(String directory, int max) {
        try {
            //if (working) return;
            //1、设置工作状态
            //working = true;
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
            //working = false;
        }
    }

    public void cleanExcel(String directory, int max){
        //从指定目录获取文件
        int count = 0;
        File file = new File(directory);
        if (!file.exists()) return;
        String[] fileNames = file.list();
        //文件按照股票代码进行分类
        Map<String, StockExcelFileInfo> map = new LinkedHashMap<>();
        for (String fileName : fileNames) {
            String stockCode = fileName.substring(0, 6);
            StockExcelFileInfo stockExcelFileInfo = map.get(stockCode);
            if (stockExcelFileInfo == null) stockExcelFileInfo = new StockExcelFileInfo(stockCode);

            if (fileName.endsWith("main_year.xls")){
                stockExcelFileInfo.setMainYear(fileName);
            }else if (fileName.endsWith("benefit_year.xls")){
                stockExcelFileInfo.setBenefitear(fileName);
            }else if (fileName.endsWith("debt_year.xls")){
                stockExcelFileInfo.setDebtYear(fileName);
            }else {
                continue;
            }
            map.put(stockCode,stockExcelFileInfo);
        }
        // 从文件中获取有用的信息
        int counter = 0;
        map.forEach((key,value) -> {
            System.out.println(key+"___"+value.toString());
            String mainYear = value.getMainYear();
            String benefitear = value.getBenefitear();
            String debtYear = value.getDebtYear();

            int startRowNum = 1;//数据开始行
            File file1 = FileUtil.getFile(directory,mainYear);
            Map<String, Map<String, String>> content1 = ExcelUtil.getContent(file1, startRowNum);
            File file2 = FileUtil.getFile(directory,benefitear);
            Map<String, Map<String, String>> content2 = ExcelUtil.getContent(file2, startRowNum);
            File file3 = FileUtil.getFile(directory,debtYear);
            Map<String, Map<String, String>> content3= ExcelUtil.getContent(file3, startRowNum);



            System.out.println(content1);

        });

    }


}
