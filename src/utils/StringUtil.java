package utils;

import java.util.regex.Pattern;

public class StringUtil {
    public static void main(String[] args) {
//        String imgPath = "/sss/ssda/mars/111.jpg";
//        String fileName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
//        System.out.println(fileName);
//
//        String aaa = "13164768025";
//        System.out.println("aaa = " + a(aaa));


        //0x4235AC1
        int so = Integer.parseInt("0xbb1e6000", 16);
//        Integer so = 0xbb1e6000;

        System.out.println("so = " + so);
        int n_addr_func_offset = 0x4235AC1;         //要hook的函数在函数里面的偏移
        int n_addr_func = so + n_addr_func_offset;
        System.out.println("lwl: 333 func addr is ---" + n_addr_func);
    }


    public static String a(String str) {
        String str2 = "";
        if (str == null) {
            return str2;
        }
        if (d(str)) {
            str = str.replaceAll("\\s|\\(|\\)|\\+|-", str2);
            if (str.length() <= 11) {
                return str;
            }
            str = str.substring(str.length() - 11);
        }
        return str;
    }

    public static boolean d(String str) {
        if (m(str)) {
            return false;
        }
        return Pattern.compile("^(?:(?:\\+?(?:86)?)?|(?:\\+?(?:\\(86\\))?)?|(?:\\(\\+?(?:86\\))?)?)\\s?\\d{3}([\\s\\-]?)\\d{4}\\1\\d{4}$").matcher(str).matches();
    }

    public static boolean m(CharSequence charSequence) {
        return n(charSequence, false);
    }

    public static boolean n(CharSequence charSequence, boolean z) {
        boolean z2 = false;
        String str = "";
        if (z) {
            if (charSequence == null || str.equals(charSequence.toString().trim())) {
                z2 = true;
            }
            return z2;
        }
        if (charSequence == null || str.equals(charSequence)) {
            z2 = true;
        }
        return z2;
    }
}
