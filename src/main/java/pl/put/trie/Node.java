package pl.put.trie;

import java.util.List;

public interface Node {
	public Integer findLabel(Integer value);
	public Node findChildByLabel(Integer label);
	public Node findChild(Integer value);
	public Node addChild(List<Integer> value);
	public Node addChildByLabel(Integer label);
	public void setLabels(List<Integer> labels);
	public Integer getLastElement();
	public List<Integer> getValue();
	public void addLabel(Integer label);
	public List<Node> getChildren();
	public List<Integer> getLabels();
	
}
