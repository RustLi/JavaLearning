package sorts;

import java.util.List;

import static sorts.base.Utils.swap;

public class ShellSort {

    private void shellSort(int[] array) {
        if (array == null) {
            return;
        }
        int length = array.length;
        if (length <= 0) {
            return;
        }
        int incre = length;
        while (true) {
            incre = incre/2;
            for(int k = 0;k<incre;k++){    //根据增量分为若干子序列

                for(int i=k+incre;i<length;i+=incre){

                    for(int j=i;j>k;j-=incre){
                        if(array[j]<array[j-incre]){
                            swap(array, j, j-1);
                        }else{
                            break;
                        }
                    }
                }
            }

            if(incre == 1){
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] intput = {9,2,3,5,1,6,7};
        for (int i = 0; i < intput.length; i++) {
            System.out.println(intput[i]);
        }
        System.out.println("=====");
        ShellSort shellSort = new ShellSort();
        shellSort.shellSort2(intput);
        System.out.println("after");
        for (int i = 0; i < intput.length; i++) {
            System.out.println(intput[i]);
        }
    }

    private void shellSort2(int[] arr){
        int length = arr.length;
        int inc = length;
        int i, j;
        do {
            inc = inc / 3 + 1;//增量
            for (i = inc ; i < length; i++){//增量划分为子序列
                if (arr[i] < arr[i - inc]){
                    int temp = arr[i];//保存后面的值
                    for (j = i-inc; j >= 0 && arr[j] > temp; j-=inc) {
                        arr[j+inc] = arr[j];//如果后面的值小于前面的，把前面的赋值给后面
                    }
                    arr[j+inc] = temp;//保存的后面的值给前面，实现交换
                }
            }
        }while (inc > 1);
    }


}
