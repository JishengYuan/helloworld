package com.sinobridge.eoss.business.order.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.util.Assert;

/**
 * <code>DateUtils</code>
 * 
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2014年4月25日
 */
public class DateUtils{

    /**
     * <code>convertStringToDate</code>
     * 字符转转行成时间
     * @param dateStr
     * @param pattern
     * @return
     * @since   2014年4月25日    guokemenng
     */
    public static Date convertStringToDate(String dateStr,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * <code>convertDateToString</code>
     * 时间转换成字符串
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     * @since   2014年4月25日    guokemenng
     */
    public static String convertDateToString(Date date,String pattern) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
    
    /**
     * <code>getDuringDays</code>
     * 得到两个时间段的天数  时间格式为 yyyy-mm-dd
     * @param startTime
     * @param endTime
     * @return
     * @since   2014年4月25日    guokemenng
     */
    public static List<Date> getDuringDays(String startTime,String endTime){
        Assert.notNull(startTime);
        Assert.notNull(endTime);
        
        List<Date> dList = new ArrayList<Date>();
        String pattern = "yyyy-MM-dd";
        String[] startStrs = startTime.split("-");
        String[] endStrs = endTime.split("-");
        
        GregorianCalendar time1 = new GregorianCalendar(Integer.parseInt(startStrs[0]), Integer.parseInt(startStrs[1]) - 1, Integer.parseInt(startStrs[2]));
        GregorianCalendar time2 = new GregorianCalendar(Integer.parseInt(endStrs[0]), Integer.parseInt(endStrs[1]) - 1, Integer.parseInt(endStrs[2]));
        
        dList.add(convertStringToDate(startTime, pattern));
        while(time2.after(time1)){
            time1.add(Calendar.DATE, 1);
            String newTime = String.valueOf(time1.get(Calendar.YEAR)) + "-" + String.valueOf(time1.get(Calendar.MONTH) + 1) + "-" + String.valueOf(time1.get(Calendar.DAY_OF_MONTH));
            Date newDate = convertStringToDate(newTime, pattern);
            dList.add(newDate);
        }
        return dList;
    }
}
