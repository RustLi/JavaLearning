package test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTimeTest {
    public static void main(String[] args) {
        SimpleDateFormat sdfm = new SimpleDateFormat("yyyyMMdd.HHmmss");
        String formatDateStr = sdfm.format(new Date());
    }
}
