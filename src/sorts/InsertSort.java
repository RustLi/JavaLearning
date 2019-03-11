package sorts;

import sorts.base.Utils;

import java.util.Arrays;

public class InsertSort {


    //最好o(n),最差o(n*n),平均o(n*n)
    public void insertSort(int[] input){
        if (input == null || input.length == 0){
            return;
        }
        int length = input.length;
        int j,insertNum;
        for (int i = 1; i < length; i++){
            if (input[i] < input[i-1]){
                j = i-1;
                //当前值为要插入的值，如果当前的值小于前面的值，对前面的值进行移位操作，最后插入值
                insertNum = input[i];
                for (; j >= 0 && input[j] > insertNum; j--){
                    //移位
                    input[j+1] = input[j];
                }
                //插入值
                input[j+1] = insertNum;
            }
        }
        System.out.println(Arrays.toString(input));
    }


    private void insertSort1(int[] inputArr){
        int length = inputArr.length;
        for(int i = 0; i < length -1; i++){
            for(int j = i+1; j > 0; j--){
                if(inputArr[j] < inputArr[j-1]){
                    Utils.swap(inputArr, j, j-1);
                }
            }
        }
        System.out.println(Arrays.toString(inputArr));
    }

    public  static void main(String[] args){
        int[] intput = {9,2,3,5,1,6,7};
        InsertSort insertSort = new InsertSort();
        insertSort.insertSort1(intput);
    }
}
