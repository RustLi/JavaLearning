package org.lwl.test;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class NumberTest {
    public static void main(String[] args) {
//        System.out.println(truncateToFirstDecimal(0.0));
//        System.out.println(truncateToFirstDecimal(0));
//        System.out.println(truncateToFirstDecimal(0.2));
//        System.out.println(truncateToFirstDecimal(3));
//        System.out.println(truncateToFirstDecimal(3.5));
//        System.out.println(truncateToFirstDecimal(56.5));
//        System.out.println(truncateToFirstDecimal(53));
//        System.out.println(truncateToFirstDecimal(100.5));
//        System.out.println(truncateToFirstDecimal(100.555555));


        System.out.println(calculateSplitAmounts(100L,2));
    }


    /**
     * 计算金额等分列表，保证总额等于原金额
     * @param totalAmount 金额（单位：分），正数
     * @param count 分摊数量
     * @return 等分后的金额列表（负数，BigDecimal元）
     */
    private static List<BigDecimal> calculateSplitAmounts(Long totalAmount, int count) {
        List<BigDecimal> result = Lists.newArrayList();
        if (totalAmount == null || count <= 0) {
            return result;
        }
        BigDecimal total = new BigDecimal(totalAmount).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).negate();
        BigDecimal perAmount = total.divide(new BigDecimal(count), 2, RoundingMode.DOWN);
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                // 最后一条：总额减去前 n-1 条之和，修正误差
                BigDecimal sumOfOthers = perAmount.multiply(new BigDecimal(i));
                result.add(total.subtract(sumOfOthers));
            } else {
                result.add(perAmount);
            }
        }
        return result;
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
