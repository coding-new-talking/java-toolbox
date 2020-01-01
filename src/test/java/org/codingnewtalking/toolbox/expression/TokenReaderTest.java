package org.codingnewtalking.toolbox.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixinjie
 * @since 2020-01-01
 */
public class TokenReaderTest {

	static void test1(String expression, char[] boundaries) {
		TokenReader reader = new TokenReader(expression, boundaries);
		List<String> tokens = new ArrayList<>();
		String token;
		while ((token = reader.read()) != null) {
			tokens.add(token);
		}
		System.out.println(expression);
		System.out.println(tokens);
		System.out.println("---");
	}
	
	static void test2(String expression, String[] boundaries) {
		TokenReader reader = new TokenReader(expression, boundaries);
		List<String> tokens = new ArrayList<>();
		String token;
		while ((token = reader.read()) != null) {
			tokens.add(token);
		}
		System.out.println(expression);
		System.out.println(tokens);
		System.out.println("---");
	}
	
	public static void main(String[] args) {
		char[] boundaries1 = new char[] {'+', '-', '*', '/', '(', ')', ' '};
		test1("12 + 34 - 56 * 78 / 90 * (aa + bb / (cc - dd)) + ((ee + ff) * 43)", boundaries1);
		test1("(  a   +   b   *  (  c  +    d))", boundaries1);
		
		String[] boundaries2 = new String[] {"+", "-", "*", "/", "(", ")", " ", "++", "--", "+=", "-=", "*=", "/=", "//", "((", "))"};
		test2("aa++ + bb-- * ((aa // bb)) += (aa / bb)", boundaries2);
	}

}
