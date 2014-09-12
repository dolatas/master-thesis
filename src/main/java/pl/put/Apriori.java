package pl.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import pl.put.model.Itemset;
import pl.put.model.Transaction;
import pl.put.trie.Node;
import pl.put.trie.Trie; 

public class Apriori {

	private static final int MINSUP = 2;
	
	private Trie trie;
	private Node<Integer> root;
	private int currentDepth = 1;
	private List<Transaction> transactions;
	private Set<Integer> items;
	private List<Node<Integer>> currentCandidates;
	private List<Node<Integer>> previousCandidates;
	
	
	public List<Itemset> fastApriori(){
		
		buildTree();
//		printTree();
//		printNodes("currentCandidates", currentCandidates);
		
		while(items.size() > currentDepth){
			
			countCandidatesSupport();
			printTree();

			
//			generateCandidates();


			currentDepth++;
		}
		
		
		return null;
	}
	
	private void buildTree(){
		trie = new Trie(null);
		root = trie.getRoot();

		items = new TreeSet<Integer>();
		for(Transaction transaction : transactions){
			for(Integer item : transaction.getItems()){
				items.add(item);
			}
		}
		
		int itemsNo = items.size();
		
//		Integer[] itemsArray = new Integer[itemsNo];
		List<Integer> itemsList = new ArrayList<Integer>();
		itemsList.addAll(items);
		generateCandidates(new ArrayList<Integer>(), itemsList);
		
//		items.powerSet();
//		items.toArray(itemsArray);
//		for(int i = 0; i < itemsNo; i++){
//			Integer[] branch = new Integer[itemsNo - i];
//			for(int j = i; j < itemsNo; j++){
//				branch[j - i] = itemsArray[j];
//			}
////			trie.insert(branch);
//			
//		}

	}
	
	private void prepareTree(){
		trie = new Trie(null);
		root = trie.getRoot();

		items = new TreeSet<Integer>();
		for(Transaction transaction : transactions){
			for(Integer item : transaction.getItems()){
				items.add(item);
			}
		}
		
		previousCandidates = new ArrayList<Node<Integer>>();
		previousCandidates.add(root);
		
		currentCandidates = new ArrayList<Node<Integer>>();
		for(Integer item : items){
			root.addChild(item);
			currentCandidates.add(new Node<Integer>(item));
		}

	}
	
	private void recursiveCounting(List<Integer> transactionElements, int elementIndex, List<Node<Integer>> children){
		int elementsNo = transactionElements.size();
		if(elementsNo >= currentDepth && children != null){
			for(Node<Integer> node : children){
				for(int i = 0; i < elementsNo; i++){
					if(node.getValue() == transactionElements.get(i)){
						if(elementIndex == currentDepth){
							node.incrementCounter();
						} else {
							recursiveCounting(transactionElements.subList(i + 1, elementsNo), elementIndex + 1, node.getChildren());
						}
					} 
				}
			}
		} 		
	}
	

	private void countCandidatesSupport(){
		for(Transaction transaction : transactions){
			recursiveCounting(transaction.getItems(), 1, root.getChildren());
		}
	}
		
	

	
	private void generateCandidates(List<Integer> prefix, List<Integer> sufixes){
		
		
		for(int i = 0; i < sufixes.size(); i++){
			List<Integer> newPrefix = prefix;
			newPrefix.add(sufixes.get(i));
			Integer[] newPrefixArray = new Integer[newPrefix.size()];
			trie.insert(newPrefix.toArray(newPrefixArray));
			System.out.println(">>>>>>> INSERT " + newPrefix.size());
			if (i != sufixes.size() - 1){
				List<Integer> newSufixes = sufixes.subList(i + 1, sufixes.size());
				generateCandidates(newPrefix, newSufixes);
			}
		}
		
		
		
		
//		List<Node<Integer>> nextLevelCandidates = new ArrayList<Node<Integer>>();
		
//		previousCandidates = trie.getNodesAtLevel(currentDepth - 1);
		
//		if(currentDepth == 1){
//			for(int j = 0; j < root.getChildren().size() - 1; j++){
//				for(int k = j + 1; k < root.getChildren().size(); k++)
//					nextLevelCandidates.add(root.getChildren().get(j).addChild(root.getChildren().get(k).getValue()));
//				}
//		} else {
//			for(int i = 0; i < trie.getNodesAtLevel(currentDepth - 1).size() - 1; i++){ 
//				for(int j = 0; j < trie.getNodesAtLevel(currentDepth - 1).get(i).getChildren().size() - 1; j++){
//					for(int k = j + 1; k < trie.getNodesAtLevel(currentDepth - 1).get(i).getChildren().size(); k++){
//						Node<Integer> newNode = trie.getNodesAtLevel(currentDepth - 1).get(i).getChildren().get(j).addChild(trie.getNodesAtLevel(currentDepth - 1).get(k).getValue());
//						nextLevelCandidates.add(newNode);
//					}
//				}
//			}
//		}
//		previousCandidates = currentCandidates;
//		currentCandidates = nextLevelCandidates;
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
	
	
// **** test functions ****
	private void printNodes(String listName, List<Node<Integer>> nodes){
		System.out.println("List: " + listName);
		for(Node<Integer> node : nodes){
			System.out.println("    n: " + node.getValue() + "; c: " + node.getCounter());
		}
	}
	
	
	private void printTree(){
		System.out.println(">>>>>>>>> TREE <<<<<<<<<< depth:" + currentDepth);
		System.out.println("root");
		for(int i=0; i<=items.size();i++ )
			printNextLevel(trie.getNodesAtLevel(i));
	}
	
	private void printNextLevel(List<Node<Integer>> nodes){
		StringBuilder output = new StringBuilder();
		output.append("|");
		output.append(" ");
		for(Node<Integer> node : nodes){
			for(Node<Integer> child: node.getChildren()){
				output.append(child.getValue());
				output.append("(");
				output.append(child.getCounter());
				output.append(")");
				output.append(" ");
			}
		output.append("|");
		}
		System.out.println(output.toString());
		
	}
	
}
