import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import test.FriendFissionRestBonus;
import test.LambdaTest;
import test.User;
import test.enumTest.EnumTest1;
import utils.FixedCapacityMap;
import utils.Md5Util;
import utils.XmlUtil;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static T1 aaa = new T1();

    public static void main(String[] args) {
        Main main = new Main();
//        main.sortList();

//        main.testSet();
//        main.testStr();


        //public static final int FLAG_ACTIVITY_NO_HISTORY = 0x40000000;
        //    /**
        //     * If set, the activity will not be launched if it is already running
        //     * at the top of the history stack.
        //     */
        //    public static final int FLAG_ACTIVITY_SINGLE_TOP = 0x20000000;

//        int a = 0x04000000 | 0x20000000;
//        System.out.println(a);

//        T1 bbb = aaa;
//        bbb.name = "111";
//        bbb.age = 2;
//
//        System.out.println("bbb = " + bbb);
//        System.out.println("aaa = " + aaa);

//        String url = "https://kuaike-dev.oss-cn-beijing.aliyuncs.com/skynet-app/19f7d11fc0b840c291a01814c585cf0f-7f7e2c21-3232-4f43-8af6-9c1b034311f3.mp4";

//        String url = "https://yunxi-dev-cn.kuaikeguanjia.com/skynet-app/d31276a0e9b7471a8d809a33d3762b40-a15f56cd-c9e5-42fb-8fa2-58b37561e422.mp4";
//
//        if (url.contains(".aliyuncs.com") || url.contains("yunxi-cdn.kuaikeguanjia.com") || url.contains("yunxi-dev-cn.kuaikeguanjia.com")) {
//            System.out.println(111);
//        }else{
//            System.out.println(222);
//        }

        String aaa = "1.8万";
        System.out.println("aaa = " + extractDigit(aaa));

        String bbb = "3万";
        System.out.println("bbb = " + extractDigit(bbb));

        String ccc = "1.8亿";
        System.out.println("ccc = " + extractDigit(ccc));

        String ddd = "dsadasda";
        System.out.println("ddd = " + extractDigit(ddd));

        String eee = "4千";
        System.out.println("eee = " + extractDigit(eee));

    }

    /**
     * 正则表达式提取数字（包括小数点）
     **/
    public static String getNumber(String str){
        // 控制正则表达式的匹配行为的参数(小数)
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        //Matcher类的构造方法也是私有的,不能随意创建,只能通过Pattern.matcher(CharSequence input)方法得到该类的实例.
        Matcher m = p.matcher(str);
        //m.find用来判断该字符串中是否含有与"(\\d+\\.\\d+)"相匹配的子串
        if (m.find()) {
            //如果有相匹配的,则判断是否为null操作
            //group()中的参数：0表示匹配整个正则，1表示匹配第一个括号的正则,2表示匹配第二个正则,在这只有一个括号,即1和0是一样的
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果匹配不到小数，就进行整数匹配
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                //如果有整数相匹配
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
                str = "";
            }
        }
        return str;
    }


    private static long extractDigit(String str) {
        String num = getNumber(str);
        if (num == null || num.equals("")){
            return 0L;
        }
        if (str.contains("万")){
            double temp = Double.parseDouble(num);
            return  (long) (temp * Math.pow(10,4));
        }else if(str.contains("亿")){
            double temp = Double.parseDouble(num);
            return  (long) (temp * Math.pow(10,8));
        }
        return (long)Double.parseDouble(num);
    }

    public static class T1{
        public String name;
        public int age;

        @Override
        public String toString() {
            return "T1{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    private void testStr(){
        String format = "你好: {0},{1}";
        System.out.println(MessageFormat.format(format,"1111",222));
    }

    private void testSet(){
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        Set<Integer> set2 = new HashSet<>();
        set2.add(2);
        set2.add(3);
        set2.add(4);
        set2.add(null);

        Set<Integer> set3 = Sets.intersection(set1,set2);
        Set<Integer> set4 = Sets.difference(set1,set2);
        Set<Integer> set5 = Sets.difference(set2,set1);

        System.out.println("set1 = " + set1);
        System.out.println("set2 = " + set2);
        System.out.println("set3 = " + set3);
        System.out.println("set4 = " + set4);
        System.out.println("set5 = " + set5);

        System.out.println("-----");
        System.out.println(set1);
        System.out.println(set2);
    }

    private void testGuava(){
        List<String> mList = new ArrayList();
        System.out.println(111);
        Lists.partition(mList,50).forEach(nList -> {
            for (String aaa: nList) {
                System.out.println(aaa);
            }
        });
        System.out.println(222);
    }

    private void testMap111(String format){
        Map<Integer, Integer> stageRewardCountMap = new HashMap<>();
        stageRewardCountMap.put(1,999);
        stageRewardCountMap.put(2,2);
        Map<Integer, Integer> receiveStageRewardCountMap = new HashMap<>();
        receiveStageRewardCountMap.put(1,3);

        Map<String, String> model = new HashMap<>();

        Map<Integer, String> restBonusMap = FriendFissionRestBonus.getCache();
        Set<Map.Entry<Integer, String>> entries = restBonusMap.entrySet();
        for (Map.Entry<Integer, String> e : entries) {
            Integer key = e.getKey();
            String value = e.getValue();

            if (format.contains(value)) {
                int rewardCount = stageRewardCountMap.get(key) == null ? 0 : stageRewardCountMap.get(key);
                int receiveCount = receiveStageRewardCountMap == null ? 0 : receiveStageRewardCountMap.get(key) == null ? 0 : receiveStageRewardCountMap.get(key);
                int resetCount = Math.max(rewardCount - receiveCount, 0);
                model.put(value, String.valueOf(resetCount));
            }
        }

//        for (Map.Entry<String,String> a: model.entrySet()) {
//            System.out.println("lwl: key  = " + a.getKey() + ", value = " + a.getValue());
//        }

        String text = render(format, model);
        System.out.println("lwl test: " + text);
    }

    public static String render(String template, Map<String, String> model) {
        String comment = template;

        Set<Map.Entry<String, String>> entries = model.entrySet();
        for (Map.Entry<String, String> e : entries) {
            String k = e.getKey();
            String v = e.getValue();

            String value = v == null ? "" : v;
            comment = replace(comment, k, value);
        }

        return comment;
    }

    public static String replace(String source, String placeHolder, String target) {
        // 若原串为空，则不处理。
        if (source == null) {
            return source;
        }

        // 若占位符为空，也不处理。
        if (placeHolder == null) {
            return source;
        }

        // 占位符和目标串相同，也不处理。
        if (placeHolder.equals(target)) {
            return source;
        }

        // 用目标串替换原串中所有的占位符
        // 这里不使用正则表达式，避免占位符字符串中有特殊字符。
        while (source.contains(placeHolder)) {
            source = source.replace(placeHolder, target);
        }
        return source;
    }


    /**
     * 测试汉字字节数
     **/
    private void testChByteNum(){
        String aaa = "aa";
        byte[] ccc = aaa.getBytes(StandardCharsets.UTF_8);
        System.out.println("aaa = " + aaa.length());
        System.out.println("ccc = " + ccc.length);

        String bb = "我的";
        System.out.println("bb = " + bb.length());
        byte[] ddd = new byte[0];
        try {
            ddd = bb.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("ddd = " + ddd.length);
    }

    /**
     * List排序
     **/
    private void sortList(){
        List<Integer> mlist = new ArrayList<>();
        mlist.add(555);
        mlist.add(1);
        mlist.add(2);
        mlist.add(3);
        mlist.add(4);
        mlist.add(5);

        mlist.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        List<Apple> appleList = Lists.newArrayList();
        appleList.add(new Apple(4));
        appleList.add(new Apple(1));
        appleList.add(new Apple(3));

        appleList.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                if (o1 == null || o2 == null){
                    return 0;
                }
                return o1.getSize() - o2.getSize();
            }
        });

        for (Apple m1: appleList) {
            System.out.println("m1 = " + m1);
        }


