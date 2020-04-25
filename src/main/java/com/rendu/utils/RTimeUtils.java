package com.rendu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @ClassName RTimeUtils
 * @Description: TODO
 * @Author: li
 * @Date: 2020/3/19 10:05
 * @Version v1.0
 **/
public class RTimeUtils {
    
    /**
     * 获取某天的时间
     * @param index 为正表示当前时间加小时数，为负表示当前时间减小时数
     * @return String
     */
    
    public static String getTimeHour(int index){
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.add(Calendar.HOUR_OF_DAY,index);
        String date = fmt.format(calendar.getTime());
        return date;
    }
    
    /**
     * 获取某天的时间
     * @param index 为正表示当前时间加分钟数，为负表示当前时间减分钟数
     * @return String
     */
    public static String getTimeMinute(int index){
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.add(Calendar.MINUTE,index);
        String date = fmt.format(calendar.getTime());
        return date;
    }
    
    /**
     * 将String类型的时间戳转化为String类型的时间格式
     * */
    
    public static String stringToDate(String s){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setTime(Long.parseLong(s + "000"));
        s = sdf.format(date);
        return s;
    }
    
    
    
    public static int queryDateHTransInt(String queryDate){
        int result = Integer.parseInt(queryDate.substring(0,queryDate.length()-1));
        return (~result+1);
    }
    
    public static int queryDateMTransInt(String queryDate){
        int result = Integer.parseInt(queryDate.substring(0,queryDate.length()-1));
        return (~result+1);
    }
    
    public static String queryDateHTransTimeStamp(String queryDate){
        return getTimeHour(queryDateHTransInt(queryDate));
    }
    
    public static String queryDateMTransTimeStamp(String queryDate){
        return getTimeMinute(queryDateMTransInt(queryDate));
    }
}
