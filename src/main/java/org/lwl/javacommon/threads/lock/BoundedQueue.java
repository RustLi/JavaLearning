package org.lwl.javacommon.threads.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现一个有界队列，队列为空时，获取操作线程阻塞；队列满时，插入操作线程阻塞，直到为空
 **/
public class BoundedQueue<T>{
    private Object[] items;
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    /**
     * 如果数组满了，添加线程进入等待状态，直到有空
     **/
    public void add(T t) throws InterruptedException{
        lock.lock();
        try {
            while (count == items.length){
                notEmpty.await();
            }
            items[addIndex] = t;
            if (++addIndex == items.length){
                addIndex = 0;
            }
            count++;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 从头部删除一个元素，如果数组空，则删除线程进入等待，直到有新元素
     **/
    public T remove() throws InterruptedException{
        lock.lock();
        try {
            while (count == 0){
                notFull.await();
            }
            T t = (T) items[removeIndex];
            if (++removeIndex == items.length){
                removeIndex = 0;
            }
            count--;
            notFull.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }
}
