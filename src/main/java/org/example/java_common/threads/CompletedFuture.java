package java_common.threads;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: lwl
 * @date: 2021/6/25
 *
 * //CompletableFuture可以指定异步处理流程：
 *
 * thenAccept()处理正常结果；
 * exceptional()处理异常结果；
 * thenApplyAsync()用于串行化另一个CompletableFuture；
 * anyOf()和allOf()用于并行化多个CompletableFuture。
 *
 *
 **/
public class CompletedFuture {
    private static final Executor commonExecutor = Executors.newSingleThreadExecutor();
    private static final Executor executorService = Executors.newFixedThreadPool(5);


    public static void main(String[] args) throws Exception {
//        test1(false);
//        test2();

        test222();
    }

    private static int count = 0;

    private static void test222(){
        List<Long> list = Lists.newArrayList();
        for (long i = 0; i < 10000; i++) {
            list.add(i);
        }

        List<List<Long>> partition = Lists.partition(list, 1000);
        ArrayList<CompletableFuture<Void>> resultFutureList = Lists.newArrayListWithExpectedSize(partition.size());
        for (List<Long> part : partition) {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                try {
                    count++;
                    System.out.println("count 111 = " + count + ", part = " + part);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("e = "+ e);
                }
            }, executorService);
            resultFutureList.add(completableFuture);
        }
        CompletableFuture.allOf(resultFutureList.toArray(new CompletableFuture[0])).whenComplete((r,t)->{
            if (t != null) {
                System.out.println("写跟进记录 error " + t);
            }
        }).join();

        //发送kafka消息
        System.out.println("发送kafka, count = " + count);
    }

    /**
     * 异步处理
     **/
    private static void test1(boolean isExe){
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture
                .supplyAsync(() -> fetchPrice(isExe))
                .whenComplete((r,e)->{
                    System.out.println("r = " + r + ", e = " + e);
                })
                .exceptionally((e)->{
                    System.out.println("e = " + e);
                    return null;
                });

        CompletableFuture<Double> cf1 = CompletableFuture.supplyAsync(() -> fetchPrice(isExe));
        // 如果执行成功:
        cf1.thenAccept((result) -> {
            System.out.println("thenAccept price: " + result);
        });
        cf1.whenComplete((result,throwable)->{
            System.out.println("whenComplete result: " + result + ", e = " + throwable);
        });
        // 如果执行异常:
        cf1.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });


        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量异步处理
     **/
    private static void test2(){
        List<Integer> targetList = new ArrayList<>();
        targetList.add(0);
        targetList.add(1);
        targetList.add(2);
        targetList.add(3);

        List<List<Integer>> partition = Lists.partition(targetList, 2);
        ArrayList<Object> resultFutureList = Lists.newArrayListWithExpectedSize(partition.size());
        for (List<Integer> list: partition) {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> doSomethingForValue(list), commonExecutor);
            resultFutureList.add(completableFuture);
        }
        CompletableFuture.allOf(resultFutureList.toArray(new CompletableFuture[partition.size()])).whenComplete((r, t) -> {
            if (t != null) {
                System.out.println("e = " + t);
            }
        }).join();
    }

    private static void doSomethingForValue(List<Integer> list){
        System.out.println("逻辑处理");
        list.forEach(t -> {
            System.out.println("t = " + t);
        });
    }

    private static Double fetchPrice(boolean isException) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isException){
            throw new RuntimeException("fetch price failed!");
        }
        return Math.random();
    }
}
