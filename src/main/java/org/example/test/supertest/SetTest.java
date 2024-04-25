package test.supertest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

public class SetTest {

    public static void main(String[] args) {
        testSet();

//        test1();

//        allocTest();

//        retainAllTest();
    }

    private static void listDiffTest(){
//        Collection<String> addWechatIds = CollectionUtils.subtract(newWechatIds, existWechatIds);
//        Collection<String> delWechatIds = CollectionUtils.subtract(existWechatIds, newWechatIds);
//        System.out.println("modSop addWechatIds " +  addWechatIds + ", System.out.println" + delWechatIds);
    }

    private static void retainAllTest(){
        List<String> aList = Lists.newArrayList(Arrays.asList("11","22"));
        List<String> bList = Lists.newArrayList(Arrays.asList("22","33"));
        boolean isRetain = aList.retainAll(bList);

        System.out.println("aList = " + aList + ", bList = " + bList + ", isRetain = " + isRetain);
    }

    //userId2LeadsIds = {501=[1], 502=[2]}
//userId2LeadsIds = {501=[1]}
    private static void allocTest(){
        List<Long> allLeadIds = Lists.newArrayList();
        allLeadIds.add(1L);
        allLeadIds.add(2L);
        allLeadIds.add(3L);
        allLeadIds.add(4L);

        List<Long> userIds = Lists.newArrayList();
        userIds.add(500L);
        userIds.add(501L);
        userIds.add(502L);
        userIds.add(503L);

        Long allocToUserId = null;
        Map<Long, List<Long>> userId2LeadsIds = Maps.newHashMap();

        int j = 0;
        for (Long lId : allLeadIds) {
            j++;
            if (j == userIds.size()) {
                j = 0;
            }
            allocToUserId = userIds.get(j);
            List<Long> userLeadsIds;
            if (userId2LeadsIds.containsKey(allocToUserId)) {
                userLeadsIds = userId2LeadsIds.get(allocToUserId);
            } else {
                userLeadsIds = Lists.newArrayList();
            }
            userLeadsIds.add(lId);
            userId2LeadsIds.put(allocToUserId, userLeadsIds);
        }

        System.out.println("userId2LeadsIds = " + userId2LeadsIds);
    }

    private static void testSet(){
        Set<Integer> set1 = new HashSet<>();
        set1.add(5);
        set1.add(2);
        set1.add(3);

        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(2);
        set2.add(3);
        set2.add(4);
//        set2.add(null);

        set1.retainAll(set2);

        Set<Integer> set3 = Sets.intersection(set1,set2);
//        Set<Integer> set4 = Sets.difference(set1,set2);
//        Set<Integer> set5 = Sets.difference(set2,set1);

        System.out.println("set1 = " + set1);
        System.out.println("set2 = " + set2);
        System.out.println("set3 = " + set3);
//        System.out.println("set4 = " + set4);
//        System.out.println("set5 = " + set5);

        System.out.println("-----");
        System.out.println(set1);
        System.out.println(set2);
    }

    private static void test1(){
        Set<Integer> existBjyRelayRoomIds = new HashSet<>();
        existBjyRelayRoomIds.add(1);
        existBjyRelayRoomIds.add(2);
        existBjyRelayRoomIds.add(3);

        Set<Integer> reqRelayRoomIds = new HashSet<>();
//        set2.add(2);
//        set2.add(3);
//        set2.add(4);
//        set2.add(null);

        Set<Integer> insertBjyRelayRoomIds = Sets.difference(reqRelayRoomIds,existBjyRelayRoomIds);
        Set<Integer> delBjyRelayRoomIds = Sets.difference(existBjyRelayRoomIds,reqRelayRoomIds);

        System.out.println("insertBjyRelayRoomIds = " + insertBjyRelayRoomIds + ", delBjyRelayRoomIds = " + delBjyRelayRoomIds);
    }
}

