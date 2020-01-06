package org.codingnewtalking.toolbox.tree;

/**
 * @author lixinjie
 * @since 2020-01-06
 */
public class InfixToTreeLikePrefixTest {

	static void test(String infix) {
		System.out.println(infix);
		System.out.println(InfixToTreeLikePrefix.infixToTree(infix));
		System.out.println("---");
	}
	
	public static void main(String[] args) {
		test("1+2-3+4");
		test("1*2/3*4");
		test("a+b*c/d-e");
		test("a*b/c-d/e*f");
		test("(1+2)*(3-4)");
		test("((1+2)*3)/(5/(6-7))");
		test("(1+2*(3-4/(5+6*7)))+(((a-b/c)+d*e)-f*g)");
		test("1-(2*(3-(4+5)/6)+7)*8");
		test("1");
		test("(1)");
		test("(((a)))");
		test("(((a)+(b)*(c)))");
		test("((((a))+((b))*((c))))");
		test("(((((a))+((b)))*((c))))");
	}

}
