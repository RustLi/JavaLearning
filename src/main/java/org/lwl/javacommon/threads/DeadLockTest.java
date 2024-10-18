package org.lwl.javacommon.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DeadLockTest {

    public static void main(String[] args) {
        DeadLockTest test = new DeadLockTest();
//        test.lockTest();

        String path = "/Users/lwl/lwl/111";
        File sFile = new File(path);

        try (FileOutputStream fout = new FileOutputStream(sFile)){
            fout.flush();
            System.out.println(333);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(2344);
    }

    public Object resourceA = new Object();
    public Object resourceB = new Object();
    private void lockTest(){
        Runnable runnableA = new Runnable() {
            @Override
            public void run() {
                synchronized(resourceA) {
                    System.out.printf(
                            "[INFO]: %s get resourceA" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf(
                            "[INFO]: %s trying to get resourceB" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    synchronized(resourceB) {
                        System.out.printf(
                                "[INFO]: %s get resourceB" + System.lineSeparator(),
                                Thread.currentThread().getName()
                        );
                    }
                    System.out.printf(
                            "[INFO]: %s has done" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                }
            }
        };
        Runnable runnableB = new Runnable() {
            @Override
            public void run() {
                synchronized(resourceB) {
                    System.out.printf(
                            "[INFO]: %s get resourceB" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf(
                            "[INFO]: %s trying to get resourceA" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                    synchronized(resourceA) {
                        System.out.printf(
                                "[INFO]: %s get resourceA" + System.lineSeparator(),
                                Thread.currentThread().getName()
                        );
                    }
                    System.out.printf(
                            "[INFO]: %s has done" + System.lineSeparator(),
                            Thread.currentThread().getName()
                    );
                }
            }
        };
        new Thread(runnableA).start();
        new Thread(runnableB).start();
    }
}
