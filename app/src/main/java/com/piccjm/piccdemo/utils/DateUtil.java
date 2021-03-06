package com.piccjm.piccdemo.utils;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * Created by mangowangwang on 2017/10/23.
 */

public class DateUtil {

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


        public static int getHourOfDay(Date dt) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            return hour;

        }



    /**
     * 获取当前日期的星期
     * @return
     */
    public static String getDayOfWeekFromDate(String dateString)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format.applyPattern("EEEE");
        return format.format(date);
    }


    /**
     * 获取当前日期
     * @param format  日期格式
     * @return
     */
    public static String getCurrentDate(String format)
    {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取明天日期
     * @return
     */
    public static String getTomorrowDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return String.valueOf(Integer.valueOf(df.format(new Date()))+1);
    }


    /**
     * 获取当期日期字符串
     * @return
     */
    public static String getCurrentDateString()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(new Date());
    }

    /**
     * 获取当前的年
     * @return
     */

    public static int getCurrentYear()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前的月
     * @return
     */

    public static int getCurrentMonth()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }


    /**
     * 获取当前的日
     * @return
     */

    public static int getCurrentDay()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    /**
     * 切割标准时间
     * @param time
     * @return
     */
    @Nullable
    public static String subStandardTime(String time)
    {
        int idx  = time.indexOf(".");
        if (idx > 0) {
            return time.substring(0, idx).replace("T"," ");
        }
        return null;
    }


    /**
     * 将时间戳转化为字符串
     * @param showTime
     * @return
     */
    public static String formatTime2String(long showTime) {
        return formatTime2String(showTime,false);
    }

    /**
     * 判断距离发表时间
     */
    public static String formatTime2String(long showTime , boolean haveYear) {
        String str = "";
        long distance = currentTimeMillis()/1000 - showTime;
        if(distance < 300){
            str = "刚刚";
        }else if(distance >= 300 && distance < 600){
            str = "5分钟前";
        }else if(distance >= 600 && distance < 1200){
            str = "10分钟前";
        }else if(distance >= 1200 && distance < 1800){
            str = "20分钟前";
        }else if(distance >= 1800 && distance < 2700){
            str = "半小时前";
        }else if(distance >= 2700){
            Date date = new Date(showTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = formatDateTime(sdf.format(date) , haveYear);
        }
        return str;
    }

    /**
     * 判断距离发表日期的时长
     * @param time
     * @return
     */
    public static String formatDate2String(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time == null){
            return "未知";
        }
        try {
            long createTime = format.parse(time).getTime() / 1000;
            long currentTime = currentTimeMillis() / 1000;
            if (currentTime - createTime - 24 * 3600 > 0) { //超出一天
                return (currentTime - createTime) / (24 * 3600) + "天前";
            } else {
                return (currentTime - createTime) / 3600 + "小时前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "未知";
    }

    /**
     * 判断发表时间是今天还是昨天
     * @param time
     * @param haveYear
     * @return
     */

    public static String formatDateTime(String time ,boolean haveYear) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time == null){
            return "";
        }
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH)-1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        if(current.after(today)){
            return "今天 "+time.split(" ")[1];
        }else if(current.before(today) && current.after(yesterday)){
            return "昨天 "+time.split(" ")[1];
        }else{
            if(haveYear) {
                int index = time.indexOf(" ");
                return time.substring(0,index);
            }else {
                int yearIndex = time.indexOf("-")+1;
                int index = time.indexOf(" ");
                return time.substring(yearIndex,time.length()).substring(0,index);
            }
        }
    }












}
