package com.cchcz.blog.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * created by zhangcheng 2018/8/5
 */
public class DateUtil2 {

    private DateUtil2() {
    }

    public static String YYYY = "yyyy";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYYMMDD_DOT = "yyyy.MM.dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.s";
    public static String YYYYMMDDHH = "yyyyMMddHH";
    public static String YYMMDDHHMMSS = "yyMMddHHmmss";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYYMMDDHHMMSS_DOT = "yyyy.MM.dd.HH.mm.ss";
    public static String YYMMDDHHMM = "yyMMddHHmm";
    public static String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static String MM_DD_HH_MM = "MM-dd HH:mm";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String HH_MM = "HH:mm";

    /**
     * @param date
     * @param format
     * @return
     */
    public static String formatToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static Date formatToDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date addDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    public static Date setHour(Date date, int hour, int second, int minute, int millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.MILLISECOND, millisecond);
        return cal.getTime();
    }

    public static int betweenDays(long time1, long time2) {
        return (int) ((time2 - time1) / (1000 * 3600 * 24));
    }

    public static String getCurrenttYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    public static String getPreYear() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR, -1);
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    public static long getTimeMillis(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTimeZone());
        calendar.set(year, month - 1, date);
        return calendar.getTimeInMillis();
    }

    public static int getYear(long timeMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        return cal.get(Calendar.YEAR);
    }

    public static long getCurrentYearTime() {
        Date date = new Date();//取时间
        String dateValue = formatToString(date, DateUtil2.YYYY);
        long time = formatToDate(dateValue, DateUtil2.YYYY).getTime();
        return time;
    }

    public static long getCurrentDayTime() {
        Date date = new Date();//取时间
        String dateValue = formatToString(date, DateUtil2.YYYY_MM_DD);
        long time = formatToDate(dateValue, DateUtil2.YYYY_MM_DD).getTime();
        return time;
    }

    public static Date getManyDayBefore(int dayNumber) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -dayNumber);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();
    }

    public static long getDayTimeBefore(int dayNumber) {
        long yesterday = getManyDayBefore(dayNumber).getTime();
        String yesterdayValue = formatToString(new Date(yesterday), YYYY_MM_DD);
        return formatToDate(yesterdayValue, YYYY_MM_DD).getTime();
    }

    public static String getDayBefore(int dayNumber) {
        long yesterday = getManyDayBefore(dayNumber).getTime();
        return formatToString(new Date(yesterday), YYYY_MM_DD);
    }

    public static String getLastTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastBeginDate = formatToString(calendar1.getTime(), YYYY_MM_DD_HH_MM_SS);
        String lastEndDate = formatToString(calendar2.getTime(), YYYY_MM_DD_HH_MM_SS);
        return lastBeginDate + "," + lastEndDate;
    }

    public static Date getTomorrow() {
        Date date = new Date();
        long time = date.getTime() / 1000L + 86400L;
        date.setTime(time * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = format.parse(format.format(date));
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }

        return date;
    }

    public static void main(String[] args) throws Exception {
        Date tomorrow = getTomorrow();
        System.out.println(tomorrow+"---->"+formatToString(tomorrow,YYYY_MM_DD_HH_MM_SS)
                +"---->"+formatToDate(formatToString(tomorrow,YYYY_MM_DD_HH_MM_SS),YYYY_MM_DD_HH_MM_SS).getTime());

        System.out.println(formatToDate("2018-08-01 00:00:00",YYYY_MM_DD_HH_MM_SS).getTime());
    }

}
