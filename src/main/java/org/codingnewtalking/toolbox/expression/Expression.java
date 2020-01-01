package org.codingnewtalking.toolbox.expression;

/**
 * @author lixinjie
 * @since 2019-12-31
 */
public class Expression {

	public static final char[] ARITHMETIC_BOUNDARIES = new char[] {'+', '-', '*', '/', '(', ')', ' '};
	
	public static String infixToSuffix(String expr) {
		TokenReader reader = new TokenReader(expr, ARITHMETIC_BOUNDARIES);
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("a".startsWith("ab"));
	}

}