//        Integer targetId = 4;
        //            System.out.println("o1 = " + o1 + ", o2 = " + o2);
        //            if (o1.equals(targetId)) {
        //                return -1;
        //            }
        //            return 1;

        //按照升序排序
//        mlist.sort(Integer::compareTo);

//        mlist.stream().sorted().forEach(System.out::println);

//        sortListByMainDept(mlist,targetId);

//        for (Integer m1: mlist) {
//            System.out.println("m1 = " + m1);
//        }

//        Map<Integer,Integer> deptNames = new LinkedHashMap<>();
//        deptNames.put(2,2);
//        deptNames.put(1,1);
//        deptNames.put(3,3);
//        for (Map.Entry<Integer, Integer> entry : deptNames.entrySet()) {
//            System.out.println(entry.getValue());
//        }

    }

    public static class Apple{
        public Integer size;

        public Integer getSize() {
            return size;
        }

        public Apple(Integer size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "size=" + size +
                    '}';
        }
    }

    private static void sortListByMainDept(List<Integer> deptIds, Integer targetId){
//        log.info("sortListByMainDept: targetId:{}",targetId);
        deptIds.sort((o1, o2) -> {
            if (o1.equals(targetId)) {
                return -1;
            }
            return 1;
        });
    }


//    private List<Integer> sortDeptsByOrder(List<Integer> deptIds, List<Integer> orderIds){
//
//        if (Objects.isNull(deptIds) || Objects.isNull(orderIds) || deptIds.size() != orderIds.size()){
//            log.error("sortDeptsByOrder, input is error. deptIds:{},orderIds:{}",deptIds,orderIds);
//            return Lists.newArrayList();
//        }
//
//        List<Integer> retDeptIds = Lists.newArrayList();
//        List<Integer> sortedOrderIds = orderIds.stream().sorted().collect(Collectors.toList());
//
//        int length = orderIds.size();
//        for (int i = 0 ; i < length; i++) {
//            for (int j = 0; j < length; j++) {
//                if (Objects.equals(orderIds.get(i),sortedOrderIds.get(j))){
//                    retDeptIds.set(j, deptIds.get(i));
//                }
//            }
//        }
//        return retDeptIds;
//    }
//
//    private Integer[] sortDeptsByOrder(Integer[] deptIds, Integer[] orderIds){
//
//        Integer[] retDeptIds = new Integer[deptIds.length];
//        Integer[] sortedOrderIds = Arrays.stream(orderIds).sorted(Comparator.naturalOrder()).collect(Collectors.toList()).toArray(new Integer[orderIds.length]);
//
//        for (int i = 0, length = orderIds.length ; i < length; i++) {
//            for (int j = 0, sortLength = sortedOrderIds.length; j < sortLength; j++) {
//                if (Objects.equals(orderIds[i],sortedOrderIds[j])){
//                    retDeptIds[j] = deptIds[i];
//                }
//            }
//        }
//
//        return retDeptIds;
//    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    private static void test12345(){
//        System.out.println(12345);
//        String path = "THUMBNAIL_DIRPATH://th_b9afe7064f67e199d19b4ef3d1d38b68";
//        if (path != null && path.contains("THUMBNAIL_DIRPATH://")){
//            String name = path.split("//")[1];
//            System.out.println(name);
//        }

