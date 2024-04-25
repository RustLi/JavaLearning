package org.lwl.test;

import java.security.MessageDigest;

public final class Md5Util {
	private Md5Util() {}

	private static final String[] CHARS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
			"f" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (byte value : b) {
			sb.append(byteToHexString(value));
		}
		return sb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return CHARS[d1] + CHARS[d2];
	}

	public static String md5(String origin) {
		String rs = origin;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			rs = byteArrayToHexString(md.digest(rs.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}
}
