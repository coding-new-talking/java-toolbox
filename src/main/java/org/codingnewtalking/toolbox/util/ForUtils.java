package org.codingnewtalking.toolbox.util;

/**
 * @author lixinjie
 * @since 2019-09-01
 */
public class ForUtils {
	
	public static void each(int end, IndexAction1 indexAction) {
		each(0, end, indexAction);
	}
	
	public static void each(int start, int end, IndexAction1 indexAction) {
		each(start, end, 1, indexAction);
	}
	
	public static void each(int start, int end, int step, IndexAction1 indexAction) {
		for (int index = start; index < end; index += step) {
			indexAction.action(index);
		}
	}
	
	public static void each(int end, IndexAction2 indexAction) {
		each(0, end, indexAction);
	}
	
	public static void each(int start, int end, IndexAction2 indexAction) {
		each(start, end, 1, indexAction);
	}
	
	public static void each(int start, int end, int step, IndexAction2 indexAction) {
		for (int index = start; index < end; index += step) {
			indexAction.action(index, end);
		}
	}
	
	public static void reverseEach(int end, IndexAction1 indexAction) {
		reverseEach(0, end, indexAction);
	}
	
	public static void reverseEach(int start, int end, IndexAction1 indexAction) {
		reverseEach(start, end, 1, indexAction);
	}
	
	public static void reverseEach(int start, int end, int step, IndexAction1 indexAction) {
		for (int index = end; index > start; index -= step) {
			indexAction.action(index);
		}
	}
	
	public static void reverseEach(int end, IndexAction2 indexAction) {
		reverseEach(0, end, indexAction);
	}
	
	public static void reverseEach(int start, int end, IndexAction2 indexAction) {
		reverseEach(start, end, 1, indexAction);
	}
	
	public static void reverseEach(int start, int end, int step, IndexAction2 indexAction) {
		for (int index = end; index > start; index -= step) {
			indexAction.action(index, end);
		}
	}
	
	@FunctionalInterface
	public static interface IndexAction1 {
		void action(int index);
	}
	
	@FunctionalInterface
	public static interface IndexAction2 {
		void action(int index, int end);
	}
}
