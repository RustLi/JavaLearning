package org.lwl.test;

import org.apache.http.util.TextUtils;

import java.util.Objects;

public class NullTest {

    public static void main(String[] args) {

//        testNull();

        String aaa = "43141-4134";
        System.out.println("aaa = " + isVideoValid(aaa));
    }

    private static void testBox(){
        //x为包装类型，100L为基本类型，会触发装箱
        //Long y = true ? x.longValue() : 100.longValue();
        Long x = null;
        Long y = true ? x : 100L;
        System.out.println(y);
    }


    private static void testNull() {
        Integer x = null;
        boolean isFlag = Objects.equals(1,x);
        System.out.println(isFlag);
    }

    private static boolean isVideoValid(String videoId) {
        return !TextUtils.isEmpty(videoId) && videoId.matches("\\d+");
    }
}
