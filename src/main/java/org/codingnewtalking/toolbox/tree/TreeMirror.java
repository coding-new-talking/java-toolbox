package org.codingnewtalking.toolbox.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixinjie
 * @since 2020-01-06
 */
public class TreeMirror {
	
	private Object root;
	private ObtainLeftNode oln;
	private ObtainRightNode orn;
	private ObtainNodeContent onc;
	private int depth;
	
	public TreeMirror(Object root, ObtainLeftNode oln, ObtainRightNode orn, ObtainNodeContent onc) {
		this.root = root;
		this.oln = oln;
		this.orn = orn;
		this.onc = onc;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void appear() {
		List<Object> nodeList = flattenToList(true);
		List<ContentWidthAdapter> contentList = new ArrayList<>(nodeList.size());
		int[] maxWidth = new int[1];
		for (Object node : nodeList) {
			final String content = node != null ? onc.obtainContent(node) : "";
			if (content.length() > maxWidth[0]) {
				maxWidth[0] = content.length();
			}
			contentList.add(() -> {
				int less = maxWidth[0] - content.length();
				int before = less / 2;
				int after = less - before;
				return getBlanks(before) + content + getBlanks(after);
			});
		}
		for (ContentWidthAdapter cwa : contentList) {
			System.out.println(cwa.adaptWidth());
		}
	}
	
	public List<Object> flattenToList(boolean fillAbsentNodesWithNull) {
		List<Object> nodeList = new ArrayList<>();
		nodeList.add(root);
		int index = 0;
		Object node, left, right;
		if (!fillAbsentNodesWithNull) {
			while (index < nodeList.size()) {
				node = nodeList.get(index);
				left = oln.obtainLeft(node);
				if (left != null) {
					nodeList.add(left);
				}
				right = orn.obtainLeft(node);
				if (right != null) {
					nodeList.add(right);
				}
				index++;
			}
			return nodeList;
		}
		int checkIndex = 0;
		while (true) {
			node = nodeList.get(index);
			left = node != null ? oln.obtainLeft(node) : null;
			nodeList.add(left);
			right = node != null ? orn.obtainLeft(node) : null;
			nodeList.add(right);
			checkIndex = computeCheckIndex(nodeList.size());
			if (checkIndex > 0 && isAllNull(nodeList, checkIndex)) {
				nodeList = nodeList.subList(0, checkIndex);
				break;
			}
			index++;
		}
		return nodeList;
	}
	
	private boolean isAllNull(List<Object> nodeList, int checkIndex) {
		for (int i = checkIndex; i < nodeList.size(); i++) {
			if (nodeList.get(i) != null) {
				return false;
			}
		}
		return true;
	}
	
	private int computeCheckIndex(int size) {
		int depth = 0;
		int count1 = 0;
		int count2 = 0;
		while (true) {
			this.depth = depth;
			count1 = computeNodesCount(depth);
			count2 = computeNodesCount(depth + 1);
			if (size == count2) {
				return count1;
			}
			if (size < count2) {
				return -1;
			}
			depth++;
		}
	}
	
	private int computeNodesCount(int depth) {
		int layerCount = 0;
		int totalCount = 0;
		int nth = 0;
		while (nth <= depth) {
			if (nth == 0) {
				layerCount = 1;
				totalCount = 1;
			} else {
				layerCount *= 2;
				totalCount += layerCount;
			}
			nth++;
		}
		return totalCount;
	}
	
	private String getBlanks(int length) {
		char[] blanks = new char[length];
		for (int i = 0; i < blanks.length; i++) {
			blanks[i] = ' ';
		}
		return new String(blanks);
	}
	
	@FunctionalInterface
	interface ObtainLeftNode {
		Object obtainLeft(Object node);
	}
	
	@FunctionalInterface
	interface ObtainRightNode {
		Object obtainLeft(Object node);
	}
	
	@FunctionalInterface
	interface ObtainNodeContent {
		String obtainContent(Object node);
	}
	
	@FunctionalInterface
	interface ContentWidthAdapter {
		String adaptWidth();
	}
}
