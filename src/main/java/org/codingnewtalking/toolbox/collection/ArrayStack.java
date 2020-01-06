package org.codingnewtalking.toolbox.collection;

import java.lang.reflect.Array;

/**
 * @author lixinjie
 * @since 2020-01-02
 */
public class ArrayStack<E> implements Stack<E> {

	private E[] array;
	private int count;
	private int index;
	
	public ArrayStack(Class<E> itemType) {
		this(itemType, 10);
	}
	
	public ArrayStack(Class<E> itemType, int initCapacity) {
		this.array = newArray(itemType, initCapacity);
		this.count = 0;
		this.index = -1;
	}

	@Override
	public boolean empty() {
		return count == 0;
	}

	@Override
	public E push(E item) {
		expandIfNeed();
		count++;
		array[++index] = item;
		return item;
	}

	@Override
	public E pop() {
		if (count > 0) {
			count--;
			return array[index--];
		}
		return null;
	}

	@Override
	public E peek() {
		if (count > 0) {
			return array[index];
		}
		return null;
	}
	
	@Override
	public E peek(E item) {
		if (count > 0) {
			E old = array[index];
			array[index] = item;
			return old;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private E[] newArray(Class<?> itemType, int capacity) {
		return (E[])Array.newInstance(itemType, capacity);
	}
	
	private void expandIfNeed() {
		if (count == array.length) {
			E[] newArray = newArray(array.getClass().getComponentType(), array.length * 2);
			System.arraycopy(array, 0, newArray, 0, array.length);
			array = newArray;
		}
	}
}
