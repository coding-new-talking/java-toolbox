package org.codingnewtalking.toolbox.util;

/**
 * @author lixinjie
 * @since 2019-09-01
 */
public class StringUtils {

	public static String subString(String str, int begin, int end) {
		int length = str.length();
		return str.substring(transformIndex(length, begin), transformIndex(length, end));
	}

	public static String headString(String str, int end) {
		return subString(str, 0, end);
	}
	
	public static String tailString(String str, int begin) {
		return subString(str, begin, str.length());
	}
	
	public static String replaceChar(String str, char oldChar, char newChar) {
		return str.replace(oldChar, newChar);
	}
	
	public static String replaceString(String str, String oldStr, String newStr) {
		return str.replace(oldStr, newStr);
	}
	
	private static int transformIndex(int length, int index) {
		return index < 0 ? (index + length) : index;
	}
}
