package org.lwl.test;

import java.util.Objects;

public class PriceUtil {

    public static void main(String[] args) {
//        System.out.println("4740 = " + priceScale(4740L));
//        System.out.println("14733 = " + priceScale(14733L));
//        System.out.println("1004733 = " + priceScale(1004733));
        System.out.println("90 = " + priceScale(99L));
//        System.out.println("3 = " + priceScale(3L));

        long payPrice = 100;
        String totalMount = String.valueOf(payPrice / 100);
        System.out.println(totalMount);

    }

    public static long priceScale(long price) {
        double originalValue = (double) price / 10000;
        double result = Math.floor(originalValue * 100) * 100;
        return (long) result;
    }
}