//        String path1111 = "/storage/emulated/0/Android/data/com.tencent.mm/MicroMsg/62e3b1ffd611dacdf7bc5f0a8aa80b30/image2/58/41/th_5841ce90b2af44e1027ab32f5b6a7e46";
//        String p1 = path1111.substring(path1111.lastIndexOf("/")+1);
//        System.out.println(p1);
//        System.out.println(p1.length());

//        String p2 = "th_b6b7c7c2e3c0ce3a671b9f5525a3d631hd";
//        String p3 = p2.substring(0, p2.length() - 2);
//        System.out.println(p3);

//        String test = "https://channels.weixin.qq.com/cgi-bin/mmfinderassistant-bin/auth/auth_scan_qrcode?token=AQAAAPgQKiLFMcIm_bJxvg&exportKey=n_ChQIAhIQvBTXDHJF0AlrzYH9QvReRRLpAQIE97dBBAEAAAAAABdFDUZ9aCsAAAAOpnltbLcz9gKNyK89dVj00UprOyYEO%2BFGmtjs93J4nj5yhAqPhO9y0JyqLygtEbbrTpnH0gfX88JaLdOOTx5C7aRuDxkr%2FXqZVAmfr0RKlk5obERR9uwgqWIvXeu%2BYZ3TQzZ4UwHMIPETZBx5pdjVQoyBmmrI8SjoZalzjlt5yNFnqSnOhkBEnZPbOSvZFDIGJ%2Fk5QuH13gB2Apf7AgleemKY70ncFjh6GZGv6IGog6EnuV2%2F2pRi%2BBGzbH7RlWaJ0ltkuZrdP7hRCSE4cshIB%2BDD&timestamp=1615629684175";
//        String temp = test.substring(0, test.lastIndexOf("&"));
//        System.out.println(temp);

        String t1 = "https://channels.weixin.qq.com/mobile/confirm.html?token=AQAAAA8GEbvdzx1LFri3xA&exportkey=n_ChQIAhIQprUzvJrzlg0WCrRtyKmZeRLpAQIE97dBBAEAAAAAAEslOnu%2FQWgAAAAOpnltbLcz9gKNyK89dVj0LLGzjDPotd7igcocJILwyT4KutjtuKCgRHOQHbMtHl8viIzetRRQLkGzgiCshbNQtlU43OnZGX0ZwYWEtNUhf6SpiIfGYLd%2BjffE3k4VvQ5nHbBEEk2Vl5EjJ0jLIen%2FEWtjR5wqsYxLC4Ov%2FB9kMSBx38%2BdOjLgPjBE63%2FunbvqKrbXGpJAOuHvsr4%2BJfWdJ087oIgpo2WNMiKrzQT3eTpd7LacbJjXjXtNoA7bcIoxrGwt6l5fMQaKStudchOeOjDa&pass_ticket=WqXKFDK9degdPSj3%2BbBkpDNPfqXpDGq9U1Eb0N%2BtL39jmpclLivqgVKiEKwjcqk%2F&wx_header=0";
//        String t2 = t1.split("\\?")[1];
//        System.out.println(t2);

        String[] arr = t1.split("&");
        String p0 = arr[0].split("\\?")[1];
//        System.out.println(p0);
        System.out.println(p0.split("=")[1]);


        String exportKey = arr[1];
