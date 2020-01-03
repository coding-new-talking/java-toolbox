package org.codingnewtalking.toolbox.expression;

/**
 * <p>token读取器
 * @author lixinjie
 * @since 2019-08-13
 */
public class TokenReader {

	public static final char BLANK = ' ';
	public static final char END = '\0';
	
	private boolean reverse;
	private CharReader charReader;
	private char[] charBoundaries;
	private String[] strBoundaries;
	private StringBuilder buffer;
	private StringBuilder builder;
	
	private TokenReader(String source, boolean reverse) {
		this.reverse = reverse;
		this.charReader = new CharReader(source, reverse);
		this.buffer = new StringBuilder();
	}
	
	public TokenReader(String source, char... boundaries) {
		this(source, false, boundaries);
	}
	
	public TokenReader(String source, boolean reverse, char... boundaries) {
		this(source, reverse);
		this.charBoundaries = boundaries;
	}
	
	public TokenReader(String source, String... boundaries) {
		this(source, false, boundaries);
	}
	
	public TokenReader(String source, boolean reverse, String... boundaries) {
		this(source, reverse);
		this.strBoundaries = boundaries;
		this.builder = new StringBuilder();
	}
	
	/**读取一个token，到达结尾则返回null*/
	public String read() {
		if (charBoundaries != null) {
			return read(charBoundaries);
		}
		if (strBoundaries != null) {
			return read(strBoundaries);
		}
		return null;
	}
	
	private String read(char[] charBoundaries) {
		char ch;
		buffer.setLength(0);
		while (true) {
			ch = readChar();
			if (isBoundary(ch, charBoundaries)) {
				if (isBlank(ch)) {
					skipRemaindBlank();
					if (buffer.length() > 0) {
						break;
					}
				} else {
					if (buffer.length() == 0) {
						buffer.append(ch);
					} else {
						walkBack();
					}
					break;
				}
			} else if (isEnd(ch)) {
				break;
			} else {
				buffer.append(ch);
			}
		}
		if (buffer.length() > 0) {
			return reverse ? reverse(buffer.toString()) : buffer.toString();
		}
		return null;
	}
	
	private String read(String[] strBoundaries) {
		String str;
		buffer.setLength(0);
		builder.setLength(0);
		boolean maybe = false;
		while (true) {
			builder.append(readChar());
			str = builder.toString();
			if (maybeBoundary(str, strBoundaries)) {
				maybe = true;
			} else if (maybe && isBoundary(maxMaybe(str), strBoundaries)) {
				if (isEnd(lastOne(str))) {
					if (buffer.length() == 0) {
						buffer.append(maxMaybe(str));
					} else {
						walkBack(str.length() - 1);
					}
					break;
				} else if (isBlank(maxMaybe(str))) {
					if (isBlank(lastOne(str))) {
						skipRemaindBlank();
					} else {
						walkBack();
					}
					builder.setLength(0);
					if (buffer.length() > 0) {
						break;
					}
				} else {
					if (buffer.length() == 0) {
						buffer.append(maxMaybe(str));
						walkBack();
					} else {
						walkBack(str.length());
					}
					break;
				}
			} else if (isEnd(str)) {
				break;
			} else {
				buffer.append(str);
				builder.setLength(0);
			}
		}
		if (buffer.length() > 0) {
			return reverse ? reverse(buffer.toString()) : buffer.toString();
		}
		return null;
	}
	
	private String maxMaybe(String str) {
		return str.substring(0, str.length() - 1);
	}
	
	private String lastOne(String str) {
		return str.substring(str.length() - 1);
	}
	
	private char readChar() {
		return charReader.read();
	}
	
	private void skipRemaindBlank() {
		charReader.skipBlank(BLANK);
	}
	
	private void walkBack() {
		charReader.back();
	}
	
	private void walkBack(int n) {
		charReader.back(n);
	}
	
	private boolean isBoundary(char ch, char[] charBoundaries) {
		for (int i = 0; i < charBoundaries.length; i++) {
			if (charBoundaries[i] == ch) {
				return true;
			}
		}
		return false;
	}
	
	private boolean maybeBoundary(String str, String[] strBoundaries) {
		if (reverse) {
			for (int i = 0; i < strBoundaries.length; i++) {
				if (reverseStartsWith(str, strBoundaries[i])) {
					return true;
				}
			}
			return false;
		}
		for (int i = 0; i < strBoundaries.length; i++) {
			if (strBoundaries[i].startsWith(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isBoundary(String str, String[] strBoundaries) {
		if (reverse) {
			for (int i = 0; i < strBoundaries.length; i++) {
				if (reverseEquals(str, strBoundaries[i])) {
					return true;
				}
			}
			return false;
		}
		for (int i = 0; i < strBoundaries.length; i++) {
			if (strBoundaries[i].equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isEnd(char ch) {
		return ch == END;
	}
	
	private boolean isEnd(String str) {
		return str.length() == 1 && str.charAt(0) == END;
	}
	
	private boolean isBlank(char ch) {
		return ch == BLANK;
	}
	
	private boolean isBlank(String str) {
		return str.length() == 1 && str.charAt(0) == BLANK;
	}
	
	private String reverse(String str) {
		if (str.length() < 2) {
			return str;
		}
		char[] reversed = new char[str.length()];
		for (int i = str.length() - 1, j = 0; i >= 0; i--, j++) {
			reversed[j] = str.charAt(i);
		}
		return new String(reversed);
	}
	
	private boolean reverseStartsWith(String str, String target) {
		if (str.length() > target.length()) {
			return false;
		}
		if (str.length() == 1) {
			return str.charAt(0) == target.charAt(target.length() - 1);
		}
		for (int i = 0, j = target.length() - 1; i < str.length(); i++, j--) {
			if (str.charAt(i) != target.charAt(j)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean reverseEquals(String str, String target) {
		if (str.length() != target.length()) {
			return false;
		}
		if (str.length() == 1) {
			return str.charAt(0) == target.charAt(target.length() - 1);
		}
		for (int i = 0, j = target.length() - 1; i < str.length(); i++, j--) {
			if (str.charAt(i) != target.charAt(j)) {
				return false;
			}
		}
		return true;
	}
}
