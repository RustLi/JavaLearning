package org.lwl.utils;

import java.text.ParseException;
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

//        boolean isInTimeInterval = isInTimeInterval1(7, 3);
//        System.out.println("isInTimeInterval  11 = " + isInTimeInterval);

        // 示例：按月周期计算日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Date start1 = sdf.parse("2025-01-31");
            // Date result1 = addMonthCycle(start1, 1);
            // System.out.println("2025-01-31 +1个月 = " + sdf.format(result1));

            // Date start2 = sdf.parse("2025-01-01");
            // Date result2 = addMonthCycle(start2, 6);
            // System.out.println("2025-01-01 +6个月 = " + sdf.format(result2));

            // Date rangeStart = sdf.parse("2025-03-22");
            // Date rangeEnd = sdf.parse("2026-03-24");
            // int coveredMonths = countCoveredMonths(rangeStart, rangeEnd);
            // System.out.println("2025-03-22~2026-03-24 覆盖月份数 = " + coveredMonths);


            Date date = sdf.parse("2026-01-01");
            Date result3 = addMonthsToDate(date, 0);
            System.out.println("2026-01-01 1个月 = " + sdf.format(result3));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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


    /**
     * 按月周期计算日期：先在日历上向后平移 {@code months} 个月，
     * 若目标月份没有与起始日相同的日，则取该月最后一天，然后再减去 1 天。
     * <p>
     * 例如：
     * 2025-01-31 + 1 个月 → 2025-02-27
     * 2025-01-01 + 6 个月 → 2025-06-30
     *
     * @param startDate 起始日期，不能为空
     * @param months    月份偏移量，可为负数或 0
     * @return 计算后的日期
     * @throws IllegalArgumentException 当 startDate 为空时抛出
     */
    public static Date addMonthCycle(Date startDate, int months) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate must not be null");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        int originDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, months);
        System.out.println("calendar = " + calendar.getTime());

        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (originDay > maxDayOfMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);
        }

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }
    
    public static Date addMonthsToDate(Date date, Integer months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 字符串版本的按月周期计算日期。
     *
     * @param startDateStr 起始日期字符串
     * @param months       月份偏移量
     * @param pattern      日期格式，例如 yyyy-MM-dd、yyyyMMdd
     * @return 计算后的日期字符串
     * @throws IllegalArgumentException 当参数非法或解析失败时抛出
     */
    public static String addMonthCycle(String startDateStr, int months, String pattern) {
        if (startDateStr == null || pattern == null) {
            throw new IllegalArgumentException("startDateStr and pattern must not be null");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date startDate = sdf.parse(startDateStr);
            if (startDate == null) {
                throw new IllegalArgumentException("startDateStr could not be parsed: " + startDateStr);
            }
            Date targetDate = addMonthCycle(startDate, months);
            return sdf.format(targetDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse startDateStr: " + startDateStr, e);
        }
    }

    /**
     * 计算开始时间和结束时间覆盖到的自然月份数（含首尾月）。
     * 同一个月内返回 1。
     *
     * @param startDate 开始时间，不能为空
     * @param endDate   结束时间，不能为空
     * @return 覆盖月份数（含首尾月）
     */
    public static int countCoveredMonths(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate must not be null");
        }

        Date realStart = startDate;
        Date realEnd = endDate;
        if (realStart.after(realEnd)) {
            realStart = endDate;
            realEnd = startDate;
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(realStart);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(realEnd);

        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH);
        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH);

        return (endYear - startYear) * 12 + (endMonth - startMonth) + 1;
    }

    /**
     * 字符串版本的区间覆盖月份数计算。
     *
     * @param startDateStr 开始时间字符串
     * @param endDateStr   结束时间字符串
     * @param pattern      日期格式，例如 yyyy-MM-dd、yyyyMMdd
     * @return 覆盖月份数（含首尾月）
     */
    public static int countCoveredMonths(String startDateStr, String endDateStr, String pattern) {
        if (startDateStr == null || endDateStr == null || pattern == null) {
            throw new IllegalArgumentException("startDateStr, endDateStr and pattern must not be null");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("Date string could not be parsed");
            }
            return countCoveredMonths(startDate, endDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse date string", e);
        }
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
