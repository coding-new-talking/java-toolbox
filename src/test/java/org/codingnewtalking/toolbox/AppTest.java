package org.codingnewtalking.toolbox;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

public class AppTest {

	public static void main(String[] args) {
		Collection<String> c = new ArrayList<String>();
		System.out.println(c.getClass().getTypeParameters()[0] instanceof ParameterizedType);
		System.out.println(c.getClass().getTypeParameters()[0] instanceof ParameterizedType);
		System.out.println("\033[30m" + "就是酱紫的" + "\033[0m");
		System.out.println("\033[31m" + "就是酱紫的" + "\033[0m");
		System.out.println("\033[32m" + "就是酱紫的" + "\033[0m");
		System.out.println("\033[92m" + "就是酱紫的" + "\033[0m");
		System.out.println("\033[33m" + "就是酱紫的" + "\033[0m");
		System.out.println("\033[34m" + "就是酱紫的" + "\033[0m");
		System.out.println("\033[35m" + "就是酱紫的" + "\033[0m");
		System.out.println("\033[36m" + "就是酱紫的" + "\033[0m");
	}
}
