public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        int[] intput = {9,2,3,5,1,6,7};
        //for bubbleSort begin
        /*BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort(intput);*/
        //for bubbleSort end

        //for quickSort
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(intput);

    }
}
