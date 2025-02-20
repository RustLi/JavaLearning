package org.lwl.javacommon.guava;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiMapTest {
    public static void main(String[] args) {
        test();
    }

    private static void test(){
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("A","1");
        biMap.put("A","4");
        biMap.put("B","2");
        biMap.put("C","3");
        System.out.println(biMap);
        //使用key获取value
        System.out.println(biMap.get("A"));

        BiMap<String, String> inverse = biMap.inverse();
        //使用value获取key
        System.out.println(inverse.get("1"));

//        biMap.forcePut("D","3");
//        System.out.println(biMap);

        inverse.put("3","D");
        System.out.println("翻转后的 inverse = " + inverse);
        System.out.println("翻转后执行操作原来的biMap = " + biMap);
    }

    public static List<String> findKey(Map<String, String> map, String val){
        List<String> keys=new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(val))
                keys.add(key);
        }
        return keys;
    }
}
