package pl.put.trie;

import java.util.ArrayList;
import java.util.List;

public class NodeCC implements Node {
 
	private List<Integer> value;
 
	private int counter;
 
	private List<Node> children;
	
	private List<Integer> labels;
 
 
	public NodeCC(List<Integer> value) {
		this.value = value;
		this.counter = 0;
		this.children = new ArrayList<Node>();
		this.labels = new ArrayList<Integer>();
	}
 
	public Integer findLabel(Integer value) {
		if(children != null) {
			for(Integer label : labels) {
				if(label.equals(value)) {
					return label;
				}
			}
		}
		return null;
	}
	
	public Node findChildByLabel(Integer label){
		if(children != null) {
			for(Node n : children) {
				if(n.getLastElement().equals(label)) {
					return n;
				}
			}
		}
		return null;
		
	}
	
	public Node findChild(Integer value) {
		if(children != null) {
			for(Node n : children) {
				if(n.getValue().equals(value)) {
					return n;
				}
			}
		}
		return null;
	}
 
	public List<Integer> getValue() {
		return value;
	}
 
	public Node addChild(List<Integer> value) {
		Node n = new NodeCC(value);
		children.add(n);
		return n;
	}
	
	public NodeCC addChildByLabel(Integer label) {
		List<Integer> value = new ArrayList<Integer>();
		value.addAll(this.value);
		value.add(label);
		NodeCC n = new NodeCC(value);
		children.add(n);
		return n;
	}
	
	 
	public int getCounter() {
		return counter;
	}
	public void incrementCounter() {
		this.counter++;
	}

	public List<Node> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public List<Integer> getLabels() {
		return labels;
	}

	public void addLabel(Integer label) {
		this.labels.add(label);
	}
	
	public void setLabels(List<Integer> labels) {
		this.labels = labels;
	}
	
	public Integer getLastElement(){
		return value.get(value.size()-1);
	}
	
 
}