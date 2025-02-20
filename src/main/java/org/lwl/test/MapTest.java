package org.lwl.test;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MapTest {
    public static void main(String[] args) {

//        Map<Integer, School> aMap = new HashMap<>();
//        School s1 = new School();
//        User u1 = new User();
//        u1.setId(1);
//        s1.setUser(u1);
//
//        School s2 = new School();
//        User u2 = new User();
//        u2.setId(2);
//        s2.setUser(u2);
//
//        aMap.put(2,s2);
//        aMap.put(1,s1);
//        List<School> list = aMap.values().stream().collect(Collectors.toList());
//        list.sort((o1, o2) -> Integer.compare(o2.getUser().getId(), o1.getUser().getId()));
//        System.out.println("list = " + list);

        String a = null;
        Map<String, String> map = new HashMap<>();
        map.put(a,"a");
        map.put(null, null);
        String b = map.get(a);
        System.out.println("b = " + b);
    }



    /**
     * 修改map里面的对象属性，会改变map里面属性值
     **/
    private static void modMapField(){
        LambdaTest.Apple aApple = new LambdaTest.Apple(1,"aa",null,"");
        Map<String, LambdaTest.Apple> aMap = new HashMap<String, LambdaTest.Apple>();
        String key = "aa";
        aMap.put(key,aApple);

        if(aMap.containsKey(key)){
            System.out.println(111);
            LambdaTest.Apple bApple = aMap.get(key);
            bApple.setName("bb");

            System.out.println("aApple = " + aApple);
        }
    }

    /**
     * 测试computeIfAbsent
     **/
    private static void testComputeIfAbsent(){

        long cTime = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("耗时 = " + (System.currentTimeMillis() - cTime));
        Map<String, String> customerNumMap = new HashMap<String, String>();
//        customerNumMap.put("aa","");
//        customerNumMap.put("bb","1");
        customerNumMap.put("cc",null);

        System.out.println("values = " + customerNumMap.values());

        Set<String> mainNumSet = customerNumMap.values().stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        System.out.println("mainNumSet = " + mainNumSet);

        User user = new User();
        user.setName("aa");
        user.setRemark("r_aa");

        User user1 = new User();
        user1.setName("aa");
        user1.setRemark("r_bb");

        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user1);


        Map<String,List<User>> result = new HashMap<String,List<User>>();

//        for (User user2 : list) {
//            String key = user2.getName();
//            List<User> list1 = result.getOrDefault(key,Lists.newArrayList());
//            list1.add(user2);
//            result.put(key, list1);
//        }

        for (User user2 : list) {
            result.computeIfAbsent(user2.getName(), k -> new ArrayList<>()).add(user2);
        }

        System.out.println("result = " + result);


//        Map<String, List<FollowResp>> result = new HashMap<String, List<FollowResp>>();

//        for (SearchHit searchHit : searchHits) {
//            try {
//                String doc = searchHit.getSourceAsString();
//                FollowResp msg = JacksonUtil.str2Obj(doc, FollowResp.class);
//                String key = customerNumRelationMap.get(msg.getCustomerNum());
//
//                List<FollowResp> list1 = result.get(key);
//                if (list1 == null) {
//                    list1 = new ArrayList<>();
//                    result.put(key,list1);
//                }
//                list1.add(msg);
//
//                result.computeIfAbsent(key, k -> new ArrayList<>()).add(msg);
//            } catch (IOException e) {
//                log.error("listBatch: add error : ",e);
//            }
//        }

    }

    public static class School{
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "School{" +
                    "user=" + user +
                    '}';
        }
    }

    private static class User{
        private int id;
        private String name;
        private String remark;
        private Integer age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", remark='" + remark + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    private static void testValueNull(){
        System.out.println(1111);
        User user = new User();
        user.setName("111");
        user.setRemark("mmm");

        User user1 = new User();
        user1.setName("222");
        user1.setRemark("");

        List<User> userList = Lists.newArrayList();
        userList.add(user);
        userList.add(user1);
        Map<String, String> tel2RemarkMap = userList.stream().collect(Collectors.toMap(User::getName, User::getRemark));
        System.out.println("tel2RemarkMap = " + tel2RemarkMap);

    }

    private static void test(){
        List<FollowRecordMsg> bList = Lists.newArrayList();
        FollowRecordMsg msg = new FollowRecordMsg();
        msg.setPhone("131");
        msg.setDate("a");
        msg.setUserId("1");
        bList.add(msg);

        FollowRecordMsg msg1 = new FollowRecordMsg();
        msg1.setPhone("131");
        msg1.setDate("b");
        msg1.setUserId("1");
        bList.add(msg1);



        FollowRecordMsg msg2 = new FollowRecordMsg();
        msg2.setPhone("131");
        msg2.setDate("a");
        msg2.setUserId("2");
        bList.add(msg2);


//        Set<String> keySet = Sets.newHashSet();
//        for (FollowRecordMsg recordMsg : bList) {
//            String key = getKey(recordMsg.getPhone(), recordMsg.getUserId(), recordMsg.getDate());
//            if (keySet.contains(key)){
//                continue;
//            }
//            keySet.add(key);
//        }
//        System.out.println("size = " + keySet.size());


        int recordCount1 = bList.stream().map(FollowRecordMsg::getDate).collect(Collectors.toSet()).size();
        System.out.println("recordCount1 = " + recordCount1);

        int recordCount = bList.stream().map(it -> getKey(it.getPhone(), it.getUserId(), it.getDate())).collect(Collectors.toSet()).size();
        System.out.println("recordCount = " + recordCount);
    }

    private static String getKey(String phone, String userId, String date){
//        StringBuilder sb = new StringBuilder();
//        return sb.append(phone).append(userId).append(date).toString();

        return phone + "_" + userId + "_" + date;
    }

    private static class FollowRecordMsg{
        private String phone;
        private String date;
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "FollowRecordMsg{" +
                    "phone='" + phone + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }
}
