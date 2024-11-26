package org.lwl.javacommon.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 1. 基本概念
 * CyclicBarrier 是一个同步辅助类，允许一组线程互相等待，直到所有线程都到达一个屏障点。
 * 初始化：创建时需要指定一个屏障点的参与者数量（parties），还可以指定一个可选的 Runnable 任务，在所有线程到达屏障点后执行。
 * 等待：调用 await() 方法的线程会阻塞，直到所有线程都调用了 await() 方法。
 * 重用：与 CountDownLatch 不同，CyclicBarrier 可以被重用，即一旦所有线程到达屏障点，屏障可以被重置，以便再次使用。
 * 2. 使用场景
 * 多阶段任务：适用于多个线程需要分阶段协同工作的场景，每个阶段结束后所有线程需要同步。
 * 循环任务：适用于需要多次重复执行相同任务的场景。
 **/
public class CyclicBarrierExample {
    public static void main(String[] args) {
        int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("All threads have reached the barrier point");
        });

        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is working");
                try {
                    Thread.sleep(1000);
                    barrier.await(); // 等待所有线程到达屏障点
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " has passed the barrier");
            }).start();
        }
    }
}
