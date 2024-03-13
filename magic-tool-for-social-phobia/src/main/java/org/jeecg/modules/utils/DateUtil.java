package org.jeecg.modules.utils;

import cn.hutool.core.date.DateField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @ClassName DateUtil
 * @Author bo
 * @date 2021.11.02 16:55
 */
public class DateUtil {

    /**
     * 日期0点
     *
     * @param date
     * @return
     */
    public static Date beginOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 日期23:59:59
     *
     * @param date
     * @return
     */
    public static Date endOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 日期0点
     *
     * @param date
     * @return
     */
    public static Date beginOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 日期23:59:59
     *
     * @param date
     * @return
     */
    public static Date endOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 小时 00:00
     *
     * @param date
     * @return
     */
    public static Date beginOfHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 小时 59:59
     *
     * @param date
     * @return
     */
    public static Date endOfHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     * 增加小时
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() + (hour * 1000 * 3600L));
        return calendar.getTime();
    }


    /**
     * 日期列表
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<String> dayList(Date beginTime, Date endTime, int max) {
        ArrayList<String> dayList = new ArrayList<>();
        if (beginTime == null || endTime == null) {
            return dayList;
        }
        Date dayBegin = DateUtil.beginOfDate(beginTime);
        Date dayEnd = DateUtil.beginOfDate(endTime);
        if (dayBegin.getTime() > dayEnd.getTime()) {
            return dayList;
        }
        if (dayBegin.getTime() == dayEnd.getTime()) {
            dayList.add(DateFormatUtils.formate(dayBegin, "yyyy-MM-dd"));
            return dayList;
        }


        while (dayBegin.getTime() <= dayEnd.getTime() && max > 0) {
            dayList.add(DateFormatUtils.formate(dayBegin, "yyyy-MM-dd"));
            max--;
            try {
                dayBegin = DateFormatUtils.addDay(dayBegin, 1);
            } catch (Exception e) {
                return dayList;
            }
        }

        return dayList;
    }


    /**
     * 日期列表
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<String> dayList(Date beginTime, Date endTime, String pattern, int max) {
        ArrayList<String> dayList = new ArrayList<>();
        if (beginTime == null || endTime == null) {
            return dayList;
        }
        Date dayBegin = DateUtil.beginOfDate(beginTime);
        Date dayEnd = DateUtil.beginOfDate(endTime);
        if (dayBegin.getTime() > dayEnd.getTime()) {
            return dayList;
        }
        if (dayBegin.getTime() == dayEnd.getTime()) {
            dayList.add(DateFormatUtils.formate(dayBegin, pattern));
            return dayList;
        }


        while (dayBegin.getTime() <= dayEnd.getTime() && max > 0) {
            dayList.add(DateFormatUtils.formate(dayBegin, pattern));
            max--;
            try {
                dayBegin = DateFormatUtils.addDay(dayBegin, 1);
            } catch (Exception e) {
                return dayList;
            }
        }

        return dayList;
    }

    /**
     * 日期列表
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<String> dayList(Date beginTime, Date endTime, int max, String pattern) {
        ArrayList<String> dayList = new ArrayList<>();
        if (beginTime == null || endTime == null) {
            return dayList;
        }
        Date dayBegin = DateUtil.beginOfDate(beginTime);
        Date dayEnd = DateUtil.beginOfDate(endTime);
        if (dayBegin.getTime() > dayEnd.getTime()) {
            return dayList;
        }
        if (dayBegin.getTime() == dayEnd.getTime()) {
            dayList.add(DateFormatUtils.formate(dayBegin, pattern));
            return dayList;
        }


        while (dayBegin.getTime() <= dayEnd.getTime() && max > 0) {
            dayList.add(DateFormatUtils.formate(dayBegin, pattern));
            max--;
            try {
                dayBegin = DateFormatUtils.addDay(dayBegin, 1);
            } catch (Exception e) {
                return dayList;
            }
        }

        return dayList;

    }


    public static List<String> monthList(Date beginTime, Date endTime, int max) {
        ArrayList<String> monthList = new ArrayList<>();
        if (beginTime == null || endTime == null) {
            return monthList;
        }
        Date dayBegin = DateUtil.beginOfDate(beginTime);
        Date dayEnd = DateUtil.beginOfDate(endTime);
        if (dayBegin.getTime() > dayEnd.getTime()) {
            return monthList;
        }
        if (dayBegin.getTime() == dayEnd.getTime()) {
            monthList.add(DateFormatUtils.formate(dayBegin, "yyyy-MM"));
            return monthList;
        }
        while (dayBegin.getTime() <= dayEnd.getTime() && max > 0) {
            monthList.add(DateFormatUtils.formate(dayBegin, "yyyy-MM"));
            max--;
            try {
                dayBegin = DateFormatUtils.addMonth(dayBegin, 1);
            } catch (Exception e) {
                return monthList;
            }
        }

        return monthList;
    }

    public static List<String> monthList(Date beginTime, Date endTime, int max, String pattern) {
        ArrayList<String> monthList = new ArrayList<>();
        if (beginTime == null || endTime == null) {
            return monthList;
        }
        Date dayBegin = DateUtil.beginOfDate(beginTime);
        Date dayEnd = DateUtil.beginOfDate(endTime);
        if (dayBegin.getTime() > dayEnd.getTime()) {
            return monthList;
        }
        if (dayBegin.getTime() == dayEnd.getTime()) {
            monthList.add(DateFormatUtils.formate(dayBegin, pattern));
            return monthList;
        }
        while (dayBegin.getTime() <= dayEnd.getTime() && max > 0) {
            monthList.add(DateFormatUtils.formate(dayBegin, pattern));
            max--;
            try {
                dayBegin = DateFormatUtils.addMonth(dayBegin, 1);
            } catch (Exception e) {
                return monthList;
            }
        }

        return monthList;
    }

    /**
     * yyyy-MM-dd hh 列表
     *
     * @param beginTime
     * @param endTime
     * @param max
     * @return
     */
    public static List<String> hourList(Date beginTime, Date endTime, int max) {
        ArrayList<String> dayList = new ArrayList<>();
        if (beginTime == null || endTime == null) {
            return dayList;
        }
        if (beginTime.getTime() > endTime.getTime()) {
            return dayList;
        }
        // 相差多少个小时
        Date beginHour = beginOfHour(beginTime);
        Date endHour = endOfHour(endTime);

        dayList.add(DateFormatUtils.formate(beginHour, "yyyy-MM-dd HH:00:00"));
        int hour = (int) ((endHour.getTime() - beginHour.getTime()) / 1000 / 3600);
        while (hour >= 0 && max > 0) {
            max -= 1;
            hour -= 1;
            Date date = addHour(endHour, -hour);
            cn.hutool.core.date.DateUtil.offset(endHour, DateField.MINUTE, -2);
            dayList.add(DateFormatUtils.formate(date, "yyyy-MM-dd HH:00:00"));
        }

        return dayList;
    }


    /**
     * 获取固定间隔时刻集合
     *
     * @param start    开始时间
     * @param end      结束时间
     * @param pattern  时间格式
     * @param interval 时间间隔(单位：分钟)
     * @return
     */
    public static List<String> getIntervalTimeList(Date start, Date end, String pattern, int interval) {
        Date startDate = convertString2Date(pattern, start);
        Date endDate = convertString2Date(pattern, end);
        List<String> list = new ArrayList<>();
        if (startDate != null && endDate != null) {
            while (startDate.getTime() <= endDate.getTime()) {
                list.add(convertDate2String(pattern, startDate));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MINUTE, interval);
                if (calendar.getTime().getTime() > endDate.getTime()) {
                    if (!startDate.equals(endDate)) {
                        list.add(convertDate2String(pattern, endDate));
                    }
                    startDate = calendar.getTime();
                } else {
                    startDate = calendar.getTime();
                }

            }
        }
        return list;
    }

    /**
     * 时间转换
     *
     * @param format
     * @param dateStr
     * @return
     */
    private static Date convertString2Date(String format, Date dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse(simpleDateFormat.format(dateStr));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param format
     * @param date
     * @return
     */
    private static String convertDate2String(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 判断时间区间是否是今天
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean isSameDay(Date beginTime, Date endTime) {
        LocalDate begin = beginTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return begin.isEqual(end);
    }
}
