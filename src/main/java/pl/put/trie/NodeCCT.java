package pl.put.trie;

import java.util.List;

public class NodeCCT  extends NodeCC implements Node {

	private int[] counters;
	public NodeCCT(List<Integer> value, int dmqSize) {
		super(value);
		this.counters = new int[dmqSize];
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
	
 
}