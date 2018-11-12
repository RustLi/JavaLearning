package sorts;

import utils.Utils;

import java.util.Arrays;

public class SelectSort {

    //时间复杂度o(n*n)
    public void selectSort(int[] input){
        if (input == null || input.length == 0){
            return;
        }
        int length = input.length;
        int min;
        for (int i = 0; i < length; i++){
            min = i;
            for (int j = i; j <= length-1; j++){
                if (input[j] < input[min]){
                    min = j;//以某一个为标准，找出剩下的最小的,与标准值进行交换，继续寻找接下来的最小值
                }
            }
            if (min != i){
                Utils.swap(input,min,i);
            }
        }
        System.out.println(Arrays.toString(input));
    }

    public  static void main(String[] args){
        int[] intput = {9,2,3,5,1,6,7};
        SelectSort selectSort = new SelectSort();
        selectSort.selectSort(intput);
    }
}
