package org.jeecg.modules.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title: DateFormatUtils</p>
 * <p>Description: 时间格式工具类</p>
 *
 * @author Xuerong Xue
 * @author ZHANGSHILIN
 * @date 2015/12/12.
 * @date 2015/12/22.
 */
public abstract class DateFormatUtils {
    public static final String SPLIT_PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SPLIT_PATTERN_TIME = "HH:mm:ss";
    public static final String SPLIT_PATTERN_DATE = "yyyy-MM-dd";
    public static final String SPLIT_PATTERN_MONTH = "yyyy-MM";
    public static final String TIGHT_PATTERN_DATETIME = "yyyyMMddHHmmss";
    public static final String TIGHT_PATTERN_TIME = "HHmmss";
    public static final String TIGHT_PATTERN_DATE = "yyyyMMdd";
    // Web接口统一时间格式
    public static final SimpleDateFormat SPLIT_FORMAT_DATETIME = new SimpleDateFormat(SPLIT_PATTERN_DATETIME);
    public static final SimpleDateFormat SPLIT_FORMAT_TIME = new SimpleDateFormat(SPLIT_PATTERN_TIME);
    public static final SimpleDateFormat SPLIT_FORMAT_DATE = new SimpleDateFormat(SPLIT_PATTERN_DATE);
    public static final SimpleDateFormat TIGHT_FORMAT_DATETIME = new SimpleDateFormat(TIGHT_PATTERN_DATETIME);
    public static final SimpleDateFormat TIGHT_FORMAT_TIME = new SimpleDateFormat(TIGHT_PATTERN_TIME);
    public static final SimpleDateFormat TIGHT_FORMAT_DATE = new SimpleDateFormat(TIGHT_PATTERN_DATE);
    private static Logger LOG = LoggerFactory.getLogger(DateFormatUtils.class);
    private DateFormatUtils() {
        throw new RuntimeException("DateFormatUtils.class can't be instantiated");
    }

    /**
     * <p>使用默认时间格式（yyyy-MM-dd HH:mm:ss）进行时间格式化</p>
     * <p>add by xuexuerong 20151212</p>
     *
     * @param date 待格式化时间
     * @return 格式化后的时间字符串
     */
    public static String formate(Date date) {
        return formate(date, SPLIT_PATTERN_DATETIME);
    }

    /**
     * <p>使用指定的时间格式进行时间格式化</p>
     * <p>add by xuexuerong 20151212</p>
     *
     * @param date    待格式化时间
     * @param pattern 时间格式
     * @return 格式化后的时间字符串
     */
    public static String formate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * <p>使用默认时间格式（yyyy-MM-dd HH:mm:ss）进行时间解析</p>
     * <p>add by xuexuerong 20151212</p>
     *
     * @param date 待解析的时间字符串
     * @return 解析后的时间对象
     */
    public static Date parse(String date) {
        return parse(date, SPLIT_PATTERN_DATETIME);
    }

    /**
     * <p>使用指定的时间格式进行时间解析</p>
     * <p>add by xuexuerong 20151212</p>
     *
     * @param date    待解析的时间字符串
     * @param pattern 时间格式
     * @return 解析后的时间对象
     */
    public static Date parse(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取间隔为sec的时间
     *
     * @param date
     * @param sec
     * @return
     * @throws Exception
     */
    public static Date addSec(Date date, int sec) throws Exception {
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(date);
            cl.add(Calendar.SECOND, sec);
            return parse(SPLIT_FORMAT_DATETIME.format(cl.getTime()));
        } catch (Exception e) {
            throw new Exception("时间格式化报错" + e);
        }
    }

