package pl.put.model;

import java.util.ArrayList;
import java.util.List;

public class AprioriResult {
	
	public AprioriResult(){
		this.itemsets = new ArrayList<>();
	}

	private List<Itemset> itemsets;
	
	public void addFrequentItemset(List<Integer> value){
		itemsets.add(new Itemset(value));
	}
	
	public void addFrequentItemsets(List<Itemset> itemsets){
		this.itemsets.addAll(itemsets);
	}

	public List<Itemset> getItemsets() {
		return itemsets;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Itemset itemset : itemsets){
			builder.append(itemset);
		}
		return builder.toString();
	}

	
	
	
	
}
