package org.lwl.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sunwen
 */
public final class MobileUtils {
    private MobileUtils() {
    }

    private static final int MOBILE_LENGTH = 11;

    public static boolean isPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }

        String regex = "^1[0-9]{10}$";
        if (phone.length() != MOBILE_LENGTH) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    /**
     * 处理手机号
     *
     * @param mobiles
     * @return
     */
    public static void encryptMobile(List<String> mobiles) {
        if (CollectionUtils.isEmpty(mobiles)) {
            return;
        }

        mobiles.replaceAll(MobileUtils::encryptMobile);
    }

    /**
     * 处理手机号
     *
     * @param mobile
     */
    public static String encryptMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return mobile;
        }
        if (mobile.length() < 4) {
            return mobile;
        }
        char[] chars = mobile.toCharArray();
        for (int j = 0; j < 4; j++) {
            if (chars.length > j + 3) {
                chars[j + 3] = '*';
            }
        }
        mobile = new String(chars);
        return mobile;
    }
}
