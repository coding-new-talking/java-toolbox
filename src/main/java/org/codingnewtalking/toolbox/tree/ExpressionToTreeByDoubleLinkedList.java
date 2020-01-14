package org.codingnewtalking.toolbox.tree;

import java.util.ArrayList;
import java.util.List;

import org.codingnewtalking.toolbox.string.TokenReader;

/**
 * <p>表达式解析器
 * @author lixinjie
 * @since 2019-07-11
 */
public class ExpressionToTreeByDoubleLinkedList {

	public static final char[] ARITHMETIC_BOUNDARIES = new char[] {'+', '-', '*', '/', '(', ')', ' '};
	
	public static final char[] ARITHMETIC_OPERATORS = new char[] {'+', '-', '*', '/'};
	
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
	
	public static boolean isParenthesis(String str) {
		return isParenthesis(str.charAt(0));
	}
	
	public static boolean isParenthesis(char ch) {
		return ch == '(' || ch == ')';
	}
	
	//操作符的优先级
	public static final int ADD_PRIORITY = 1;
	public static final int SUB_PRIORITY = 1;
	public static final int MUL_PRIORITY = 10;
	public static final int DIV_PRIORITY = 10;
	public static final int LEFT_PARENTHESIS_PRIORITY = 100;
	public static final int RIGHT_PARENTHESIS_PRIORITY = -100;
	
	/**获取操作符的优先级*/
	public static int getPriority(String operator) {
		switch (operator) {
			case "+": return ADD_PRIORITY;
			case "-": return SUB_PRIORITY;
			case "*": return MUL_PRIORITY;
			case "/": return DIV_PRIORITY;
			case "(": return LEFT_PARENTHESIS_PRIORITY;
			case ")": return RIGHT_PARENTHESIS_PRIORITY;
		}
		throw new IllegalArgumentException("illegal Operator '" + operator + "'.");
	}
	
	public static Node expressionToTree(String expression) {
		List<String> tokens = expressionToTokens(expression);
		List<Operator> operators = new ArrayList<>();
		Node nodeList = tokensToDoubleLinkedList(tokens, operators);
		Node nodeTree = doubleLinkedListToTree(nodeList, operators);
		return nodeTree;
	}
	
	private static Node doubleLinkedListToTree(Node nodeList, List<Operator> operators) {
		if (operators.isEmpty()) {
			return nodeList;
		}
		if (operators.size() == 1) {
			Operator root = operators.get(0);
			root.left = root.prev;
			root.right = root.next;
			root.prev = null;
			root.next = null;
			return root;
		}
		operators.sort((operator1, operator2) -> {
			return operator2.priority - operator1.priority;
		});
		Node root = null;
		for (Operator operator : operators) {
			operator.left = operator.prev;
			operator.right = operator.next;
			if (operator.prev.prev != null) {
				operator.prev.prev.next = operator;
				operator.prev = operator.prev.prev;
			}
			if (operator.next.next != null) {
				operator.next.next.prev = operator;
				operator.next = operator.next.next;
			}
			operator.left.prev = null;
			operator.left.next = null;
			operator.right.prev = null;
			operator.right.next = null;
			root = operator;
		}
		return root;
	}
	
	private static Node tokensToDoubleLinkedList(List<String> tokens, List<Operator> operators) {
		Node head = null;
		if (tokens.size() == 1) {
			head = new Operand(tokens.get(0));
			return head;
		}
		Node curr = null;
		int contextPriority = 0;
		for (String token : tokens) {
			if (isParenthesis(token)) {
				contextPriority += getPriority(token);
				if (contextPriority < 0) {
					throw new IllegalArgumentException("表达式解析过程中，上下文优先级不能小于0，contextPriority=" + contextPriority);
				}
			} else if (isOperator(token)) {
				Operator operator = new Operator(token, getPriority(token) + contextPriority);
				curr.next = operator;
				operator.prev = curr;
				curr = operator;
				operators.add(operator);
			} else {
				Operand operand = new Operand(token);
				if (curr == null) {
					head = operand;
					curr = head;
				} else {
					curr.next = operand;
					operand.prev = curr;
					curr = operand;
				}
			}
		}
		if (contextPriority != 0) {
			throw new IllegalArgumentException("表达式解析结束后，上下文优先级应该恢复到0，contextPriority=" + contextPriority);
		}
		if (!operators.isEmpty()) {
			curr = head;
			Node operator;
			Node operand;
			while (curr.next != null) {
				operator = curr.next;
				operand = operator.next;
				if (!(operator instanceof Operator) || !(operand instanceof Operand)) {
					throw new IllegalArgumentException("解析完成后校验时，发现表达式错误，可能是操作符和操作数的位置发生错乱");
				}
				curr = operand;
			}
		}
		return head;
	}
	
	private static List<String> expressionToTokens(String expression) {
		TokenReader reader = new TokenReader(expression, ARITHMETIC_BOUNDARIES);
		return reader.readAll();
	}

}
