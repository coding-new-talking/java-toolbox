package org.codingnewtalking.toolbox.tree;

/**
 * @author lixinjie
 * @since 2020-01-06
 */
public class Node {

	protected String content;
	protected Node left;
	protected Node right;
	
	//使用双向链表生成树
	protected Node prev;
	protected Node next;
	protected int priority;
	
	protected Node(String content) {
		this.content = content;
	}
	
	protected Node(String content, Node left, Node right) {
		this(content);
		this.left = left;
		this.right = right;
	}
	
	protected Node(String content, int priority) {
		this.content = content;
		this.priority = priority;
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
	
	@Override
	public String toString() {
		return content;
	}
}
