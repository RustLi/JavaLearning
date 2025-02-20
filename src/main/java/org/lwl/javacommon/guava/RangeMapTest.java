package org.lwl.javacommon.guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class RangeMapTest {
    public static void main(String[] args) {
        test();
    }

    private static void test(){
        System.out.println(getStage(59));
        System.out.println(getStage(60));
        System.out.println(getStage(90));
        System.out.println(getStage(91));

        //RangeMap
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0,60),"C");
        rangeMap.put(Range.closed(60,90),"B");
        rangeMap.put(Range.openClosed(90,100),"A");
        System.out.println(rangeMap);

        System.out.println(rangeMap.get(59));
        System.out.println(rangeMap.get(60));
        System.out.println(rangeMap.get(90));
        System.out.println(rangeMap.get(91));

        rangeMap.remove(Range.closed(70,80));
        System.out.println(rangeMap);
    }

    private static String getStage(int score){
        if (0<=score && score<60){
            return "C";
        } else if (60<=score && score<=90){
            return "B";
        } else if (90<score && score<=100){
            return "A";
        }
        return null;
    }
}
