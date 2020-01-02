package org.codingnewtalking.toolbox.collection;

/**
 * @author lixinjie
 * @since 2020-01-02
 */
public class StringStack extends ArrayStack<String> {

	public StringStack() {
		super(String.class);
	}

	@Override
	protected String mergeItems(String old, String item) {
		return old + item;
	}
}
