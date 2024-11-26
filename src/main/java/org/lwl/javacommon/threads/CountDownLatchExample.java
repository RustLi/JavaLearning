package org.lwl.javacommon.threads;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 * 1. 基本概念
 * CountDownLatch 是一个同步辅助类，允许一个或多个线程等待其他线程完成操作。
 * 初始化：创建时需要指定一个计数值（count），这个值表示需要等待的事件数量。
 * 倒计数：每当一个事件发生时，调用 countDown() 方法减少计数值。
 * 等待：调用 await() 方法的线程会阻塞，直到计数值变为 0。
 * 2. 使用场景
 * 一次性等待：适用于一个或多个线程需要等待多个任务完成后再继续执行的场景。
 * 启动顺序：可以用来确保某些线程在所有准备工作完成后再开始执行。
 *
 *
 *
 * 主要区别
 * 重用性
 * CountDownLatch：不可重用，一旦计数值达到 0，无法重置。
 * CyclicBarrier：可以重用，一旦所有线程到达屏障点，屏障可以被重置，以便再次使用。
 * 等待条件
 * CountDownLatch：等待计数值变为 0。
 * CyclicBarrier：等待所有线程到达屏障点。
 * 应用场景
 * CountDownLatch：适用于一个或多个线程需要等待多个任务完成后再继续执行的场景。
 * CyclicBarrier：适用于多个线程需要分阶段协同工作的场景，每个阶段结束后所有线程需要同步。
 * 初始化参数
 * CountDownLatch：初始化时指定一个计数值。
 * CyclicBarrier：初始化时指定一个屏障点的参与者数量，还可以指定一个可选的 Runnable 任务。
 * 总结
 * CountDownLatch 适用于一次性等待多个任务完成的场景。
 * CyclicBarrier 适用于多阶段任务和需要多次重复执行相同任务的场景。
 **/
public class CountDownLatchExample {
    public static void main(String[] args) {
        int numberOfThreads = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is working");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " finished");
                latch.countDown();
            }).start();
        }

        try {
            latch.await(); // 主线程等待所有子线程完成
            System.out.println("All threads have finished their work");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
