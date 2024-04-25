package org.lwl.test;

import java.util.Collection;
import java.util.Collections;

public class CollectionTest {

    public static void main(String[] args) {
        String orderNo = null;
        Collection<String> orderNos = Collections.singletonList(orderNo);
//        Collection<String> orderNos = Collections.singleton(orderNo);
        System.out.println("orderNos = " + orderNos + ", size = " + orderNos.size());
    }
}
