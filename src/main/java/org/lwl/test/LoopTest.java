package org.lwl.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LoopTest {
    public static void main(String[] args) {
//        testContinue();
        testFilter();
    }

    private static void testContinue(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        //forEach 不能用continue和break
        list.forEach(it -> {
            if (it % 5 == 0){
                System.out.println("lwl");
                return;
            }
            System.out.println(it);
        });
    }

    private static void testFilter(){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        // 使用filter来跳过偶数
        numbers.stream()
                .filter(n -> n % 2 != 0)
                .forEach(System.out::println);
        System.out.println("numbers = " + numbers);

        // 找到第一个大于3的元素并停止
        Optional<Integer> firstGreaterThanThree = numbers.stream()
                .filter(n -> n > 7)
                .findFirst();

        System.out.println("firstGreaterThanThree = " + firstGreaterThanThree);
        firstGreaterThanThree.ifPresent(System.out::println);
    }
}
