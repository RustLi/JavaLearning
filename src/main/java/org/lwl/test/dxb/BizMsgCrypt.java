package org.lwl.test.dxb;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * 提供接收和推送加解密接口(UTF8编码的字符串).
 * 
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * <li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html</li>
 * <li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * <li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * <li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
@SuppressWarnings("java:S3329")
public class BizMsgCrypt {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final Random RANDOM = new SecureRandom();
    /**
     * 明文消息的随机头部字节数
     */
    private static final int HEADER_SIZE = 16;

    /**
     * 采用 PKCS#7 Padding，填充明文消息对齐到 16 字节
     */
    private static final int PKCS7_PADDING_SIZE = 16;

    /**
     * 十六进制数
     */
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    private final String receiveId;
    private final String token;
    private final byte[] aesKey;

    /**
     * 构造函数
     * 
     * @param token 开发者设置的token
     * @param encodingAesKey 开发者设置的EncodingAESKey
     * @param receiveId, 开发者appId
     * 
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public BizMsgCrypt(String token, String encodingAesKey, String receiveId) throws AesException {
        if (encodingAesKey.length() != 43) {
            throw new AesException(AesException.ILLEGAL_AES_KEY);
        }

        this.token = token;
        this.receiveId = receiveId;
        this.aesKey = Base64.getDecoder().decode(encodingAesKey + "=");
    }

    /**
     * 生成4个字节的网络字节序（即大端字节序）
     * 
     * @param digit
     * @return
     */
    public static byte[] writeInt32(int digit) {
        byte[] b = new byte[4];
        b[0] = (byte) (digit >> 24 & 0xFF);
        b[1] = (byte) (digit >> 16 & 0xFF);
        b[2] = (byte) (digit >> 8  & 0xFF);
        b[3] = (byte) (digit       & 0xFF);
        return b;
    }

    /**
     * 还原4个字节的网络字节序（即大端字节序）
     * 
     * @param b
     * @return
     */
    public static int readInt32(byte[] b) {
        return (((b[0]       ) << 24) |
                ((b[1] & 0xff) << 16) |
                ((b[2] & 0xff) <<  8) |
                (b[3] & 0xff));
    }

    // 随机生成16位字符串
    public static byte[] getRandomHeader(int len) {
        byte[] rand = new byte[len];
        for (int i = 0; i < len; i++) {
            rand[i] = (byte) Math.floor(Math.random() * 256);
        }
        return rand;
    }

    // 随机生成16位字符串
    public static String getNonce() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int number = RANDOM.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 对明文进行加密.
     * 
     * @param msg 需要加密的明文
     * 
     * @return 加密后base64编码的字符串
     * @throws AesException aes加密失败
     */
    public String encrypt(String msg) throws AesException {
        byte[] header = getRandomHeader(HEADER_SIZE);
        byte[] msgBytes = msg.getBytes(UTF_8);
        byte[] msgLength = writeInt32(msgBytes.length);
        byte[] receiveidBytes = receiveId.getBytes(UTF_8);

        // 生成填充数据 PKCS#7Padding 填充数据
        int len = HEADER_SIZE + 4 + msgBytes.length + receiveidBytes.length;
        byte[] padding = getPKCS7Padding(len);

        /*
         * 获得最终的字节流, 未加密
         */
        byte[] original = new byte[len + padding.length];

        // header + msgLen + msg + receiveid + PKCS#7Padding
        System.arraycopy(header, 0, original, 0, header.length);
        System.arraycopy(msgLength, 0, original, 16, msgLength.length);
        System.arraycopy(msgBytes, 0, original, 20, msgBytes.length);
        System.arraycopy(receiveidBytes, 0, original, 20 + msgBytes.length, receiveidBytes.length);
        System.arraycopy(padding, 0, original, 20 + msgBytes.length + receiveidBytes.length, padding.length);

        /*
         * AES加密
         */
        byte[] encrypted;
        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            encrypted = cipher.doFinal(original);
        } catch (Exception e) {
            throw new AesException(AesException.ENCRYPT_AES_ERROR);
        }

        /*
         * 使用BASE64对加密后的消息进行编码
         */
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 对密文进行解密.
     * 
     * @param text 需要解密的密文
     * @return 解密得到的明文
     * @throws AesException aes解密失败
     */
    public String decrypt(String text) throws AesException {
        /*
         * 使用BASE64对密文进行解码
         */
        byte[] encrypted = Base64.getDecoder().decode(text);

        /*
         * 使用AES解密数据
         */
        byte[] decrypted;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            // 解密
            decrypted = cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new AesException(AesException.DECRYPT_AES_ERROR);
        }

        /*
         * 数据解码
         */
        String msg;
        String fromReceiveId;
        try {
            // 删除PKCS7Padding填充字符
            byte[] original = delPKCS7Padding(decrypted);

            // 读取msg长度 (4bytes BigEndian)
            byte[] msgLength = Arrays.copyOfRange(original, 16, 20);
            int msgLen = readInt32(msgLength);

            // 读取msg数据
            byte[] msgBytes = Arrays.copyOfRange(original, 20, 20 + msgLen);
            msg = new String(msgBytes, UTF_8);

            // 读取receiveid数据
            byte[] receiveidBytes = Arrays.copyOfRange(original, 20 + msgLen, original.length);
            fromReceiveId = new String(receiveidBytes, UTF_8);
        } catch (Exception e) {
            throw new AesException(AesException.ILLEGAL_BUFFER);
        }

