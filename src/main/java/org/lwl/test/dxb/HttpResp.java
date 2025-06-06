package org.lwl.test.dxb;

import java.nio.charset.StandardCharsets;

/**
 * Http响应
 * 
 * @title HttpResp
 * @author yanmaoyuan
 * @date 2019年10月30日
 * @version 1.0
 */
public class HttpResp {
    public int code; // 响应码

    public byte[] body; // 响应结果

    public int length;// 消息长度

    public String content = "";// 字符串

    public HttpResp(int code, byte[] body) {
        this.code = code;
        if (body != null) {
            this.body = body;
            this.content = new String(body, StandardCharsets.UTF_8);
            length = body.length;
        }
    }
}
