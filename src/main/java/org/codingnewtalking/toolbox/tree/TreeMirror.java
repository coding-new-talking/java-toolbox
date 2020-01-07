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
	private int gridHeight;
	private int gridWidth;
	
	public TreeMirror(Object root, ObtainLeftNode oln, ObtainRightNode orn, ObtainNodeContent onc) {
		this.root = root;
		this.oln = oln;
		this.orn = orn;
		this.onc = onc;
		this.nodeList = flattenToList(true);
	}
	
	public List<Object> getNodeList() {
		return nodeList;
	}
	
	public int getTreeDepth() {
		return treeDepth;
	}
	
	public int getTreeWidth() {
		return treeWidth;
	}
	
	public int getGridHeight() {
		return gridHeight;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void appear() {
		List<ContentWidthAdapter> cwaList = new ArrayList<>();
		final int[] maxWidth = {0}; //节点内容最宽时的宽度
		final int[] currCursor = {0}; //输出时当前光标位置
		int layer = 0; //树的层
		int layerCount = computeLayerCount(layer); //本层节点数
		int totalCount = computeTotalCount(layer); //所有节点总数
		int layerNth = 0; //第几个节点
		for (Object node : nodeList) {
			layerNth++;
			final String content = node != null ? onc.obtainContent(node) : null;
			if (content != null) {
				if (content.length() > maxWidth[0]) {
					maxWidth[0] = content.length();
				}
				final int lineCount = layerCount;
				final int allCount = totalCount;
				final int lineNth = layerNth;
				cwaList.add(() -> {
					//当前行第一个节点距离行首的宽度
					int headGapWidth = (gridWidth - allCount) / (lineCount * 2);
					//当前行除第一个节点外的后一个节点距离前一个节点的宽度
					int innerGapWidth = headGapWidth * 2 + 1;
					//当前节点在当前行的位置（一个headGap，N-1个innerGap，N-1个节点自身宽度）
					int selfPosition = headGapWidth + (lineNth - 1) * innerGapWidth + (lineNth - 1);
					//缺少的宽度（当前光标位置距离节点自身位置的宽度）
					int absentWidth = selfPosition - currCursor[0];
					//更新当前光标位置
					currCursor[0] = selfPosition + 1;
					//将当前节点内容补齐到最大宽度
					String adaptContent = content;
					int less = maxWidth[0] - content.length();
					if(less > 0) {
						int before = less / 2;
						int after = less - before;
						adaptContent = getBlanks(before) + content + getBlanks(after);
					}
					//刷上颜色
					adaptContent = brushColor(adaptContent);
					String crlf = "";
					//当前节点是本层最后一个节点
					if (lineNth == lineCount) {
						//光标归零
						currCursor[0] = 0;
						//回车换行
						crlf = "\r\n" + getLines(gridWidth) + "\r\n";
					}
					return getBlanks(absentWidth * maxWidth[0]) + adaptContent + crlf;
				});
			} else {
				//本层最后一个节点是null
				if (layerNth == layerCount) {
					cwaList.add(() -> {
						currCursor[0] = 0;
						return "\r\n" + getLines(gridWidth) + "\r\n";
					});
				}
			}
			//本层结束，进入下一层
			if (layerNth == layerCount) {
				layer++;
				layerCount = computeLayerCount(layer);
				totalCount = computeTotalCount(layer);
				layerNth = 0;
			}
		}
		System.out.println(getLines(gridWidth));
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
			count1 = computeTotalCount(depth);
			count2 = computeTotalCount(depth + 1);
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
		this.treeWidth = computeLayerCount(depth);
		this.gridHeight = treeDepth + 1;
		this.gridWidth = treeWidth * 2 - 1;
		return checkIndex;
	}
	
	private int computeTotalCount(int depth) {
		return (int)Math.pow(2, depth + 1) - 1;
	}
	
	private int computeLayerCount(int depth) {
		return (int)Math.pow(2, depth);
	}
	
	private String getBlanks(int length) {
		return getChars(length, ' ');
	}
	
	private String getLines(int length) {
		return getChars(length, '-');
	}
	
	private String getChars(int length, char ch) {
		char[] blanks = new char[length];
		for (int i = 0; i < blanks.length; i++) {
			blanks[i] = ch;
		}
		return new String(blanks);
	}
	
	private String brushColor(String content) {
		return "\033[32m" + content + "\033[0m";
	}
	
	@FunctionalInterface
	interface ObtainLeftNode {
		Object obtainLeft(Object node);
	}
	
	public String getTreeSize() {
		return "Tree: " + treeDepth + " x " + treeWidth;
	}
	
	public String getGridSize() {
		return "Grid: " + gridHeight + " x " + gridWidth;
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
