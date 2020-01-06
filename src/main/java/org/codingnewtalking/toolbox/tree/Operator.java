package org.codingnewtalking.toolbox.tree;

/**
 * @author lixinjie
 * @since 2020-01-06
 */
public class Operator extends Node {

	public Operator(String content) {
		super(content);
	}

	public Operator(String content, Node left, Node right) {
		super(content, left, right);
	}
}
