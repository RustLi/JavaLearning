package org.lwl;

import org.lwl.test.LiveTimeGapCountItem;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        LiveTimeGapCountItem liveTimeGapCountItem = new LiveTimeGapCountItem();
        Long count = liveTimeGapCountItem.getCount();
        System.out.println("count = " + count);
    }
}