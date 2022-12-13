package java_common.threads;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: lwl
 * @date: 2022/9/14
 * @description: 多线程并发执行，同步返回
 **/
public class ExecutorTest {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        long cTime = System.currentTimeMillis();
        Map<String,String> mMap = asyncFetchUserContactIds();

//        mMap.forEach((k,v)->{
//            System.out.println("map: key = " + k + ", value = " + v);
//        });

        System.out.println("时间：" + (System.currentTimeMillis() - cTime));

        long c1Time = System.currentTimeMillis();
        Map<String,String> m1Map = asyncFetchUserContactIds();

//        mMap.forEach((k,v)->{
//            System.out.println("map: key = " + k + ", value = " + v);
//        });

        System.out.println("时间1：" + (System.currentTimeMillis() - c1Time));
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
