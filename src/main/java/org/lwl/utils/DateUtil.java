package org.lwl.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private static final String now = formatter.format(new Date());

    public static void main(String[] args) {
        boolean isValid = between(11L,"03:59","03:00","03:00");
        System.out.println(isValid);
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
