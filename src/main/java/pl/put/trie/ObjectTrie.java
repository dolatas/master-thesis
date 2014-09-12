package pl.put.trie;

import java.util.ArrayList;
import java.util.List;

public class ObjectTrie<T> {
	 
	private Node root;
 
	private int numberEntries;
 
	public ObjectTrie(List<Integer> rootNodeValue) {
		root = new Node(rootNodeValue); // "empty value", usually some "null"  value or "empty string"
		numberEntries = 0;
	}
 
	public void insert(List<Integer> value) {
		Node current = root;
		if (!value.isEmpty()) {
			for (int i = 0; i < value.size(); i++) {
				Node child = current.findChildByLabel(value.get(i));
				if (child != null) {
					current = child;
				} else {
					current = current.addChild(value);
				}
				numberEntries++;
				
//				if (i == value.size() - 1) {
//					if (!current.isEndMarker()) {
//						current.setEndMarker(true);
//						
//					}
//				}
				
			}
		} else {
			System.out.println("Not adding anything");
		}
	}
 
	public boolean search(List<Integer> value) {
		Node current = root;
		for (int i = 0; i < value.size(); i++) {
			if (current.findChildByLabel(value.get(i)) == null) {
				return false;
			} else {
				current = current.findChildByLabel(value.get(i));
			}
		}

		
//		if (current.isEndMarker()) {
//			return true;
//		} else {
//			return false;
//		}
		
		return true;
	}
 
	public Node searchNode(List<Integer> value) {
		Node current = root;
		for (int i = 0; i < value.size(); i++) {
			if (current.findChildByLabel(value.get(i)) == null) {
				return null;
			} else {
				current = current.findChild(value.get(i));
			}
		}
		/*
		 * Array T[] values found in ObjectTrie. Must verify that the "endMarker" flag
		 * is true
		 */
		return current;
	}
	
	public int numberEntries() {
		return numberEntries;
	}
 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("number entries: ");
		sb.append(numberEntries);
 
		return sb.toString();
	}

	public Node getRoot() {
		return root;
	}
	
	public List<Node> getNodesAtLevel(int level){
		if(level < 0){
			return null;
		} else if(level == 0){
			List<Node> list = new ArrayList<Node>();
			list.add(root);
			return list;
		} else if (level == 1){
			return root.getChildren();
		} else {
			return getNextLevel(root.getChildren(), level - 1);
		}
	}
		
	
	private List<Node> getNextLevel(List<Node> nodes, int levelsToGo){
		List<Node> nodesAtLevel = new ArrayList<Node>();
		for (Node child : nodes){
			nodesAtLevel.addAll(child.getChildren());
		}
		if(levelsToGo == 0){
			return nodes;
		} else {
			return getNextLevel(nodesAtLevel, levelsToGo - 1);
		}
	}
 
}
