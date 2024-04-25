package org.lwl.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeTest {

    public static void main(String[] args) {
        long cTime = 1665072000000L;
        LocalDateTime startTime = new Date(cTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("startTime = " + startTime);

        Date cDate = new Date(cTime);
        System.out.println("cDate = " + cDate);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println("nowTime = " + nowTime);
        for (int i = 0; i < 3; i++) {

            LocalDateTime startTime1 = nowTime.minusMinutes(10L);

            String startTimeStr = df.format(startTime1);
            String endTimeStr = df.format(nowTime);

            System.out.println("startTimeStr = " + startTimeStr + ", endTimeStr = " + endTimeStr);
        }
    }

}
