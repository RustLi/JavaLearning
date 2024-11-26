package org.lwl.algorithms.strings;

/**
 * @date: 2019/12/18
 * @author: lwl
 * @description: 最大字串和,，最大连续字串和，需要考虑都是负值的情况
 *
 * [1,-1,2,3,-2]  -->> 5
 *
 * 核心思想是联机算法，数据只读入一次，读入后即处理；
 */
public class MaxSubStringSum {
    public static void main(String[] args) {
        int[] b = new int[]{1,-1,-2,3,-2};
//        System.out.println(maxSeqSubString(b));
        System.out.println(maxSubString(b));
    }

    /**
     * des: 最大连续字串和
     * 1. 每次读入相加，如果小于0，置为0，如果大于max，max更新;
     * 2. max==0，找出最大的数；
     */
    private static int maxSeqSubString(int[] a){
        if (a == null || a.length == 0){
            return 0;
        }
        int max = 0, sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            if (sum < 0){
                sum = 0;
            }else if (sum > max){
                max = sum;
            }
        }
        if (max == 0){
            max = a[0];
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i+1] > max){
                    max = a[i+1];
                }
            }
        }
        return max;
    }

    /**
     * des: 最大字串和
     * 1.如果数字大于0，相加；
     * 2.max == 0, 找出最大值；
     */
    private static int maxSubString(int[] a){
        if (a == null || a.length == 0){
            return 0;
        }
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > 0){
                max += a[i];
            }
        }
        if (max == 0){
            max = a[0];
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i+1] > max){
                    max = a[i+1];
                }
            }
        }
        return max;
    }
}
