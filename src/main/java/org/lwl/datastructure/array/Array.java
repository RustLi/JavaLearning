package org.lwl.datastructure.array;

import java.util.*;

/**
 * @description: 线性表操作
 * @author: RustLi
 * @date: 2019/1/4
 **/
public class Array {
    public static void main(String[] args) {
        /*List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.forEach(System.out::println);
        list.set(1,2);
        list.forEach(System.out::println);
        Iterator iterator = list.iterator();
        iterator.forEachRemaining(o -> {
            if (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        });*/
//        Array array = new Array();
//        int[] mList = new int[3];
//        mList[0] = 8;
//        mList[1] = 9;
//        mList[2] = 10;
//        for (int i = 0; i < mList.length; i++) {
//            System.out.println(mList[i]);
//        }
//        array.insertElement(mList,1,3);

//        int[] a0 = new int[5];
//        int[] a1 = {1,2,3};
//        System.out.println(a0[2]);
//        System.out.println(a1[2]);


//        Integer[] a = {5, 1, 2, 3, 4};
//        List<Integer> v1 = new ArrayList<>(Arrays.asList(a));
////        v1.set(2,-1);
////        v1.add(2,-1);
////        Collections.sort(v1);
//        Iterator it = v1.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
//        for (int i = 0; i < v1.size(); i++) {
//            System.out.println(v1.get(i));
//        }
//        List<Integer> v2 = new ArrayList<>(v1);
//        System.out.println(v2.get(1));

//        Map<String, String> myMap = new HashMap<String, String>();
//        myMap.put("1","a");
//        myMap.put("2","b");
//        myMap.put("3","c");
        //省略myMap的的赋值过程
//         Iterator<Map.Entry<String,String> iteratorMap = myMap.entrySet().iterator();
//        Iterator<Map.Entry<String, String>> mapIterator = myMap.entrySet().iterator();
//        while (mapIterator.hasNext()) {
//            Map.Entry<String,String> mapEntry = mapIterator.next();
//            if (mapEntry.getKey().equals("1")){
//                System.out.println(mapEntry.getValue());
//            }
//        }
//        for (Object obj: myMap.entrySet()) {
//            System.out.println(obj);
//        }

        int[][] a = new int[3][2];
    }

    /**
     * des: 起别名：赋值会改变原来的数组
     * @param
     */
    private void alisName(){
        int[] a = new int[3];
        a[1] = 111;
        int[] b = a;
        b[1] = 222;
        System.out.println(a[1]);
    }


    /**
     * @description: 插入元素到数组i处
     **/
    private final int MAXSIZE = 10000;

    private void insertElement(int[] mList, int position, int insertNum) {
        if (mList == null) return;//null
        if (mList.length > MAXSIZE) return;//size已满
        if (position < 1 || position > mList.length + 1) return;//不在范围内
        int[] newList = new int[4];
        if (position <= mList.length) {
            for (int i = 0; i < position; i++) {
                newList[i] = mList[i];
            }
            newList[position] = insertNum;
            for (int i = mList.length - 1; i >= position; i--) {
                newList[i + 1] = mList[i];
            }
        }
        for (int i = 0; i < newList.length; i++) {
            System.out.println(newList[i]);
        }
    }

    /**
     * des: 判断一个数是否为素数
     * @param
     */
    private static  boolean  isPrime(int N){
        if (N < 2) return false;
        //此处 i*i 是因为一个数的因数是成对出现的，如100的因数有：1和100，2和50，4和25，5和20，10和10，
        //一个因数必然小于100的开平方，另一个必然大于100的开平方；
        for (int i = 2; i * i < N; i++) {
            if (N % i == 0) return false;
        }
        return true;
    }

}
