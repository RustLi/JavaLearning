import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {

//    Main b = new Main();

    private String c = "aaa";

    static class A {
        final A b = this;
    }

    public enum Status{
        NO,
        RUN,
        OUTOFDATE
    }

    public static void main(String[] args) {
//        System.out.println(2 & 32);
//        boolean cm = (2 & 32) > 0;
//        System.out.println(cm);

        System.out.println(Status.NO);

//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println(Thread.currentThread().getId());
//                    }
//                }).start();
//            }
//        });
//        System.out.println(thread1.getId());
//        thread1.start();
//        int b = -1;
//        int a = b & 2;
//        System.out.println(a);
//        System.out.println(b);

//        String a = "111";
//        String[] b = a.split(",");
//        for (String c: b) {
//            System.out.println(c);
//        }

//        System.out.println("Hello World!");

//        getDbPassWord();

//        imei:
//        869423033134168
//        869423033134176
//        uin 1007966035
//        System.out.println(getMD5("8694230331341681007966035"));
//        System.out.println(getMD5("8694230331341761007966035"));
//        test1111();

//        String a = "a:2:223";
//        String b = a.split(":",2)[1];
//        System.out.println(b);

//        int aaa = "2".hashCode();
//        System.out.println(aaa);
//        test();

//        EnumTest.INSTANCE.testEnum();

//        System.out.println(Singer.i);


//        test1("");
//
//        System.out.println("11111");
//        test1("1");
//
//        System.out.println("2222");
//
//        test1("1,2,3");
//
//        System.out.println("3333");

//        int[] a = new int[2];
//        for (int b : a) {
//            System.out.println(b);
//        }

//        String name = "abcd";
//        for (int i = 0; i < 10 ; i++) {
//            if (i == 5){
//                name = null;
//                break;
//            }
//        }
//        checkNotNull(name);
//        System.out.println("lll");

//        new Main().testThread();

//        new Main().testThread1();

//        new Main().testMap();

//        try {
//            test222(-1);
//        } catch (Exception e) {
////            e.printStackTrace();
//            System.out.println(e.toString());
//        }

//        ArrayList<String> lists = new ArrayList<>();
//        lists.add("a");
//        lists.add("b");
//        lists.add("c");
//
//        System.out.println(lists);
//        testList(lists);
//
//        System.out.println(lists);
//        String a = "aa";
//        testStr(a);
//        System.out.println(a);
//        System.out.println(test333());


//        String words = "大家好，欢迎入群";
//        System.out.println(words.substring(0,words.length()-2));

//        List<String> akList = new ArrayList<>();
//        akList.add("aa");
//        akList.add("bb");
//        akList.add("cc");
//        System.out.println(akList);
//
//        List<String> test1 = new ArrayList<>();
//        test1.add("1");
//        test1.add("2");
//        test1.add("aa");
//        System.out.println(test1);
//
//        akList.addAll(test1);
//
//        System.out.println(akList+ "lllll");
//
//        //去重
//        HashSet set = new HashSet(akList);
//        akList.clear();
//        akList.addAll(set);
//        System.out.println(akList);
    }

    private static void testStr(String s){
        if (s != null){
            s = s + "new";
        }
    }

    private static void testList(ArrayList<String> list){
        if (list != null){
            list.add("aaaa");
        }
    }


    private static int test333(){
        int m = 0;
        try {
            m--;
            test222(m);
            m++;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("exception");
        }
        return m;
    }

    private static int mmm = 0;
    private static void test222(int i) throws Exception {
        if (i < 0){
            throw new Exception("llll");
        }
        mmm++;
        System.out.println("after llllll");
        System.out.println("dadadad");
    }

    private void testMap() {
        System.out.println("testMap");
        int a = 10;
//        System.out.println(a);
//        Map map = new HashMap();
//        map.put(a,"c");
//        map.put(10,5);
//        System.out.println(map.get(a));
//        System.out.println(map.get(10));
    }


    private final Object lock = new Object();

    private void testThread1() {
        System.out.println("start --");
        long time = System.currentTimeMillis();
        synchronized (lock) {
            try {
                lock.wait(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("continue---");
        if (System.currentTimeMillis() - time < 5 * 1000) {
            System.out.println("aaaaa");
        }
    }

    private Thread guardThread;

    private void testThread() {
        for (int i = 0; i < 2; i++) {
            guardThread = new Thread(new GuardRunnable(), "GuardRunnable");
            guardThread.start();
        }
    }

    public class GuardRunnable implements Runnable {
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name);
            long id = Thread.currentThread().getId();
            System.out.println(id);
        }
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
        String sInfoPath = "/Users/lwl/work/resource/wechat_db/systemInfo.cfg";
        File cInfoFile = new File(cInfoPath);
        File sInfoFile = new File(sInfoPath);
        Map<Integer, Object> cMaps = getMapInfoFromFile(cInfoFile);
        Map<Integer, Object> sMaps = getMapInfoFromFile(sInfoFile);
        String imei = "";
        if (cMaps != null) {
            for (Integer key : cMaps.keySet()) {
                System.out.println("imei key:" + key + " ,value:" + cMaps.get(key));
                if (key == 258) {
                    imei = (String) cMaps.get(key);
                }
            }
        }
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


    private static void test1111() {
        String a = "111";
        String b = "222";
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add(a);
        arrayList.add(b);
        LinkedList linkedList = new LinkedList();
        linkedList.add(arrayList);
        System.out.println(linkedList);

        LinkedList list = new LinkedList();
        list.add(a);
        list.add(b);
        System.out.println(list);
    }

    public static void test1(String str) {
        if (str == null || str.length() <= 0) {
            System.out.println("str is null");
            return;
        }
        String[] split = str.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
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

}
