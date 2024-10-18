package org.lwl.javacommon.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest {

    public static void main(String[] args) {
        Callable<Integer> myCallable = new MyCallable();    // 创建MyCallable对象
        FutureTask<Integer> ft = new FutureTask<Integer>(myCallable); //使用FutureTask来包装MyCallable对象
        Thread thread = new Thread(ft);   //FutureTask对象作为Thread对象的target创建新的线程
        thread.start();                      //线程进入到就绪状态
        System.out.println("主线程for循环执行完毕..");
        try {
            int sum = ft.get();            //取得新创建的新线程中的call()方法返回的结果
            System.out.println("sum = " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MyCallable implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            return 100;
        }
    }
}
