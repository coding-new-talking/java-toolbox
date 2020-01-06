package org.codingnewtalking.toolbox.tree;

/**
 * @author lixinjie
 * @since 2020-01-06
 */
public class Node {

	protected String content;
	protected Node left;
	protected Node right;
	
	protected Node(String content) {
		this.content = content;
	}
	
	protected Node(String content, Node left, Node right) {
		this(content);
		this.left = left;
		this.right = right;
	}
	
	public String getContent() {
		return content;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}
	
}
