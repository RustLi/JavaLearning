package test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.javafx.css.Size;
import org.apache.commons.lang3.StringUtils;
import test.beanutil.Person;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaTest {
    private static final String SOP_NO_TEMPLATE = "<a href=\\\"{0}/sopRemind?mainNum={1}&user_id={2}\\\">>>客户名单</a>";


    public static void main(String[] args) {
//        LambdaTest lambdaTest= new LambdaTest();
//
//        lambdaTest.lambdaTest();

//        lambdaTest.sqList();

//        lambdaTest.flatMap();

//        lambdaTest.test();

//        test111(5);

//        lambdaTest.groupByTest();

//        sortTest();

//        optionalMap();

//        lambdaTest();

//        boolean isEqual = "".equals(null);
//        System.out.println(isEqual);

//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
//        int a = 6;
//        int b = 10;
////        double percentage = (double) a / b * 100;
//        String cc = decimalFormat.format((double) a / b * 100) + "%";
//        System.out.println(cc);

//        rankTest();

//        multiGroupBy();

//        groupByTest();


//        System.out.println(getMoney(300));
//        System.out.println(getMoney(0));
//        System.out.println(getMoney(5));
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");


    private static String getCompletePercent(Integer duration, Long meetingLength) {
        String format = "0.0000";
        if (Objects.isNull(duration) || Objects.isNull(meetingLength)) {
            return format;
        }
        Double percent = (double) duration / meetingLength;
        DecimalFormat df = new DecimalFormat(format);
        return df.format(percent);
    }

    private static String getMoney(Integer money){
        String prefix = "¥ ";
        if (money == null){
            return prefix;
        }
        double result = (double)money / 100;
        DecimalFormat df = new DecimalFormat("0.00");
        return prefix + df.format(result);
    }

    public static String getRate(Long totalCount, Long numCount) {
        if (totalCount != null && numCount != null && totalCount > 0 && numCount <= totalCount) {
            return (decimalFormat.format((double) numCount / totalCount * 100) + "%");
        }
        return "0.00%";
    }

    private static void multiGroupBy(){
        System.out.println("111");
        List<Apple> appleList = new ArrayList<>();//存放apple对象集合
        Apple apple7 =  new Apple(8,"A",Collections.singletonList("3"),"1");
        Apple apple8 = new Apple(8,null,Collections.singletonList("A"),"2");
        Apple apple9 = new Apple(7,"C",Collections.singletonList("A"),"3");

        appleList.add(apple7);
        appleList.add(apple8);
        appleList.add(apple9);

        for (Apple apple : appleList) {
            if (apple.getName() == null){
                apple.setName("");
            }
        }

        // 使用Lambda表达式进行分组
        Map<Integer, Map<String, List<Apple>>> result = appleList.stream()
                .collect(Collectors.groupingBy(Apple::getId, Collectors.groupingBy(Apple::getName)));

        System.out.println("result = " + result);

        // 打印结果
        for (Map.Entry<Integer, Map<String, List<Apple>>> entry1 : result.entrySet()) {
            System.out.println("Group: " + entry1.getKey());
            for (Map.Entry<String, List<Apple>> entry2 : entry1.getValue().entrySet()) {
                System.out.println("Subgroup: " + entry2.getKey());
                for (Apple obj : entry2.getValue()) {
                    System.out.println("obj = " + obj);
                }
            }
        }
    }


    private static void rankTest() {
        List<User> users = new ArrayList<>();
        users.add(new User("B", "90"));
        users.add(new User("C", "90"));
        users.add(new User("E", "80"));
        users.add(new User("F", null));


        List<String> scoreList = new ArrayList<>();
        scoreList.add("90.5");
        scoreList.add("");
        scoreList.add("85.0");
        scoreList.add("85.0");
        scoreList.add("81.00");
        scoreList.add(null);

//        System.out.println(getRankByScore(scoreList));


        System.out.println("111 = " + getRankMapForUser(users));

//        List<Integer> scores = Arrays.asList(100,100,90);
//
//        Map<Integer, Integer> rankingMap = getRankMap(scores);
//        System.out.println(rankingMap);

//        for (User user : users) {
//            String id = user.getId();
//            int rank = rankingMap.get(id);
//            System.out.println("ID: " + id + ", Rank: " + rank);
//        }

//        List<Integer> scores = Arrays.asList(100, 100, 90);
//        Map<Integer, Integer> scoreRankMap = calculateRankings(scores);
//        for (Map.Entry<Integer, Integer> entry : scoreRankMap.entrySet()) {
//            int score = entry.getKey();
//            int rank = entry.getValue();
//            System.out.println("分数: " + score + ", 排名: " + rank);
//        }

//        List<String> scores = Arrays.asList("100", "100", "90");
//        Map<String, Integer> scoreRankMap = calculateRankingsStr(scores);
//        for (Map.Entry<String, Integer> entry : scoreRankMap.entrySet()) {
//            String score = entry.getKey();
//            int rank = entry.getValue();
//            System.out.println("分数: " + score + ", 排名: " + rank);
//        }
    }

    private static Map<String,Integer> getRankByScore(List<String> scoreList) {
//        if (CollectionUtils.isEmpty(scoreList)){
//            return Collections.emptyMap();
//        }

        List<String> sortList = scoreList.stream()
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        Map<String, Integer> rankMap = Maps.newHashMap();
        int rank = 1;
        for (String score : sortList) {
            if (!rankMap.containsKey(score)) {
                rankMap.put(score,rank);
            }
            rank++;
        }
        return rankMap;
    }


    /**
     * 计算分数，并列不算名次，[100,100,90] -->> [1,1,2]
     **/
    public static Map<String, Integer> getRankMapForUser(List<User> list) {

//        list.forEach(user -> {
//            if (user.getScore() == null) {
//                user.setScore("0.00");
//            }
//        });


        List<User> sortList = list.stream()
                .sorted(Comparator.comparing(User::getScore).reversed())
                .collect(Collectors.toList());

        Map<String, Integer> rankMap = new HashMap<>();

        System.out.println("list = " + list);
        //人，分数
        Map<String, Integer> userMap = new HashMap<>();

        //分数，排名
        int rank = 1;
        for (User result : sortList) {
            String score = result.getScore();
            if (!rankMap.containsKey(score)) {
                rankMap.put(score, rank);
            }
            rank++;
        }

        for (User result : sortList) {
            String score = result.getScore();
            if (rankMap.containsKey(score)) {
                String stuId = result.getId();
                userMap.put(stuId, rankMap.get(score));
            }
        }
        return userMap;
    }

    public static Map<Integer, Integer> calculateRankings(List<Integer> scores) {
        Map<Integer, Integer> scoreRankMap = new HashMap<>();
        List<Integer> sortedScores = new ArrayList<>(scores);
        Collections.sort(sortedScores, Collections.reverseOrder());

        int rank = 1;
        for (Integer score : sortedScores) {
            if (!scoreRankMap.containsKey(score)) {
                scoreRankMap.put(score, rank);
                rank++;
            }
        }

        return scoreRankMap;
    }

    public static Map<String, Integer> calculateRankingsStr(List<String> scores) {
        Map<String, Integer> scoreRankMap = new HashMap<>();
        List<String> sortedScores = new ArrayList<>(scores);
        Collections.sort(sortedScores, Collections.reverseOrder());

        int rank = 1;
        for (String score : sortedScores) {
            if (!scoreRankMap.containsKey(score)) {
                scoreRankMap.put(score, rank);
                rank++;
            }
        }

        return scoreRankMap;
    }

    public static Map<Integer, Integer>getRankMap(List<Integer> scores) {
        Map<Integer, Integer> scoreMap = new HashMap<>();
        List<Integer> sortedScores = new ArrayList<>(scores);
        Collections.sort(sortedScores, Collections.reverseOrder());

        int rank = 1;
        for (Integer score : sortedScores) {
            if (!scoreMap.containsKey(score)) {
                scoreMap.put(score, rank);
                rank++;
            }
        }

        return scoreMap;
    }

    public static Map<String, Integer> getRankingMap(List<User> users) {
        List<User> sortedUsers = users.stream()
                .sorted(Comparator.comparing(User::getScore).reversed())
                .collect(Collectors.toList());

        Map<String, Integer> rankingMap = new HashMap<>();
        int rank = 1;
        for (int i = 0; i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            String id = user.getId();
            if (!rankingMap.containsKey(id)) {
                rankingMap.put(id, rank);
            }
            rank++;
        }

        return rankingMap;
    }

    public static class User {
        private String id;
        private String score;

        public User(String id, String score) {
            this.id = id;
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public String getScore() {
            return score;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }


    public static void sortTest(){
        List<Apple> appleList = new ArrayList<>();//存放apple对象集合
        Apple apple7 =  new Apple(8,"A",Collections.singletonList("3"),"1");
        Apple apple8 = new Apple(9,"A",Collections.singletonList("A"),"2");
        Apple apple9 = new Apple(7,"A",Collections.singletonList("A"),"3");


        appleList.add(apple7);
        appleList.add(apple8);
        appleList.add(apple9);

        appleList.sort((r1, r2) -> r2.getId().compareTo(r1.getId()));
        System.out.println("appleList1 = " + appleList);

        appleList.sort((r1, r2) -> r1.getId().compareTo(r2.getId()));
        System.out.println("appleList2 = " + appleList);

    }


    private static void test111(int i){
        System.out.println(111);
        for (int j = 0; j < 10; j++) {
            if (j == i){
                break;
            }
        }
        System.out.println(222);
    }

    public static Date getDateAddMin(Date date, int minutes) {
        Calendar no = Calendar.getInstance();
        no.setTime(date);
        no.add(Calendar.MINUTE, minutes);
        return no.getTime();
    }

    private void test(){
        List<String> aList = Lists.newArrayList();
        aList.add("1");
        aList.add("2");
        aList.add("3");

        Map<Integer,String> aMap = Maps.newHashMap();
//        for (int i = 0, size = aList.size(); i < size; i++) {
//            aMap.put(i, aList.get(i));
//        }

//        for (Map.Entry<Integer, String> entry: aMap.entrySet()) {
//            System.out.println(entry.getKey() + "_" + entry.getValue());
//        }

        IntStream.range(0, aList.size()).forEach(i -> {
            aMap.put(i, aList.get(i));
        });

//        Stream.iterate(0, i -> i + 1).limit(aList.size()).forEach(i -> {
//            aMap.put(i, aList.get(i));
//        });

        for (Map.Entry<Integer, String> entry: aMap.entrySet()) {
            System.out.println(entry.getKey() + "_" + entry.getValue());
        }
    }

    /**
     * flatMap 元素集合
     **/
    private void flatMap(){
        Stream<List<Integer>> s = Stream.of(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9));

        Stream<Integer> i = s.flatMap(list -> list.stream());
        System.out.println(i.collect(Collectors.toList()));
    }

    /**
     * reduce聚合操作
     **/
    private void reduce(){
        // 按行读取配置文件:
        List<String> props = Arrays.asList("profile=native", "debug=true", "logging=warn", "interval=500");
        Map<String, String> map = props.stream()
                // 把k=v转换为Map[k]=v:
                .map(kv -> {
                    String[] ss = kv.split("\\=", 2);
                    Map<String,String> newMap = new HashMap();
                    newMap.put(ss[0],ss[1]);
                    return newMap;
                })
                // 把所有Map聚合到一个Map:
                .reduce(new HashMap<String, String>(), (m, kv) -> {
                    m.putAll(kv);
                    return m;
                });
        // 打印结果:
        map.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
    }

    /**
     * 对list中的每个数求平方
     **/
    private void sqList(){
        List<Integer> sqList = new ArrayList();
        sqList.add(1);
        sqList.add(2);
        sqList.add(3);

        Stream<Integer> s1 = sqList.stream();
        Stream<Integer> s2 = s1.map(n -> n*n);

        s2.forEach(System.out::println);
    }

    private void commonTest(){
        MathOperation add = (int a,int b) -> a+b;
        MathOperation sub = (a,b) -> a-b;
        MathOperation multiplication = (int a,int b) -> {return a*b;};
        MathOperation division = (int a,int b) -> a/b;
        System.out.println(operate(10,5,add));
        System.out.println(operate(10,5,sub));
        System.out.println(operate(10,5,multiplication));
        System.out.println(operate(10,5,division));

        GreetingService greetingService = message ->
                System.out.println("hello" + message);
        greetingService.sayMessage("world llalal");

        repeat(10, i -> System.out.println("countdown :: " + (9 - i)));
    }

    private static void groupByTest(){
        List<Apple> appleList = new ArrayList<>();//存放apple对象集合
        Apple apple7 =  new Apple(1,"A",Collections.singletonList("3"),"1");
        Apple apple8 = new Apple(2,null,Collections.singletonList("A"),"1");
        Apple apple9 = new Apple(3,"A",Collections.singletonList("B"),"2");
        Apple apple10 = new Apple(4,"B",Collections.singletonList("A"),"3");
        Apple apple11 = new Apple(5,"B",Collections.singletonList("A"),"4");

//        Apple apple9 =  new Apple(9,"香蕉",Collections.singletonList("5"));
        appleList.add(apple7);
        appleList.add(apple8);
        appleList.add(apple9);
        appleList.add(apple10);
        appleList.add(apple11);


        Map<String,List<Apple>> existModelMap = appleList.stream().collect(Collectors.groupingBy(Apple::getName));
        System.out.println("existModelMap = " + existModelMap);

//        Map<String,List<Apple>> mMap = appleList.stream().collect(Collectors.groupingBy(Apple::getbName));
//        mMap.forEach((k, v) -> System.out.println(k + "------" + v));

        Map<String,List<Apple>> nMap = appleList.stream().collect(Collectors.groupingBy(it -> buildKey(it.name,it.bName)));
//        System.out.println("nMap = " + nMap);
//        nMap.forEach((k, v) -> System.out.println("nMap = " + k + "------" + v));

        Map<String, Set<String>> leadsTagMap = appleList.stream().collect(Collectors.groupingBy(Apple::getName, Collectors.mapping(Apple::getbName, Collectors.toSet())));
        System.out.println("leadsTagMap = " + leadsTagMap);

//        List<Apple> targetList = mMap.getOrDefault("aaaa",new ArrayList<>());
//        Set<Integer> dateSet = targetList.stream().map(Apple::getId).collect(Collectors.toSet());
//        System.out.println(dateSet.size());

//        mMap.forEach((k, v) -> System.out.println(k + "------" + v));

        List<String> aaaList = new ArrayList<>();
        aaaList.add("222");
        aaaList.add("333");
        aaaList.add("444");
        Map<String, Long> map = aaaList.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()));
        map.forEach((k, v) -> System.out.println(k + ":" + v));

        Map<String,String> aMap = new HashMap<>();
        List<String> aList = new ArrayList<>();
        String A = "A";
        String a1 = "a1";
        String a2 = "a2";
        aMap.put(A,A);
        aMap.put(a1,A);
        aMap.put(a2,A);
        aList.add(A);
        aList.add(a1);
        aList.add(a2);

        String B = "B";
        String b1 = "b1";
        aMap.put(B,B);
        aMap.put(b1,B);
        aList.add(B);
        aList.add(b1);

        String C = "C";
        String c1 = "c1";
        aMap.put(C,C);
        aMap.put(c1,C);
        aList.add(C);
        aList.add(c1);
    }

    /**
     * 将一个 List 集合中的元素按照 leadsLibId 属性值进行分组，并对每个分组内的元素取 id 属性值最大的 RawLeadsLib 对象。
     * 最终返回一个以 leadsLibId 为键、RawLeadsLib 对象为值的 Map 集合。
     **/
    private static void optionalMap(){

        List<Apple> appleList = new ArrayList<>();//存放apple对象集合
        Apple apple7 =  new Apple(1,"A",Collections.singletonList("3"),"1");
        Apple apple8 = new Apple(2,"A",Collections.singletonList("A"),"1");
        Apple apple9 = new Apple(3,"A",Collections.singletonList("B"),"2");
        Apple apple10 = new Apple(4,"B",Collections.singletonList("A"),"3");
        Apple apple11 = new Apple(5,"B",Collections.singletonList("A"),"4");

//        Apple apple9 =  new Apple(9,"香蕉",Collections.singletonList("5"));
        appleList.add(apple7);
        appleList.add(apple8);
        appleList.add(apple9);
        appleList.add(apple10);
        appleList.add(apple11);

        Map<String,Optional<Apple>> optionalMap = appleList.stream().collect(Collectors.groupingBy(Apple::getName, Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Apple::getId)))));
        System.out.println("optionalMap = " + optionalMap);
    }

    private static String buildKey(String value1,String value2){
        return value1 + "_" + value2;
    }

    private void lambdaTest(){

        List<Apple> appleList = new ArrayList<>();//存放apple对象集合

        Apple apple1 =  new Apple(3,"111",new BigDecimal("3.25"),10,false);
        Apple apple12 = new Apple(1,"111",new BigDecimal("1.35"),20,false);
        Apple apple2 =  new Apple(9,"222",new BigDecimal("2.89"),20,true);

//        Apple apple3 =  new Apple(3,"444",new BigDecimal("9.99"),40,false);
//        Apple apple5 =  new Apple(4,"香蕉",new BigDecimal("2.89"),30);
//        Apple apple6 =  new Apple(4,"",new BigDecimal("2.89"),30,true);

        appleList.add(apple1);
        appleList.add(apple12);
        appleList.add(apple2);
//        appleList.add(apple3);
//        appleList.add(apple5);
//        appleList.add(apple6);


        Collections.sort(appleList, Comparator.comparing(Apple::getId));
        System.out.println("排序后的: " + appleList);


        Map<Integer, String> tel2RemarkMap = appleList.stream().collect(Collectors.toMap(Apple::getId, Apple::getName));

        System.out.println("tel2RemarkMap = " + tel2RemarkMap);

        Set<String> nameSet = appleList.stream()
                .map(Apple::getName)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
//        System.out.println("nameSet = " + nameSet);

        Map<String,Integer> tMap = new HashMap<>();
        tMap.put("111",10);
        tMap.put("222",20);
        long sum = tMap.values().stream().mapToLong(Integer::longValue).sum();
        System.out.println("sum = " + sum);

        Collection<Integer> tInts = Optional.ofNullable(tMap).map(Map::values).get();
        System.out.println("tInts = " + tInts + ", tMap.values() = " + tMap.values());

//        Map<Integer, List<Apple>> aMap = appleList.stream().collect(Collectors.groupingBy(a -> buildKey(a.getId(),a.getNum()));

        Map<Integer, String> quitRoomMap = appleList.stream()
                .collect(Collectors.toMap(Apple::getId, a->a.getName() == null ? "-1" : a.getName(),(u,v)->v));
        System.out.println("quitRoomMap = " + quitRoomMap);

        Map<String, Apple> contactId2Entity = appleList.stream().collect(Collectors.toMap(a -> a.getId() + "$$" + a.getName(), Function.identity()));

        List<Orange> orangeList = appleList.stream().filter(Objects::nonNull).map(it -> {
            Orange orange = new Orange();
            orange.id = it.id;
            orange.name = it.name;
            return orange;
        }).collect(Collectors.toList());

        orangeList.forEach( e->{
            System.out.println("orange = " + e);
        });

        List<Apple> aList = appleList.stream().filter(a -> a.getName() != null && !a.getName().equals("香蕉")).collect(Collectors.toList());

        System.out.println("adsjaslkdja: aList = " + aList);

        System.out.println("lwl11112:  size = " + aList.size());

        List<Apple> bList = appleList.stream().filter(a -> !a.isDraft).collect(Collectors.toList());

        System.out.println("lwl:  bList.size = " + bList.size());

//        Map<Integer,String> result = appleList.stream().filter(p -> p.getName() != null).collect(Collectors.toMap(Apple::getId, Apple::getName));
//        System.out.println("lwl::: " + result);

        List<Apple> joinDetailList = appleList.stream().filter(a -> Objects.nonNull(a.getId()) && a.getId() == 1)
                .collect(Collectors.toList());
//        System.out.println("lwl .... joinDetailList = " + joinDetailList);

        Map<Integer, Apple> dMap = appleList.stream().filter(a -> Objects.nonNull(a.getId()) && a.getId() == 10)
                .collect(Collectors.toMap(Apple::getId, a->a,(k, v)->v));
        System.out.println("lwl000 = " + dMap + ", size = " + dMap.size());
        for (Map.Entry entry: dMap.entrySet()) {
//            System.out.println("111 key = " + entry.getKey() + ", value = " + entry.getValue());
        }


        List<Apple> newList1 = new ArrayList<>();
        Set<String> ids = appleList.stream().map(Apple::getName).filter(a -> a != null && a.contains("aa")).
                collect(Collectors.toSet());

        System.out.println("ids: " + ids);

        Set<String> keySet = new HashSet<>();
        List<Apple> newList = new ArrayList<>();
        for (Apple apple : appleList) {
            String key = buildKey(apple.getId(),apple.getName());
            if (keySet.contains(key)){
                //比较num，如何更新newList
            }
            keySet.add(key);
            newList.add(apple);
        }

//        System.out.println(apple1.getName().length());

        List<String> stringList = appleList.stream().map(item -> buildKey(item.getId(), item.getName()))
                .collect(Collectors.toList());

        System.out.println("555 = " + stringList);

        long count = stringList.stream().distinct().count();
//        System.out.println(count);

        List<Integer> meetingIds = new ArrayList<>();
        for (Apple apple : appleList) {
            Integer appleId = apple.getId();
            meetingIds.add(appleId);
        }
        meetingIds.sort(Integer::compareTo);

//        System.out.println("lwl ... " + meetingIds);

        Map<Integer, List<Apple>> stageReasonMap = appleList.stream().collect(Collectors.groupingBy(Apple::getId));

        Map<Integer, List<Integer>> bbbMap = appleList.stream().collect(
                Collectors.groupingBy(Apple::getId, Collectors.mapping(Apple::getNum, Collectors.toList())));

        //将list 排序，并按照排序后的结果进行有序分组
        LinkedHashMap<String, List<Apple>> appleMap = appleList.stream().sorted(Comparator.comparingInt(Apple::getId))
                .collect(Collectors.groupingBy(Apple::getName, LinkedHashMap::new, Collectors.toList()));
        for (Map.Entry<String,List<Apple>> entry : appleMap.entrySet()) {
            String key = entry.getKey();
            List<Apple> list = entry.getValue();
            System.out.println("排序后的数组：key = " + key + ", list = " + list);
        }

        System.out.println("bbbMap = " + bbbMap);

        Map<Integer, Set<Integer>> cccMap = appleList.stream().collect(
                Collectors.groupingBy(Apple::getId, Collectors.mapping(Apple::getNum, Collectors.toSet())));

        System.out.println("cccMap = " + cccMap);

//        List<Apple> cccList = appleList.stream().filter(distinctByKey(Apple::getName))
//                .collect(Collectors.toList());

//        Map<Integer, String> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, Apple::getName, (u,v)->v));

//        Map<Integer, Apple> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, Function.identity()));

//        Map<Integer, Apple> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, a -> a, (k1,k2) -> k2));

//        long count1 = appleList.stream().map(Apple::getName).collect(Collectors.toSet()).size();
//        System.out.println("count1 "+ count1);

//        for (Map.Entry<Integer,Apple> entry: appleMap.entrySet()) {
//            System.out.println("lwl-" + entry.getKey() + "_" + entry.getValue());
//        }

//        Long count = appleList.stream().filter(distinctByKey(InvoiceGroupRelation::getCorpId)).distinct().count();
//        if(count < invoiceGroupRelations.size()){
//
//        }

        List<String> dupSet = new ArrayList<>();
        appleList.stream().map(reasonDto -> buildKey(reasonDto.getId(),reasonDto.getName())).forEach(dupSet::add);
        for (String id: dupSet) {
//            System.out.println("lwl_" + id);
        }

        List<String> list1 = appleList.stream().map(Apple::getName).collect(Collectors.toList());
//        System.out.println(list1.size());

        Set<String> list2 = appleList.stream().map(a->buildKey(a.getId(),a.getName())).collect(Collectors.toSet());
//        System.out.println(list2.size());

//        long count = appleList.stream().filter(a -> a.getName().equals("香蕉")).count();

//        System.out.println(count);
//        List<Apple> filterList = appleList.stream().filter(a -> a.getName() != null && a.getName().length() > 5).collect(Collectors.toList());

//        List<Apple> filterList = appleList.stream().filter(a -> a.getId() == 1).collect(Collectors.toList());

//        for (Apple apple: filterList) {
//            System.out.println(apple);
//        }

        List<Integer> inputList = Arrays.asList(10,1,8,6,3);
        List<Integer> list = Arrays.asList(5, 8, 4, 9, 3);
        List<Integer> sortedlist = list.stream().sorted().collect(Collectors.toList());

        System.out.println(list);
        System.out.println(sortedlist);

        int[] inputArr = new int[]{10,1,8,6,3};
        int[] retArr = new int[inputArr.length];

        List<Integer> inputList1 = Arrays.asList(10,1,8,6,3);

        List<Integer> retList = new ArrayList<>();
//        Arrays.sort(inputArr);

//        for (int i = 0; i < inputArr.length; i++) {
//            System.out.println(inputArr[i]);
//        }


        List<Integer> orderList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            int num = list.get(i);

            for (int j = 0; j < sortedlist.size(); j++) {
                if (num == sortedlist.get(j)){
                    orderList.add(j);
                    retArr[j] = inputArr[i];
//                    retList.add(j,inputList1.get(i));

                }
            }
        }

