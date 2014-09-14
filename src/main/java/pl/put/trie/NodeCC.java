package pl.put.trie;

import java.util.ArrayList;
import java.util.List;

public class NodeCC extends Node {
 
	private int counter;
 
	public NodeCC(List<Integer> value) {
		super(value);
		this.counter = 0;
	}
 
	@Override
	public Node addChild(List<Integer> value) {
		Node n = new NodeCC(value);
		children.add(n);
		return n;
	}
	
	@Override
	public Node addChildByLabel(Integer label) {
		List<Integer> value = new ArrayList<Integer>();
		value.addAll(this.value);
		value.add(label);
		Node n = new NodeCC(value);
		children.add(n);
		return n;
	}
	 
	public int getCounter() {
		return counter;
	}
	public void incrementCounter() {
		this.counter++;
	}

}