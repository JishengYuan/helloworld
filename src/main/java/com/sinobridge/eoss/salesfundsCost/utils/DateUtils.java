/*
 * FileName: DateUtils.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.salesfundsCost.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author vermouth
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年3月1日 上午11:17:42          vermouth        1.0         To create
 * </p>
 *
 * @since 
 * @see     
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
     * <code>getLatMaoth</code>
     * 得到当前时间的上一个月
     * @return
     * @since   2014年7月4日    guokemenng
     */
    public static Date getLastMonth() {
        String pattern = "yyyy-MM-dd";
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -1);
        String newTime = String.valueOf(cd.get(Calendar.YEAR)) + "-" + String.valueOf(cd.get(Calendar.MONTH) + 1) + "-" + String.valueOf(cd.get(Calendar.DAY_OF_MONTH));
        return convertStringToDate(newTime, pattern);
    }

    /**
     * <code>getLatMaoth</code>
     * 得到当前时间的下一个月
     * @return
     * @since   2016年4月21日   wangya
     */
    public static Date getNextMonth() {
        String pattern = "yyyy-MM-dd";
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, 1);
        String newTime = String.valueOf(cd.get(Calendar.YEAR)) + "-" + String.valueOf(cd.get(Calendar.MONTH) + 1) + "-" + String.valueOf(cd.get(Calendar.DAY_OF_MONTH));
        return convertStringToDate(newTime, pattern);
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
     * <code>getLastThreeMonthDay</code>
     * 前三个月
     * @return
     * @since   2015年1月15日    guokemenng
     */
    public static Date getLastThreeMonthDay() {
        String pattern = "yyyy-MM-dd";
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -3);
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
     * <code>getMondayPlus</code>
     * 获得当前日期与本周一相差的天数
     * @return
     * @since   2014年7月7日    guokemenng
     */
    public static int getMondayPlus(Calendar cd) {
        //获得今天是一周的第几天，星期日是第一天，星期一是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * <code>getLastWeekTime</code>
     * 得到当前时间的上一周时间的日期数组{周一，周日}
     * @return
     * @since   2014年7月7日    guokemenng
     */
    public static Date[] getLastWeekTime() {
        Date[] dates = new Date[2];

        Calendar cd = Calendar.getInstance();
        String pattern = "yyyy-MM-dd";
        //        Date time = new Date();
        //        String timeStr = null;
        //        try {
        //            timeStr = convertDateToString(time, pattern);
        //        } catch (ParseException e) {
        //            e.printStackTrace();
        //        }
        //        String[] startStrs = timeStr.split("-");
        //        GregorianCalendar timeC = new GregorianCalendar(Integer.parseInt(startStrs[0]), Integer.parseInt(startStrs[1]) - 1, Integer.parseInt("6"));
        cd.add(Calendar.DATE, getMondayPlus(cd));
        cd.add(Calendar.DATE, -7);
        String newTime = String.valueOf(cd.get(Calendar.YEAR)) + "-" + String.valueOf(cd.get(Calendar.MONTH) + 1) + "-" + String.valueOf(cd.get(Calendar.DAY_OF_MONTH));
        dates[0] = convertStringToDate(newTime, pattern);
        cd.add(Calendar.DATE, 6);
        String newTime1 = String.valueOf(cd.get(Calendar.YEAR)) + "-" + String.valueOf(cd.get(Calendar.MONTH) + 1) + "-" + String.valueOf(cd.get(Calendar.DAY_OF_MONTH));
        dates[1] = convertStringToDate(newTime1, pattern);
        return dates;
    }

    public static Date[] getCurrentWeekTime(Date time) {
        Date[] dates = new Date[2];

        String timeStr = null;
        String pattern = "yyyy-MM-dd";
        try {
            timeStr = convertDateToString(time, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] startStrs = timeStr.split("-");
        GregorianCalendar timeC = new GregorianCalendar(Integer.parseInt(startStrs[0]), Integer.parseInt(startStrs[1]) - 1, Integer.parseInt(startStrs[2]));
        timeC.add(Calendar.DATE, getMondayPlus(timeC));
        String newTime = String.valueOf(timeC.get(Calendar.YEAR)) + "-" + String.valueOf(timeC.get(Calendar.MONTH) + 1) + "-" + String.valueOf(timeC.get(Calendar.DAY_OF_MONTH));
        dates[0] = convertStringToDate(newTime, pattern);
        timeC.add(Calendar.DATE, 6);
        String newTime1 = String.valueOf(timeC.get(Calendar.YEAR)) + "-" + String.valueOf(timeC.get(Calendar.MONTH) + 1) + "-" + String.valueOf(timeC.get(Calendar.DAY_OF_MONTH));
        dates[1] = convertStringToDate(newTime1, pattern);
        return dates;
    }

    /**
     * <code>getMonthFirstDay</code>
     * 得到本月的第一天   
     * @return
     * @since   2014年7月7日    guokemenng
     */
    public static Date getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    /**
     * <code>getMonthLastDay</code>
     * 得到本月的最后一天
     * @return
     * @since   2014年7月7日    guokemenng
     */
    public static Date getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * <code>getCurrentMonthTime</code>
     * 得到当前是时间的第一个和最后一天的时间数组{月份第一天，月份最后一天}
     * @return
     * @since   2014年7月7日    guokemenng
     */
    public static Date[] getCurrentMonthTime() {
        Date[] dates = new Date[2];
        dates[0] = getMonthFirstDay();
        dates[1] = getMonthLastDay();
        return dates;
    }

    public static long dateDiff(String startDate, String endDate) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar endGC = new GregorianCalendar();
        long times, days1 = 0l;
        try {
            times = sd.parse(endDate).getTime() - sd.parse(startDate).getTime();
            long days = times / (1000 * 24 * 60 * 60);
            days1 = (days / 7) * 5;
            long days2 = days % 7;
            endGC.setTime(sd.parse(endDate));
            int weekDay = endGC.get(Calendar.DAY_OF_WEEK);
            if (weekDay == 1) {
                days1 += days2 > 2 ? days2 - 2 : 0;
            } else if (weekDay == 7) {
                days1 += days2 > 1 ? days2 - 1 : 0;
            } else if (weekDay - 1 < days2) {
                days1 += days2 - 2;
            } else if (weekDay - 1 > days2) {
                days1 += days2;
            } else if (weekDay - 1 == days2) {
                days1 += weekDay - 1;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return days1;
    }

    /**
     * <code>getDuringThreeMonths</code>
     * 从当前时间算起   得到前三个月区间时间
     * key 值：startTime endTime
     * @return
     * @since   2014年11月6日    guokemenng
     */
    public static Map<String, Date> getDuringThreeMonths() {
        Map<String, Date> map = new HashMap<String, Date>();

        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -3);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMinimum(Calendar.DAY_OF_MONTH));
        map.put("startTime", cd.getTime());

        Calendar cd1 = Calendar.getInstance();
        cd1.add(Calendar.MONTH, -1);
        cd1.set(Calendar.DAY_OF_MONTH, cd1.getActualMaximum(Calendar.DAY_OF_MONTH));
        map.put("endTime", cd1.getTime());

        return map;
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
     * <code>getMonth</code>
     * 得到给定时间的 该月份第一天和最后一天的数组
     * @param d
     * @return
     * @since   2014年12月31日    guokemenng
     */
    public static Date[] getMonth(Date d) {
        Date[] dS = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        dS[0] = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        dS[1] = calendar.getTime();
        return dS;
    }

    /**
     * <code>someDayAgo</code>
     * 给定时间后7天
     * @param sdf 
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     * @since   2015年9月7日    wangya
     */
    public static String someDayAgo(Date time, int i) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time.getTime());
        c.add(Calendar.DATE, +i);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
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

    public static int timeCompare(String t1, String t2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            c2.setTime(formatter.parse(t2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        return result;
    }

    public static void main(String[] args) {

        //        String startDate="2014-10-10";
        //        String endDate="2014-10-14";

        try {
            Date[] dS = getMonth(new Date());
            System.out.println(getLastThreeMonthDay());
            System.out.println(convertDateToString(dS[1], "yyyy-MM-dd"));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //        Map<String, Date> map = getDuringThreeMonths();
        //        String pattern = "yyyy-MM-dd";
        //        try {
        //            System.out.println(convertDateToString(map.get("startTime"), pattern));
        //            System.out.println(convertDateToString(map.get("endTime"), pattern));
        //        } catch (ParseException e) {
        //            e.printStackTrace();
        //        }

        //        String startTime = "2014-01-01";
        //        String[] startStrs = startTime.split("-");
        //        GregorianCalendar time1 = new GregorianCalendar(Integer.parseInt(startStrs[0]), Integer.parseInt(startStrs[1]) - 1, Integer.parseInt(startStrs[2]));
        //        time1.add(Calendar.MONTH, -1);
        //        String newTime = String.valueOf(time1.get(Calendar.YEAR)) + "-" + String.valueOf(time1.get(Calendar.MONTH) + 1) + "-" + String.valueOf(time1.get(Calendar.DAY_OF_MONTH));
        //        Date newDate = convertStringToDate(newTime, "yyyy-MM-dd");
        //        System.out.println(newTime);
        //        System.out.println(newDate.getMonth());

        //        Calendar c = Calendar.getInstance();
        //        c.setTime(new Date(System.currentTimeMillis()));
        //        System.out.println(c.get(Calendar.DAY_OF_MONTH));
        //        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        //        switch (dayOfWeek) {
        //            case 1:
        //                System.out.println("星期日");
        //                break;
        //            case 2:
        //                System.out.println("星期一");
        //                break;
        //            case 3:
        //                System.out.println("星期二");
        //                break;
        //            case 4:
        //                System.out.println("星期三");
        //                break;
        //            case 5:
        //                System.out.println("星期四");
        //                break;
        //            case 6:
        //                System.out.println("星期五");
        //                break;
        //            case 7:
        //                System.out.println("星期六");
        //                break;
        //        }
        //        Date[] dates = getLastWeekTime();
        //        String pattern = "yyyy-MM-dd";
        //        try {
        //            System.out.println(convertDateToString(dates[0],pattern)+"==="+convertDateToString(dates[1],pattern));
        //        } catch (ParseException e) {
        //            // TODO Auto-generated catch block
        //            e.printStackTrace();
        //        }
        //        Date[] dates = getCurrentWeekTime(new Date());
        //        String pattern = "yyyy-MM-dd";
        //        try {
        //            System.out.println(convertDateToString(dates[0], pattern) + "===" + convertDateToString(dates[1], pattern));
        //        } catch (ParseException e) {
        //            // TODO Auto-generated catch block
        //            e.printStackTrace();
        //        }

    }
}
