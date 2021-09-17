package test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaTest {
    public static void main(String[] args) {
        LambdaTest lambdaTest= new LambdaTest();
//        lambdaTest.lambdaTest();

//        lambdaTest.sqList();

//        lambdaTest.flatMap();

        lambdaTest.test();
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


    private void lambdaTest(){

        List<Apple> appleList = new ArrayList<>();//存放apple对象集合

        Apple apple1 =  new Apple(1,null,new BigDecimal("3.25"),10);
        Apple apple12 = new Apple(2,"aaa",new BigDecimal("1.35"),20);
        Apple apple2 =  new Apple(2,"香蕉",new BigDecimal("2.89"),30);

        Apple apple3 =  new Apple(3,"荔枝",new BigDecimal("9.99"),40);
//        Apple apple5 =  new Apple(4,"香蕉",new BigDecimal("2.89"),30);
        Apple apple6 =  new Apple(4,"香蕉",new BigDecimal("2.89"),30);

        appleList.add(apple1);
        appleList.add(apple12);
        appleList.add(apple2);
        appleList.add(apple3);
//        appleList.add(apple5);
        appleList.add(apple6);

        Map<Integer,String> result = appleList.stream().filter(p -> p.getName() != null).collect(Collectors.toMap(Apple::getId, Apple::getName));
        System.out.println("lwl::: " + result);

        List<Apple> joinDetailList = appleList.stream().filter(a -> Objects.nonNull(a.getId()) && a.getId() == 1).collect(Collectors.toList());
//        System.out.println("lwl .... joinDetailList = " + joinDetailList);

        Map<Integer, Apple> dMap = appleList.stream().filter(a -> Objects.nonNull(a.getId()) && a.getId() == 1)
                .collect(Collectors.toMap(Apple::getId, a->a,(k, v)->v));
        for (Map.Entry entry: dMap.entrySet()) {
//            System.out.println("111 key = " + entry.getKey() + ", value = " + entry.getValue());
        }


        List<Apple> newList1 = new ArrayList<>();
        Set<String> ids = appleList.stream().map(Apple::getName).filter(Objects::nonNull).
                collect(Collectors.toSet());

//        System.out.println("ids: " + ids);

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


        long count = stringList.stream().distinct().count();
//        System.out.println(count);

        List<Integer> meetingIds = new ArrayList<>();
        for (Apple apple : appleList) {
            Integer appleId = apple.getId();
            meetingIds.add(appleId);
        }
        meetingIds.sort(Integer::compareTo);

        System.out.println("lwl ... " + meetingIds);

        Map<Integer, List<Apple>> stageReasonMap = appleList.stream().collect(Collectors.groupingBy(Apple::getId));

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

    public class Apple {
        private Integer id;
        private String name;
        private BigDecimal money;
        private Integer num;
        public Apple(Integer id, String name, BigDecimal money, Integer num) {
            this.id = id;
            this.name = name;
            this.money = money;
            this.num = num;
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

}
