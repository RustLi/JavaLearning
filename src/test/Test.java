package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

        String Times = " 2018-10-19 14:16:17";

        String str = Times.replaceAll("[[\\s-:punct:]]","");

        System.out.println(str);

        System.out.println("-----------------------");


        String name = DataBean.class.getName();
        System.out.println(name);


        System.out.println((int) Math.ceil((double) 0/20));
        System.out.println((int) Math.ceil((double) 5/20));
        System.out.println((int) Math.ceil((double) 20/20));
        System.out.println((int) Math.ceil((double) 35/20));
        System.out.println((int) Math.ceil((double) 21/20));




        String localTime = "2019-01-29 09:20:39";
        String updateTime = "2019-01-29 09:21:46";

        System.out.println("比较结果：：：：：" + compStr(localTime,updateTime));
    }

    public static final int STR_EQUAL = 0;
    public static final int STR_GREATER = 1;
    public static final int STR_LESS = -1;
    public static final int STR_COMP_ERROR = -2;
    public static int compStr(String str1,String str2){
        /*if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)){
            return STR_COMP_ERROR;
        }*/
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = format.parse(str1);
            Date date2 = format.parse(str2);

            //compareTo()方法的返回值，date1小于date2返回-1，date1大于date2返回1，相等返回0
            return date1.compareTo(date2);

        } catch (ParseException e) {
//            LogUtils.e(TAG, "comVersionStr: ParseException = " + e.getMessage());
            e.printStackTrace();
        }
        return STR_EQUAL;
    }

    public static class DataBean{
        public  String name = "li" ;
        public  String sex = "male";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
