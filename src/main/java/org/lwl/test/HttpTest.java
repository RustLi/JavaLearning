package org.lwl.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpTest {

    private static final String Code = "2d3b4466db28550e9201c9c8deb21d45";
//    private static final String Authorization = "0d2cc09df9bb6777e25faaa858f117fb";

    public static void main(String[] args) {
//        String url = "https://api.kyexam.com/api/v1/student/queryresults?code=" + Code + "&start=0&limit=30&time=1712539532";

        String host = "https://api.kyexam.com";
        String resource = "/api/v1/student/queryresults";
        long time = System.currentTimeMillis() / 1000;
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("code", Code);
        queryParams.put("time", String.valueOf(time));
        queryParams.put("start", "0");
        queryParams.put("limit", "30");
//         queryParams.put("loginValues", "13626880088");

        StringBuilder queryBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append("&");
            }
            queryBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }

        String query = queryBuilder.toString();
        String finalUrl = host + resource + "?" + query;

        String authorization = Md5Util.md5(time + Code);
        System.out.println("finalUrl = " + finalUrl + ", authorization = " + authorization);

        try {
            URL apiUrl = new URL(finalUrl);
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", authorization);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                OutputStream outputStream = new FileOutputStream("test.txt");
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}