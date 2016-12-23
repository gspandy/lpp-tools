/**
* 文件名：TimeUtils.java
* 创建日期： 2016年8月14日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年8月14日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 功能描述：日期转换工具类
 */
public abstract class DateUtils {

    /**
     * 常用日期格式集
     * Author:lipanpan</br>
     * Date:2016年12月23日</br>
     * Description:</br>
     * Copyright (c) 2016 code</br>
     */
    static enum DateFormat {

        DATE_FORMAT_1("yyyy-MM-dd HH:mm:ss", "\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"),
        DATE_FORMAT_2("yyyy-MM-dd", "\\d{4}-\\d{2}-\\d{2}"),
        DATE_FORMAT_3("yyyy/MM/dd HH:mm:ss", "\\d{4}/\\d{2}/\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"),
        DATE_FORMAT_4("yyyy/MM/dd", "\\d{4}/\\d{2}/\\d{2}"),
        DATE_FORMAT_5("yyyy年MM月dd日 HH:mm:ss", "\\d{4}年\\d{2}月\\d{2}日\\s+\\d{2}:\\d{2}:\\d{2}"),
        DATE_FORMAT_6("yyyy年MM月dd日", "\\d{4}年\\d{2}月\\d{2}日"),
        DATE_FORMAT_7("yyyy.MM.dd HH:mm:ss", "\\d{4}.\\d{2}.\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"),
        DATE_FORMAT_8("yyyy.MM.dd", "\\d{4}.\\d{2}.\\d{2}"),
        DATE_FORMAT_9("yyyy.M", "\\d{4}.\\d{1,2}");

        private String format = null;
        private String pattern = null;

        private DateFormat(String format, String pattern) {
            this.format = format;
            this.pattern = pattern;
        }
        
        public String getFormat() {
            return format;
        }
        
        public String getPattern() {
            return pattern;
        }
        
        /**
         * 日期格式匹配
         * @param formatDate
         * @return
         */
        public static DateFormat toDateFormat(String formatDate) {
            if (formatDate.matches(DATE_FORMAT_1.getPattern())) {
                return DATE_FORMAT_1;
            } else if (formatDate.matches(DATE_FORMAT_2.getPattern())) {
                return DATE_FORMAT_2;
            } else if (formatDate.matches(DATE_FORMAT_3.getPattern())) {
                return DATE_FORMAT_3;
            } else if (formatDate.matches(DATE_FORMAT_4.getPattern())) {
                return DATE_FORMAT_4;
            } else if (formatDate.matches(DATE_FORMAT_5.getPattern())) {
                return DATE_FORMAT_5;
            } else if (formatDate.matches(DATE_FORMAT_6.getPattern())) {
                return DATE_FORMAT_6;
            } else if (formatDate.matches(DATE_FORMAT_7.getPattern())) {
                return DATE_FORMAT_7;
            } else if (formatDate.matches(DATE_FORMAT_8.getPattern())) {
                return DATE_FORMAT_8;
            } else if (formatDate.matches(DATE_FORMAT_9.getPattern())) {
                return DATE_FORMAT_9;
            } else {
                return null;
            }
        }
    }

    /**
     * 以默认格式(yyyy-MM-dd)，格式化日期
     * @param date
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DateFormat.DATE_FORMAT_2.getFormat());
        return format.format(date);
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, DateFormat dateformat) {
        SimpleDateFormat format = new SimpleDateFormat(dateformat.getFormat());
        return format.format(date);
    }

    /**
     * 将日期字符串转换为Date对象
     * @param formatDate
     * @return
     */
    public static Date toDate(String formatDate) {
        DateFormat dateFormat = DateFormat.toDateFormat(formatDate);
        return toDate(formatDate, dateFormat);
    }

    /**
     * 将日期字符串以指定格式转换为Date对象
     * @param formatDate
     * @return
     */
    public static Date toDate(String formatDate, DateFormat dateformat) {
        SimpleDateFormat format = new SimpleDateFormat(dateformat.getFormat());
        try {
            return format.parse(formatDate);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 比较两个日期之间的天数差异，例如：如果left比right晚一天，返回1，如果相等返回0，
     * 如果left比right早一天，返回-1
     * @param left
     * @param right
     * @return int 差异天数
     */
    public static int getDiffDays(Date left, Date right) {
        GregorianCalendar leftCaldr = new GregorianCalendar();
        GregorianCalendar rightCaldr = new GregorianCalendar();
        leftCaldr.setTime(left);
        rightCaldr.setTime(right);

        // 清除掉时，分，秒等时间，仅计算天数差
        leftCaldr.set(GregorianCalendar.HOUR_OF_DAY, 0);
        leftCaldr.set(GregorianCalendar.MINUTE, 0);
        leftCaldr.set(GregorianCalendar.SECOND, 0);
        leftCaldr.set(GregorianCalendar.MILLISECOND, 0);

        rightCaldr.set(GregorianCalendar.HOUR_OF_DAY, 0);
        rightCaldr.set(GregorianCalendar.MINUTE, 0);
        rightCaldr.set(GregorianCalendar.SECOND, 0);
        rightCaldr.set(GregorianCalendar.MILLISECOND, 0);

        // 将日期时间转换为毫秒数
        long leftMilSec = leftCaldr.getTimeInMillis();
        long rightMilSec = rightCaldr.getTimeInMillis();

        long res = (leftMilSec - rightMilSec) / (24L * 60L * 60L * 1000L);

        return (int) res;
    }
    

    public static void main(String[] args) {
        System.out.println(toDate("2016年12月23日"));
        System.out.println(getDiffDays(new Date(), toDate("2016年12月22日")));
    }

}
