package org.lwl.test;

import java.text.DecimalFormat;

public class NumberTest {
    public static void main(String[] args) {
        System.out.println(truncateToFirstDecimal(0.0));
        System.out.println(truncateToFirstDecimal(0));
        System.out.println(truncateToFirstDecimal(0.2));
        System.out.println(truncateToFirstDecimal(3));
        System.out.println(truncateToFirstDecimal(3.5));
        System.out.println(truncateToFirstDecimal(56.5));
        System.out.println(truncateToFirstDecimal(53));
        System.out.println(truncateToFirstDecimal(100.5));
        System.out.println(truncateToFirstDecimal(100.555555));
    }

    /**
     * 截断到小数点后第一位
     * @param number 需要处理的浮点数
     * @return 截断后的浮点数
     */
//    public static double truncateToFirstDecimal(double number) {
//        // 乘以10取整后再除以10实现截断效果
//        return Math.floor(number * 10) / 10.0;
//    }

    /**
     * 截断到小数点后第一位，并移除末尾的0和小数点（如果小数是0）
     * @param number 需要处理的浮点数
     * @return 处理后的字符串形式
     */
    public static String truncateToFirstDecimal(double number) {
        // 截断到小数点后第一位
        double truncated = Math.floor(number * 10) / 10.0;

        // 使用DecimalFormat来格式化，移除不需要的尾随零
        DecimalFormat df = new DecimalFormat("0.#");
        String result = df.format(truncated);

        return result;
    }

//    public static int truncateToFirstDecimal(double number) {
//        return  (int)(number * 10) % 10;
//    }
}
