package org.codingnewtalking.toolbox.expression;

/**
 * @author lixinjie
 * @since 2020-01-02
 */
public class ArithmeticExpressionTest {

	static void test(String infix) {
		System.out.println(infix);
		System.out.println(ArithmeticExpression.infixToSuffix(infix));
		System.out.println("---");
	}
	
	public static void main(String[] args) {
		test("1+2-3+4");
		test("1*2/3*4");
		test("a+b*c/d-e");
		test("a*b/c-d/e*f");
		test("(1+2)*(3-4)");
		test("((1+2)*3)/(5/(6-7))");
	}

}
