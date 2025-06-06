package org.lwl.test.dxb;

import lombok.extern.slf4j.Slf4j;
import org.lwl.utils.JacksonUtil;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * @title CallbackClient
 * @author yanmaoyuan
 * @date 2019年10月30日
 * @version 1.0
 */
@Slf4j
public class CallbackClient {

    /**
     * 超时时间(毫秒)
     */
    private final int timeout;

    /**
     * 断线重试次数(0表示不重试)
     */
    private final int retries;

    public CallbackClient() {
        this(5000, 0);
    }

    public CallbackClient(int timeout, int retries) {
        if (timeout < 100) {
            timeout = 100;
        }

        if (retries < 0) {
            retries = 0;
        }

        this.timeout = timeout;
        this.retries = retries;
    }

    /**
     * 构造Http请求头
     * 
     * @param conn
     */
    public static void setHeader(HttpURLConnection conn) {
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        conn.setRequestProperty("Cache-Control", "no-cache");
    }

    /**
     * 构造HTTP请求
     * 
     * @param method 请求方法 GET/POST
     * @param urlStr 请求URL
     * @return 返回HttpURLConnection
     * @throws IOException
     */
    public HttpURLConnection getConn(String method, String urlStr) throws IOException {
        log.debug("{} : {}", method, urlStr);
        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);// 请求方法 GET/POST
        conn.setConnectTimeout(timeout);// 链接超时
        conn.setReadTimeout(timeout);// 读取超时
        conn.setDoInput(true);// 需要获得输入

        setHeader(conn);