//        System.out.println(exportKey);
        System.out.println(exportKey.split("=")[1]);

        String p1 = "https://channels.weixin.qq.com/cgi-bin/mmfinderassistant-bin/auth/auth_login_action?";
        String newUrl = p1 + p0 + "&" + exportKey +  "&action=0&timestamp=" + String.valueOf(System.currentTimeMillis());

        System.out.println(newUrl);
    }

    private static void test222(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time1 = System.currentTimeMillis();
                System.out.println(time1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long time2 = System.currentTimeMillis();
                System.out.println(time2);

                System.out.println(time2 - time1);
            }
        }).start();
    }

    private static void patternTest() {
        System.out.println("patternTest start...");

//        String line1 = "IPCThreadPool#InnerWorkerThread-161155063=1,skynet_logicPool#2=1,skynet_logicPool#3=1,[GT]ForceNotifyService=1,skynet_logicPool#1=1,skynet_commonPool#2=1,skynet_commonPool#1=1,SearchDaemon=1,parallels-4=1,oss-android-log-thread=1,parallels-3=1,parallels-2=1,parallels-1=1,parallels-5=1,VFS.Maintenance=1,[GT]TP#Internal=1,parallels-0=1,pool-5-thread-1=1,OkHttp ConnectionPool=3,matrix_time_update_thread=1,pool-3-thread-1=1,Okio Watchdog=2,TbsHandlerThread=1,OkHttp https://dev-seed.kuaikeguanjia.com/...=1,[D]#worker=1,nioEventLoopGroup-2-1=1,pool-16-thread-1=1,[GT]ColdPool#15=1,i=1,[GT]ColdPool#13=1,[GT]ColdPool#14=1,FileObserver=1,Timer-0=1,[GT]ColdPool#1=1,[GT]ColdPool#0=1,[GT]HotPool#7=1,[GT]ColdPool#5=1,[GT]ColdPool#11=1,skynet_logThread=1,[GT]ColdPool#4=1,[GT]ColdPool#12=1,[GT]ColdPool#3=1,[GT]ColdPool#2=1,[GT]ColdPool#10=1,[GT]ColdPool#9=1,[GT]HotPool#0=1,[GT]ColdPool#8=1,[GT]ColdPool#7=1,[GT]HotPool#2=1,[GT]ColdPool#6=1,[GT]HotPool#1=1,[GT]HotPool#4=1,[GT]HotPool#3=1,[GT]HotPool#6=1,[GT]HotPool#5=1,skynet_TaskManager#2=1,skynet_TaskManager#1=1,main=1,ConnectivityThread=1,AppForegroundDelegate=1,OpenGLAllResRecorder=1,TP_NetInform=1,pool-22-thread-1=1,[GT]playSoundThread=1,Vending-HeavyWorkThread=1,BroadcastAnrThread=1,skynet_DelaySet#1=1,skynet_messagePool#1=1,skynet_messagePool#2=1,queued-work-looper=1,|ANR-WatchDog|=1,skynet_schedulePool#2=1,skynet_schedulePool#1=1,skynet_schedulePool#3=1,[GT]HCPerfManager=1,skynet_messagePool#7=1,skynet_messagePool#8=1,skynet_messagePool#9=1,skynet_messagePool#3=1,skynet_messagePool#4=1,EGLAllResRecorder=1,skynet_messagePool#5=1,skynet_messagePool#6=1,IPCThreadPool#Thread-2=1,IPCThreadPool#WorkerThread=1,default_matrix_thread=1,BroadcastThread=1,arch_disk_io_0=1,arch_disk_io_1=1,arch_disk_io_2=1,arch_disk_io_3=1,IPCThreadPool#Thread-0=1,IPCThreadPool#Thread-1=1,SoterGenKeyHandlerThreadName=1,Thread-4=1,TBSOneThread=1,MicroMsg.GLThread365=1,Thread-6=1,Thread-9=1,Vending-LogicThread=1,skynet_delayHandle=4,skynet_Client#2=1,skynet_Client#1=1,pool-15-thread-1=1,TcmReceiver=1,Thread-19=1,pool-21-thread-1=1,oss-android-api-thread=1,";
//        String line1 = "asafg_adad_12";
//        String line2 = line1.replaceAll("[0-9]\\d{1,1000}", "?");
//        System.out.println("line2 = " + line2);
        // 按指定模式在字符串查找
//        String line = "This order was placed for QT3000! OK? [222]";
//        String line = "[微笑][撇嘴][色][发呆][得意][流泪][害羞][闭嘴][睡][大哭][尴尬][发怒][调皮][呲牙][惊讶][难过][酷][冷汗][抓狂][吐][偷笑][愉快][白眼][傲慢][饥饿][困][惊恐][流汗][憨笑][悠闲][奋斗][咒骂][疑问][嘘][晕][疯了][衰][骷髅][敲打][再见][擦汗][抠鼻][鼓掌][糗大了][坏笑][左哼哼][右哼哼][哈欠][鄙视][委屈][快哭了][阴险][亲亲][吓][可怜][菜刀][西瓜][啤酒][篮球][乒乓][咖啡][饭][猪头][玫瑰][凋谢][嘴唇][爱心][心碎][蛋糕][闪电][炸弹][刀][足球][瓢虫][便便][月亮][太阳][礼物][拥抱][强][弱][握手][胜利][抱拳][勾引][拳头][差劲][爱你][NO][OK][爱情][飞吻][跳跳][发抖][怄火][转圈][磕头][回头][跳绳][投降][激动][乱舞][献吻][左太极][右太极][奸笑][嘿哈][捂脸][机智][茶][红包][蜡烛][耶][皱眉][鸡][福][吃瓜][加油][汗][天啊][Emm][社会社会][旺柴][好的][打脸][加油加油][哇][發]";
//        String line = "   qdwqwqfq [微笑] 4124235[[[ [撇嘴][色] [色]]]]]##[[**\\//1!@$%@%#^$^&[1112]..";
//        String line = "12423423[色]1111[OK]698e73[NO][你好]NO[Emm][}}}}";
        String line = "【色】[色]";

//        String pattern = "(\\D*)(\\d+)(.*)";
//        String pattern = "\\[  ([^\\[ \\] ]+)  \\]";

//        String pattern = "(.*)(\\[([\\u4e00-\\u9fa5]+)\\])(.*)";
//        String pattern = "(\\[([\\u4e00-\\u9fa5]+)\\])";
        String pattern = "(\\[([(\\u4e00-\\u9fa5) | OK | NO | Emm]{1,10000})\\])";

        String[] list111 = line.split(pattern);
        System.out.println("length = " + list111.length);
        List<String> list1 = new ArrayList<>(Arrays.asList(list111));
//        for (String spiltStr : list111) {
////            System.out.println("spiltStr = " + spiltStr);
//            list1.add(spiltStr);
//        }

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        List<String> list2 = new ArrayList<>();
//        Stack<String> stack = new Stack<>();

        while (m.find()) {
            String group = m.group();
            list2.add(group);
//            stack.push(group);
        }

        System.out.println("size = " + list2.size());
        for (String mmm : list2) {
            System.out.println(mmm);
        }

        int length1 = list1.size();
        int length2 = list2.size();
        int max = Math.max(length1, length2);
        //新建一个数组list，来接受最终结果
        List<String> list = new ArrayList<>();
        //遍历较大长度，保证所有数据都能取到
        for (int i = 0; i < max; i++) {
            if (i < length1) {
                String s1 = list1.get(i);
                list.add(s1);
            }

            if (i < length2) {
                String s2 = list2.get(i);
                list.add(s2);
            }
        }

        System.out.println("length == " + list.size());

        for (String jjj : list){
//            System.out.println("jjj = " + jjj);
        }
    }


    private static List<String> splitText(String line){
        if (line == null) return null;
        String pattern = "(\\[([\\u4e00-\\u9fa5]+)\\])";

        String[] list111 = line.split(pattern);
        System.out.println("length = " + list111.length);
        List<String> list1 = new ArrayList<>();
        for (String spiltStr : list111) {
            System.out.println("spiltStr = " + spiltStr);
            list1.add(spiltStr);
        }
        return list1;
    }

    private static List<String> splitEmoji(String line){
        if (line == null) return null;
        String pattern = "(\\[([\\u4e00-\\u9fa5]+)\\])";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        List<String> list2 = new ArrayList<>();
//        Stack<String> stack = new Stack<>();

        while (m.find()) {
            String group = m.group();
            list2.add(group);
//            stack.push(group);
        }
        return list2;
    }

    public static void printTest(){
        System.out.println(111);
        long duration = TimeUnit.SECONDS.toMillis(3);
        System.out.println(duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(222);
    }

    public static void sleepRandom(long minMillis, long maxMillis) {
        long range = maxMillis - minMillis;

        try {
            long time = minMillis;
            if (range > 0) {
                time += System.currentTimeMillis() % range;
            }
//            L.d("SleepUtil", "sleepRandom millis :" + time);
            System.out.println(time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
//            L.e("SleepUtil", e, e.getStackTrace());
        }
    }

    public static String getStr(String text){
        return text != null ? text.split("\\[")[0] : "";
    }

    public static String emojiTest(){
//        String text = "\uD83D\uDE04[微笑]";
//        String text =
//                "[难过]\n" +
//                "[再见]";
//        String text = "[微笑][撇嘴][色][发呆][得意][流泪][害羞][闭嘴][睡][大哭][尴尬][发怒][调皮][呲牙][惊讶][难过][酷][冷汗][抓狂][吐][偷笑][愉快][白眼][傲慢][饥饿][困][惊恐][流汗][憨笑][悠闲][奋斗][咒骂][疑问][嘘][晕][疯了][衰][骷髅][敲打][再见][擦汗][抠鼻][鼓掌][糗大了][坏笑][左哼哼][右哼哼][哈欠][鄙视][委屈][快哭了][阴险][亲亲][吓][可怜][菜刀][西瓜][啤酒][篮球][乒乓][咖啡][饭][猪头][玫瑰][凋谢][嘴唇][爱心][心碎][蛋糕][闪电][炸弹][刀][足球][瓢虫][便便][月亮][太阳][礼物][拥抱][强][弱][握手][胜利][抱拳][勾引][拳头][差劲][爱你][NO][OK][爱情][飞吻][跳跳][发抖][怄火][转圈][磕头][回头][跳绳][投降][激动][乱舞][献吻][左太极][右太极][奸笑][嘿哈][捂脸][机智][茶][红包][蜡烛][耶][皱眉][鸡][福][吃瓜][加油][汗][天啊][Emm][社会社会][旺柴][好的][打脸][加油加油][哇][發]";

//        while(start < end && str.charAt(start) == ' ') {
//            start++;
//        }

//        String temp = text;

//        int leftIndex = temp.lastIndexOf("[");
//        System.out.println(leftIndex);
//        int rightIndex = temp.lastIndexOf("]");
//        System.out.println(rightIndex);
//
//        String left = temp.substring(0, leftIndex);
//        String middle = temp.substring(leftIndex,rightIndex+1);
//        String right = temp.substring(rightIndex+1);
//
//        System.out.println(left);
//        System.out.println(middle);
//        System.out.println(right);

        String text = "\uD83D\uDCE3 【说大事】重磅来袭！\n" +
                "老师给同学带来了元宵福利，晚19：40来直播间，参加抽奖活动，100份京东礼品卡等你来拿[色]抽到直接发到手里\n" +
                "PS:只限直播间同学才可领取哈，开课前20分钟准时来参加~\uD83C\uDF1E\n" +
                "赶紧动动小手看一下可不可以进入直播间\n" +
                "https://play.kaikeba.com/video/320729\n" +
                "有问题一定要在抽奖前找老师解决[[握手]]\n" +
                "没有问题回复【1】";

        Stack<String> textList = new Stack<>();
        if (!text.contains("[")){
            return "";
        }
        String temp = text;
        int leftIndex = 0;
        int rightIndex = 0;

        while (temp != null && temp.length() > 0 && leftIndex <= rightIndex){
            leftIndex = temp.lastIndexOf("[");
            System.out.println("leftIndex = " + leftIndex);
            if (leftIndex < 0){
                textList.push(temp);
                break;
            }
            rightIndex = temp.lastIndexOf("]");
            System.out.println("rightIndex = " + rightIndex);
            if (rightIndex < 0){
                break;
            }
            if (leftIndex > rightIndex){
                continue;
            }
            String left = temp.substring(0, leftIndex);
            String middle = temp.substring(leftIndex,rightIndex + 1);
            String right = temp.substring(rightIndex + 1);

            if (right.length() > 0){
                textList.push(right);
            }

            if (middle.length() > 0){
                textList.push(middle);
            }
            temp = left;
        }

//        StringBuilder sb = null;
//        StringBuilder textSb = new StringBuilder();
//        boolean isAppend = false;
////
//        if (!text.contains("[")){
//            return text;
//        }
//
//        for (int i = 0, length = text.length(); i < length; i++) {
//            char aaa = text.charAt(i);
////            System.out.println(aaa);
//
//            if ('[' == aaa){
//                sb = new StringBuilder();
//                isAppend = true;
//            }
//
//            if (isAppend){
//                sb.append(aaa);
//            }
//
//            if (']' == aaa){
//                isAppend = false;
//                textList.add(sb.toString());
//                continue;
//            }
//
//            if (!isAppend){
//                String ccc = ("\\u" + Integer.toHexString(aaa)).toString();
//                System.out.println(ccc);
//                textList.add(ccc);
//            }
//        }

        System.out.println("size = " + textList.size());
        for (int i = 0, size = textList.size(); i < size; i++) {
            System.out.println(textList.pop());
        }

        return "";
    }

    /**
     * unicode转字符串
     *
     * @param unicode
     * @return
     */
    public static String unicodeToStr(String unicode) {
        StringBuilder sb = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            return null;
        }
        return bytesToHexString(digest.digest());
    }


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static <T> T noZero(T data){
        if (data == null) return null;
        return data.toString().equals("0") || data.toString().equals("0.0") ? null : data;
    }


    private static class Test111{
        public String a;
        public String b;

        @Override
        public String toString() {
            return "Test111{" +
                    "a='" + a + '\'' +
                    ", b='" + b + '\'' +
                    '}';
        }
    }



    public static boolean isGroupRobot(long j) {
        return (j >> 48) == 39;
    }


    public static boolean isInnerCustomerServer(long j) {
        return (j >> 48) == 30;
    }


    public static boolean isExterCustomer(long j) {
        j >>= 48;
        return j == 33 || j == 34;
    }

    public static boolean isAppUser(long j) {
        return (j >> 48) == 20;
    }


    public static boolean cmp(java.lang.Object a, java.lang.Object b) {
        return a != null && a.equals(b) || a == b;
    }

    //aList -->> urls  bList -->> imgPaths
    private static List<String> sortAByB(List<String> imgPaths, List<String> aList, List<String> bList){
        if (aList == null || aList.size() <= 0 || bList == null || bList.size() <= 0 || aList.size() != bList.size()){
            return imgPaths;
        }

        Map<String,String> imgPathMap = new HashMap<>();
        for (String b: bList) {
            //1.解析imgPath 获取Key和imgPath Map
            String key = b;
            imgPathMap.put(key,b);
        }

        List<String> retList = new ArrayList<>();
        for (String a: aList) {
            //1.解析url获取Key和Url Map
            String key = a;
            String value = imgPathMap.get(key);
            retList.add(value);
        }
        return imgPaths;
    }

    public static String listToStr1(List<String> list){
        if (list != null && list.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str: list) {
                stringBuilder.append(str);
                stringBuilder.append(",");
            }
            return stringBuilder.substring(0,stringBuilder.length()-1);
        }
        return "";
    }

    public static String listToStr(List<String> list){
//        if (list != null){
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String str: list) {
//                stringBuilder.append(str);
//                stringBuilder.append(",");
//            }
//        }
        String str = String.join(",", list);
        return str;
    }

    public static boolean isMobile(final String str) {
        if (str == null || str.length() != 11){
            return false;
        }
        String mobileStr = "^[1][3-9][0-9]{9}$";
        return Pattern.compile(mobileStr).matcher(str).matches();
    }

