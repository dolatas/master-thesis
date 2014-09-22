package pl.put.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeCCT extends Node {

	private int[] counters;
	private boolean[] fromQuery;
	private int dmqNo;
	
	public NodeCCT(List<Integer> value, int dmqNo) {
		super(value);
		this.dmqNo = dmqNo;
		counters = new int[dmqNo];
		fromQuery = new boolean[dmqNo];
	}
	public NodeCCT(List<Integer> value, int dmqNo, boolean fromQueryDefault) {
		super(value);
		this.dmqNo = dmqNo;
		counters = new int[dmqNo];
		fromQuery = new boolean[dmqNo];
		Arrays.fill(fromQuery, fromQueryDefault);
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
	public boolean getIsFrequent(int[] minsup) {
		for(int i = 0; i < dmqNo; i++){
			if (fromQuery[dmqNo] && counters[dmqNo] >= minsup[dmqNo]){
				return true;
			}
		}
		return false;
	}

	@Override
	public Node addChild(List<Integer> value) {
		Node n = new NodeCCT(value, dmqNo);
		children.add(n);
		return n;
	}
	
	@Override
	public Node addChildByLabel(Integer label, int dmqIndex) {
		List<Integer> value = new ArrayList<Integer>();
		value.addAll(this.value);
		value.add(label);
		Node n = new NodeCCT(value, dmqNo);
		((NodeCCT) n).setFromQueryOnIndex(dmqIndex, true);
		children.add(n);
		return n;
	}
	
	public Node addChildByLabel(Integer label, boolean fromQueryDefault) {
		List<Integer> value = new ArrayList<Integer>();
		value.addAll(this.value);
		value.add(label);
		Node n = new NodeCCT(value, dmqNo, fromQueryDefault);
		children.add(n);
		return n;
	}
	
	public boolean[] getFromQuery() {
		return fromQuery;
	}
	public boolean getFromQueryOnIndex(int index) {
		return fromQuery[index];
	}
	public void setFromQuery(boolean[] fromQuery) {
		this.fromQuery = fromQuery;
	}
	public void setFromQueryOnIndex(int index, boolean value) {
		this.fromQuery[index] = value;
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
	public void setChildrenFrom(int labelIndex, int dmqIndex) {
		((NodeCCT) children.get(labelIndex)).setFromQueryOnIndex(dmqIndex, true);
	}
	
 
}