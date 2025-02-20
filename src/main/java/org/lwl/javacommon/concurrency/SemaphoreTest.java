package org.lwl.javacommon.concurrency;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    private static final int NUM_THREADS = 5;
    private static final int NUM_PERMITS = 2;

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(NUM_PERMITS);

        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new Worker(semaphore, i));
            thread.start();
        }

    }

    static class Worker implements Runnable {
        private final Semaphore semaphore;
        private final int id;

        public Worker(Semaphore semaphore, int id) {
            this.semaphore = semaphore;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("Worker " + id + " is trying to acquire a permit.");
                semaphore.acquire();
                System.out.println("Worker " + id + " acquired a permit.");

                // 模拟工作
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("Worker " + id + " releasing the permit.");
                semaphore.release();
                System.out.println("Worker " + id + " released the permit.");
            }
        }
    }
}
