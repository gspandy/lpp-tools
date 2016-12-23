package lpp.tools.comm;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 设计模式：外观模式运用，通过对Calendar的包装，让时间处理更简单灵活
 * Author:lipanpan</br>
 * Date:2016年12月23日</br>
 * Description:</br>
 * Copyright (c) 2016 code</br> 
 */
public class Time implements Serializable, Cloneable, Comparable<Time> {

    private static final long serialVersionUID = 1L;

    private Calendar cal = null;

    /**
     * 取当前时间及系统默认时区
     */
    public Time() {
        cal = Calendar.getInstance();
    }

    public Time(TimeZone zone, Locale aLocale) {
        cal = Calendar.getInstance(zone, aLocale);
    }

    /**
     * 指定时间毫秒数
     * @param millis
     */
    public Time(long millis) {
        this();
        cal.setTimeInMillis(millis);
    }

    /**
     * 指定日期的Date
     * @param date
     */
    public Time(Date date) {
        this();
        cal.setTime(date);
    }

    /**
     * 获取当前时间的年份，例如：2016
     * @return
     */
    public int year() {
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份，例如：8
     * @return
     */
    public int month() {
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前时间几号，例如：29
     * @return
     */
    public int dayOfMonth() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间小时(24小时制),例如：18
     * @return
     */
    public int hourOfDay() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取周的第几天，星期天返回1，星期6返回7
     * @return
     */
    public int dayOfWeek() {
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取本月的第几周
     * @return
     */
    public int weekOfMonth() {
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取本年的第几周
     * @return
     */
    public int weekOfYear() {
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取本年的第几季度
     * @return
     */
    public int quarterOfYear() {
        int month = month();
        if (month >= 1 && month <= 3) {
            return 1;
        } else if (month > 3 && month <= 6) {
            return 2;
        } else if (month > 6 && month <= 9) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * 星期格式转换
     * @param preffix 前缀：星期或周等
     * @return 星期六
     */
    public String dayOfWeek(String preffix) {
        if (StringUtils.isBlank(preffix)) {
            preffix = "星期";
        }
        int weekNum = dayOfWeek();
        switch (weekNum) {
            case 1:
                return preffix + "日";
            case 2:
                return preffix + "一";
            case 3:
                return preffix + "二";
            case 4:
                return preffix + "三";
            case 5:
                return preffix + "四";
            case 6:
                return preffix + "五";
            case 7:
                return preffix + "六";
            default:
                break;
        }
        return null;
    }

    /**
     * 在当前时间基础上，增加num年，num值可以是负数，0，正数
     * @param num
     * @return
     */
    public Time addYear(int num) {
        cal.add(Calendar.YEAR, num);
        return this;
    }

    /**
     * 在当前时间基础上，增加num月，num值可以是负数，0，正数
     * @param num
     * @return
     */
    public Time addMonth(int num) {
        cal.add(Calendar.MONTH, num);
        return this;
    }

    /**
     * 在当前时间基础上，增加num天，num值可以是负数，0，正数
     * @param num
     * @return
     */
    public Time addDay(int num) {
        cal.add(Calendar.DAY_OF_MONTH, num);
        return this;
    }

    /**
     * 在当前时间基础上，增加num小时，num值可以是负数，0，正数
     * @param num
     * @return
     */
    public Time addHour(int num) {
        cal.add(Calendar.HOUR_OF_DAY, num);
        return this;
    }

    /**
     * 在当前时间基础上，增加num分钟，num值可以是负数，0，正数
     * @param num
     * @return
     */
    public Time addMinute(int num) {
        cal.add(Calendar.MINUTE, num);
        return this;
    }

    /**
     * 在当前时间基础上，增加num秒，num值可以是负数，0，正数
     * @param num
     * @return
     */
    public Time addSec(int num) {
        cal.add(Calendar.SECOND, num);
        return this;
    }

    /**
     * 在当前时间基础上，增加num星期，num值可以是负数，0，正数
     * @param num
     * @return
     */
    public Time addWeek(int num) {
        cal.add(Calendar.WEEK_OF_YEAR, num);
        return this;
    }

    /**
     * 获取对应的Date
     * @return
     */
    public Date getDate() {
        return cal.getTime();
    }

    @Override
    public int compareTo(Time o) {
        return getDate().compareTo(o.getDate());
    }

    /**
     * 返回一个当前时间的Time实例
     * @return
     */
    public static Time now() {
        return new Time();
    }


    public static void main(String[] args) {
        System.out.println(Time.now().year());
        System.out.println(Time.now().month());
        System.out.println(Time.now().dayOfWeek());
        System.out.println(Time.now().dayOfMonth());
        System.out.println(Time.now().weekOfMonth());
        System.out.println(Time.now().hourOfDay());
        System.out.println(Time.now().weekOfYear());
        System.out.println(Time.now().dayOfWeek(null));
        System.out.println(Time.now().quarterOfYear());
    }

}
