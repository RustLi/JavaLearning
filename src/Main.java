import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello World!");

//        getDbPassWord();

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
    }

    private final Object lock = new Object();
    private void testThread1(){
        System.out.println("start --");
        long time = System.currentTimeMillis();
       synchronized (lock){
            try {
                lock.wait(5 * 1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
       }
        System.out.println("continue---");
        if (System.currentTimeMillis() - time < 5 * 1000){
            System.out.println("aaaaa");
       }
    }

    private Thread guardThread;
    private void testThread(){
        for (int i = 0; i < 2; i++) {
            guardThread = new Thread(new GuardRunnable(), "GuardRunnable");
            guardThread.start();
        }
    }

    public class  GuardRunnable implements Runnable {
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
     * @param
     */
    private static void getDbPassWord(){
        String cInfoPath = "/Users/lwl/work/resource/wechat_db/CompatibleInfo.cfg";
        String sInfoPath = "/Users/lwl/work/resource/wechat_db/systemInfo.cfg";
        File cInfoFile = new File(cInfoPath);
        File sInfoFile = new File(sInfoPath);
        Map<Integer, Object> cMaps = getMapInfoFromFile(cInfoFile);
        Map<Integer, Object> sMaps = getMapInfoFromFile(sInfoFile);
        String imei = "";
        if (cMaps != null){
            for (Integer key : cMaps.keySet()) {
                System.out.println("key:" + key + " ,value:" + cMaps.get(key));
                if (key == 258){
                    imei = (String) cMaps.get(key);
                }
            }
        }
        Object uin = "";
        if (sMaps != null){
            for (Integer key : sMaps.keySet()) {
                System.out.println("key:" + key + " ,value:" + sMaps.get(key));
                if (key == 1){
                    uin = sMaps.get(key);
                }
            }
        }
        if (!"".equals(imei) && !"".equals(uin)){
            String md5 = getMD5(imei + uin);
            System.out.println(md5);
            System.out.println(md5.substring(0,7));
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
     * @param
     */
    private static Map<Integer,Object> getMapInfoFromFile(File cfgFile) {
        if (cfgFile == null || !cfgFile.exists()){
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
