package test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CMap {

    ConcurrentHashMap<Integer,Integer> concurrentHashMap = new ConcurrentHashMap();
    ExecutorService executorService = Executors.newCachedThreadPool();

    public void test(){
        System.out.println("test");
        for (int i = 0; i < 5000; i++) {
            final int j = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("j = " + j + " id = " + Thread.currentThread().getId());
                    add(j,j);
                }
            });
        }

        for (int i = 0; i < 5000; i++) {
            final int k = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("k = " + k + " id = " + Thread.currentThread().getId());
//                    add(j,j);
                    int m = concurrentHashMap.get(k);
                    System.out.println("m = " + m);
                    concurrentHashMap.remove(m);
                }
            });
        }

        try {
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("finish size = " + concurrentHashMap.size());
        for (int i = 0; i < concurrentHashMap.size(); i++) {
            int n = concurrentHashMap.get(i);
            System.out.println("n = " + n);
        }
    }

    private void add(Integer key, Integer value){
        if (key != null && value != null && !concurrentHashMap.containsKey(key)){
            concurrentHashMap.put(key,value);
        }
    }
}
