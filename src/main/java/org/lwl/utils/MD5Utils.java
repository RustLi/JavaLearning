package org.lwl.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * 
 * @author zhangbing
 *
 */
public class MD5Utils {

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
        "e", "f" };

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode32(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
        } catch (Exception ex) {
        }
        return resultString;
    }

    public static String MD5Encode16(String origin) {
        return MD5Encode32(origin).substring(8, 24);
    }

    public static String MD5Encode8(String origin) {
        return MD5Encode32(origin).substring(16, 24);
    }

    public static String MD5Encode4(String origin) {
        return MD5Encode32(origin).substring(16, 20);
    }

    // MD5加码。32位
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    // 可逆的加密算法
    public static String KL(String inStr) {
        // String s = new String(inStr);
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    // 加密后解密
    public static String JM(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String k = new String(a);
        return k;
    }
    
    public static void main(String args[]) {
//        UUID uuid = UUID.randomUUID();
//        String key = uuid.toString().replace("-", "");
//        System.out.println(key);
//        System.out.println(MD5Encode16("1"));

        String url1 = "https://yunxi-cdn.kuaikeguanjia.com/skynet-app/c0f0b1e90d014deba6023c49e1395835-4fd041dd-ca1c-43eb-b6f2-0ce71374be14.jpg";
        String url2 ="https://yunxi-cdn.kuaikeguanjia.com/skynet-app/7bdd64c841cc4cd39caec64dbeedcd0f-860c46d0-7158-4eb3-b2e0-e7fad031ff42.jpg";
        String url3 ="https://yunxi-cdn.kuaikeguanjia.com/skynet-app/ae443e4a4d9543c19b1d43b17a9f0393-b9e4151e-6af7-41f3-a2c5-fb8d12758d7c.jpg";

        String md51 = MD5Encode8(url1);
        String md52 = MD5Encode8(url2);
        String md53 = MD5Encode8(url3);

        System.out.println(md51);
        System.out.println(md52);
        System.out.println(md53);
    }
}
