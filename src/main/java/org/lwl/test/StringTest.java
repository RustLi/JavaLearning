package org.lwl.test;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kuaike.common.errorcode.CommonErrorCode;
import com.kuaike.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.util.TextUtils;
import org.lwl.utils.DateUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class StringTest {
    private static final String[] COLORS = {"a", "b", "c"};

    private static final String APPLET_LINK_STR = "{小程序授权链接}";
    private static final String TEMPLATE_APPLET_STR = "${20,30}";
    private static final String CUSTOMER_NAME_STR = "{客户名称}";
    private static final String CUSTOMER_PHONE_STR = "{手机号}";
    private static final String TEMPLATE_NAME_STR = "${0,20}";
    private static final String TEMPLATE_PHONE_STR = "${10,11}";

    public static final String NET_SCHOOL_CONFIG_ERROR = "该校区未配置支付商户，请先配置后，再录入订单:1:15";

    public static void main(String[] args) {
//        System.out.println(test1111(""));

//       String liveUrl= "https://e45805187.test-at.kuaikeguanjia.com/web/room/enter" +
//               "?room_id=25120249835556&user_avatar=&user_name=&user_role=0&sign=61a7b64d14c963fd6c6102d664852d1c&meeti" +
//               "ng_id=mCgt3eXbGh&pay_channel=1&customstr=_mCgt3PQafs&share_url=https%3A%2F%2Fa.kuaikeguanjia.com%2Fs%2Fm6Udzc32jM&dxbTicket=mCgt3PQafs&dxbDomain=https%3A%2F%2Fa.kuaikeguan" +
//               "jia.com&nope_sense=m6UdzpKinc";
//

//       String liveUrl = "https://e54131384.at.juvox.com.cn/web/room/enter?room_id=25111495675116&user_avatar=&user_name=&user_role=0&sign=448533c7b823c93fc8eedf4af04911cb&meeting_id=MGkzfCBkI1&pay_channel=1&customstr=_MyDPZtRQJ5&share_url=https%3A%2F%2Fdxb.juvox.com.cn%2Fs%2Fm62iM2GRI0&dxbTicket=MyDPZtRQJ5&dxbDomain=https%3A%2F%2Fdxb.juvox.com.cn&inviteUserId=trQz8nv0ND&inviteUserName=%E9%99%B6%E6%98%9F%E7%BE%BD-%E4%BA%91%E5%B8%8C%E7%A7%91%E6%8A%80&nope_sense=m62iMaNSbz";
//       System.out.println(getQueryParams(liveUrl));

       Boolean aa = null;
        System.out.println(Boolean.TRUE.equals(aa));

        try {
            test1();
        }catch (Exception e){
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new BusinessException(CommonErrorCode.PARAM_ERROR, e.getMessage());
        }
    }

    private static void test1(){
        try {
            throw new BusinessException(CommonErrorCode.BUSINESS_ERROR, "订单号：" + "111" + "状态无法编辑");
        }catch (BusinessException businessException){
            System.out.println("businessException.getMessage() = " + businessException.getMessage() + ", businessException = " + businessException);
            businessException.setStackTrace(new StackTraceElement[0]);
            throw businessException;
        } catch (Exception e){
            throw e;
        }
    }

    public static Map<String, String> getQueryParams(String url) {
        Map<String, String> result = new HashMap<>();

        int start = url.indexOf('?');
        System.out.println("start = " + start);
        if (start < 0) {// 无参数
            return result;
        }

        int end = url.lastIndexOf('#',start);
//        int end = url.lastIndexOf(start, '#');
        System.out.println("end 1 = " + end);
        if (end < 0) {
            end = url.length();
        }
        System.out.println("end 2 = " + end);

        if (start > 0) {
            String queryString = url.substring(start + 1, end);
            String[] pairs = queryString.split("&");// 分解成 key-value 键值对
            for (String s : pairs) {
                int index2 = s.indexOf('=');
                if (index2 < 0) {
                    result.put(s, "");
                } else {
                    String key = s.substring(0, index2);
                    String value = s.substring(index2 + 1);
                    result.put(key, value);
                }
            }
        }

        return result;
    }


    private static String test1111(String goodsId){
        return  "/shopClient/shopOrder?roomId=1" + "&payChannel=1&productId=" + goodsId;
    }

    private static String checkConflict() {
        String filePath = "/storage/emulated/0/Android/data/com.tencent.wework/skyeye";
        int offset = filePath.lastIndexOf(".");
        if (offset == -1) {
            offset = filePath.length();
        }
        //去掉扩展名
        String noExtension = filePath.substring(0, offset);
        String extension = filePath.substring(offset);

        System.out.println("noExtension = " +  noExtension + " extension = " + extension);

        int num = 1;
        String newMainFile = filePath;
        String cfgFile;

        while (true) {
            cfgFile = newMainFile + "~cfg";

            if (new File(newMainFile).exists()) {
                //主文件存在，需要重命名
                System.out.println("main file exist " + newMainFile);
            } else if (new File(cfgFile).exists()) {
                //配置文件存在，且正在下载，需要重命名
                //配置文件存在，且不一致，需要重命名
            } else {
                //否则，此名可用
                break;
            }
            newMainFile = noExtension + "(" + num + ")" + extension;
            num++;
        }

        System.out.println("newMainFile = " + newMainFile);
        return newMainFile;
    }


    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();
            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }
    public static String d(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        if (list != null && list.size() > 0) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                stringBuilder.append((String) list.get(i));
                if (i < size - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("\u0000");
        }
        return stringBuilder.toString();
    }

    public static Integer getGoodsType(String productId) {
        return Integer.parseInt(productId.substring(6, 8));
    }


    public static Integer getSchoolId(String productId) {
        if (productId.length() == 16) {
            return 2;
        }

        try {
            return  Integer.parseInt(productId.substring(16, 20));
        } catch (Exception e) {
            System.out.println("error:" + e);
            return 2;
        }
    }

    private static boolean isErrorDomain(int errType, int errCode, String respUrl){
        return errType != 0 || errCode != 0 || (!TextUtils.isEmpty(respUrl) && respUrl.contains("weixin110.qq.com"));
    }

    private static String getMessage(String url, int errType, int errCode) {
        String now = DateUtil.dateTimeFormat();
        StringBuilder builder = new StringBuilder();
        builder.append("###域名异常提醒(").append("test").append(")\n");
        builder.append("**域名:** ").append(url).append("\n");
        builder.append("**检测时间:** ").append(now).append("\n");
        builder.append("**错误码:** (").append(errType).append(",").append(errCode).append(")\n");
        return builder.toString();
    }

    private static String replaceUrl(String url){
        if (!url.startsWith("http")){
            return url;
        }
        // 查找 "//" 后的第一个 '/' 位置
        int start = url.indexOf("//");
        if (start != -1) {
            int pathStart = url.indexOf('/', start + 2);
            System.out.println("pathStart = " + pathStart);
            if (pathStart != -1) {
                return url.substring(pathStart);
            }
        }
        return url;
    }

    private static String splitUrl(String url){
        if (!url.startsWith("http")){
            return url;
        }
        String[] partsAfterDoubleSlash = url.split("//");
        if (partsAfterDoubleSlash.length > 1) {
            String domainAndPath = partsAfterDoubleSlash[1];
            String[] pathParts = domainAndPath.split("/", 2);
            if (pathParts.length > 1) {
                return  "/" + pathParts[1];
            }
        }
        return url;
    }

    private static String buildKey(Long liveId, String bjyUserNumber, String customerNum){
        return liveId + "_" + bjyUserNumber + "_" + customerNum;
    }

    private static String buildKey(Long liveId, String bjyUserNumber, String customerNum, Date date){
        return liveId + "_" + bjyUserNumber + "_" + customerNum + "_" + date;
    }

    private static void testTry(String test){
        System.out.println("start");
        try {
            System.out.println(1111);
            if (test.equals("a")){
                System.out.println(412412);
                return;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }finally {
            System.out.println(2222);
        }
        System.out.println("end");
    }

    private static String prefixedFormat(String prefix, Object... args) {
        return prefix + String.format("live_big_screen_room", args);
    }

    private static String replaceContent(String content, String phone, String name) {
        if (StringUtils.isBlank(content)) {
            return StringUtils.EMPTY;
        }
        name = StringUtils.isBlank(name) ? StringUtils.EMPTY : name;
        content = content.replace(CUSTOMER_NAME_STR, name);
        content = content.replace(CUSTOMER_PHONE_STR, phone);
        content = content.replace(TEMPLATE_NAME_STR, name);
        content = content.replace(TEMPLATE_PHONE_STR, phone);
        return content;
    }

    public static String removeCustomer(String url){
        return url.replace("${customstr}", "");
    }

    public static String buildOrCondition(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(" > 0");
            if (i < list.size() - 1) {
                sb.append(" or ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private static String test111(String aaa){
        String wildcard = "*" + aaa.trim().replace("*", "\\*").replace("?", "\\?") + "*";
        return wildcard;
    }

    public static String truncateString1(String input, int maxLength) {
        if (input == null) {
            return "";
        }
        if (input.length() <= maxLength) {
            return input;
        } else {
            return input.substring(0, maxLength - 3) + "...";
        }
    }

    private static void allocColor(){
        int numPeople = 10;

        for (int i = 0; i < numPeople; i++) {
            int index = i % COLORS.length;
            String color = COLORS[index];
            System.out.println("Person " + i + ": " + color + ", index = " + index);
        }
    }

    private static void formatTest(){
        String format = "%s##%s";
        String aaa = "aaa";
        String bbb = "";
        String ccc = String.format(format,aaa,bbb);
        System.out.println("ccc = " + ccc);

        String[] strList = ccc.split("##");
        String ddd = strList[0];
        String eee = strList[1];
        System.out.println("ddd = " + ddd + ", eee = " + eee);
    }

    private static void compareTest(){
        String str1 = "B";
        String str2 = "B";

        int result = StringUtils.compare(str1, str2);

        if (result < 0) {
            System.out.println("str1 is less than str2");
        } else if (result > 0) {
            System.out.println("str1 is greater than str2");
        } else {
            System.out.println("str1 is equal to str2");
        }

        String aa = "A";
    }

    private static void test11(String maxOption){
        Set<String> tmpSet = Sets.newHashSet();
        for (char c = 'A'; c <= 'Z'; c++) {
            if (Character.toString(c).compareTo(maxOption) <= 0) {
                tmpSet.add(Character.toString(c));
            }
        }
        System.out.println(tmpSet);
    }

    private static void testImportDtoList(){
        ImportDto importDto = new ImportDto();
        importDto.setLevel(0);
        importDto.setName("一级");

        ImportDto importDto1 = new ImportDto();
        importDto1.setLevel(1);
        importDto1.setName("二级");

        List<ImportDto> importDtoList = new ArrayList<>();
        importDtoList.add(importDto);
        importDtoList.add(importDto1);

        List<RowDto> rowDtoList = new ArrayList<>();
        RowDto rowDto1 = new RowDto();
        rowDto1.setImportDtoList(importDtoList);

        RowDto rowDto2 = new RowDto();
        rowDto2.setImportDtoList(importDtoList);

        rowDtoList.add(rowDto1);
        rowDtoList.add(rowDto2);

        System.out.println("rowDtoList = " + rowDtoList);

        List<Map<Integer, String>> mapList = toMapList(rowDtoList);
        System.out.println("toMap = " + mapList);

        List<RowDto> toList = toList(mapList);
        System.out.println("toList = " + toList);
    }

    private static List<Map<Integer, String>> toMapList(List<RowDto> rowDtoList){
        List<Map<Integer,String>> resultList = new ArrayList<>();
        for (RowDto rowDto : rowDtoList) {
            Map<Integer,String> map = new HashMap<>();
            List<ImportDto> importDtoList = rowDto.getImportDtoList();
            for (ImportDto importDto : importDtoList) {
                map.put(importDto.level,importDto.getName());
            }
            resultList.add(map);
        }
        return resultList;
    }

    private static List<RowDto> toList(List<Map<Integer, String>> mapList){
        List<RowDto> resultList = new ArrayList();
        for (Map<Integer, String> map : mapList) {
            List<ImportDto> importDtoList = new ArrayList<>();
            for (Map.Entry<Integer,String> entry : map.entrySet()) {
                ImportDto importDto = new ImportDto();
                importDto.setLevel(entry.getKey());
                importDto.setName(entry.getValue());
                importDtoList.add(importDto);
            }
            RowDto rowDto = new RowDto();
            rowDto.setImportDtoList(importDtoList);
            resultList.add(rowDto);
        }
        return resultList;
    }

    private static class RowDto{
        public List<ImportDto> importDtoList;

        public void setImportDtoList(List<ImportDto> importDtoList) {
            this.importDtoList = importDtoList;
        }

        public List<ImportDto> getImportDtoList() {
            return importDtoList;
        }

        @Override
        public String toString() {
            return "RowDto{" +
                    "importDtoList=" + importDtoList +
                    '}';
        }
    }

    private static class ImportDto{
        public Integer level;
        public String name;

        public void setLevel(Integer level) {
            this.level = level;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "ImportDto{" +
                    "level=" + level +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    public static Map<Integer,Integer> getRankByScoreInt(List<Integer> scoreList) {
//        if (CollectionUtils.isEmpty(scoreList)){
//            return Collections.emptyMap();
//        }
        List<Integer> sortList = scoreList.stream().filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Map<Integer, Integer> rankMap = Maps.newHashMap();
        int rank = 1;
        for (Integer score : sortList) {
            if (!rankMap.containsKey(score)) {
                rankMap.put(score,rank);
            }
            rank++;
        }
        return rankMap;
    }


    private static void toStrWithPattern(){
        String str = "qiqing88020\n" +
                "274323935\n" +
                "Jason870870\n" +
                "cx.3333\n" +
                "A.1941311\n" +
                "BGLBX\n" +
                "w13521103125\n" +
                "1112827409\n" +
                "Xh13834277755\n" +
                " LVjieliaolic\n" +
                "CPIC.666\n" +
                "BaiJunRong\n" +
                "630843242\n" +
                "1485323071\n" +
                "FXC95500\n" +
                "SB20080828\n" +
                "335241722";

        String[] lines = str.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if (line != null && !line.isEmpty()) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(line);
            }
        }

        String output = result.toString();

        String path = "/Users/lwl/work/账号采集/导入导出/交付数据/aaaccc.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(output);
            System.out.println("字符串已成功写入文件。");
        } catch (IOException e) {
            System.out.println("写入文件时出现错误：" + e.getMessage());
        }
        System.out.println(output);
    }

    /**
     * string转sql string
     **/
    private static void toSqlStr(){
        String input = "woHh4aBgAA5LRtFYVRQ3KtnKzV3DI5GQ, woHh4aBgAAAkWWUK544W38VV3_A_VDHA, woHh4aBgAAzq2UuWawsMq_jQGQHhkP4Q, woHh4aBgAAOooyZXjyux9HNF_F1-O1NQ, woHh4aBgAAsDzwwF_coDLMZg7T5KGZNA, woHh4aBgAAZF_mWAYqjvZpoUIC7c9KZA, woHh4aBgAActjts3d1XNVfLFXo7xmVfQ, woHh4aBgAACWDHBHGZVwm2PmaBMei85w, woHh4aBgAAv3wINwRC0qGlIeUVjx29Rg, woHh4aBgAAGZoAu4WxQm_QTE_xCCioWw, woHh4aBgAAqENWsCr9KulLP7CmAjF9Sg, woHh4aBgAAtqYmK-4YrZS26NCEEI5VOQ, woHh4aBgAA7zRE-0PWXXW4kO4M-OrF3Q, woHh4aBgAArcf4W8xtitBKPuj_T67bKg, woHh4aBgAAnHbeAq6zTxVlvhfpR31SUQ, woHh4aBgAAtpahHM_qxpnVKUQp3FZfZQ, woHh4aBgAAfCaUZFQNT_LfUKkHvzjfOg, woHh4aBgAA8zBN1-NdL9feJbH5tBCG4g, woHh4aBgAAHNUs-uaaomfmBYoZA76eIg, woHh4aBgAAxZ4VCQWkb1QZshkV9nk8zQ, woHh4aBgAA9udb4uwED_BnvhkvNBlC6w, woHh4aBgAAETwngCKKJui8VqI6oCoLog, woHh4aBgAA4bvVHShti7eXPcPQU9NswA, woHh4aBgAAypp9q3wPrS4rQe9LwzhsxQ, woHh4aBgAAh4ZcOiqm5ZU9znefxfotNQ, woHh4aBgAAGqGhujw3-Mc4QdnpSWIONw, woHh4aBgAA_JPzhV3IqH82ohdeH-WFjg, woHh4aBgAAFDlhb_i7zgGUBq0qr2edWg, woHh4aBgAA1AGaYPcD4AFuflRooCEHFQ, woHh4aBgAA8fWmddeLfy7ES2XC9o_ZPQ, woHh4aBgAAzNOWiSm_-Lfujo4KP6xBxQ, woHh4aBgAATpvOg9XaKF_KnMu49z3PgQ, woHh4aBgAA781o0hjoy5_eTZyou6uNvg, woHh4aBgAAVdrkGTw7f2zOBA5EYLbJTQ, woHh4aBgAACjsuwj4q3a_JHtbC6jVDqQ, woHh4aBgAAB_TwP-2XKBeS00gD4SffgQ, woHh4aBgAAKDdRni0GvILbnWHbnaKAqg, woHh4aBgAACehKkW_0p0VCiW1JM-cGkA, woHh4aBgAAjprotbihfmcABzMhg5JeOg, woHh4aBgAAdYaH1hLBJucQDyCP_eNXNA, woHh4aBgAAzkCdR3K3PA6NWxSCx_JDwA, woHh4aBgAAVgG4AlE211fVi29yNynmwA, woHh4aBgAA7eQ9IKdMGOF3-9YNcuixMQ, woHh4aBgAAtYqhf74WsiMB9mdJHR8a0Q, woHh4aBgAAfUgJwT61Mpx7jbXA-EijKw, woHh4aBgAAXIGfFc-tZOGESkNpmdZ5Zg, woHh4aBgAAqUNBH8-5907wW9Glt3q8iw, woHh4aBgAAYVBhi33pypO_QgxmSEVxkQ, woHh4aBgAAYLmMyHyU-MAlPyidQKIB4g, woHh4aBgAAPi3HjcIppnS8wQCSTs5bkA, woHh4aBgAAxxPJkN7sXYNNm-cIM3d6QQ, woHh4aBgAAaHgDpiHQchv5M_pmLBzsAA, woHh4aBgAAQkmIBTYpkwVLjett9TJR_w, woHh4aBgAAMBXvx5Iru4MRgyD5pqp4-A, woHh4aBgAA-zW7QSNRPGDg8TpNzDrEeA, woHh4aBgAAPEc39k5txOUvdA0Pkcub7A, woHh4aBgAAaOBI5UY_dsCxKvLq8AJpYw, woHh4aBgAA_AuIPYLBUXUgmBNj24i9iQ, woHh4aBgAAVci0YiKtWTjuHJGop--f1Q, woHh4aBgAANtg7V6KllzTgvYYr9TtiGw, woHh4aBgAAqWskDRVbjSEGY1r572Yl3w, woHh4aBgAAXCAfIF-1xkZ__QfxFm-cOQ, woHh4aBgAAbiMe0cqf3HGyWU59TJdqhQ, woHh4aBgAAbbDM_ayg4IErKqbKQfWaXg, woHh4aBgAApw8e5TngY3qGLhdmpdgyoA, woHh4aBgAA8uyouVOgeA6cEiHlgHBekQ, woHh4aBgAAHSv6Sgm6J7tzTGlZFAsdyA, woHh4aBgAAbFJsNsWOrE9WDZWWgH92nQ, woHh4aBgAA6JuUUrX9M5ze1RiTZB6Lwg, woHh4aBgAAN3vJ9sZgthPGBuM4lifdCg, woHh4aBgAAlhIWYNlSX9GLcsu6Q06hgw, woHh4aBgAAqu0BiIX_cqkWDV0mqBIXtA, woHh4aBgAAMt6LFRe4Jzz_ApaPjOfDXA, woHh4aBgAAeOsUvthKJzZzU0Ae79iQUg, woHh4aBgAAyaJApDshsJEgomyhlP8YnA, woHh4aBgAA8FnfMoiQe3cAuTJLLspKnw, woHh4aBgAAF_EOHgSSyZtpm5F1ITayFw, woHh4aBgAAjHmCxPNxN0t21VplwJgH_A, woHh4aBgAAZw7oYb3gPPPkIAf7JtJxpg, woHh4aBgAAFtcdZphTSemBnsgmZRJPTQ, woHh4aBgAADWqvPci00bIKVLZm0HhxSg, woHh4aBgAA91cmKBkVsASwx62oZ0kBNw, woHh4aBgAAyxROE3278bRXjIKmCWv64g, woHh4aBgAAULlI-EODRx-if7sA6j9Gzw, woHh4aBgAAUMrcKzNjSX6_1AiI7UmnQQ, woHh4aBgAAGTuh6uu-J6UKhW2mSj4jiw, woHh4aBgAA4h9YVmxyXYxFWA6NDz-WYw, woHh4aBgAA1LmUCpfgiiYntkAsGveSYA, woHh4aBgAAFFuvWp__oQxtktluSLAvwA, woHh4aBgAA_PrJcIgLb4plfKRU26wzMQ, woHh4aBgAAcSfFWawZS-853MK5BIoHYA, woHh4aBgAAIk4i1dGDsnvFsKmcm1VvxA, woHh4aBgAArkuDU0caInV1zjjBmxnxvA, woHh4aBgAATq1iAUyc6jcqRnF2Kgiqew, woHh4aBgAALYZ_70ai0rpKmwocqHWtWw, woHh4aBgAArSIAmRLLZJw94nAXuzjFFg, woHh4aBgAA4B8BvDHFdhalU5dZtbuZpg, woHh4aBgAAxKLC8H-dN4foRHgYKeHUhQ, woHh4aBgAAOMG8uZnJ_cT0osD04wJLNw, woHh4aBgAAHEMmnlRHu14xikw6YO0AGw, woHh4aBgAAqHSvKvLeu0N-HrhQp5e02w, woHh4aBgAAUZ_55Rcz5XQKfKN9eGaB3w, woHh4aBgAAPQD1U4m_zpL6q0spSYytBQ, woHh4aBgAAtRLy8SgEjt6Nww4iSw_nTA, woHh4aBgAA42O95MjThIqRS3Bjda4v1Q, woHh4aBgAAeZM5GrSpuUsejSiEKeHBVg, woHh4aBgAAD5mupeQBomOaloZTPaXLxA, woHh4aBgAAkrPvXpV_tZ3AG8z1veynTA, woHh4aBgAAfwUfnJYXMIcKStGmz_Vnhw, woHh4aBgAAi5YoV2SlXWFbKGsbi8XMOg, woHh4aBgAAQ1bLHRduLPyWIDpCxY3KPQ, woHh4aBgAAFTlMOlSm-lU0VdiOtipsqw, woHh4aBgAABrbV_UD1PiPd55crgICiSg, woHh4aBgAAwjeT1NqozzbLlBprA9c_KA, woHh4aBgAA7vUMcCN6H_6ZOivJ5YVbvg, woHh4aBgAAPWEjThUaHJ7ejWMcT_rIvQ, woHh4aBgAAmefdct7I-BrhYC8OJ4dPCA, woHh4aBgAAWoZd50WNc0g1CfugV6lfYw, woHh4aBgAApLyZKX80SO7IqGybN9AXpw, woHh4aBgAA7xLK6nsGn4XvjRpVTKraKA, woHh4aBgAAdzYYKt0g4ZHqUFdlVDrg9w, woHh4aBgAABG-Wb---JYgYILzZ00vnLA, woHh4aBgAAHtLfOqtOa4n5gyijOUdCew, woHh4aBgAACoBWVCuYUo1t6hIBydw2_g, woHh4aBgAApj8rizYR4alR2qyfr4IBtQ, woHh4aBgAAfGIBliCT4HSPt87Ssz7YFg, woHh4aBgAAy6jfpwc-2P05_JwLB8WnJQ, woHh4aBgAA3q2GBDm2jyElaIaQTEsjug, woHh4aBgAAU0e-nR5cJrus_i1-KSzxZQ, woHh4aBgAAlzai8KqNsSQxIflY1ocn-A, woHh4aBgAAFYauUt3oUfN7xIm1MDoqeQ, woHh4aBgAAYktR4didCO14T4EPlaBXTg, woHh4aBgAAqlriF1Ia1gTaGaiBt8IPJg, woHh4aBgAAn32VMPzQoS78lrvYxNt8bA, woHh4aBgAAUOgsc7_r3_f4smMIQ25HMA, woHh4aBgAAtarS7NcAUYiIIwnjIOA9ig, woHh4aBgAAaD3ZhWwIdHYKpTvRol17yw, woHh4aBgAApUiHd82CyD_qha_mjOfITQ, woHh4aBgAAk_Hj6PAjvDzRYcRc-pQsRA, woHh4aBgAAhEH0x7h3oHlI-qDxK10ZLg, woHh4aBgAA-niJOciXW8EQTQJOkRR_ug, woHh4aBgAA6tO6-M5RuIjT775_FENvkw, woHh4aBgAAJLeAzukCzHPRCS5LApE9wg, woHh4aBgAAnHhOKCZ09XLwgyolMfFTWw, woHh4aBgAAfWhCtK6Yxjk8VIR4dzIjiA, woHh4aBgAASVPpukpwjGriOyCUd2q8ZQ, woHh4aBgAA2CQj4JVmYVtOy7G6ENJhag, woHh4aBgAAYXKg0OOYBmzO41cXbWDakw, woHh4aBgAABSgWFa3M7oct0HUsvG3-_A, woHh4aBgAACI7xwAGUYGMOn5sCi_C9ew, woHh4aBgAAj17KpB4c7fy8Itlrqra1rw, woHh4aBgAAQRhECAG_JyhyeEYWPflCmw, woHh4aBgAAvzq-2BXY4MGfVXoLKGuPuA, woHh4aBgAAF3uF8c8WtX50sS0UY-IDFA, woHh4aBgAA5Igqgbul5DiyI3y08XjQVQ, woHh4aBgAA8DP_6w7C_Z9fc0Ot1jo7Ow, woHh4aBgAABwOJwG__rF6g_Y-g6r2b2g, woHh4aBgAAon7EtcL8HQ58TT5Y7KXVQw, woHh4aBgAAMDg-ihOMivr5VZAj5ZKtJw, woHh4aBgAAgBZL0U0BU6OYtPd1ywmQ5w, woHh4aBgAA4wwVPRGL4EmbVHKnzmGXuQ, woHh4aBgAAbcvPw4KfWOgduvhjy2UVYg, woHh4aBgAAl37uArsrnISVcNOrTh_JtQ, woHh4aBgAAIfacbNm6CqfJr5S_t5vg8A, woHh4aBgAAERwYj7NrfQrfArKrZ5-gyg, woHh4aBgAAT8ukd7MAuOYR-Qg1ZeLbhw, woHh4aBgAAfFYfWZpVOJCi1jbY6nczyg, woHh4aBgAAtcGnFGle7tvkobd12UUDTQ, woHh4aBgAAmKv49LQffkkZ9m6NfrI12Q, woHh4aBgAAqiGVnmEjH3VBkmp3lICiTA, woHh4aBgAAqRNwr6LSgRods5ZpNr_R8w, woHh4aBgAAqroXe42njWHeURxhBkWdig, woHh4aBgAAFOk7bdxcpvRCm2pbIEW01Q, woHh4aBgAAksrQ3Fqd3kilpNIN7nFi4A, woHh4aBgAA_3dwIN0RCxNiXoQDlxmw_g, woHh4aBgAA7uTVkxbq5AAgM2vu169PqQ, woHh4aBgAAouHe93b2YkblsoCMbvnPVg, woHh4aBgAAAt1hixmaTM5L0XtcJZ2UNA, woHh4aBgAA0_y3TWB9_PMv-FyILjtHlw, woHh4aBgAAajwuUolKzO4EsKf-18q0FA, woHh4aBgAARxjFVsudhXGuxSU6viPENA, woHh4aBgAAhzgt_sKOf647eJJCD7YnGw, woHh4aBgAAvtK49dvXMISy9SN-e1k87Q, woHh4aBgAAO7PJ0LCn893xX2pNy79Uyw, woHh4aBgAA6ZAhRYflcQ0rSXqM3XGTnA, woHh4aBgAACMIq-fz_0UAY7l49uk1GQw, woHh4aBgAAV9s_DK5YTSBVWlQjYfrzPQ, woHh4aBgAAgb0lxpsadgwxp-kkWYXJQw, woHh4aBgAA0ddBupvtNfPRrYBtsyLUgg, woHh4aBgAAUOM18SiKMNbWD4YDY5iwtA, woHh4aBgAApCgHMPIIt9rJzX0wfvWd5g, woHh4aBgAA4zntqMqws2wmN9IrgLCZxA, woHh4aBgAAKd-3e-0if1_pmJ204wOLHg, woHh4aBgAAIId98-5bWMki3VOlkUgqKQ, woHh4aBgAAWqOO7mdSuTlO1yBzwPSuTA, woHh4aBgAAeCWMuk_u0-Eey-Wu5DnKKw, woHh4aBgAA_JaVsYMM2cYhg2Idh50wHA, woHh4aBgAA_mf-K1aAZtM2MwvKKdbBZA, woHh4aBgAA2-WOJ3_mBXNbAsTfbs7hqw, woHh4aBgAAnoJPhe19D5xou0NjxQqUyQ, woHh4aBgAAMN5OgUB-uwGavX2MRzYlKQ, woHh4aBgAA1ukWIr9sA0n1VTmCVQM2OQ, woHh4aBgAAKEZ2SY6a1p4h_yTFjj6PDA, woHh4aBgAABjvENUIubGaQ59EExYjYog, woHh4aBgAAoTyqB-aazYlvyxIGKl3yUw, woHh4aBgAAhAfpfz37PqXWrpInhtg47Q, woHh4aBgAAUREtfqmfZaYFSiHvViOQOw, woHh4aBgAAErOH1owS2Pqk7pwFF-kNmg, woHh4aBgAADdDMA4WD5l220zWf2XVuuQ, woHh4aBgAAjCVmGPYI4j_9vbIf3CgOOg, woHh4aBgAAGGPoBHfd2_CrnX4_XrxG6g, woHh4aBgAA1FKb-4Y10YNlLOvrQgsG1g, woHh4aBgAAUarHt-L2W_TGcTOMb8Wr5g, woHh4aBgAAb17w71Vo6_ocp7tksibhDA, woHh4aBgAAnsL5oS8P4mFQPanSYtVVBQ, woHh4aBgAAic01Z4xrVulTF5PIqpZJtw, woHh4aBgAAQuKNLd9ZIJk5RUuiXu86Ww";

        // 去除字符串中的空格
        String cleanedInput = input.replaceAll("\\s+", "");

        // 将字符串拆分为数组
        String[] array = cleanedInput.split(",");

        // 将数组转换为 List
        List<String> list = Arrays.asList(array);

        System.out.println("list = " + list);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("'");
            sb.append(list.get(i));
            sb.append("',");
            sb.append("\n");
        }
        System.out.println("sb = " + sb);
    }

    private static String toStr(String encodedString){
        String decodedString = "";
        Matcher matcher = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(encodedString);
        while (matcher.find()) {
            char c = (char) Integer.parseInt(matcher.group(1), 16);
            decodedString += encodedString.substring(0, matcher.start()) + c;
            encodedString = encodedString.substring(matcher.end());
            matcher.reset(encodedString);
        }
        decodedString += encodedString;
        return decodedString;
    }

    public static String listToStr(List<String> list) {
        if (list != null && list.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : list) {
                if (str == null){
                    continue;
                }
                stringBuilder.append(str);
                stringBuilder.append(",");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return "";
    }

    public static String truncateString(String input, int maxLength) {
        // 计算实际长度
        int length = Math.min(input.length(), maxLength);
        // 截取字符串
        return input.substring(0, length);
    }

    public static String listToString(List<String> list) {
        return String.join(",", list);
    }


    private static boolean checkVoiceFile(String filename){
//        if (StringUtils.isBlank(filename)){
//            return false;
//        }
//        if (!filename.contains(".")){
//            return false;
//        }
//
//        String endName = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
//        System.out.println("endName = " + endName);
//        if (StringUtils.isBlank(endName)){
//            return false;
//        }
        if (StringUtils.endsWithIgnoreCase(filename,".mp3") || StringUtils.endsWithIgnoreCase(filename,".wav")){
            System.out.println("filename = " + filename);
        }

        return true;
    }

    private static void allocAvg(){
        List<Object> redisAllocTargetIds = Lists.newArrayList();
        List<Long> ids = Lists.newArrayList(1L,1L,3L,4L,5L);
        List<Integer> ratio = Lists.newArrayList(1,2);
        if (ratio.size() < ids.size()){
            int diffLength = ids.size() - ratio.size();
            for (int i = 0; i < diffLength; i++) {
                ratio.add(0);
            }
        }
//        System.out.println(ratio);
        for (int i = 0; i < ids.size(); i++) {
            for (int j = 0; j < ratio.get(i); j++) {
                redisAllocTargetIds.add(ids.get(i));
            }
        }
        System.out.println(redisAllocTargetIds);
    }

    private static void alloc(){
        System.out.println("alloc...");
        List<Object> redisAllocTargetIds = Lists.newArrayList();
        List<Long> ids = Lists.newArrayList(1L,1L,3L,4L,5L);
        List<Integer> userIds = Lists.newArrayList(1,2);
        for (int i = 0; i < ids.size(); i++) {
            Long bindingId = ids.get(i);
            Integer targetId = userIds.get(i % userIds.size());
            System.out.println("bindingId = " + bindingId + ", targetId = " + targetId + ", index = " + i % userIds.size());
        }
    }


    private static void spiltTest(){
        List<Integer> mList = Lists.newArrayList();
        mList.add(1);
        mList.add(2);
        mList.add(3);

        mList.add(4);
        mList.add(5);
        mList.add(6);

        mList.add(7);
        mList.add(8);
        mList.add(9);

        int size = mList.size();
        int spiltCount = 0;
        if (size % 3 == 0){
            spiltCount = mList.size() / 3;
        }else {
            spiltCount = mList.size() / 3 + 1;
        }


        List<Integer> aList = new ArrayList<>();
        List<Integer> bList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (i < 3){
                aList.add(mList.get(i));
            }else {
                bList.add(mList.get(i));
            }
        }

        List<List<Integer>> sList = Lists.partition(mList,3);
        System.out.println("sList = " + sList);
    }


    private void handleTagException(Exception e) {
        throw new NullPointerException("error");
    }
}
