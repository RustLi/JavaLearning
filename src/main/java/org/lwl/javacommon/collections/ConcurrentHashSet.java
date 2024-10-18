package org.lwl.javacommon.collections;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConcurrentHashSet {

    private Set<Long> manageBizIds = Sets.newHashSet(1L,2L,3L);
    public static void main(String[] args) {
        ConcurrentHashSet concurrentHashSet = new ConcurrentHashSet();

        for (long j = 0; j < 10; j++) {
            final long j1 = j;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("线程:" + Thread.currentThread().getName() + ", j1 = " + j1);
                    concurrentHashSet.test3(1L);
                }
            }).start();
        }
    }

    private void test2(Long bizId){
        manageBizIds.remove(bizId); // 在迭代之前先移除一个元素

        Iterator<Long> iterator = manageBizIds.iterator();
        while (iterator.hasNext()) {
            Long manageBizId = iterator.next();
            System.out.println("Processing: " + manageBizId);
            iterator.remove(); // 使用 Iterator.remove() 安全地移除元素
        }
    }

    private void test3(Long bizId) {
        manageBizIds.remove(bizId); // 在迭代之前先移除一个元素

        Set<Long> newManageBizIds = new HashSet<>(manageBizIds);
        for (Long manageBizId : newManageBizIds) {
            System.out.println("Processing: " + manageBizId);
            manageBizIds.remove(manageBizId); // 在新集合上迭代，原集合上移除元素
        }
    }

}
