package sorts;

public class BubbleSort {

    //增加一个flag值，最好的情况下，o(n)，最坏的情况下o(n*n)
    public void sort(int[] input){
        int length = input.length;
        if (length == 0){
            return;
        }
        boolean isFlag = true;
        for (int i = 0; i < length && isFlag; i++){
            isFlag = false;
            for (int j = length -1; j > i; j--){
                if (input[j] < input[j-1]){
                    swap(input,j,j-1);
                    isFlag = true;
                }
            }
        }
        for (int m = 0; m < input.length -1; m++){
            System.out.println(input[m]);
        }
    }

    private void swap(int[] input,int a ,int b){
        int temp = input[a];
        input[a] = input[b];
        input[b] = temp;
    }
}
