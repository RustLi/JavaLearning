package org.lwl.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("yyyyMMddHH", Locale.getDefault());
    private static final SimpleDateFormat MINUTE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());

    public static String formatToday() {
        return DATE_FORMAT.format(new Date());
    }

    public static String formatNowMinute() {
        return MINUTE_FORMAT.format(new Date());
    }

    public static String formatNowHour() {
        return HOUR_FORMAT.format(new Date());
    }

    private static final ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        }
    };

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private static final String now = formatter.format(new Date());

    public static void main(String[] args) {
//        boolean isValid = between(11L,"03:59","03:00","03:00");
//        System.out.println(isValid);

//        long time = 1733125590000L; //2024-12-02 15:46:30
//        long aaa = time - time % 60_000L; //2024-12-02 15:46:00
//        System.out.println("aaa = " + aaa);
//
////        long timestamp = 1751967133L; // 示例时间戳
//        long timestamp = 1746759560L; // 示例时间戳
//        if (isOverTime(timestamp)) {
//            // 在未来1个月内
//            System.out.println("TimeCheck" + "该时间超过一个月了");
//        } else {
//            // 不在范围内
//            System.out.println("TimeCheck" + "该时间在1个月内");
//        }
//        System.out.println(getTimestampBeforeDays());

        boolean isInTimeInterval = isInTimeInterval1(7, 3);
        System.out.println("isInTimeInterval  11 = " + isInTimeInterval);
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


    public static boolean isInTimeInterval1(int startHour, int endHour) {
        if (startHour != -1 && endHour != -1) {
            int hour;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            hour = calendar.get(Calendar.HOUR_OF_DAY);

            System.out.println("hour = " + hour);
            if (hour >= startHour && hour < endHour) {
                return true;
            } else {
                return (endHour < startHour) && (hour >= startHour || hour < endHour);
            }
        }
        return true;
    }

    private static final long DAYS = 7 * 24 * 60 * 60;
    public static long getTimestampBeforeDays() {
        return System.currentTimeMillis() / 1000 - DAYS;
    }

    public static boolean isOverTime(long timestampInSeconds) {
        long nowInSeconds = System.currentTimeMillis() / 1000;
        // 1个月前的时间戳
        long oneMonthAgoInSeconds = nowInSeconds - 30 * 24 * 60 * 60;
        return timestampInSeconds < oneMonthAgoInSeconds;
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

    public static String dateTimeFormat() {
        return defaultDateTimeFormat.get().format(new Date());
    }

}
