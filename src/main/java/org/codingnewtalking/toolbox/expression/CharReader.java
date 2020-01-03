package org.codingnewtalking.toolbox.expression;

/**
 * <p>字符读取器
 * @author lixinjie
 * @since 2019-07-12
 */
public class CharReader {

	public static final char END = '\0';
	
	private char[] source;
	private boolean reverse;
	private int endIndex;
	private int index;
	private char end;
	
	public CharReader(String source) {
		this(source, false, END);
	}
	
	public CharReader(String source, boolean reverse) {
		this(source, reverse, END);
	}
	
	public CharReader(String source, char end) {
		this(source, false, end);
	}
	
	public CharReader(String source, boolean reverse, char end) {
		this.source = source.toCharArray();
		this.reverse = reverse;
		this.endIndex = reverse ? -1 : source.length();
		this.index = reverse ? source.length() - 1 : 0;
		this.end = end;
	}
	
	/**读取一个字符*/
	public char read() {
		if (reverse) {
			if (index > endIndex) {
				return source[index--];
			}
			return end;
		}
		if (index < endIndex) {
			return source[index++];
		}
		return end;
	}
	
	/**退回一个字符*/
	public void back() {
		back(1);
	}
	
	/**退回n个字符*/
	public void back(int n) {
		if (reverse) {
			index += n;
		} else {
			index -= n;
		}
	}
	
	/**读取一个字符，但索引不前进*/
	public char look() {
		if (reverse) {
			if (index > endIndex) {
				return source[index];
			}
			return end;
		}
		if (index < endIndex) {
			return source[index];
		}
		return end;
	}
	
	/**跳过空白*/
	public void skipBlank(char blank) {
		if (reverse) {
			while (look() == blank) {
				index--;
			}
		} else {
			while (look() == blank) {
				index++;
			}
		}
	}
}
