package org.lwl.test.exam;

import com.google.common.collect.Lists;
import com.kuaike.common.utils.HttpClientUtils;
import com.kuaike.common.utils.JacksonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwl.test.Md5Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpTest {

//    private static final String Code = "2d3b4466db28550e9201c9c8deb21d45";
//    public static final String MOBILE = "13626880088";
//    public static final String MOBILE = "13261238519";

    //新的还没用起来，用原来的code
    /**
     * https://admin.kyexam.com/sign-in.html?traffic_source=baidu&keyword=%E4%BC%98%E8%80%83%E8%AF%95#/sign-in/
     * 账号：13240935343
     * 密码：hd2024
     **/
    private static final String Code = "c39f1e611b24437a7f4073886765d20d";
//    public static final String MOBILE = "15151886784";
    public static final String MOBILE = "13261238519";

//    private static final String Authorization = "0d2cc09df9bb6777e25faaa858f117fb";

    private static final String DEF_CHARSET = "UTF-8";

    public static void main(String[] args) {
        List<ScoreResultDto> resultList =  getExamResult();
        System.out.println("resultList = " + resultList);
        if (CollectionUtils.isNotEmpty(resultList)){
            List<String> eids = resultList.stream().map(ScoreResultDto::getEid).collect(Collectors.toList());
            System.out.println("eids = " + eids);
            System.out.println("size = " + resultList.size() + ", eid size = " + eids.size());
        }
    }

    private static List<ScoreResultDto> getExamResult(){
        int total = 0;
        int start = 0;
        int limit = 100;
        int i = 1;
        List<ScoreResultDto> resultList = Lists.newArrayList();
        do {
            System.out.println("第几次， i = " + i + ", start = " + start + "', limit = " + limit);
            ScoreResultResp resp = getResp(String.valueOf(start), String.valueOf(limit), MOBILE);
            if (resp != null){
                total = resp.getTotal() == null ? 0 : resp.getTotal();
                if (resp.getData() != null){
                    System.out.println("条数， i=  " + i + ", count = " + resp.getData().size());
                }else {
                    System.out.println("条数， i=  " + i + ", count = " + 0);
                }
                if (CollectionUtils.isNotEmpty(resp.getData())){
                    resultList.addAll(resp.getData());
                }
            }
            start += limit;
            i++;
        }while (start < total);
        return resultList;
    }

    private static ScoreResultResp getResp(String start, String limit, String mobile){
        long time = System.currentTimeMillis() / 1000;
        String url = "https://api.kyexam.com/api/v1/student/queryresults";

        String authorization = Md5Util.md5(time + Code);
        System.out.println("url = " + url + ", authorization = " + authorization);

        Map<String, String> params = new HashMap<>();
        params.put("code", Code);
        params.put("time", String.valueOf(time));
        params.put("start", start);
        params.put("limit", limit);
        params.put("loginValues", mobile);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorization);
        try {
            String resultJson = HttpClientUtils.doGet(url, params, headers, DEF_CHARSET);
            System.out.println("resultJson = " + resultJson);
            ScoreResultResp resp = JacksonUtil.str2Obj(resultJson, ScoreResultResp.class);
            System.out.println("resp = " + resp);
            return resp;
        } catch (Exception e) {
            System.out.println("获取数据失败, e = " + e);
        }
        return null;
    }


    private static void getExamResult1(){
        //        String url = "https://api.kyexam.com/api/v1/student/queryresults?code=" + Code + "&start=0&limit=30&time=1712539532";

        String host = "https://api.kyexam.com";
        String resource = "/api/v1/student/queryresults";
        long time = System.currentTimeMillis() / 1000;
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("code", Code);
        queryParams.put("time", String.valueOf(time));
        queryParams.put("start", "0");
        queryParams.put("limit", "30");
        queryParams.put("loginValues", "13626880088");

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
            System.out.println("Response Code: " + responseCode);
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