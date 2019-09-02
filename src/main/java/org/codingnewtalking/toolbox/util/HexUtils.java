package org.codingnewtalking.toolbox.util;

/**
 * @author lixinjie
 * @since 2019-08-31
 */
public class HexUtils {
	
	public static final String HEX_PREFIX = "0x";

	public static String toHexString(int value) {
		return toHexString(value, false);
	}
	
	public static String toHexString(long value) {
		return toHexString(value, false);
	}
	
	public static String toHexString(int value, boolean upperCase) {
		String hex = Integer.toHexString(value);
		hex = alignHex(hex);
		hex = upperCaseIfNeed(hex, upperCase);
		return HEX_PREFIX + hex;
	}
	
	public static String toHexString(long value, boolean upperCase) {
		String hex = Long.toHexString(value);
		hex = alignHex(hex);
		hex = upperCaseIfNeed(hex, upperCase);
		return HEX_PREFIX + hex;
	}
	
	private static String alignHex(String hex) {
		int length = hex.length();
		if (length == 4 || length == 8 || length == 12 || length == 16) {
			return hex;
		}
		if (length < 4) {
			return paddingZero(hex, 4 - length);
		}
		if (length < 8) {
			return paddingZero(hex, 8 - length);
		}
		if (length < 12) {
			return paddingZero(hex, 12 - length);
		}
		if (length < 16) {
			return paddingZero(hex, 16 - length);
		}
		return hex;
	}
	
	private static String paddingZero(String hex, int count) {
		if (count == 1) {
			return "0" + hex;
		}
		if (count == 2) {
			return "00" + hex;
		}
		if (count == 3) {
			return "000" + hex;
		}
		return hex;
	}
	
	private static String upperCaseIfNeed(String hex, boolean upperCase) {
		return upperCase ? hex.toUpperCase() : hex;
	}
}
