package org.codingnewtalking.toolbox.string;

/**
 * @author lixinjie
 * @since 2020-01-05
 */
public class StringReverseBuilderTest {

	public static void main(String[] args) {
		StringReverseBuilder srb = new StringReverseBuilder();
		srb.prepend("dd");
		srb.prepend("cc");
		srb.prepend("bb");
		srb.prepend("aa");
		System.out.println(srb.toString());
		srb.delete(2, 4);
		System.out.println(srb.toString());
		srb.deleteCharAt(4);
		System.out.println(srb.toString());
		srb.insert(4, 'a');
		System.out.println(srb.toString());
		srb.insert(2, "cc");
		System.out.println(srb.toString());
		srb.replace(2, 6, "xx");
		System.out.println(srb.toString());
		System.out.println(srb.substring(1, 5));
	}

}
