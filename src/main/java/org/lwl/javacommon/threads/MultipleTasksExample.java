package org.lwl.javacommon.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultipleTasksExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        multiFuture();
        multiFutureTask();
    }

    private static void multiFuture() throws ExecutionException, InterruptedException{
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // 创建任务列表
        List<Future<Integer>> futures = new ArrayList<>();

        // 提交多个任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            Future<Integer> future = executorService.submit(() -> {
                Thread.sleep((long) (Math.random() * 3000)); // 模拟随机耗时操作
                return taskId * 10; // 返回结果
            });

            futures.add(future);
        }

        // 获取所有任务的结果
        for (Future<Integer> future : futures) {
            Integer result = future.get(); // 等待任务完成并获取结果
            System.out.println("Result: " + result + ", time = " + System.currentTimeMillis());
        }

        System.out.println("end...");

        // 关闭ExecutorService
        executorService.shutdown();
    }

    private static void multiFutureTask() throws ExecutionException, InterruptedException{
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // 创建任务列表
        List<Future<String>> futures = new ArrayList<>();

        // 提交多个任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            Callable<String> callable = () -> {
                Thread.sleep((long) (Math.random() * 1000)); // 模拟随机耗时操作
                return "Task " + taskId + " completed";
            };

            FutureTask<String> futureTask = new FutureTask<>(callable);
            executorService.submit(futureTask);
            futures.add(futureTask);
        }

        // 获取所有任务的结果
        for (Future<String> future : futures) {
            String result = future.get(); // 等待任务完成并获取结果
            System.out.println("Result: " + result);
        }

        // 关闭 ExecutorService
        executorService.shutdown();
    }
}
