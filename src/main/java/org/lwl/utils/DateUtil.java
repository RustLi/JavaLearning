package org.lwl.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private static final String now = formatter.format(new Date());

    public static void main(String[] args) {
//        boolean isValid = between(11L,"03:59","03:00","03:00");
//        System.out.println(isValid);

        long time = 1733125590000L; //2024-12-02 15:46:30
        long aaa = time - time % 60_000L; //2024-12-02 15:46:00
        System.out.println("aaa = " + aaa);
    }

    private static String convertSecondToTimeStr(Integer duration) {
        if (duration == null || duration <=  0) {
            return "00分00秒";
        }

        StringBuilder sb = new StringBuilder();
        int hour = duration / 3600;
        int remainder = duration % 3600;
        int minute = remainder / 60;
        int second = remainder % 60;
        if (hour > 0) {
            sb.append(hour).append("小时");
        }
        if (minute > 0) {
            sb.append(minute).append("分钟");
        }
        if (second > 0) {
            sb.append(second).append("秒");
        }
        return sb.toString();
    }


    /**
     * 比较时间是否在某个范围内，可以跨0点
     **/
    private static boolean between(Long bizId, String now, String begin, String end) {
        int cp = begin.compareTo(end);
        if (cp > 0) {// 时间一模一样，休息
            if (now.compareTo(begin) >= 0 || now.compareTo(end) <= 0) {
//                log.info("It's rest time, bizId={}, now={}, begin={}, end={}", bizId, now, begin, end);
                return true;
            }
        } else {
            if (now.compareTo(begin) >= 0 && now.compareTo(end) <= 0) {
//                log.info("It's rest time, bizId={}, now={}, begin={}, end={}", bizId, now, begin, end);
                return true;
            }
        }

        return false;
    }


}
