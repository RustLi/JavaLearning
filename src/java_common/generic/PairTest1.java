package java_common.generic;

import java.util.ArrayList;
import java.util.List;

public class PairTest1 {

    public static void main(String[] args) {
        String[] words = {"a","b","c","d"};
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println("min = " + mm.first);
        System.out.println("max = " + mm.second);

        String middle = ArrayAlg.getMiddle(words);
        System.out.println("middle = " + middle);


        List<String> ls = new ArrayList<String>();
        List<Integer> li = new ArrayList<Integer>();
        System.out.println(ls.getClass() == li.getClass());
    }
}
