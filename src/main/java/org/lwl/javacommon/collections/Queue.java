package org.lwl.javacommon.collections;

import java.util.concurrent.LinkedBlockingQueue;

public class Queue {
    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue(5);


    public static void main(String[] args) {
        queue.offer(1);
        queue.offer(2);

        System.out.println(queue);

        Integer aa = queue.element();
        System.out.println(aa);

        Integer bb = queue.poll();
        System.out.println(queue);
        System.out.println(bb);
    }


}
