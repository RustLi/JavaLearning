package org.lwl.datastructure;

import java.util.PriorityQueue;

/**
 *
 *@author  lwl
 *@date 2024/10/24
 *@description 优先队列默认是最小堆，根节点最小。构造函数可以传入比较器，构成最大堆。
 */
public class PriorityQueueExample {
    public static void main(String[] args) {
        // 创建一个优先队列
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        // 插入元素
        priorityQueue.add(10);
        priorityQueue.add(20);
        priorityQueue.add(15);
        priorityQueue.add(5);

        System.out.println("Initial PriorityQueue: " + priorityQueue);

        // 查找优先级最高的元素
        Integer peekElement = priorityQueue.peek();
        System.out.println("Peek element (highest priority): " + peekElement);

        // 删除优先级最高的元素
        Integer pollElement = priorityQueue.poll();
        System.out.println("Polled element (highest priority): " + pollElement);
        System.out.println("PriorityQueue after polling: " + priorityQueue);

        // 修改元素（通过删除再插入）
        int oldElement = 15;
        int newElement = 25;
        if (priorityQueue.remove(oldElement)) {
            priorityQueue.add(newElement);
            System.out.println("Modified PriorityQueue: " + priorityQueue);
        } else {
            System.out.println("Element " + oldElement + " not found in PriorityQueue.");
        }

        // 插入更多元素
        priorityQueue.offer(30);
        priorityQueue.offer(10);
        System.out.println("PriorityQueue after adding more elements: " + priorityQueue);

        // 遍历优先队列
        System.out.println("Traversing PriorityQueue:");
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }
    }
}
