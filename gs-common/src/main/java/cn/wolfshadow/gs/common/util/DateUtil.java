package cn.wolfshadow.gs.common.util;

import lombok.SneakyThrows;
import org.apache.http.client.utils.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 复用年轻时写的代码
 */
public class DateUtil {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYY_MM_DD_hh = "yyyy-MM-dd hh";
    public static final String YYYY_MM_DD_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_hh_mm = "yyyy-MM-dd hh:mm";
    public static final String YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";
    public static final String YYYY_MM_DD_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYY_MM_DD_hh_mm_ss_SSS = "yyyy-MM-dd hh:mm:ss.SSS";

    public static final String YYYY_MM_DD2 = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH2 = "yyyy/MM/dd HH";
    public static final String YYYY_MM_DD_hh2 = "yyyy/MM/dd hh";
    public static final String YYYY_MM_DD_HH_mm2 = "yyyy/MM/dd HH:mm";
    public static final String YYYY_MM_DD_hh_mm2 = "yyyy/MM/dd hh:mm";
    public static final String YYYY_MM_DD_HH_mm_ss2 = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYY_MM_DD_hh_mm_ss2 = "yyyy/MM/dd hh:mm:ss";
    public static final String YYYY_MM_DD_HH_mm_ss_SSS2 = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String YYYY_MM_DD_hh_mm_ss_SSS2 = "yyyy/MM/dd hh:mm:ss.SSS";

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_HH = "yyyyMMdd HH";
    public static final String YYYYMMDD_hh = "yyyyMMdd hh";
    public static final String YYYYMMDD_HH_mm = "yyyyMMdd HH:mm";
    public static final String YYYMMDD_hh_mm = "yyyyMMdd hh:mm";
    public static final String YYYYMMDD_HH_mm_ss = "yyyyMMdd HH:mm:ss";
    public static final String YYYYMMDD_hh_mm_ss = "yyyyMMdd hh:mm:ss";
    public static final String YYYYMMDD_HH_mm_ss_SSS = "yyyyMMdd HH:mm:ss.SSS";
    public static final String YYYYMMDD_hh_mm_ss_SSS = "yyyyMMdd hh:mm:ss.SSS";

    public static final long MILLISECONDS_ONE_DAY = 24 * 60 * 60 * 1000;

    /**
     * 时间格式化（不带秒钟数）
     * @param date
     * @return
     * @author ServerZhang
     * @date 2016年4月6日
     */
    public static String toStrWithoutSeconds(Date date) {
        return getDateStr(date,YYYY_MM_DD_HH_mm);
    }
    /**
     * 时间格式化（带秒钟数）
     * @param date
     * @return
     * @author ServerZhang
     * @date 2016年4月6日
     */
    public static String toStrWithSeconds(Date date) {
        return getDateStr(date,YYYY_MM_DD_HH_mm_ss);
    }
    /**
     * 时间处理为字符串
     * @param date
     * @param dateType DateType
     * @return
     * @author ServerZhang
     * @date 2016年4月18日
     */
    public static String getDateStr(Date date,String dateType) {
        if (date == null)
            return "";
        SimpleDateFormat formatter = new SimpleDateFormat(
                dateType);
        return formatter.format(date);
    }

    /**
     * 字符串处理为时间
     * @param dateStr
     * @return
     * @author ServerZhang
     * @date 2016年4月6日
     */
    public static Date toDate(String dateStr) {
        return toDate(dateStr,YYYY_MM_DD_HH_mm_ss);
    }
    public static Date toDate(String dateStr, String dateType) {
        try {
            DateFormat format = new SimpleDateFormat(dateType);
            return format.parse(dateStr);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 从初始时间到结束时间的所有月份区间
     * @param startDate
     * @return
     * @author ServerZhang
     * @date 2016年6月23日
     */
    public static List<Map<String, Object>> getMonthPeriod(Date startDate, Date endDate){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Calendar instance = Calendar.getInstance();

        instance.setTime(startDate);
        instance.get(Calendar.YEAR);
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH)+1;

        instance.setTime(endDate);
        int endYear = instance.get(Calendar.YEAR);
        int endMonth = instance.get(Calendar.MONTH)+1;
        for(int i=endYear; i>=endYear; i--){
            int start = 12;
            int end = 1;
            if (i == year) {
                end = month;
            }
            if (i == endYear) {
                start = endMonth;
            }
            for (int j = start; j >= end; j--) {
                String tempJ = j > 10 ? j+"" : "0"+j;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("periodText", i+"年"+tempJ+"月");
                map.put("periodValue", i+"-"+tempJ);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 根据日期计算星期几
     * @param date
     * @return
     * @author ServerZhang
     * @date 2017年12月4日
     */
    public static int dayInWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int index = cal.get(Calendar.DAY_OF_WEEK);
        if (index == 1) {
            return 7;
        }
        return index-1;
    }

    /**
     * 计算两个日期相差几天
     * @param date1
     * @param date2
     * @return
     * @author ServerZhang
     * @date 2017年12月4日
     */
    public static int daysBetween(Date date1,Date date2){
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        int day = 0;
        if (time1 > time2) {
            day = (int)((time1-time2)/(24*60*60*1000));
        }else if (time1 < time2) {
            day = (int)((time2-time1)/(24*60*60*1000));
        }else {
        }
        return day;
    }

    /**
     * @Description 当前日期增加天数
     * @Author wolfshadow.cn
     * @Date  2020/8/29 15:54
     * @Param [date, day]
     * @return java.util.Date
     */
    public static Date add(Date date, int day) {
        long time = date.getTime();
        return new Date(time+day*MILLISECONDS_ONE_DAY);
    }
}
