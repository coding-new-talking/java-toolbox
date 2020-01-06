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
	
	private List<Object> nodeList;
	private int treeDepth;
	private int treeWidth;
	
	public TreeMirror(Object root, ObtainLeftNode oln, ObtainRightNode orn, ObtainNodeContent onc) {
		this.root = root;
		this.oln = oln;
		this.orn = orn;
		this.onc = onc;
		this.nodeList = flattenToList(true);
	}
	
	public int getTreeDepth() {
		return treeDepth;
	}
	
	public int getTreeWidth() {
		return treeWidth;
	}
	
	public List<Object> getNodeList() {
		return nodeList;
	}
	
	public void appear() {
		List<ContentWidthAdapter> cwaList = new ArrayList<>();
		final int[] maxWidth = {0};
		final int[] currPoint = {0};
		int count = 1;
		int nth = 0;
		for (Object node : nodeList) {
			nth++;
			final String content = node != null ? onc.obtainContent(node) : null;
			if (content != null) {
				final int layerNth = nth;
				final int layerCount = count;
				int len = content.length() * 3;
				if (len > maxWidth[0]) {
					maxWidth[0] = len;
				}
				cwaList.add(() -> {
					int charWidth = treeWidth *  maxWidth[0];
					int gapWidth = (charWidth - layerCount * maxWidth[0]) / ((layerCount - 1) * 2 + 2);
					int startPoint = (layerNth * 2 - 1) * gapWidth + (layerNth - 1) * maxWidth[0];
					int absent = startPoint - currPoint[0];
					currPoint[0] = startPoint + maxWidth[0];
					String adaptContent = content;
					int less = maxWidth[0] - content.length();
					if(less > 0) {
						int before = less / 2;
						int after = less - before;
						adaptContent = getBlanks(before) + content + getBlanks(after);
					}
					if (layerNth == layerCount) {
						currPoint[0] = 0;
						return getBlanks(absent) + adaptContent + "\r\n\r\n";
					}
					return getBlanks(absent) + adaptContent;
				});
			} else {
				if (nth == count) {
					cwaList.add(() -> {
						currPoint[0] = 0;
						return "\r\n\r\n";
					});
				}
			}
			if (nth == count) {
				count *= 2;
				nth = 0;
			}
		}
		for (ContentWidthAdapter cwa : cwaList) {
			System.out.print(cwa.adaptWidth());
		}
	}
	
	private List<Object> flattenToList(boolean fillAbsentNodesWithNull) {
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
		int checkIndex = 0;
		while (true) {
			count1 = computeNodesCount(depth);
			count2 = computeNodesCount(depth + 1);
			if (size == count2) {
				checkIndex = count1;
				break;
			}
			if (size < count2) {
				checkIndex = -1;
				break;
			}
			depth++;
		}
		this.treeDepth = depth;
		this.treeWidth = (int)Math.pow(2, depth);
		return checkIndex;
	}
	
	private int computeNodesCount(int depth) {
		int count = 0;
		int nth = 0;
		while (nth <= depth) {
			count += Math.pow(2, nth);
			nth++;
		}
		return count;
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
