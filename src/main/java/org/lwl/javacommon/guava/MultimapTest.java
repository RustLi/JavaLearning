package org.lwl.javacommon.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;

import java.util.*;

public class MultimapTest {
    public static void main(String[] args) {
        test();
    }

    private static void test(){
        //常规
        Map<String, List<Integer>> map=new HashMap<>();
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        map.put("A",list);
        List<Integer> list1=new ArrayList<>();
        list1.add(5);
        map.put("B",list1);
        System.out.println("map = " + map);

        //Multimap实现
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("A",1);
        multimap.put("A",2);
        multimap.put("A",2);
        multimap.put("A",3);
        multimap.put("B",5);
        System.out.println("multimap  111  = " + multimap);

        List<Integer> value = multimap.get("A");
        System.out.println("value = " + value);

        List<Integer> value1 = multimap.get("C");
        System.out.println("value1 = " + value1);

//        value.remove(0);//这个0是下标
//        System.out.println("删除了第一个元素：value = " + value);

//        multimap.remove("A",2);

        Map<String, Collection<Integer>> map2 = multimap.asMap();
        map2.get("A").add(4);
        System.out.println("map2 = " + map2);
//        System.out.println(multimap);
        
        //采用MultimapBuilder构建
        // creates a ListMultimap with tree keys and array list values
        ListMultimap<String, Integer> treeListMultimap =
                MultimapBuilder.treeKeys().arrayListValues().build();
        treeListMultimap.put("A",1);
        treeListMultimap.put("A",2);
        treeListMultimap.put("A",3);
        treeListMultimap.put("B",5);
        System.out.println("treeListMultimap = " + treeListMultimap);

    }
}
