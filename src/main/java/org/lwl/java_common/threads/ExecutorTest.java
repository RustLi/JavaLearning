package java_common.threads;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: lwl
 * @date: 2022/9/14
 * @description: 多线程并发执行，同步返回
 **/
public class ExecutorTest {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    ExecutorService executor = new ThreadPoolExecutor(
            1,  // corePoolSize
            1,  // maximumPoolSize
            0L, // keepAliveTime
            java.util.concurrent.TimeUnit.MILLISECONDS,
            new java.util.concurrent.LinkedBlockingQueue<>(1),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) {

        ExecutorTest executorTest = new ExecutorTest();
        for (int i = 0; i < 10; i++) {
            executorTest.testCallRunsPolicy(i);
        }

    }


    private void testCallRunsPolicy(int outer){
        // 创建线程池，设置核心线程数和最大线程数为 1，
        // 工作队列大小为 1，使用 CallerRunsPolicy 策略

        // 提交任务
//            System.out.println(" is running in thread: " +
//                    Thread.currentThread().getName() + " outer:" + outer);
            executor.submit(() -> {
                System.out.println(" is running in thread: " +
                        Thread.currentThread().getName() + " outer:" + outer);
                try {
                    Thread.sleep(2000); // 模拟任务执行时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        // 关闭线程池
//        executor.shutdown();
    }

    /**
     * 175ms
     **/
    private static Map<String, String> fetchUserContactIds() {
        Map<String, String> map = Maps.newHashMap();

        List<String> userIds = Lists.newArrayList();
        userIds.add("111");
        userIds.add("222");
        userIds.add("333");

        List<Callable<Void>> fetchTasks = Lists.newArrayList();
        for (String weworkUserId : userIds) {
            System.out.println(222 + ", thread = " + Thread.currentThread().getName() + "， cTime = "
                    + LocalDateTime.now() + ", userId = " + weworkUserId);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 145ms 多线程可以节约时间
     **/
    private static Map<String, String> asyncFetchUserContactIds() {
        System.out.println(111);
        Map<String, String> map = Maps.newHashMap();

        List<String> userIds = Lists.newArrayList();
        userIds.add("111");
        userIds.add("222");
        userIds.add("333");

        List<Callable<Void>> fetchTasks = Lists.newArrayList();
        for (String weworkUserId : userIds) {
            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    System.out.println(222 + ", thread = " + Thread.currentThread().getName() + "， cTime = " + LocalDateTime.now());
                    try {
                        synchronized (map) {
                            map.put(weworkUserId, weworkUserId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            };

            fetchTasks.add(task);
        }

        // 执行并发任务
        try {
            //待所有的任务执行完成后统一返回
            executorService.invokeAll(fetchTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        System.out.println(333);
        return map;
    }

}
