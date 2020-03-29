package algorithms.sorts;

/**
 * @description: 折半查找必须保证数组本身是有序的
 * @author: RustLi
 * @date: 2019-03-12
 **/
public class BinarySearch {
    public static void main(String[] args) {
        int[] intput = {1, 2, 3, 5, 6, 7, 7};
        int key = 7;
        BinarySearch binarySearch = new BinarySearch();
        int ret = binarySearch.binarySearch(intput, key);
        System.out.println(ret);
    }

    private int binarySearch(int[] a, int key) {
        int low, mid, high;
        low = 0;//最小下标
        high = a.length - 1;//最大小标
        while (low <= high) {
            mid = low + (high - low) / 2;//折半下标
            if (key < a[mid]) {
                high = mid - 1;//关键字比折半值小，则最大下标调成折半下标的前一位
            } else if (key > a[mid]) {
                low = mid + 1; //关键字比折半值大，则最小下标调成折半下标的下一位
            } else {
                return mid; //关键字和折半值相等时返回折半下标
            }
        }
        return -1;
    }


    /**
     * des: 递归实现
     * @param
     */
    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1);
    }

    private static int rank(int key, int[] a, int lo, int hi) { //如果key存在于a[]中，它的索引不会小于lo且不会大于hi
        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) return rank(key, a, lo, mid - 1);
        else if (key > a[mid]) return rank(key, a, mid + 1, hi);
        else return mid;
    }

}
