package pl.put.trie;

import java.util.ArrayList;
import java.util.List;

public class NodeCCT extends Node {

	private int[] counters;
	private int dmqNo;
	
	public NodeCCT(List<Integer> value, int dmqNo) {
		super(value);
		this.dmqNo = dmqNo;
		this.counters = new int[dmqNo];
	}
	public int[] getCounters() {
		return counters;
	}
	public void incrementCounter(int index){
		counters[index]++;
	}
	public int getCounterAtIndex(int index){
		return counters[index];
	}

	@Override
	public Node addChild(List<Integer> value) {
		Node n = new NodeCCT(value, dmqNo);
		children.add(n);
		return n;
	}
	
	@Override
	public Node addChildByLabel(Integer label) {
		List<Integer> value = new ArrayList<Integer>();
		value.addAll(this.value);
		value.add(label);
		Node n = new NodeCCT(value, dmqNo);
		children.add(n);
		return n;
	}
	
 
}