        return conn;
    }

    /**
     * 解析响应结果
     * 
     * @param conn
     * @return
     * @throws IOException
     */
    public static HttpResp getResp(HttpURLConnection conn) throws IOException {
        // get response code
        int code = conn.getResponseCode();
        byte[] body = null;

        HttpStatus status = HttpStatus.valueOf(code);
        if (code == 500 || code == 501 || code == 502) {
            return new HttpResp(code, status.getReasonPhrase().getBytes());
        }

        String contentEncoding = conn.getHeaderField("Content-Encoding");

        if (code == 301 || code == 302) {
            String redirect = conn.getHeaderField("Location");
            body = redirect.getBytes();
        } else if (code == 200 || code == 201) {
            // 支持gzip压缩编码
            if (contentEncoding != null && contentEncoding.contains("gzip")) {
                body = readBytes(new GZIPInputStream(conn.getInputStream()));
            } else {
                body = readBytes(conn.getInputStream());
            }
        } else {
            byte[] error = readBytes(conn.getErrorStream());
            log.error("请求失败, url={}, error:{}", conn.getURL(), new String(error, StandardCharsets.UTF_8));
            body = status.getReasonPhrase().getBytes();
        }

        return new HttpResp(code, body);
    }

    public static byte[] readBytes(InputStream inputStream) throws IOException {
        // Read input
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(inputStream);

        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }

        // Get response
        return out.toByteArray();
    }

    /**
     * 发送GET请求，返回对象
     * 
     * @param urlStr
     * @param clazz
     * @return
     */
    public <T> T getEntity(String urlStr, Class<T> clazz) {
        HttpResp resp = get(urlStr);

        if (resp != null) {
            if (resp.code == 200 || resp.code == 201) {
                try {
                    return JacksonUtil.str2Obj(resp.content, clazz);
                } catch (IOException e) {
                    log.error("解析json失败, content={}", resp.content, e);
                }
            } else {
                log.warn("Failed, code={}, content={}", resp.code, resp.content);
                return null;
            }
        }

        return null;
    }

    public static URI appendUri(URI uri, String appendQuery) throws URISyntaxException {
        String newQuery = uri.getQuery();
        if (newQuery == null) {
            newQuery = appendQuery;
        } else {
            newQuery += "&" + appendQuery;
        }
        return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), newQuery, uri.getFragment());
    }

    /**
     * 发送GET请求，获得数据。
     * 
     * @param urlStr
     * @return
     */
    public HttpResp get(String urlStr) {
        HttpURLConnection conn = null;
        HttpResp resp = null;

        int tries = 0;
        boolean done = false;
        while (!done && tries <= retries) {
            if (tries > 0) {
                log.warn("retries:{}/{}, url={}", tries, retries, urlStr);
            }
            tries++;

            try {
                conn = getConn("GET", urlStr);
                conn.connect();

                resp = getResp(conn);
                log.info("resp, code:{}, content:{}", resp.code, resp.content);
                done = true;
            } catch (ConnectException e) {
                log.error("Connection failed, url={}", urlStr);
                resp = new HttpResp(HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase().getBytes());
            } catch (SocketTimeoutException e) {
                log.error("Timeout url={}", urlStr);
                resp = new HttpResp(HttpStatus.REQUEST_TIMEOUT.value(),
                    HttpStatus.REQUEST_TIMEOUT.getReasonPhrase().getBytes());
            } catch (IOException e) {
                log.error("GET failed url={}", urlStr);
                resp = new HttpResp(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().getBytes());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return resp;
    }

    /**
     * 发送post表单请求
     * 
     * @param urlStr
     * @param data
     * @return
     */
    public HttpResp postForm(String urlStr, Map<String, String> data) {
        HttpURLConnection conn = null;
        HttpResp resp = null;

        int tries = 0;
        boolean done = false;
        while (!done && tries <= retries) {
            if (tries > 0) {
                log.warn("retries:{}/{}, url={}", tries, retries, urlStr);
            }
            tries++;

            try {
                conn = getConn("POST", urlStr);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // post data
                String content = urlencodedFormData(data);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();

                resp = getResp(conn);
                log.info("resp, code:{}, content:{}", resp.code, resp.content);
                done = true;
            } catch (ConnectException e) {
                log.error("Connection failed, url={}", urlStr);
                resp = new HttpResp(HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase().getBytes());
            } catch (SocketTimeoutException e) {
                log.error("Timeout url={}", urlStr);
                resp = new HttpResp(HttpStatus.REQUEST_TIMEOUT.value(),
                    HttpStatus.REQUEST_TIMEOUT.getReasonPhrase().getBytes());
            } catch (IOException e) {
                log.error("POST failed url={}", urlStr);
                resp = new HttpResp(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().getBytes());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return resp;
    }

    /**
     * 构造form表单数据
     * 
     * @param data
     * @return
     */
    public static String urlencodedFormData(Map<String, String> data) {
        StringBuilder builder = new StringBuilder();
        boolean empty = true;
        if (!data.isEmpty()) {
            Set<Entry<String, String>> entries = data.entrySet();
            for (Entry<String, String> e : entries) {
                if (!empty) {
                    builder.append('&');
                }

                // encode
                String val;
                try {
                    val = URLEncoder.encode(e.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    val = e.getValue();
                }

                builder.append(e.getKey()).append('=').append(val);
                empty = false;
            }
        }

        if (!empty) {
            return builder.toString();
        }

        return "";
    }

    /**
     * 发送post表单请求
     * 
     * @param urlStr
     * @param data
     * @return
     */
    public HttpResp postJson(String urlStr, Object data) {
        HttpURLConnection conn = null;
        HttpResp resp = null;

        int tries = 0;
        boolean done = false;
        while (!done && tries <= retries) {
            if (tries > 0) {
                log.warn("retries:{}/{}, url={}", tries, retries, urlStr);
            }
            tries++;

            try {
                conn = getConn("POST", urlStr);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                // post data
                String content = JacksonUtil.obj2Str(data);
                log.info("postJson content:{}", content);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();

                resp = getResp(conn);
                log.info("resp, code:{}, content:{}", resp.code, resp.content);
                done = true;
            } catch (ConnectException e) {
                log.error("Connection failed, url={}", urlStr);
                resp = new HttpResp(HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase().getBytes());
            } catch (SocketTimeoutException e) {
                log.error("Timeout url={}", urlStr);
                resp = new HttpResp(HttpStatus.REQUEST_TIMEOUT.value(),
                    HttpStatus.REQUEST_TIMEOUT.getReasonPhrase().getBytes());
            } catch (IOException e) {
                log.error("POST failed url={}", urlStr);
                resp = new HttpResp(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().getBytes());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return resp;
    }
}
