package pl.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pl.put.model.AprioriResult;
import pl.put.model.Dmq;
import pl.put.model.Itemset;
import pl.put.model.SelectionPredicate;
import pl.put.model.Transaction;
import pl.put.trie.Node;
import pl.put.trie.NodeCC;
import pl.put.trie.Trie;

public class AprioriCC extends Apriori {

	public AprioriCC(Dmq[] dmqs, SelectionPredicate[] selectionPredicates){
		this.dmqs = dmqs;
		this.selectionPredicates = selectionPredicates;
		tries = new ArrayList<Trie>();
	}
	
	private List<Trie> tries;
//	private Node root;
	private int currentDepth;
//	private List<Transaction> transactions;
//	private int minsup;
	private List<Integer> items;
	private Dmq[] dmqs;
	private int trieIndex;
	private SelectionPredicate[] selectionPredicates;
	
	@Override
	public List<AprioriResult> fastApriori(){


		Set<Integer> allItemsSet = new TreeSet<Integer>();
		for(SelectionPredicate sp : selectionPredicates){
			for(Transaction transaction : sp.getTransactions()){
				for(Integer item : transaction.getItems()){
					allItemsSet.add(item);
				}
			}
		}
		
		items = new ArrayList<Integer>();
		items.addAll(allItemsSet);
		allItemsSet = null;
		
		//create tree for each dmq
		for (int i = 0; i < dmqs.length; i++) {
			tries.add(buildTree());
		}

		currentDepth++;
		int candidatesNo = items.size();
		items = null;
		
		while (candidatesNo > 0) {
			for (SelectionPredicate s : selectionPredicates) {
				countCandidatesSupportForSP(s);
			}

			candidatesNo = 0;
			for (int i = 0; i < tries.size(); i++) {
				trieIndex = i;
				candidatesNo += generateCandidates();
			}

			currentDepth++;
		}
//		printTree(tries.get(0));
//		printTree(tries.get(1));


		List<AprioriResult> result = new ArrayList<AprioriResult>();
//		 System.out.println("cc> depth without candidates:" + currentDepth);
		result.add(findAprioriResult());

		return result;
	}

	@Override
	public Trie buildTree(){
		Node root = new NodeCC(new ArrayList<Integer>());
		Trie trie = new Trie(root);
		root = trie.getRoot();
		currentDepth = 0;

		root.setLabels(items);
		for(Integer value : items){
			root.addChildByLabel(value);
		}
		
		return trie;

	}
	
	@Override
	public void recursiveCounting(List<Integer> transactionElements, int countingDepth, Node parent){
		int elementsNo = transactionElements.size();
		if((elementsNo + countingDepth >= currentDepth) && parent.getLabels() != null){
			for(Integer label : parent.getLabels()){
				for(int i = 0; i < elementsNo; i++){
					if(label.equals(transactionElements.get(i))){
						if(countingDepth == currentDepth){
							((NodeCC)parent.findChildByLabel(label)).incrementCounter();
						} else {
							recursiveCounting(transactionElements.subList(i + 1, elementsNo), countingDepth + 1, parent.findChildByLabel(label));
						}
					} 
				}
			}
		} 		
	}
	

	@Override
	public void countCandidatesSupport(){
//		for(Transaction transaction : transactions){
//			recursiveCounting(transaction.getItems(), 1, root);
//		}
	}
	
	protected void countCandidatesSupportForSP(SelectionPredicate selectionPredicate){
		for(Transaction transaction : selectionPredicate.getTransactions()){
			for(int i = 0; i < dmqs.length; i++){
				if(dmqs[i].getFromExcluded() <= selectionPredicate.getFromExcluded() && dmqs[i].getToIncluded() >= selectionPredicate.getToIncluded()){
					recursiveCounting(transaction.getItems(), 1, tries.get(i).getRoot());
				}
			}
		}
	}
	

	@Override
	public int generateCandidates(){
		int candidatesNo = 0;
		List<Integer> prefix = new ArrayList<Integer>();
		List<Node> nodesWithCommonPrefix = new ArrayList<Node>();
		List<Integer> possibleItems = new ArrayList<Integer>();
		
		for(Node parent : tries.get(trieIndex).getNodesAtLevel(currentDepth)){
			
			if(((NodeCC) parent).getCounter() >= dmqs[trieIndex].getMinsup()){
				
				
				if(prefix.equals(parent.getPrefix())){
					Integer labelToAdd = parent.getLastElement();
					possibleItems.add(labelToAdd);
					for(Node node : nodesWithCommonPrefix){
						node.addLabel(labelToAdd);
						node.addChildByLabel(labelToAdd);
						candidatesNo++;
					}
				} else {
					prefix = parent.getPrefix();
					possibleItems = new ArrayList<Integer>();
					nodesWithCommonPrefix = new ArrayList<Node>();
				}
				nodesWithCommonPrefix.add(parent);
				
			}
		}
		return candidatesNo;
	}

	

	@Override
	public AprioriResult findAprioriResult() {
		AprioriResult aprioriResult = new AprioriResult();
		for(int i = 0; i < tries.size(); i++){
			aprioriResult.addFrequentItemsets(findFrequentItemsets(tries.get(i).getRoot(), dmqs[i].getMinsup()));
		}
		return aprioriResult;
	}
	
	private List<Itemset> findFrequentItemsets(Node parent, int minsup){
		List<Itemset> itemsets = new ArrayList<Itemset>();
		for (Node node : parent.getChildren()){
			if (((NodeCC) node).getCounter() >= minsup){
				itemsets.add(new Itemset(node.getValue()));
				itemsets.addAll(findFrequentItemsets(node, minsup));
			}
		}
		return itemsets;
	}
	
	
	
// **** console print functions ****
	protected void printTree(Trie trie){
		System.out.println(">>>>>>>>> TREE <<<<<<<<<< depth:" + currentDepth);
		for(int i = 0; i < currentDepth; i++)
			printNextLevel(trie.getNodesAtLevel(i));
		}
		
	protected void printNextLevel(List<Node> nodes){
		StringBuilder output = new StringBuilder();
		output.append("|");
		output.append(" ");
		for(Node node : nodes){
			for(Node child: node.getChildren()){
				output.append(child.getValue());
				output.append("(");
				output.append(((NodeCC)child).getCounter());
				output.append(")");
				output.append(" ");
			}
		output.append("|");
		}
		System.out.println(output.toString());
		
	}
}
