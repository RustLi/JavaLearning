package org.lwl.test;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Security;

/**
 * 手机号加密器
 *
 * <p>提供手机号加密、解密功能。</p>
 */
public final class MobileCipher {

    private final Key key;

    private static final String SM4_ALGORITHM = "SM4";
    private static final String SM4_MODE = "SM4/ECB/PKCS5Padding";
    private static final int SM4_KEY_LENGTH = 16; // 128-bit

    private static final String secretKey = "xD4hM4iB1oQ3jG2c";// 手机号加密密钥

    private static final MobileCipher mobileCipher = new MobileCipher(secretKey);

    /**
     * @param secretKey 密钥，16位字符串，对应国密(SM4)128位秘钥
     */
    public MobileCipher(String secretKey) {
        if (secretKey == null || secretKey.length() != SM4_KEY_LENGTH) {
            throw new IllegalArgumentException("密钥必须为16位长度");
        }
        try {
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            // 预热Cipher实例
            Cipher.getInstance(SM4_MODE, "BC");

            this.key = new SecretKeySpec(secretKey.getBytes(), SM4_ALGORITHM);
        } catch (Exception e) {
            throw new UnsupportedOperationException("初始化手机号加密算法失败");
        }

    }

    /**
     * 手机号加密（SM4 + Base64）
     * @param mobile 手机号明文
     * @return 加密后的手机号
     */
    public String encryptMobile(String mobile) throws GeneralSecurityException {
        Preconditions.checkArgument(StringUtils.isNotBlank(mobile), "手机号为空");
        byte[] encrypted = sm4Encrypt(mobile.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(encrypted);
    }

    /**
     * 手机号解密（Base64 + SM4）
     * @param encryptMobile 手机号密文
     * @return 解密后的手机号
     */
    public String decryptMobile(String encryptMobile) throws GeneralSecurityException {
        Preconditions.checkArgument(StringUtils.isNotBlank(encryptMobile), "手机号为空");
        byte[] decoded = Base64.decodeBase64(encryptMobile);
        return new String(sm4Decrypt(decoded), StandardCharsets.UTF_8);
    }

    private byte[] sm4Encrypt(byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(SM4_MODE, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(input);
    }

    private byte[] sm4Decrypt(byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(SM4_MODE, "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(input);
    }

    public static void main(String[] args) {
        try {
            String encryptMobile = "p0PMlQ6oWSgsh3RnANwgOQ==";
            System.out.println("解密后 = " + mobileCipher.decryptMobile(encryptMobile));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        String secret =  "~Aq%8Wu3+2aUetxvZi#n`b{yd0TSo4N[";
//        String privateSalt =  "eshop";
//        int expireInDays = 30;
//        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("secret", privateSalt, expireInDays);
//        String encryptPassword = jwtTokenUtil.encryptPassword("tsyjs2DxL9", "768025");
//        System.out.println(encryptPassword);
//    }
}
