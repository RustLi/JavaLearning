package algorithms.sorts;

import java.util.Arrays;

public class QuickSort {

    public  static void main(String[] args){
        int[] intput = {9,2,3,5,1,6,7};
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(intput);
    }

    //时间复杂度，最好情况：不断均分，o(n*logn),最坏情况：第i次需要n-i次比较，o(n*n)，平均情况：o(n*logn)
    //空间复杂度，最好：o(logn),最坏o(n)，平均o(logn)
    public void quickSort(int[] input){
        if (input == null){
            return;
        }
        int length = input.length;
        if (length == 0){
            return;
        }
        System.out.println(Arrays.toString(input));
        quickSort(input,0,length-1);
        System.out.println(Arrays.toString(input));
    }

    private void quickSort(int[] input,int low,int high){
        if (low < high){//该行是必须的，每次排完序，low和high会变化，因为middle-1和middle+1;否则会一直递归调用
            int middle = findMiddle(input,low,high);
            quickSort(input,low,middle-1);
            quickSort(input,middle + 1,high);
        }
    }

    private int findMiddle(int[] input,int start,int end){
        //优化key的选取，采用左，中，右3个值的中间值；
        int m = start + (end - start)/2;
        if (input[start] > input[end]){
            swap(input,start,end);
        }
        if (input[m] > input[end]){
            swap(input,m,end);
        }
        if (input[m] < input[start]){
            swap(input,m,start);
        }

        int key = input[start];
        while (start < end){
            //从后往前，找到比key小的值
            while (start < end && input[end] >= key){
                end--;
            }
            //如果采用直接交换不需要对input[start]进行中轴赋值,swap更耗时
//            swap(input,start,end);
            input[start] = input[end];
            //从前往后，找到比key大的值
            while (start < end && input[start] <= key){
                start++;
            }
//            swap(input,start,end);
            input[end] = input[start];
        }
        input[start] = key;
        return start;
    }

    private void swap(int[] input,int a ,int b){
        int temp = input[a];
        input[a] = input[b];
        input[b] = temp;
    }

}