        // receiveid不相同的情况
        if (!fromReceiveId.equals(receiveId)) {
            throw new AesException(AesException.VALIDATE_APPID_ERROR);
        }
        return msg;
    }

    /**
     * Java原始加密库不支持PKCS#7Padding，因此要自己实现。
     * 
     * 对明文数据采用 PKCS#7Padding 填充
     * 
     * @param len 需要进行填充补位操作的明文字节个数
     * @return 补齐用的字节数组
     */
    public static byte[] getPKCS7Padding(int len) {
        // 计算需要填充的位数
        int paddingSize = PKCS7_PADDING_SIZE - (len % PKCS7_PADDING_SIZE);

        // 生成填充数据
        byte[] padding = new byte[paddingSize];
        Arrays.fill(padding, (byte) paddingSize);
        return padding;
    }

    /**
     * 删除 PKCS#7Padding 补位字符
     * 
     * @param decrypted 解密后的明文
     * @return 删除补位字符后的明文
     */
    public static byte[] delPKCS7Padding(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > PKCS7_PADDING_SIZE) {
            // 没有填充
            return decrypted;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    /**
     * 将消息加密打包.
     * 
     * <ol>
     * <li>对要发送的消息进行AES-CBC加密</li>
     * <li>生成安全签名</li>
     * <li>将消息密文和安全签名打包</li>
     * </ol>
     * 
     * @param original 明文消息字符串
     * @param timestamp 时间戳
     * @param nonce 随机串
     * 
     * @return 加密后的可以直接回复用户的密文包。
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public EncryptMsg encryptMsg(String original, Long timestamp, String nonce) throws AesException {
        // 加密
        String encrypt = encrypt(original);

        // 生成安全签名
        String signature = sha1(token, String.valueOf(timestamp), nonce, encrypt);

        // 生成加密消息
        EncryptMsg msg = new EncryptMsg();
        msg.setEncrypt(encrypt);
        msg.setNonce(nonce);
        msg.setSignature(signature);
        msg.setTimestamp(timestamp);

        return msg;
    }

    /**
     * 检验消息的真实性，并且获取解密后的明文.
     * <ol>
     * <li>利用收到的密文生成安全签名，进行签名验证</li>
     * <li>若验证通过，则提取其中的加密消息</li>
     * <li>对消息进行解密</li>
     * </ol>
     * 
     * @param signature 签名串，对应URL参数的signature
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param postData 密文，对应POST请求的数据
     * 
     * @return 解密后的原文
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public String decryptMsg(String signature, Long timestamp, String nonce, String postData) throws AesException {

        // 提取密文
        EncryptMsg msg;
        try {
            Gson gson = new Gson();
            msg = gson.fromJson(postData, EncryptMsg.class);
        } catch (JsonSyntaxException e) {
            throw new AesException(AesException.PARSE_JSON_ERROR);
        }

        // 验证安全签名
        String sign = sha1(token, String.valueOf(timestamp), nonce, msg.getEncrypt());

        // 和URL中的签名比较是否相等
        if (!sign.equals(signature)) {
            throw new AesException(AesException.VALIDATE_SIGNATURE_ERROR);
        }

        // 解密
        return decrypt(msg.getEncrypt());
    }

    /**
     * 验证URL
     * 
     * @param signature 签名串，对应URL参数的signature
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param echoStr 随机串，对应URL参数的echostr
     * 
     * @return 解密之后的echostr
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public String verifyURL(String signature, Long timestamp, String nonce, String echoStr) throws AesException {
        String sign = sha1(token, String.valueOf(timestamp), nonce, echoStr);

        if (!sign.equals(signature)) {
            throw new AesException(AesException.VALIDATE_SIGNATURE_ERROR);
        }

        return decrypt(echoStr);
    }


    /**
     * 用SHA1算法生成安全签名
     * 
     * @param token 票据
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param encrypt 密文
     * @return 安全签名
     * @throws AesException
     */
    public static String sha1(String token, String timestamp, String nonce, String encrypt) throws AesException {
        String[] array = new String[] { token, timestamp, nonce, encrypt };
        StringBuilder sb = new StringBuilder();
        // 字符串排序
        Arrays.sort(array);
        for (int i = 0; i < 4; i++) {
            sb.append(array[i]);
        }
        String str = sb.toString();

        return sha1(str);
    }

    /**
     * 生成SHA1签名
     * 
     * @param str
     * @return
     * @throws AesException
     */
    public static String sha1(String str) throws AesException {
        byte[] digest;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            digest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new AesException(AesException.COMPUTE_SIGNATURE_ERROR);
        }

        // 把密文转换成十六进制的字符串形式
        return toHexStr(digest);
    }

    // 把密文转换成十六进制的字符串形式
    public static String toHexStr(byte[] digest) {
        char[] value = new char[digest.length * 2];
        for (int i = 0, idx = 0; i < digest.length; i++, idx += 2) {
            value[idx] = (HEX_DIGITS[(digest[i] >> 4) & 0x0F]); // 前四位
            value[idx + 1] = (HEX_DIGITS[digest[i] & 0x0F]); // 后四位
        }
        return new String(value);
    }
    
}