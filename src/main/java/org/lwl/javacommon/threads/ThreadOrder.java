package org.lwl.javacommon.threads;

/**
 * @description: T1,T2,T3按照顺序执行的几种方法
 * @author: RustLi
 * @date: 2019-03-12
 **/
public class ThreadOrder {

    /**
     * @description: 方法一：T3中调用T2，T2中调用T1
     **/
    /*private static ThreadOrder threadOrder = new ThreadOrder();
    public static void main(String[] args) {
        threadOrder.new T3().start();
    }
    class T1 extends Thread{
        @Override
        public void run() {
            System.out.println("T1...");
        }
    }
    class T2 extends Thread{
        @Override
        public void run() {
            System.out.println("T2...");
            new T1().start();
        }
    }
    class T3 extends Thread{
        @Override
        public void run() {
            System.out.println("T3...");
            new T2().start();
        }
    }*/


    /**
     * @description: 方法二：join方法，阻塞当前线程，直到join线程执行完毕
     **/
    private static ThreadOrder threadOrder = new ThreadOrder();
    public static void main(String[] args) throws InterruptedException {
        threadOrder.new T3().start();
        threadOrder.new T3().join();

        threadOrder.new T2().start();
        threadOrder.new T2().join();

        threadOrder.new T1().start();
        threadOrder.new T1().join();

    }
    class T1 extends Thread{
        @Override
        public void run() {
            System.out.println("T1...");
        }
    }
    class T2 extends Thread{
        @Override
        public void run() {
            System.out.println("T2...");
        }
    }
    class T3 extends Thread{
        @Override
        public void run() {
            System.out.println("T3...");
        }
    }
}
