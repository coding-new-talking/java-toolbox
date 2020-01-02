package org.codingnewtalking.toolbox.expression;

import org.codingnewtalking.toolbox.collection.StringStack;

/**
 * @author lixinjie
 * @since 2019-12-31
 */
public class ArithmeticExpression {

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
			return -10000;
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
	
	public static boolean superiorTo(String laterOperator, String priorOperator) {
		if ("(".equals(laterOperator) && "(".equals(priorOperator)) {
			return true;
		}
		if (")".equals(laterOperator) && ")".equals(priorOperator)) {
			return false;
		}
		if (!"(".equals(laterOperator) && "(".equals(priorOperator)) {
			return true;
		}
		if ("(".equals(laterOperator) && !"(".equals(priorOperator)) {
			return true;
		}
		if (!")".equals(laterOperator) && ")".equals(priorOperator)) {
			return false;
		}
		int laterPriority = priority(laterOperator);
		int priorPriority = priority(priorOperator);
		int diff = laterPriority - priorPriority;
		if (diff > 0) {
			return true;
		}
		if (diff < 0) {
			return false;
		}
		return false;
	}
	
	public static String popOperator(StringStack operators) {
		String operator = "";
		String item;
		boolean right = false;
		//boolean left = false;
		while ((item = operators.pop()) != null) {
			if (")".equals(item)) {
				right = true;
				continue;
			}
			if ("(".equals(item)) {
				//left = true;
				break;
			}
			operator = item;
			if (!right) {
				break;
			}
		}
		return operator;
	}
	
	public static String popOperators(StringStack operators) {
		String operator = "";
		String item;
		while ((item = operators.pop()) != null) {
			if (")".equals(item)) {
				continue;
			}
			if ("(".equals(item)) {
				continue;
			}
			operator += item;
		}
		return operator;
	}
	
	public static String infixToSuffix(String infix) {
		TokenReader reader = new TokenReader(infix, ARITHMETIC_BOUNDARIES);
		StringStack operators = new StringStack();
		StringBuilder suffix = new StringBuilder();
		String token;
		while ((token = reader.read()) != null) {
			if (isOperator(token))  {
				while (!superiorTo(token, operators.peek())) {
					suffix.append(popOperator(operators));
				}
				operators.push(token);
			} else {
				suffix.append(token);
			}
		}
		suffix.append(popOperators(operators));
		return suffix.toString();
	}

}
