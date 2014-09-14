package pl.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pl.put.model.Transaction;
import pl.put.trie.NodeCC;
import pl.put.trie.Trie;

public class Apriori {

	private int minsup;
	
	public Apriori(){}
	public Apriori(List<Transaction> transactions, int minsup){
		this.transactions = transactions;
		this.minsup = minsup;
	}
	
	
	private Trie trie;
	private NodeCC root;
	private int currentDepth;
	private List<Transaction> transactions;
	private List<Integer> items;
	
//	public List<Integer> fastApriori(){
//		
//		buildTree();
//		
//		while(currentDepth <= items.size()){
//			System.out.println("depth: " + currentDepth);
//
//			countCandidatesSupport();
//			
//			printTree();
//
//			
//			generateCandidates();
//			currentDepth++;
//		}
//		
//		
//		return null;
//	}
//	
//	private void buildTree(){
//		trie = new Trie(new ArrayList<Integer>());
//		root = trie.getRoot();
//		currentDepth = 0;
//
//		Set<Integer> allItemsSet = new TreeSet<Integer>();
//		for(Transaction transaction : transactions){
//			for(Integer item : transaction.getItems()){
//				allItemsSet.add(item);
//			}
//		}
//		items = new ArrayList<Integer>();
//		items.addAll(allItemsSet);
//		
//		root.setLabels(items);
//		for(Integer value : items){
//			root.addChildByLabel(value);
//		}
//		
//		currentDepth++;
////		generateCandidates(new ArrayList<Integer>(), items);
//		
//
//	}
//	
//	private void recursiveCounting(List<Integer> transactionElements, int countingDepth, NodeCC parent){
//		int elementsNo = transactionElements.size();
//		if((elementsNo + countingDepth >= currentDepth) && parent.getLabels() != null){
//			for(Integer label : parent.getLabels()){
//				for(int i = 0; i < elementsNo; i++){
//					if(label.equals(transactionElements.get(i))){
//						if(countingDepth == currentDepth){
//							parent.findChildByLabel(label).incrementCounter();
////							System.out.println("increased " + parent.findChildByLabel(label)); 
//						} else {
//							recursiveCounting(transactionElements.subList(i + 1, elementsNo), countingDepth + 1, parent.findChildByLabel(label));
//						}
//					} 
//				}
//			}
//		} 		
//	}
//	
//
//	private void countCandidatesSupport(){
//		for(Transaction transaction : transactions){
//			recursiveCounting(transaction.getItems(), 1, root);
//		}
//	}
//
//	
//	
//	private void generateCandidates(){
//		for(NodeCC parent : trie.getNodesAtLevel(currentDepth)){
//			
//			//add labels
//			int elementPos = items.indexOf(parent.getLastElement());
//			if (elementPos != items.size() - 1){
//				for(int i = elementPos + 1; i < items.size(); i++){
//					parent.addLabel(items.get(i));
//				}
//				
//				//add children
//				for(Integer label : parent.getLabels()){
//					parent.addChildByLabel(label);
//				}
//			}
//		}
//		
//	}
//
//
//	
//	public Trie getTrie() {
//		return trie;
//	}
//
//
//	public void setTrie(Trie trie) {
//		this.trie = trie;
//	}
//
//
//	public List<Transaction> getTransactions() {
//		return transactions;
//	}
//
//
//	public void setTransactions(List<Transaction> transactions) {
//		this.transactions = transactions;
//	}
//	
//	
//// **** test functions ****
//	private void printNodes(String listName, List<NodeCC> nodes){
//		System.out.println("List: " + listName);
//		for(NodeCC node : nodes){
//			System.out.println("    n: " + node.getValue() + "; c: " + node.getCounter());
//		}
//	}
//	
//	
//	private void printTree(){
//		System.out.println(">>>>>>>>> TREE <<<<<<<<<< depth:" + currentDepth);
//		System.out.println("root");
//		for(int i=0; i<=items.size();i++ )
//			printNextLevel(trie.getNodesAtLevel(i));
//	}
//	
//	private void printNextLevel(List<NodeCC> nodes){
//		StringBuilder output = new StringBuilder();
//		output.append("|");
//		output.append(" ");
//		for(NodeCC node : nodes){
//			for(NodeCC child: node.getChildren()){
//				output.append(child.getValue());
//				output.append("(");
//				output.append(child.getCounter());
//				output.append(")");
//				output.append(" ");
//			}
//		output.append("|");
//		}
//		System.out.println(output.toString());
//		
//	}
	
}
