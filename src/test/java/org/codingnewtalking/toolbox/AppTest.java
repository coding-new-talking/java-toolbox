package org.codingnewtalking.toolbox;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

public class AppTest {

	public static void main(String[] args) {
		Collection<String> c = new ArrayList<String>();
		System.out.println(c.getClass().getTypeParameters()[0] instanceof ParameterizedType);
		System.out.println(c.getClass().getTypeParameters()[0] instanceof ParameterizedType);
	}
}