//    Main b = new Main();

    private String c = "aaa";

    static class A {
        final A b = this;
    }

    public enum Status {
        NO,
        RUN,
        OUTOFDATE
    }

    private static long getLong(String str) {
        return new BigInteger(str).longValue();
    }

    public static String jo(long j) {
        return new BigInteger(Long.toBinaryString(j), 2).toString();
    }

    private static String toBinary(int num) {
        String str = "";
        while (num != 0) {
            str = num % 2 + str;
            num = num / 2;
        }
        return str;
    }

    private static String a = null;


    private static void testSwi(int type){
        switch(type){
            case 1:
            case 2:
                System.out.println("1..2");
                break;
        }
    }



    private static ScheduledExecutorService mScheduledThreadPool = Executors.newScheduledThreadPool(3);
    private static volatile long cTime;
    private static void testDelay(){
        System.out.println("testDelay");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("start:::" + System.currentTimeMillis());
            }
        };
        System.out.println("开始时间：："  + (System.currentTimeMillis()));
        mScheduledThreadPool.scheduleWithFixedDelay(runnable,10,20,TimeUnit.SECONDS);
    }

    private static void testMap(){
//        FixedCapacityMap.put(1,1);
//        FixedCapacityMap.put(2,2);
//        FixedCapacityMap.put(3,3);
//        Map<Long,Long> cacheMap = FixedCapacityMap.getCache();
//        for (Map.Entry<Long, Long> entry : cacheMap.entrySet()) {
//            System.out.println("111-----" + String.format("%d: %d", entry.getKey(), entry.getValue()));
//        }
//        System.out.println("------");
//        FixedCapacityMap.put(4,4);
//        Map<Long,Long> cacheMap1 = FixedCapacityMap.getCache();
//        for (Map.Entry<Long, Long> entry : cacheMap1.entrySet()) {
//            System.out.println("222----" + String.format("%d: %d", entry.getKey(), entry.getValue()));
//        }

//        for (int i = 0; i < 1000; i++) {
//            System.out.println(i);
//            FixedCapacityMap.put(i,i);
//        }
//        System.out.println(FixedCapacityMap.getCache().size());
    }

    private static boolean isDuplicate(long msgSvrId){
        if(FixedCapacityMap.contains(msgSvrId)){
            return true;
        }
        FixedCapacityMap.put(msgSvrId,msgSvrId);
        return false;
    }

    private static void testSwitch(int type){
        switch (type){
            case 1:
                System.out.println(111);
                System.out.println(12222);
            case 2:
                System.out.println(222);
            case 3:
                System.out.println(333);
                System.out.println(3333);
        }
    }

    private static void testThrowable(){
        String aa = null;
        for (int i = 0; i < 10; i++) {
            if (i == 5){
                try {
                    int b = aa.length();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("e = " + e);
                    System.out.println("getMessage = " + e.getMessage());
                    System.out.println("getCause = " + e.getCause());
                    System.out.println("toString = " + e.toString());
                }
            }
        }
    }


    /**
     * 去除字符串中的空格、回车、换行符、制表符等
     * @param str
     * @return
     */
    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }

    private static void test111(){
        String aaa = "<e>\n" +
                "    <ShowType>8</ShowType>\n" +
                "    <Content><![CDATA[系统检测到环境存在异常，为了你的帐号安全， 请轻触“确定”进行安全验证。]]></Content>\n" +
                "    <Url><![CDATA[https://weixin110.qq.com/security/readtemplate?t=login_verify_entrances/w_tcaptcha&wechat_real_lang=zh_CN&aid=2000000038&clientype=1&lang=2052&apptype=undefined&captype=7&disturblevel=1&secticket=3_18080196477159456434857418500636]]></Url>\n" +
                "    <DispSec>0</DispSec>\n" +
                "    <Title><![CDATA[]]></Title>\n" +
                "    <Action>1</Action>\n" +
                "    <DelayConnSec>0</DelayConnSec>\n" +
                "    <Countdown>0</Countdown>\n" +
                "    <Ok><![CDATA[]]></Ok>\n" +
                "    <Cancel><![CDATA[]]></Cancel>\n" +
                "    </e>";

        String dddd = "autoauth_errmsg_<e>\n" +
                "    <ShowType>1</ShowType>\n" +
                "    <Content><![CDATA[你的微信帐号于16:09在Xiaomi-MI 8设备上通过微信密码登录。如果这不是你的操作，你的微信密码已经泄漏。请尽快登录微信修改微信密码。或访问weixin110.qq.com冻结微信。]]></Content>\n" +
                "    <Url><![CDATA[]]></Url>\n" +
                "    <DispSec>30</DispSec>\n" +
                "    <Title><![CDATA[]]></Title>\n" +
                "    <Action>1</Action>\n" +
                "    <DelayConnSec>0</DelayConnSec>\n" +
                "    <Countdown>0</Countdown>\n" +
                "    <Ok><![CDATA[]]></Ok>\n" +
                "    <Cancel><![CDATA[]]></Cancel>\n" +
                "    </e>";



//        String ccc = "aaa_<e>...</e>_bbb";
//        int startIndex = ccc.indexOf("<");
//        System.out.println("startIndex = " + startIndex);
//        String fff = ccc.substring(0, startIndex);
//        System.out.println("ffff= " + fff);
//        String eee = ccc.replace(fff,"");
//        System.out.println(eee);
//
//        int endIndex = eee.lastIndexOf(">");
//        String ggg = eee.substring(endIndex+1);
//        System.out.println("ggg = " + ggg);
//        String hhh = eee.replace(ggg,"");
//        System.out.println("hhh = " + hhh);
//        String bbb = subString(aaa,"<",">");
//        System.out.println(bbb);


//        String content = "I am noob " +
//                "from runoob.com.";
//
//        String pattern = ".*runoob.*";
//
//        boolean isMatch = Pattern.matches(pattern, content);
//        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }
    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        System.out.println(strStartIndex);
        int strEndIndex = str.lastIndexOf(strEnd);
        System.out.println(strEndIndex);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        if (strStartIndex == 0){
            strStartIndex = 1;
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex-1, strEndIndex+1).substring(strStart.length());
        return result;
    }

    private static boolean testUser(int type, int verifyFlag){
        return (type & 1) != 0 && (type&32) == 0 && (type&8) == 0 && (verifyFlag&8) == 0;
    }

    private void cacheThreadPoolTest(){
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 30; i++) {
            final int index = i;
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(cacheThreadPool);
                    int threadCount = ((ThreadPoolExecutor)cacheThreadPool).getActiveCount();
                    System.out.println("总数：threadCount = " + threadCount);
                }
            });
        }
        int threadCount = ((ThreadPoolExecutor)cacheThreadPool).getActiveCount();
        System.out.println("总数1111：threadCount = " + threadCount);

