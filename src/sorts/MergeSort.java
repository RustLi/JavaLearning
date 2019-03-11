package sorts;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] intput = {9,2,3,5,1,6,7};
        MergeSort mergeSort = new MergeSort();
        int[] output = new int[7];
        mergeSort.mergeSort(intput,output,0,intput.length-1);
        System.out.println(Arrays.toString(output));
    }

    /**
     * @description: 归并排序，递归
     **/
    private void mergeSort(int[] inArr,int[] outArr,int low, int high){
        int middle;
        if (low == high){
            outArr[low] = inArr[low];
        }else {
            middle = low + (high - low)/2;
            mergeSort(inArr,outArr,low,middle);//左边部分进行二分，并完成有序排列
            mergeSort(inArr,outArr,middle+1,high);//右边部分进行二分，并完成有序排列
            merge(inArr, outArr,low, middle, high);//合并两部分
        }
    }

    /**
     * @description: 合并两个有序数组
     **/
    private void merge(int[] inArr, int[] outArr, int low, int middle, int high){
        int j,k,p;
        for (j = middle+1, k = low; j < high && low < middle; k++) {
            if (inArr[low] < inArr[j]){
                outArr[k] = inArr[low++];
            }else {
                outArr[k] = inArr[j++];
            }
        }
        if (low <= middle){
            for (p = 0; p < middle-low ; p++) {
                outArr[k+1] = inArr[low+1];
            }
        }
        if (j <= high){
            for (p = 0; p < high-j; p++) {
                outArr[k+1] = inArr[j+1];
            }
        }
    }
}
