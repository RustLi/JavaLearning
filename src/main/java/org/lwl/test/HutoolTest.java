package org.lwl.test;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HutoolTest {
    public static void main(String[] args) {
        test1();
//        test2();
    }

    public static void test1() {
//        String now = DateUtil.now();

        LocalDateTime localDateTime = LocalDateTimeUtil.parse("2020-01-23T12:23:56");
//        LocalDateTime localDateTime = LocalDateTimeUtil.now();
//
//        System.out.println("localDateTime = " + localDateTime);
//
//        // "2020-01-23T00:00"
        LocalDateTime getDateBetween = LocalDateTimeUtil.beginOfDay(localDateTime);
        System.out.println("getDateBetween = " + getDateBetween);
//
//        // "2020-01-23T23:59:59.999999999"
//        LocalDateTime endOfDay = LocalDateTimeUtil.endOfDay(localDateTime);
//
//        System.out.println(beginOfDay);
//        System.out.println(endOfDay);

//        Date now1 = new Date();
//        System.out.println("now1 = " + now1);
//        System.out.println("start = " + getStartOfDay(now1));
//
//
//        String startTime = "2023-12-29 11:59:05";
//        String currentTime = "2023-12-28 10:59:05";
//        System.out.println(getDateRange(DateUtil.parse(startTime), DateUtil.parse(currentTime)));
    }

    public static List<Date> getDateRange(Date startTime, Date currentTime) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTime);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(currentTime);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        List<Date> dateList = new ArrayList<>();

        while (!startCal.after(endCal)) {
            dateList.add(startCal.getTime());
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dateList;
    }

    public static List<String> getDateRange(String startTime, String currentTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate startDate = LocalDate.parse(startTime, formatter);
        LocalDate endDate = LocalDate.parse(currentTime, formatter);

        List<String> dateList = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            dateList.add(startDate.toString());
            startDate = startDate.plusDays(1);
        }

        return dateList;
    }

    public static List<Date> getDateBetween(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        start.set(start.get(1), start.get(2), start.get(5), 0, 0, 0);
        end.set(end.get(1), end.get(2), end.get(5), 0, 0, 0);
        Calendar current = start;

        Date date;
        while(dateCompare(current.getTime(), end.getTime()) > 0) {
            date = new Date();
            date.setTime(current.getTimeInMillis() / 1000L * 1000L);
            dates.add(date);
            current.add(5, 1);
        }

        date = new Date();
        date.setTime(current.getTimeInMillis() / 1000L * 1000L);
        dates.add(date);
        current.add(5, 1);
        return dates;
    }

    public static int dateCompare(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        start.set(start.get(1), start.get(2), start.get(5), 0, 0, 0);
        end.set(end.get(1), end.get(2), end.get(5), 0, 0, 0);
        long startTime = start.getTimeInMillis() / 1000L;
        long endTime = end.getTimeInMillis() / 1000L;
        if (startTime < endTime) {
            return 1;
        } else {
            return startTime > endTime ? -1 : 0;
        }
    }

    private static void test2(){
        //新建FIFOCache
        Cache<String,String> fifoCache = CacheUtil.newFIFOCache(3);

        //放入数据
        fifoCache.put("key1","value1");
        fifoCache.put("key2","value2");
        fifoCache.put("key3","value3");
        fifoCache.put("key4","value4");

        System.out.println(fifoCache);
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
