package cn.wolfshadow.gs.cleaner.core;

import cn.wolfshadow.gs.cleaner.base.StockExcelFileInfo;
import cn.wolfshadow.gs.cleaner.config.ExcelDataConfig;
import cn.wolfshadow.gs.common.base.AnnualRevenueData;
import cn.wolfshadow.gs.common.entity.StockValueAnalysisEntity;
import cn.wolfshadow.gs.common.util.ExcelUtil;
import cn.wolfshadow.gs.common.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 *
 * 单例数据清洗类，延迟加载
 */
//@Setter
//@Getter
@Component
public class DataCleaner {

    public static final int PROCESS_FILE_MAX = 10;//一次处理文件的最大数目

    @Autowired
    private ExcelDataConfig excelDataConfig;


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

    public List<StockValueAnalysisEntity> cleanExcel(String directory, int max){
        List<StockValueAnalysisEntity> result = new ArrayList<>();
        //从指定目录获取文件
        int count = 0;
        File file = new File(directory);
        if (!file.exists()) return result;
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
        for(Map.Entry<String, StockExcelFileInfo> entry : map.entrySet()){
            String key = entry.getKey();
            StockExcelFileInfo value = entry.getValue();
            //单支股票的价值分析实体
            StockValueAnalysisEntity entity = new StockValueAnalysisEntity();
            entity.setStockCode(key);
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

            int numLine = excelDataConfig.getNumLine();//获取数据列数
            ExcelDataConfig.Main main = excelDataConfig.getMain();
            ExcelDataConfig.Benefit benefit = excelDataConfig.getBenefit();
            ExcelDataConfig.Debt debt = excelDataConfig.getDebt();

            //需要获取的数据项
            String revenue = main.getRevenue();
            String revenueIncrease = main.getRevenueIncrease();
            String grossProfitMargin = main.getGrossProfitMargin();
            String researchFee = benefit.getResearchFee();
            String receiveable = debt.getReceiveable();

            //获取数据项对应的年度数据
            Map<String, String> revenueMap = content1.get(revenue);
            Map<String, String> revenueIncreaseMap = content1.get(revenueIncrease);
            Map<String, String> grossProfitMarginMap = content1.get(grossProfitMargin);
            Map<String, String> researchFeeMap = content2.get(researchFee);
            Map<String, String> receiveableMap = content3.get(receiveable);

            List<AnnualRevenueData> datas = new ArrayList<>();
            for (int i = 0; i < numLine; i++) {
                AnnualRevenueData data = new AnnualRevenueData();//年度营收等数据
                datas.add(data);
            }
            int temp = 0;//记录获取列的次数
            //总营收
            for(Map.Entry<String, String> entry1 : revenueMap.entrySet()){
                String key1 = entry1.getKey();
                String value1 = entry1.getValue();
                AnnualRevenueData data = datas.get(temp);
                data.setYear(Integer.parseInt(key1));
                //int intValue = (int)(Double.parseDouble(value1) * 100);
                data.setRevenue(Double.parseDouble(value1));
                temp ++;
                if (temp == numLine) break;
            }
            temp = 0;
            //总营收增长率
            for(Map.Entry<String, String> entry1 : revenueIncreaseMap.entrySet()){
                String value1 = entry1.getValue();
                AnnualRevenueData data = datas.get(temp);
                int intValue = (int)(Double.parseDouble(value1.replace("%","")) * 100);
                data.setRevenueIncrease(intValue);
                temp ++;
                if (temp == numLine) break;
            }
            temp = 0;
            //毛利率
            for(Map.Entry<String, String> entry1 : grossProfitMarginMap.entrySet()){
                String value1 = entry1.getValue();
                AnnualRevenueData data = datas.get(temp);
                int intValue = (int)(Double.parseDouble(value1.replace("%","")) * 100);
                data.setGrossProfitMargin(intValue);
                temp ++;
                if (temp == numLine) break;
            }
            temp = 0;
            //研发费用
            for(Map.Entry<String, String> entry1 : researchFeeMap.entrySet()){
                String value1 = entry1.getValue();
                AnnualRevenueData data = datas.get(temp);
                try {
                    data.setResearchFee(Double.parseDouble(value1));
                }catch (Exception e){
                    //todo 日志输出
                    e.printStackTrace();
                }finally {
                    temp ++;
                    if (temp == numLine) break;
                }
            }
            temp = 0;
            //应收账款
            for(Map.Entry<String, String> entry1 : receiveableMap.entrySet()){
                String value1 = entry1.getValue();
                AnnualRevenueData data = datas.get(temp);
                try {
                    double dValue = Double.parseDouble(value1);
                    double revenue1 = data.getRevenue();
                    int ratio = (int)(dValue * 100 * 100 / revenue1);//应收账款对于总营收占比
                    data.setReceiveable(dValue);
                    data.setReceiveableRadio(ratio);
                }catch (Exception e){
                    //todo 日志输出
                    e.printStackTrace();
                }finally {
                    temp ++;
                    if (temp == numLine) break;
                }
            }

            result.add(entity);
            counter ++;
            if (counter == count) break;
        }
        return result;
    }


}
