package org.codingnewtalking.toolbox.expression;

/**
 * <p>字符读取器
 * @author lixinjie
 * @since 2019-07-12
 */
public class CharReader {

	public static final char END = '\0';
	
	private char[] source;
	private int length;
	private int index;
	
	public CharReader(String source) {
		this.source = source.toCharArray();
		this.length = source.length();
		this.index = 0;
	}
	
	/**读取一个字符*/
	public char read() {
		if (index < length) {
			return source[index++];
		}
		return END;
	}
	
	/**退回一个字符*/
	public void back() {
		back(1);
	}
	
	/**退回n个字符*/
	public void back(int n) {
		index -= n;
	}
	
	/**读取一个字符，但索引不前进*/
	public char look() {
		if (index < length) {
			return source[index];
		}
		return END;
	}
	
	/**跳过空白*/
	public void skipBlank(char blank) {
		while (look() == blank) {
			index++;
		}
	}
}
