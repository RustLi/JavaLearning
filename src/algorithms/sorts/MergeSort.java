package algorithms.sorts;

import java.util.Arrays;
/**
 * @description:
 * 1.采用递归的方法，分成两部分;
 * 2.每部分直到无法再分，然后合成有序序列;
 * 3.将两个有序序列合成一个有序序列
 * @author: RustLi
 * @date: 2019-03-12
 **/
public class MergeSort {

    public static void main(String[] args) {
        int[] intput = {9,2,3,5,1,6,7};
        MergeSort mergeSort = new MergeSort();
        mergeSort.mergeSort(intput,0,intput.length-1);
        System.out.println(Arrays.toString(intput));
    }

    /**
     * @description: 归并排序，递归
     **/
    private void mergeSort(int[] inArr,int low, int high){
        int middle = low + (high - low)/2;
        if (low < high){
            mergeSort(inArr,low,middle);
            mergeSort(inArr,middle+1,high);
            merge(inArr,low,middle,high);
        }
    }

    /**
     * @description: 合并两个有序数组
     **/
    private void merge(int[] inArr, int low, int middle, int high){
        int j = middle+1,i = low,k = 0;
        int[] temp = new int[high-low+1];
        //合并两个有序，选取两个指针，每次比较两个序列的第一个数，小的放入k中
        while (i <= middle && j <= high){
            if (inArr[i] <= inArr[j]){
                temp[k++] = inArr[i++];
            }else {
                temp[k++] = inArr[j++];
            }
        }
        //左边的数移入数组
        while (i <= middle){
            temp[k++] = inArr[i++];
        }
        //右边剩余的数移入数组
        while (j <= high){
            temp[k++] = inArr[j++];
        }
        //复制新数组数据到inArr数组中
        for (int p = 0;p < temp.length; p++){
            inArr[low + p] = temp[p];
        }
    }
}
