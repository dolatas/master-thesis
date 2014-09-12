package pl.put.trie;

import java.util.ArrayList;
import java.util.List;

public class ObjectTrie<T> {
	 
	private Node<T> root;
 
	private int numberEntries;
 
	public ObjectTrie(T rootNodeValue) {
		root = new Node<T>(rootNodeValue); // "empty value", usually some "null"  value or "empty string"
		numberEntries = 0;
	}
 
	public void insert(T[] values) {
		Node<T> current = root;
		if (values != null) {
			if (values.length == 0) { // "empty value"
				current.setEndMarker(true);
			}
			for (int i = 0; i < values.length; i++) {
				Node<T> child = current.findChild(values[i]);
				if (child != null) {
					current = child;
				} else {
					current = current.addChild(values[i]);
				}
				if (i == values.length - 1) {
					if (!current.isEndMarker()) {
						current.setEndMarker(true);
						numberEntries++;
					}
				}
			}
		} else {
			System.out.println("Not adding anything");
		}
	}
 
	public boolean search(T[] values) {
		Node<T> current = root;
		for (int i = 0; i < values.length; i++) {
			if (current.findChild(values[i]) == null) {
				return false;
			} else {
				current = current.findChild(values[i]);
			}
		}
		/*
		 * Array T[] values found in ObjectTrie. Must verify that the "endMarker" flag
		 * is true
		 */
		if (current.isEndMarker()) {
			return true;
		} else {
			return false;
		}
	}
 
	public Node<T> searchNode(T[] values) {
		Node<T> current = root;
		for (int i = 0; i < values.length; i++) {
			if (current.findChild(values[i]) == null) {
				return null;
			} else {
				current = current.findChild(values[i]);
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

	public Node<T> getRoot() {
		return root;
	}
	
	public List<Node<T>> getNodesAtLevel(int level){
		if(level < 0){
			return null;
		} else if(level == 0){
			List<Node<T>> list = new ArrayList<Node<T>>();
			list.add(root);
			return list;
		} else if (level == 1){
			return root.getChildren();
		} else {
			return getNextLevel(root.getChildren(), level - 1);
		}
	}
		
	
	private List<Node<T>> getNextLevel(List<Node<T>> nodes, int levelsToGo){
		List<Node<T>> nodesAtLevel = new ArrayList<Node<T>>();
		for (Node<T> child : nodes){
			nodesAtLevel.addAll(child.getChildren());
		}
		if(levelsToGo == 0){
			return nodes;
		} else {
			return getNextLevel(nodesAtLevel, levelsToGo - 1);
		}
	}
 
}
