package org.codingnewtalking.toolbox.collection;

/**
 * @author lixinjie
 * @since 2020-01-02
 */
public interface Stack<E> {

	boolean empty();
	
	E push(E item);
	
	E pop();
	
	E peek();
	
	E peek(E item);
}
