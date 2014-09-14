package pl.put.trie;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {

	protected List<Integer> value;
	protected List<Node> children;
	protected List<Integer> labels;

	public Node(List<Integer> value) {
		this.value = value;
		this.children = new ArrayList<Node>();
		this.labels = new ArrayList<Integer>();
	}

	public abstract Node addChildByLabel(Integer label);
	public abstract Node addChild(List<Integer> value);

	
	public Integer findLabel(Integer value) {
		if (children != null) {
			for (Integer label : labels) {
				if (label.equals(value)) {
					return label;
				}
			}
		}
		return null;
	}

	public Node findChildByLabel(Integer label) {
		if (children != null) {
			for (Node n : children) {
				if (n.getLastElement().equals(label)) {
					return n;
				}
			}
		}
		return null;

	}

	public Node findChild(Integer value) {
		if (children != null) {
			for (Node n : children) {
				if (n.getValue().equals(value)) {
					return n;
				}
			}
		}
		return null;
	}

	public List<Integer> getValue() {
		return value;
	}

	public List<Node> getChildren() {
		return children;
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

	public Integer getLastElement() {
		return value.get(value.size() - 1);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
