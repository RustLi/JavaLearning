package org.lwl.test;

public class NullTest {

    public static void main(String[] args) {
        //x为包装类型，100L为基本类型，会触发装箱
        //Long y = true ? x.longValue() : 100.longValue();
        Long x = null;
        Long y = true ? x : 100L;
        System.out.println(y);
    }
}
