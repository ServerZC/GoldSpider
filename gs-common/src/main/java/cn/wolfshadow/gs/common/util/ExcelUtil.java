package cn.wolfshadow.gs.common.util;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExcelUtil {

    @SneakyThrows
    public static Map<String, Map<String,String>> getContent(File excelFile, int startRowNum){
        Map<String, Map<String,String>> map = new HashMap<String, Map<String, String>>();
        String name = excelFile.getName();
        FileInputStream inputStream = new FileInputStream(excelFile);
        Workbook workbook = null;
        if (name.endsWith(".xls")){
            workbook = new HSSFWorkbook(inputStream);
        }else if (name.endsWith(".xlsx")){
            workbook = new XSSFWorkbook(inputStream);
        }else {
            return map;
        }
        if (workbook == null) return map;
        int index = workbook.getActiveSheetIndex();
        Sheet sheet = workbook.getSheetAt(index);if (sheet == null) return map;
        int rows = sheet.getPhysicalNumberOfRows();
        if (rows < startRowNum) return map;

        //第一行内容
        Row firstRow = sheet.getRow(startRowNum);
        int cells = firstRow.getPhysicalNumberOfCells();
        String[] cellNames = new String[cells];
        for (int i = 0; i < cells; i++) {
            Cell cell = firstRow.getCell(i);
            CellType cellType = cell.getCellType();
            switch (cellType){
                case STRING:
                    cellNames[i] = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    cellNames[i] = (int)cell.getNumericCellValue()+"";
                    break;
                default:
                    break;

            }
        }


        //其他行的内容（暂只获取数值和字符串）
        for (int i = startRowNum+1; i < rows; i++) {
            Row row = sheet.getRow(i);
            Cell firstCell = row.getCell(0);
            String key = firstCell.getStringCellValue();
            Map<String , String> cellMap = new LinkedHashMap<>();
            for (int j = 1; j < cells; j++) {
                Cell cell = row.getCell(j);
                CellType cellType = cell.getCellType();
                String value = null;
                switch(cellType){
                    case STRING:
                        value = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        value = cell.getNumericCellValue()+"";
                        break;
                    default:
                        break;
                }
                cellMap.put(cellNames[j],value);
            }
            map.put(key,cellMap);
        }

        return map;
    }



}
