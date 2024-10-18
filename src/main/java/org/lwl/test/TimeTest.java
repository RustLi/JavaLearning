package org.lwl.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeTest {
    private static final long ONE_MINUTE_MILLIS = 60 * 1000L;

    public static void main(String[] args) {
//        long cTime = 1665072000000L;
//        LocalDateTime startTime = new Date(cTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//        System.out.println("startTime = " + startTime);
//
//        Date cDate = new Date(cTime);
//        System.out.println("cDate = " + cDate);
//
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        LocalDateTime nowTime = LocalDateTime.now();
//        System.out.println("nowTime = " + nowTime);
//        for (int i = 0; i < 3; i++) {
//
//            LocalDateTime startTime1 = nowTime.minusMinutes(10L);
//
//            String startTimeStr = df.format(startTime1);
//            String endTimeStr = df.format(nowTime);
//
//            System.out.println("startTimeStr = " + startTimeStr + ", endTimeStr = " + endTimeStr);
//        }


        //2024-07-03 14:02:48.458   INFO ForkJoinPool.commonPool-worker-1 [fe22849bf5a34d83b00bfb8d27151678] c.b.d.l.s.i.LiveInOutLogEsServiceImpl:545 - queryOnlineCustomerTimelineByInviter: time, startTime111:1719908713000, endTime111:1719924567000
        //2024-07-03 14:02:48.458   INFO ForkJoinPool.commonPool-worker-1 [fe22849bf5a34d83b00bfb8d27151678] c.b.d.l.s.i.LiveInOutLogEsServiceImpl:550 - queryOnlineCustomerTimelineByInviter: time, startTime:1719908760000, endTime:1719924600000, headTime:1719908700000, endTime:1719924600000

        long time1 = 1719908713000L;
        long time2 = 1719908760000L;
        System.out.println(getHeadTime(time1, 1));
    }

    private static long getHeadTime(long timestamp, int gapMinutes) {
        return timestamp / gapTimeStamp(gapMinutes) * gapTimeStamp(gapMinutes);
    }

    private static long getTailTime(long timestamp, int gapMinutes) {
        return getHeadTime(timestamp, gapMinutes) + gapTimeStamp(gapMinutes);
    }

    public static long gapTimeStamp(int gapMinutes) {
        return ONE_MINUTE_MILLIS * gapMinutes;
    }
}