//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(cacheThreadPool);
//            }
//        },1,5, TimeUnit.SECONDS);
    }

    private static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * des: 获取数据库密码
     *
     * @param
     */
    private static void getDbPassWord() {
        String cInfoPath = "/Users/lwl/work/resource/wechat_db/CompatibleInfo.cfg";
//        String cInfoPath = "/Users/lwl/Downloads/DB/CompatibleInfo.cfg";
//        String cInfoPath = "/Users/lwl/work/resource/wechat_db/wework/CompatibleInfo.cfg";
        String sInfoPath = "/Users/lwl/work/resource/wechat_db/systemInfo.cfg";
//        String sInfoPath = "/Users/lwl/work/resource/wechat_db/wework/systemInfo.cfg";
//        String sInfoPath = "/Users/lwl/Downloads/DB/systemInfo.cfg";


//        String cInfoPath = "/Users/lwl/temp/CompatibleInfo.cfg";
//        String sInfoPath = "/Users/lwl/temp/systemInfo.cfg";
        System.out.println("imei start");
        File cInfoFile = new File(cInfoPath);
        Map<Integer, Object> cMaps = getMapInfoFromFile(cInfoFile);
        String imei = "";
        if (cMaps != null) {
            for (Integer key : cMaps.keySet()) {
                System.out.println("imei key:" + key + " ,value:" + cMaps.get(key));
                if (key == 258) {
                    imei = (String) cMaps.get(key);
                }
            }
        }
        System.out.println("uin start");
        File sInfoFile = new File(sInfoPath);
        Map<Integer, Object> sMaps = getMapInfoFromFile(sInfoFile);
        Object uin = "";
        if (sMaps != null) {
            for (Integer key : sMaps.keySet()) {
                System.out.println("uin key:" + key + " ,value:" + sMaps.get(key));
                if (key == 1) {
                    uin = sMaps.get(key);
                }
            }
        }
        if (!"".equals(imei) && !"".equals(uin)) {
            String md5 = getMD5(imei + uin);
            System.out.println(md5);
            System.out.println(md5.substring(0, 7));
        }
    }

    private static String getUinBySp(){
        String path = "/Users/lwl/work/resource/wechat_db/wework/system_config_prefs.xml";
//        String node = "map";
        try {
            return XmlUtil.parseXml(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * des: 读取文件到Map中
     *
     * @param
     */
    private static Map<Integer, Object> getMapInfoFromFile(File cfgFile) {
        if (cfgFile == null || !cfgFile.exists()) {
            System.out.println("cfgFile is error");
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(cfgFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Map<Integer, Object>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getCfgFile(File cfgFile) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(cfgFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map<Integer, Object> maps = (Map<Integer, Object>) ois.readObject();
            for (Integer key : maps.keySet()) {
                System.out.println("key:" + key + " ,value:" + maps.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //1   -->> '1'
    //1，2，3 --->> '1' OR '2' OR '3'
    private static void test() {
        String a = "1,2,3";
//        String a = "1";
        String b = "'" + a.replace(",", "' OR labelID = '") + "'";
        System.out.println(b);
        String c = "slect * from ContactLabel where labelID = " + b;
        System.out.println(c);


        List<String> mIDs = new ArrayList<>();
        mIDs.add("1");
        mIDs.add("2");
        mIDs.add("3");
        System.out.println(mIDs);

        String s = mIDs.toString();
        System.out.println(s);

        System.out.println(listToString(mIDs));
    }

    private static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    interface Singer {
        int i = 1;
    }

    public static String MD51(String input) {
        if(input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            String result = bigInt.toString(16);
            // 不足32位高位补零
            while(result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
