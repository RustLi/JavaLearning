package test;

import com.google.common.collect.Range;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuavaTest {

    public static final String yyyy_MM_dd_HH_mm_ss_EN = "yyyy-MM-dd HH:mm:ss";
    private static Map<String, FastDateFormat> dateFormatMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        String aa = "06:00:00";
        String bb = "23:59:59";
        String nowTime = getCurTimeString("HH:mm:ss");
        System.out.println("nowTime = " + nowTime);
        String ccc = "03:03:00";
        System.out.println("llll = " + Range.closed(aa, bb).contains(nowTime));
        System.out.println("aaaa = " + Range.closed(aa, bb).contains(ccc));
    }

    public static String getCurTimeString(String formate) {
        return dateToDateString(new Date(), formate);
    }

    public static String dateToDateString(Date date, String formatStr) {
        if (formatStr == null) {
            formatStr = yyyy_MM_dd_HH_mm_ss_EN;
        }
        FastDateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    public static FastDateFormat getDateFormat(String formatStr) {
        FastDateFormat df = dateFormatMap.get(formatStr);//NOSONAR
        if (df == null) {
            df = FastDateFormat.getInstance(formatStr);
            dateFormatMap.put(formatStr, df);
        }
        return df;
    }
}
