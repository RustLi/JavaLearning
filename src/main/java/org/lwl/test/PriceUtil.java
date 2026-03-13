package org.lwl.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class PriceUtil {

    public static void main(String[] args) {
//        System.out.println("4740 = " + priceScale(4740L));
//        System.out.println("14733 = " + priceScale(14733L));
//        System.out.println("1004733 = " + priceScale(1004733));
//        System.out.println("90 = " + priceScale(99L));
//        System.out.println("3 = " + priceScale(3L));

//        long payPrice = 100;
//        String totalMount = String.valueOf(payPrice / 100);
//        System.out.println(totalMount);


        System.out.println(priceToYuan(-1L));
        System.out.println(priceToYuan(4740L));
        System.out.println(priceToYuan(1004763L));
        System.out.println(priceToYuan(99L));


        // 给定条件：每3元获取10个积分，总金额56元
        Long totalAmount = 5600L;
        Long rate = 3L;
        Long pointsPerRate = 10L;
        Long totalPoints = getPoint(totalAmount, rate, pointsPerRate);
//        System.out.println("给定" + totalAmount + "元，可以获取" + totalPoints + "个积分");

//        System.out.println(priceToDouble(135613L, 4));

    }

    public static double priceToDouble(Long price, Integer scale) {
        if (Objects.isNull(price) || Objects.isNull(scale)) {
            return 0.0;
        }
        if (price == 0 || scale == 0) {
            return price.doubleValue();
        }
        if (scale < 0) {
            throw new IllegalArgumentException("保留的位数不能为为负数");
        }
        return price.doubleValue() / getDivisor(scale);
    }

    private static double getDivisor(Integer scale) {
        double base = 10.0;
        double result = base;
        for (int i = 1; i < scale; i++) {
            result = result * base;
        }
        return result;
    }
    private static Long getPoint(Long totalAmount, Long point, Long price) {
        return (totalAmount / 10000L / point) * price;   // 计算积分
    }


    private static String cleanSpecialCharacters(String content) {
        if (content == null) {
            return null;
        }
        // 清理常见的特殊字符（根据实际需求调整）
        return content.replaceAll("[\\r\\n\\t]", "").trim();
    }

    public static String priceToYuan(Long price) {
        if (price == null || price <= 0L) {
            return "0.00";
        }
        // 使用BigDecimal确保精度
        BigDecimal yuan = new BigDecimal(price).divide(new BigDecimal(10000), 2, RoundingMode.DOWN);
        return yuan.toString();
    }

    public static long priceScale(long price) {
        double originalValue = (double) price / 10000;
        double result = Math.floor(originalValue * 100) * 100;
        return (long) result;
    }
}
