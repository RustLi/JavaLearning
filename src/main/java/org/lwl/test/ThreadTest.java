package org.lwl.test;

public class ThreadTest {

    private static int count = 0;
    public static void main(String[] args) {
        ThreadTest test = new ThreadTest();
        test.test();
    }

    public void test(){
//        for (int i = 0; i < 3; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    intoClass();
//                }
//            }).start();
//        }
//

        new Thread(new Runnable(){

            @Override
            public void run() {
                System.out.println("当前线程111: " + Thread.currentThread().getName());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("当前线程222: " + Thread.currentThread().getName());
                    }
                }).start();
            }
        }).start();
    }

    private void intoClass(){
        synchronized (ThreadTest.class){
            System.out.println("before name = " + Thread.currentThread().getName() + ", count = " + count);
//        synchronized (ThreadTest.class){
//            count++;
//        }
            count++;
            System.out.println("afetr name = " + Thread.currentThread().getName() + ", count = " + count);
        }
    }
}
