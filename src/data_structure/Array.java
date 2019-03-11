package data_structure;

import java.util.*;
import java.util.function.Consumer;

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
        Array array = new Array();
        int[] mList = new int[3];
        mList[0] = 8;
        mList[1] = 9;
        mList[2] = 10;
        for (int i = 0; i < mList.length; i++) {
            System.out.println(mList[i]);
        }
        array.insertElement(mList,1,3);
    }


    /**
     * @description: 插入元素到数组i处
     **/
    private  final int MAXSIZE = 10000;
    private  void insertElement(int[] mList,int position,int insertNum){
        if (mList == null) return;//null
        if (mList.length > MAXSIZE) return ;//size已满
        if (position < 1 || position > mList.length + 1) return;//不在范围内
        int[] newList = new int[4];
        if (position <= mList.length){
            for (int i = 0; i < position; i++) {
                newList[i] = mList[i];
            }
            newList[position] = insertNum;
            for (int i = mList.length -1; i >= position ; i--) {
                newList[i+1] = mList[i];
            }
        }
        for (int i = 0; i < newList.length; i++) {
            System.out.println(newList[i]);
        }
    }
}
