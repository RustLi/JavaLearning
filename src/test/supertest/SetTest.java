package test.supertest;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class SetTest {

    public static void main(String[] args) {
        testSet();
    }

    private static void testSet(){
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        Set<Integer> set2 = new HashSet<>();
        set2.add(2);
        set2.add(3);
        set2.add(4);
        set2.add(null);

        Set<Integer> set3 = Sets.intersection(set1,set2);
        Set<Integer> set4 = Sets.difference(set1,set2);
        Set<Integer> set5 = Sets.difference(set2,set1);

        System.out.println("set1 = " + set1);
        System.out.println("set2 = " + set2);
        System.out.println("set3 = " + set3);
        System.out.println("set4 = " + set4);
        System.out.println("set5 = " + set5);

        System.out.println("-----");
        System.out.println(set1);
        System.out.println(set2);
    }
}

