package org.lwl.test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class Base64Test {
    public static void main(String[] args) {
        int w1 = 66;
        int h1 = 89;
        Random random = new Random();
        int width = random.nextInt(w1 - 10);
        int height = random.nextInt(h1 - 10);
//        log.info("getCoordinates,width:{},height:{}", width, height);
        System.out.println("width = " + width + " height = " + height);

        String w = getBase64EncodeCoordinate(width);
        String h = getBase64EncodeCoordinate(height);
        System.out.println("w=" + w + ",h =" + h);

        String str1 = Base64.getUrlEncoder().encodeToString(w.toString().getBytes(StandardCharsets.UTF_8));
        String str2 = Base64.getUrlEncoder().encodeToString(h.toString().getBytes(StandardCharsets.UTF_8));

        //"x":"PWtETg==",
        //    "y":"PU16Tg=="
//        String x1 = "PWtETg==";
//        String y1 = "PU16Tg==";
        String x = new String(Base64.getDecoder().decode(Base64.getDecoder().decode(str1)), StandardCharsets.UTF_8);
        String y = new String(Base64.getDecoder().decode(Base64.getDecoder().decode(str2)), StandardCharsets.UTF_8);
        System.out.println("x = "+ x + " y = "+ y);
    }

    private static String getBase64EncodeCoordinate(int coordinate) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars;
        String str;
        str = Base64.getEncoder().encodeToString(String.valueOf(coordinate).getBytes(StandardCharsets.UTF_8));
        chars = str.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            stringBuilder.append(chars[i]);
        }
        str = Base64.getUrlEncoder().encodeToString(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));

        return str;
    }

}
