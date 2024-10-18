package org.lwl.test;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class HutoolTest {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1() {
        String now = DateUtil.now();

//        LocalDateTime localDateTime = LocalDateTimeUtil.parse("2020-01-23T12:23:56");
        LocalDateTime localDateTime = LocalDateTimeUtil.now();

        System.out.println("localDateTime = " + localDateTime);

        // "2020-01-23T00:00"
        LocalDateTime beginOfDay = LocalDateTimeUtil.beginOfDay(localDateTime);

        // "2020-01-23T23:59:59.999999999"
        LocalDateTime endOfDay = LocalDateTimeUtil.endOfDay(localDateTime);

        System.out.println(beginOfDay);
        System.out.println(endOfDay);

        Date now1 = new Date();
        System.out.println("now1 = " + now1);
        System.out.println("start = " + getStartOfDay(now1));
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