    /**
     * 获取间隔为day的时间
     *
     * @param date
     * @param day
     * @return
     * @throws Exception
     */
    public static Date addDay(Date date, int day) throws Exception {
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(date);
            cl.add(Calendar.DATE, day);
            return parse(SPLIT_FORMAT_DATETIME.format(cl.getTime()));
        } catch (Exception e) {
            throw new Exception("时间格式化报错" + e);
        }
    }

    /**
     * 获取间隔为hour的时间
     *
     * @param date
     * @param hour
     * @return
     * @throws Exception
     */
    public static Date addHour(Date date, int hour) throws Exception {
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(date);
            cl.add(Calendar.HOUR, hour);
            return parse(SPLIT_FORMAT_DATETIME.format(cl.getTime()));
        } catch (Exception e) {
            throw new Exception("时间格式化报错" + e);
        }
    }


    /**
     * 获取间隔为min的时间
     *
     * @param date
     * @param min
     * @return
     * @throws Exception
     */
    public static Date addMin(Date date, int min) throws Exception {
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(date);
            cl.add(Calendar.MINUTE, min);
            return parse(SPLIT_FORMAT_DATETIME.format(cl.getTime()));
        } catch (Exception e) {
            throw new Exception("时间格式化报错" + e);
        }
    }

    /**
     * 获取间隔为month的时间
     *
     * @param date
     * @param month
     * @return
     * @throws Exception
     */
    public static Date addMonth(Date date, int month) throws Exception {
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(date);
            cl.add(Calendar.MONTH, month);
            return parse(SPLIT_FORMAT_DATETIME.format(cl.getTime()));
        } catch (Exception e) {
            throw new Exception("时间格式化报错" + e);
        }
    }

    /**
     * 获取两个时间间隔(毫秒)
     *
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static long getIntervalTime(Date date1, Date date2) throws Exception {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        long l = 0;
        try {
            c1.setTime(date1);
            c2.setTime(date2);
            l = c2.getTimeInMillis() - c1.getTimeInMillis();
        } catch (Exception e) {
            throw new Exception("时间格式化报错" + e);
        }
        return l;
    }

    /**
     * 获取倒计时(分钟)
     *
     * @param date
     * @param min
     * @return
     * @throws Exception
     */
    public static String getCountDownTime(Date date, long min) throws Exception {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        long l = 0;
        long countDown = 0;
        String countDownStr = "00";
        try {
            c1.setTime(date);
            c2.setTime(new Date());
            l = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000;
            if (l < min * 60) {
                countDown = (min * 60 - l) / 60 + 1;
            }
        } catch (Exception e) {
            throw new Exception("时间格式化报错" + e);
        }
        if (countDown < 10) {
            countDownStr = "0" + countDown;
        } else {
            countDownStr = "" + countDown;
        }
        return countDownStr;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(dateToMillionSeconds(new Date()));
//        System.out.println(millionSecondsToDate(Long.valueOf("1517293664080")));
        //System.out.println(DateFormatUtils.formate(DateFormatUtils.addDay(new Date(), 1),DateFormatUtils
        // .SPLIT_PATTERN_DATE));
        //System.out.println(DateFormatUtils.getIntervalTime(DateFormatUtils.parse("2016-01-07 12:00:00"),
        // DateFormatUtils.parse("2016-01-9 11:00:00")));
        //System.out.println(DateFormatUtils.DifferentDays(new Date(), DateFormatUtils.addDay(new Date(), 100)));
        System.out.println(getTodayStartTime());
    }


    public static String getToday(String pattern) {
        Date date = new Date();
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date getTodayStartTime() {
        String startTime = getToday(SPLIT_PATTERN_DATE) + " 00:00:00";
        return parse(startTime, SPLIT_PATTERN_DATETIME);
    }

    /**
     * <p>把日期时间格式转换为日期格式</p>
     * <p>add by Chen Nan 20160504</p>
     *
     * @param datetime 日期时间
     * @return 日期 (时间为0点0分0秒)
     */
    public static Date datetime2Date(Date datetime) {
        String dateStr = DateFormatUtils.formate(datetime, DateFormatUtils.SPLIT_PATTERN_DATE);
        return DateFormatUtils.parse(dateStr, DateFormatUtils.SPLIT_PATTERN_DATE);
    }


    public static boolean isSameDay(Date date1, Date date2) {
        try {
            String d1 = formate(date1, "yyyyMMdd");
            String d2 = formate(date2, "yyyyMMdd");
            return d1.equals(d2);
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * <p>毫秒数转天数</p>
     * <p>add by Chen Nan 20160504</p>
     *
     * @param msec 毫秒数
     * @return 日期 (时间为0点0分0秒)
     */
    public static int msec2day(long msec) {
        return (int) Math.ceil((double) msec / (1000.0 * 60.0 * 60.0 * 24.0));
    }

    /**
     * 获取月份天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前是一年中的第几天
     *
     * @param date
     * @return
     */
    public static int getDaysOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 秒数转xx:xx:xx
     */
    public static String secToTime(Integer time) {
        if (time == null) return "";
        if (time == null) time = 0;
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    public static Long dateToMillionSeconds(Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime();
    }

    public static Long dateToSeconds(Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime() / 1000;
    }

    public static Date millionSecondsToDate(Long millonSeconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millonSeconds);
        Date date = c.getTime();
        return date;
    }

    public static Date addYears(Date date, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date getNowMonth() {
        String month = formate(new Date(), "yyyy-MM");
        return parse(month, "yyyy-MM");

    }


    public static int DifferentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }
    }


}
