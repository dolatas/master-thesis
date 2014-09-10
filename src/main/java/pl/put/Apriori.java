package pl.put;

import java.util.List;

import pl.put.model.Itemset;
import pl.put.model.Transaction;
import pl.put.trie.Trie;

public class Apriori {

	public boolean REMOVE_INFREQUENT_ELEMENTS = true;
	
	private Trie trie;
	private int depth;
	private List<Transaction> transactions;
	
	public List<Itemset> fastApriori(){
		depth = 1;
		trie = new Trie();
		

		
		
		return null;
	}
	
	
	private void removeInfrequentElements(){
		
		for(Transaction t : transactions){
			
		}
		
	}
	
	private int findFrequentElements(){
		Integer[] node = null;
		for(Transaction transaction : transactions){
			for(int item : transaction.getItems()){
				
			}
		}
		
		if(trie.search(node)){
			
		}
		
		return -1;

		
	}
	
	private void generateFirstCandidates(){
		
	}
	
	private void generateCandidates(){
		if (depth == 1){
			
			
		}
		
		if (REMOVE_INFREQUENT_ELEMENTS){
			removeInfrequentElements();
		}
		
	}


	public Trie getTrie() {
		return trie;
	}


	public void setTrie(Trie trie) {
		this.trie = trie;
	}


	public List<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
	
}
