package test;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringTest {

    public static void main(String[] args) {
//        String aaa = "https://work.weixin.qq.com/u/vc73291864b05a44bd?v=3.1.6.16398";
//        String[] bbb = aaa.split("/?");
//        String ccc = bbb[1];
//        System.out.println("ccc = " + ccc);
//
//        String ddd = aaa.substring(29,47);
//        System.out.println("ddd = " + ddd);
//
//        String eee = aaa.substring(1000);
//        System.out.println("eee = " + eee);

//        String ff = "abcd123";
//        System.out.println(ff.charAt(1));
//
//        String buff = ff.substring(0, ff.length() - 1);
//        System.out.println("buff = " + buff);
//
//        Set<Long> set1 = new HashSet<>();
//        set1.add(1L);
//        set1.add(2L);
//        set1.add(3L);
//
//        Set<Long> set2 = new HashSet<>();
//        set2.add(1L);
//
//        set1.retainAll(set2);
//        System.out.println("set1 = " + set1 + ", set2 = " + set2);
//
        List<String> nameList = Lists.newArrayList();
        nameList.add("");
        nameList.add("1111");
        String ret = StringUtils.join(nameList,"/");
        System.out.println("ret = " + ret);

        StringTest test = new StringTest();
        System.out.println(111);
        String aaa = null;
        try {
            for (int i = 0; i < 10; i++) {
                if (i == 5){
                    System.out.println(aaa.length());
                }
            }
        }catch (Exception e){
            test.handleTagException(e);
        }
        System.out.println(222);
    }




    private void handleTagException(Exception e) {
        throw new NullPointerException("error");
    }
}
