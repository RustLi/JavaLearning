package org.lwl.javacommon.threads.lock;


public class LockTest {
    public static void main(String[] args) {
        BoundedQueue queue = new BoundedQueue(10);
        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                try {
                    System.out.println("add  = " + i);
                    queue.add(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++){
                try {
                    System.out.println("remove = " + i);
                    queue.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
