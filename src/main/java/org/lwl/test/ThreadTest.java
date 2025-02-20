package org.lwl.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import kotlin.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.lwl.utils.NamedThreadFactory;
import org.lwl.utils.ThreadPoolMonitorUtils;

import java.util.*;
import java.util.concurrent.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class ThreadTest {


    private static int count = 0;

    private static final int processors = Runtime.getRuntime().availableProcessors();

    public static final String EXECUTOR_NAME = "weworkCustomerService";

    public static final int BATCH_PAGE_SIZE = 5;

    private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(processors * 2, processors * 2,
                2,TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(100000),
                new NamedThreadFactory(EXECUTOR_NAME),
                new ThreadPoolExecutor.CallerRunsPolicy());

    private ExecutorService syncHistoryExecutorService;
    private final int SIZE = Runtime.getRuntime().availableProcessors();
    private ExecutorService createThreadPool(){
        return new ThreadPoolExecutor(SIZE, SIZE * 2, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(2048),
                new NamedThreadFactory("syncHistoryExecutorService"), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void main(String[] args) {
        ThreadTest test = new ThreadTest();
//        test.test();

//        Map<String,String> result = test.testGetResultMap();
//        System.out.println("result = " + result);

        test.syncLiveGlobalStatisticForHistory();
    }

    public void syncLiveGlobalStatisticForHistory(){
        // 检查线程池是否已经关闭，如果是则重新初始化
        if (syncHistoryExecutorService == null || syncHistoryExecutorService.isShutdown()) {
            syncHistoryExecutorService = createThreadPool();
//            ThreadPoolMonitorUtils.addToMonitor("syncHistoryExecutorService", syncHistoryExecutorService);
        }
        for (int i = 0; i < 100; i++) {
            System.out.println("i = " + i);
            int finalI = i;
            syncHistoryExecutorService.execute(() -> {
                try {
                    //180个日期，1个长期直播，每个500-900ms, 平均800ms, 算180s
                    if (finalI % 2 == 0) {
                        System.out.println("finalI = " + finalI);
                        Thread.sleep(3 * 60 * 1000L);
                    }else {
                        System.out.println("finalI = " + finalI);
                        Thread.sleep(1000L);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        syncHistoryExecutorService.shutdown();
    }


    private Map<String,String> testGetResultMap() {
        List<String> unionidList = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15");
        Map<String, String> result = Maps.newConcurrentMap();
        Lists.partition(unionidList, BATCH_PAGE_SIZE).forEach(part -> {
            executorService.submit(() -> {
                try {
                    result.putAll(doBatchSave(part));
                } catch (Exception e) {
                    System.out.println("batchSave error = " + e.getMessage());
                }
            });
        });
        return result;
    }

    private Map<String,String> doBatchSave(List<String> list) {
        Map<String,String> result = new HashMap<>();
        list.forEach(unionId -> result.put(unionId, unionId));
        System.out.println("doBatchSave result = " + result);
        return result;
    }

    public void test(){
//        for (int i = 0; i < 3; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    intoClass();
//                }
//            }).start();
//        }
//

        new Thread(new Runnable(){

            @Override
            public void run() {
                System.out.println("当前线程111: " + Thread.currentThread().getName());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("当前线程222: " + Thread.currentThread().getName());
                    }
                }).start();
            }
        }).start();
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
