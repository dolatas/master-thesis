package pl.put.model;

import java.util.List;

public class Itemset {
	
	public Itemset(List<Integer> items){
		this.items = items;
	}
	
	private List<Integer> items;
	
	public List<Integer> getItems() {
		return items;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" [");
		for(Integer item : items){
			builder.append(" ");
			builder.append(item);
			builder.append(" ");
		}
		builder.append("]");
		return builder.toString();
	}
	
}
