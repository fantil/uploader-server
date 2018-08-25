package org.fanti.uploader.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */
public class TimeUtil {

    /**
     * 判断当前时间是否在给出的时间范围内
     *
     * @param begin 开始时间,格式要求为HH:mm
     * @param end   结束时间,格式要求为HH:mm
     * @return
     */
    public static boolean isNowInTimeRange(String begin, String end) {
        try {
            //SimpleDateFormat在方法中定义,避免线程不安全造成的错误
            SimpleDateFormat hourAndMinSDF = new SimpleDateFormat("HH:mm");
            Date now = hourAndMinSDF.parse(hourAndMinSDF.format(new Date()));
            Date beginTime = hourAndMinSDF.parse(begin);
            Date endTime = hourAndMinSDF.parse(end);
            return belongCalendar(now, beginTime, endTime);
        } catch (ParseException e) {
            //有异常则默认在区间范围内
            return true;
        }
    }

    /**
     * 判断指定的时间是否在给出的时间范围内
     *
     * @param time  指定的时间,格式要求为HH:mm
     * @param begin 开始时间,格式要求为HH:mm
     * @param end   结束时间,格式要求为HH:mm
     * @return
     */
    public static boolean isSpecTimeInTimeRange(String time, String begin, String end) {
        try {
            //SimpleDateFormat在方法中定义,避免线程不安全造成的错误
            SimpleDateFormat hourAndMinSDF = new SimpleDateFormat("HH:mm");
            Date now = hourAndMinSDF.parse(time);
            Date beginTime = hourAndMinSDF.parse(begin);
            Date endTime = hourAndMinSDF.parse(end);
            return belongCalendar(now, beginTime, endTime);
        } catch (ParseException e) {
            //有异常则默认在区间范围内
            return true;
        }
    }

    /**
     * 判断nowTime是否在beginTime和endTime的时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        return nowTime.getTime() >= beginTime.getTime() && nowTime.getTime() <= endTime.getTime();
    }

    /**
     * 判断两个日期的相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
        return days;
    }

}