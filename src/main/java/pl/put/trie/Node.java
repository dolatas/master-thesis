package pl.put.trie;

import java.util.ArrayList;

public class Node<T> {
 
	private T value;
 
	private boolean endMarker;
	
	private int counter;
 
	public ArrayList<Node<T>> children;
 
 
	public Node(T value) {
		this.value = value;
		this.endMarker = false;
		this.counter = 0;
		this.children = new ArrayList<Node<T>>();
	}
 
	public Node<T> findChild(T value) {
		if(children != null) {
			for(Node<T> n : children) {
				if(n.getValue().equals(value)) {
					return n;
				}
			}
		}
		return null;
	}
 
	public T getValue() {
		return value;
	}
 
	public void setEndMarker(boolean endMarker) {
		this.endMarker = endMarker;
	}
 
	public boolean isEndMarker() {
		return endMarker;
	}
 
	public Node<T> addChild(T value) {
		Node<T> n = new Node<T>(value);
		children.add(n);
		return n;
	}
	 
	public int getCounter() {
		return counter;
	}
	public void incrementCounter() {
		this.counter++;
	}

	public ArrayList<Node<T>> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	
 
}