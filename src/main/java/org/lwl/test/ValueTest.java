package org.lwl.test;

import java.util.concurrent.*;

public class ValueTest {

    private static final ExecutorService threadPool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        System.out.println(111);
        test1();
//        test2();
//        test3();
        // 确保主线程等待任务完成
        threadPool.shutdown();
        System.out.println(222);
    }

    private static void test1(){
        threadPool.submit(() -> {
            System.out.println(333);
            // 获取邀请人id
            long inviteUserId = getUserId();
            System.out.println(444);
        });
        System.out.println(555);
    }

    private static Long getUserId(){
        return null;
    }

    private static void test2(){
        threadPool.submit(() -> {
            System.out.println(333);
            try {
                long inviteUserId = getUserId();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error = " + e);
            }
            System.out.println(444);
        });
    }



    private static void test3(){
        System.out.println(333);
        // 获取邀请人id
        long inviteUserId = getUserId();
        System.out.println(444);
    }
}
