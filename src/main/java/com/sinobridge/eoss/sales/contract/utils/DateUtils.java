package com.sinobridge.eoss.sales.contract.utils;

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
public class DateUtils {

    /**
     * <code>convertStringToDate</code>
     * 字符转转行成时间
     * @param dateStr
     * @param pattern
     * @return
     * @since   2014年4月25日    guokemenng
     */
    public static Date convertStringToDate(String dateStr, String pattern) {
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
    public static String convertDateToString(Date date, String pattern) throws ParseException {
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
    public static List<Date> getDuringDays(String startTime, String endTime) {
        Assert.notNull(startTime);
        Assert.notNull(endTime);

        List<Date> dList = new ArrayList<Date>();
        String pattern = "yyyy-MM-dd";
        String[] startStrs = startTime.split("-");
        String[] endStrs = endTime.split("-");

        GregorianCalendar time1 = new GregorianCalendar(Integer.parseInt(startStrs[0]), Integer.parseInt(startStrs[1]) - 1, Integer.parseInt(startStrs[2]));
        GregorianCalendar time2 = new GregorianCalendar(Integer.parseInt(endStrs[0]), Integer.parseInt(endStrs[1]) - 1, Integer.parseInt(endStrs[2]));

        dList.add(convertStringToDate(startTime, pattern));
        while (time2.after(time1)) {
            time1.add(Calendar.DATE, 1);
            String newTime = String.valueOf(time1.get(Calendar.YEAR)) + "-" + String.valueOf(time1.get(Calendar.MONTH) + 1) + "-" + String.valueOf(time1.get(Calendar.DAY_OF_MONTH));
            Date newDate = convertStringToDate(newTime, pattern);
            dList.add(newDate);
        }
        return dList;
    }

    /**
     * <code>getStartTime</code>
     * 得到今天的开始时间
     * @return
     * @since   2014年4月25日    3unshine
     */
    public static long getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * <code>getDuringDays</code>
     * 得到今天的结束时间
     * @return
     * @since   2014年4月25日    3unshine
     */
    public static long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    /**
     * <code>getLastMonthDay</code>
     * 得到当前时间的上一个月 从第一天算起
     * @return
     * @since   2014年7月16日    guokemenng
     */
    public static Date getLastMonthDay() {
        String pattern = "yyyy-MM-dd";
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -1);
        String newTime = String.valueOf(cd.get(Calendar.YEAR)) + "-" + String.valueOf(cd.get(Calendar.MONTH) + 1) + "-" + String.valueOf("01");
        return convertStringToDate(newTime, pattern);
    }

    /**
     * <code>getLastMonthDayEnd</code>
     * 得到当前时间的上一个月 从最后一天算起
     * @return
     * @since   2014年12月27日    guokemenng
     */
    public static Date getLastMonthDayEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    
    /**
     * <code>someDayAgo</code>
     * 给定时间前60天
     * @param sdf 
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     * @since   2015年9月7日    wangya
     */
    public static String someDayAgo(Date time,int i) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();  
        c.setTimeInMillis(time.getTime());
        c.add(Calendar.DATE, - i);  
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }
   
}
