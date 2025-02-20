package org.lwl.test;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {

    public static final String DESC_HEAD = "#wr";
    public static final String DESC_SUFFIX = "#";
    public static final Pattern DESC_REGEX = Pattern.compile(DESC_HEAD + "([0-9a-zA-Z]+)" + DESC_SUFFIX);

    public static void main(String[] args) {
//        String a = "aaa3.5万asfafaf";
//        String regEx="[^0-9]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(a);
//        System.out.println(m.replaceAll("").trim());


//        CharSequence aaa = null;
//        String bbb = aaa + "";
//        System.out.println("bbb = " + bbb);
//        boolean isNull = bbb == null;
//        System.out.println("isNull = " + isNull);
//
//        boolean isNullStr = "null".equals(bbb);
//        System.out.println("isNullStr = " + isNullStr);
//
//        String ddd = aaa.toString();
//        System.out.println("ddd = " + ddd);


        String path = "/storage/emulated/0/skyeye/download/93154490-fd23-11ec-9778-15167de6d202(1).jpeg";

        int index = path.indexOf('\u0000');
        System.out.println("index = " + index);

        String desc = "2986；王彦彦1986年5月11日早4点；赵儆非1980年2月17日下午5点40；女婿100万转银行半年收不回来；#wrYAd0pPhD75#";
        Matcher matcher = DESC_REGEX.matcher(desc);
        while (matcher.find()) {
            String num = matcher.group(1);
            System.out.println("num = " + num);
        }

        String flag = DESC_HEAD + "YAd0pPhD75" + DESC_SUFFIX;
        String description = desc;
        description = description.replace(flag, StringUtils.EMPTY);
        System.out.println("description = " + description);
    }


//    final boolean isInvalid() {
//        if (status == null) {
//            status = (this.path.indexOf('\u0000') < 0) ? File.PathStatus.CHECKED
//                    : File.PathStatus.INVALID;
//        }
//        return status == File.PathStatus.INVALID;
//    }

    public static String getNumber(String str){
        // 控制正则表达式的匹配行为的参数(小数)
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        //Matcher类的构造方法也是私有的,不能随意创建,只能通过Pattern.matcher(CharSequence input)方法得到该类的实例.
        Matcher m = p.matcher(str);
        //m.find用来判断该字符串中是否含有与"(\\d+\\.\\d+)"相匹配的子串
        if (m.find()) {
            //如果有相匹配的,则判断是否为null操作
            //group()中的参数：0表示匹配整个正则，1表示匹配第一个括号的正则,2表示匹配第二个正则,在这只有一个括号,即1和0是一样的
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果匹配不到小数，就进行整数匹配
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                //如果有整数相匹配
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
                str = "";
            }
        }
        return str;
    }

}
