package test;

import data_structure.array.Array;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadTest {

    private static int count = 0;
    public static void main(String[] args) {
        ThreadTest test = new ThreadTest();
        test.test();
    }

    public void test(){
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    intoClass();
                }
            }).start();
        }
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
