package org.codingnewtalking.toolbox.expression;

/**
 * <p>token读取器
 * @author lixinjie
 * @since 2019-08-13
 */
public class TokenReader {

	private CharReader charReader;
	private char[] boundaries;
	private StringBuilder buffer;
	
	public TokenReader(String source, char... boundaries) {
		this.charReader = new CharReader(source);
		this.boundaries = boundaries;
		this.buffer = new StringBuilder();
	}
	
	/**读取一个token，到达结尾则返回null*/
	public String read() {
		char ch;
		buffer.setLength(0);
		while (true) {
			ch = readChar();
			if (isBoundary(ch)) {
				if (isBlank(ch)) {
					skipRemaindBlank();
					if (buffer.length() > 0) {
						break;
					}
				} else {
					if (buffer.length() == 0) {
						buffer.append(ch);
						break;
					} else {
						walkBack();
						break;
					}
				}
			} else if (isEnd(ch)) {
				break;
			} else {
				buffer.append(ch);
			}
			if (buffer.length() > 0) {
				return buffer.toString();
			}
			//return null;
			
			if (ch == ' ') {
				skipRemaindBlank();
				if (buffer.length() > 0) {
					break;
				}
			} else if (ch == '(') {
				if (buffer.length() == 0) {
					buffer.append(ch);
				} else {
					walkBack();
				}
				break;
			} else if (ch == ')') {
				if (buffer.length() == 0) {
					buffer.append(ch);
				} else {
					walkBack();
				}
				break;
			} else if (ch == '\0') {
				break;
			} else {
				buffer.append(ch);
			}
		}
		if (buffer.length() > 0) {
			return buffer.toString();
		}
		return null;
	}
	
	private char readChar() {
		return charReader.read();
	}
	
	private char lookChar() {
		return charReader.look();
	}
	
	private void skipRemaindBlank() {
		charReader.skipBlank(' ');
	}
	
	private void walkBack() {
		charReader.back();
	}
	
	private boolean isBoundary(char ch) {
		for (int i = 0; i < boundaries.length; i++) {
			if (boundaries[i] == ch) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isEnd(char ch) {
		return ch == '\0';
	}
	
	private boolean isBlank(char ch) {
		return ch == ' ';
	}
}