//        System.out.println(retList);

//        System.out.println(orderList);

//        for (int i = 0; i < retArr.length; i++) {
//            System.out.println(retArr[i]);
//        }


        Integer[] inputArr1= {10,1,8,6,3};
        Integer[] retArr2 = new Integer[inputArr1.length];
        Integer[] inputArr2= {5, 8, 4, 9, 3};

        Integer[] collect = Arrays.stream(inputArr2).sorted(Comparator.naturalOrder()).collect(Collectors.toList()).toArray(new Integer[inputArr2.length]);

//        for (int i = 0; i < inputArr2.length; i++) {
//            System.out.println(inputArr2[i]);
//        }
//
//        for (int i = 0; i < collect.length; i++) {
//            System.out.println(collect[i]);
//        }

        for (int i = 0; i < inputArr2.length; i++) {
            int num = inputArr2[i];

            for (int j = 0; j < collect.length; j++) {
                if (num == sortedlist.get(j)){
                    retArr2[j] = inputArr[i];
                }
            }
        }

//        for (int i = 0; i < retArr2.length; i++) {
//            System.out.println(retArr2[i]);
//        }

    }

    public static class Apple implements Serializable{
        private Integer id;
        private String name;
        private BigDecimal money;
        private Integer num;
        private boolean isDraft;
        private List<String> mList;
        private String bName;



        public Apple(Integer id, String name, List<String> mList, String bName) {
            this.id = id;
            this.name = name;
            this.mList = mList;
            this.bName = bName;
        }

        public Apple(Integer id, String name, BigDecimal money, Integer num, boolean isDraft) {
            this.id = id;
            this.name = name;
            this.money = money;
            this.num = num;
            this.isDraft = isDraft;
        }

        public Apple(Integer id, String name,List<String> mList) {
            this.id = id;
            this.name = name;
            this.money = this.money;
            this.num = this.num;
            this.isDraft = isDraft;
            this.mList = mList;
        }

        public Apple(Integer id, String name, BigDecimal money, Integer num) {
            this.id = id;
            this.name = name;
            this.money = money;
            this.num = num;
        }

        public String getbName() {
            return bName;
        }

        public void setbName(String bName) {
            this.bName = bName;
        }

        public boolean isDraft() {
            return isDraft;
        }

        public void setDraft(boolean draft) {
            isDraft = draft;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", money=" + money +
                    ", num=" + num +
                    '}';
        }
    }


    private static void repeat(int n,IntConsumer intConsumer){
        for (int i = 0; i < n ; i++) {
            intConsumer.accept(i);
        }
    }

    interface IntConsumer{
        void accept(int value);
    }

    interface MathOperation{
        int operation(int a,int b);
    }

    interface GreetingService{
        void sayMessage(String message);
    }

    private int operate(int a,int b,MathOperation mathOperation){
        return mathOperation.operation(a,b);
    }

    private String buildKey(Integer stageId, String reason){
        return stageId + "_" + reason;
    }

    public static class Orange {
        public Integer id;
        public String name;

        @Override
        public String toString() {
            return "Orange{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * 自定义重复key 规则
     * @param keyExtractor
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private static void groupbyTest111(){
        List<Apple> appleList = Lists.newArrayList();
        List<Apple> toNoticeList = Lists.newArrayList();
        Map<Integer,List<Apple>> map1 = appleList.stream().collect(Collectors.groupingBy(Apple::getId));
        System.out.println("map1 = " + map1);
        for (Map.Entry<Integer,List<Apple>> entry: map1.entrySet()) {
            Integer key = entry.getKey();
            System.out.println("key = " + key);
            List<Apple> remindList = entry.getValue();
            System.out.println("remindList = " + remindList);
            Map<String,List<Apple>> userIdRemindMap = remindList.stream().collect(Collectors.groupingBy(Apple::getName));
            System.out.println("userIdRemindMap = " + userIdRemindMap);
            for (Map.Entry<String,List<Apple>> userEntry: userIdRemindMap.entrySet()){
                String userId = userEntry.getKey();
                System.out.println("userId = " + userId);
                List<Apple> useRemindList = userEntry.getValue();

                System.out.println("useRemindList = " + useRemindList);
                Apple apple111 = useRemindList.get(0);

                System.out.println("apple111 = " + apple111);
                toNoticeList.add(apple111);
            }
        }
        System.out.println("toNoticeList = " + toNoticeList);
    }
}
