package org.codingnewtalking.toolbox.tree;

import org.codingnewtalking.toolbox.collection.NodeStack;
import org.codingnewtalking.toolbox.collection.StringStack;
import org.codingnewtalking.toolbox.string.TokenReader;

/**
 * @author lixinjie
 * @since 2019-12-31
 */
public class InfixToTreeLikePrefix {

	public static final char[] ARITHMETIC_BOUNDARIES = new char[] {'+', '-', '*', '/', '(', ')', ' '};
	
	public static final char[] ARITHMETIC_OPERATORS = new char[] {'+', '-', '*', '/', '(', ')'};
	
	public static boolean isOperator(String str) {
		return isOperator(str.charAt(0));
	}
	
	public static boolean isOperator(char ch) {
		for (int i = 0; i < ARITHMETIC_OPERATORS.length; i++) {
			if (ARITHMETIC_OPERATORS[i] == ch) {
				return true;
			}
		}
		return false;
	}
	
	public static int priority(String operator) {
		if (operator == null) {
			return 0;
		}
		return priority(operator.charAt(0));
	}
	
	public static int priority(char operator) {
		switch (operator) {
			case '+': return 10;
			case '-': return 10;
			case '*': return 100;
			case '/': return 100;
			case '(': return 1000;
			case ')': return 1000;
		}
		return 0;
	}
	
	public static boolean superiorTo(String priorOperator, String laterOperator) {
		if (")".equals(priorOperator)) {
			return true;
		}
		if ("(".equals(priorOperator)) {
			return false;
		}
		int priorPriority = priority(priorOperator);
		int laterPriority = priority(laterOperator);
		int diff = laterPriority - priorPriority;
		if (diff > 0) {
			return true;
		}
		if (diff < 0) {
			return false;
		}
		return true;
	}
	
	public static void popOperator(StringStack operators, NodeStack nodeStack) {
		String item;
		int count = 0;
		while ((item = operators.pop()) != null) {
			if ("(".equals(item)) {
				count++;
				continue;
			}
			if (")".equals(item)) {
				count--;
				if (count == 0) {
					break;
				}
			}
			Node left = nodeStack.pop();
			Node right = nodeStack.pop();
			Node operator = new Operator(item, left, right);
			nodeStack.push(operator);
			if (count == 0) {
				break;
			}
		}
	}
	
	public static void popOperators(StringStack operators, NodeStack nodeStack) {
		String item;
		while ((item = operators.pop()) != null) {
			if ("(".equals(item)) {
				continue;
			}
			if (")".equals(item)) {
				continue;
			}
			Node left = nodeStack.pop();
			Node right = nodeStack.pop();
			Node operator = new Operator(item, left, right);
			nodeStack.push(operator);
		}
	}
	
	public static Node infixToTree(String infix) {
		TokenReader reader = new TokenReader(infix, true, ARITHMETIC_BOUNDARIES);
		StringStack operators = new StringStack();
		NodeStack nodeStack = new NodeStack();
		String token;
		while ((token = reader.read()) != null) {
			if (isOperator(token))  {
				while (!superiorTo(operators.peek(), token)) {
					popOperator(operators, nodeStack);
				}
				operators.push(token);
			} else {
				nodeStack.push(new Operand(token));
			}
		}
		popOperators(operators, nodeStack);
		return nodeStack.pop();
	}

}
