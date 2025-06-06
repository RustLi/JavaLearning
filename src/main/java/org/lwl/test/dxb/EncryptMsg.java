package org.lwl.test.dxb;

import lombok.Data;

/**
 * @title EncryptMsg
 * @author yanmaoyuan
 * @date 2021年8月13日
 * @version 1.0
 */
@Data
public class EncryptMsg {

    /**
     * 加密签名，msgSignature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体。
     */
    private String signature;

    /**
     * 时间戳。与nonce结合使用，用于防止请求重放攻击。
     */
    private Long timestamp;

    /**
     * 随机数。与timestamp结合使用，用于防止请求重放攻击。
     */
    private String nonce;

    /**
     * 消息结构体加密后的字符串
     */
    private String encrypt;
}
