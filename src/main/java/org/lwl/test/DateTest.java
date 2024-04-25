package org.lwl.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    public static final String yyyy_MM_dd_HH_mm_ss_EN = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {
//        Date date1 = new Date();
//
//        Date date2 = new Date();
//
//        System.out.println(date1);
//        System.out.println(date1.getTime());
//        System.out.println(date2);
//        System.out.println(date2.getTime());
//
//        System.out.println(date1.compareTo(date1));
//
//        boolean isSame = equals(date1,date2);
//        System.out.println(isSame);

//        testBetweenDays();

//        long aaa = 234200L;
//        System.out.println("bbb = " + (float)aaa/10000);


        String aaa = "aaa";
        String bb = null;
        boolean isQQ = aaa.equals(bb);
        System.out.println(isQQ);
    }

    public static void testBetweenDays(){
        // 日期字符串
        String dateStr1 = "2022-04-14 21:52:28";
        String dateStr2 = "2022-04-14 21:52:29";

        // 获取日期
        Date date1 = parseDate(dateStr1, "yyyy-MM-dd HH:mm:ss");
        Date date2 = parseDate(dateStr2, "yyyy-MM-dd HH:mm:ss");

        int aaa = getBetweenDays(date1,date2);
        System.out.println("aaa = " + aaa);
    }


    public static int getBetweenDays(Date beginDate, Date endDate) {
        if (beginDate.after(endDate) || beginDate.equals(endDate)){
            return 0;
        }
        long ccc = endDate.getTime() - beginDate.getTime();
        System.out.println("ccc = " + ccc);
        int retTime = 0;
        long oneDay = 1000L*3600L*24L;

        System.out.println("bbb = " + ccc%oneDay);
        if (ccc > 0 && ccc <= oneDay){
            retTime = 1;
        }else {
            if (ccc%oneDay == 0){
                retTime = (int) (ccc/oneDay);
            }else {
                retTime = (int) (ccc/oneDay)+1;
            }
        }
        return retTime;
    }

    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date parseDate(String dateStr, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw  new RuntimeException("日期转化错误");
        }

        return date;
    }

    private static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private void testDate(Date date1, Date date2){

    }
}
