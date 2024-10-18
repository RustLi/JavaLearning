package org.lwl.javacommon.threads;

public class ThreadLocalTest {

    private static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        threadLocal.set(1);

        Thread thread = new Thread(() -> {
            System.out.println("Sub thread: " + threadLocal.get() + ", time = " + System.currentTimeMillis());

            threadLocal.set(2);

            System.out.println("Sub thread: after =  " + threadLocal.get() + ", time = " + System.currentTimeMillis());

        });
        thread.start();

        try {
            Thread.sleep(1000);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Main thread: " + threadLocal.get() + ", time = " + System.currentTimeMillis());
    }
}
