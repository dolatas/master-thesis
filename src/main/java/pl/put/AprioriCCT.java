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
import pl.put.trie.NodeCCT;
import pl.put.trie.Trie;
import pl.put.utils.DBHelper;

public class AprioriCCT extends Apriori {

	public AprioriCCT(Dmq[] dmqs, SelectionPredicate[] selectionPredicates){
		this.dmqs = dmqs;
		this.selectionPredicates = selectionPredicates;
	}
	
	private Trie trie;
//	private Node root;
	private int currentDepth;
	private List<Transaction> transactions;
	private List<Integer> items;
	private Dmq[] dmqs;
	private List<AprioriResult> result;
	private SelectionPredicate[] selectionPredicates;
	private List<Integer> validFromQuery; 
	
	@Override
	public List<AprioriResult> fastApriori(){
		
		buildTree();
		
		int candidatesNo = items.size();
		items = null;
			
		
		while(candidatesNo > 0){
			for(int i = 0; i < selectionPredicates.length; i++){
				transactions = selectionPredicates[i].getTransactions();
				validFromQuery = new ArrayList<Integer>();
				for(int j = 0; j < dmqs.length; j++){
					if(dmqs[j].getFromExcluded() <= selectionPredicates[i].getFromExcluded() && dmqs[j].getToIncluded() >= selectionPredicates[i].getToIncluded()){
						validFromQuery.add(j);
					}
				}
				
				countCandidatesSupport();
			}
			
			candidatesNo = generateCandidates();
			
			currentDepth++;
		}
			
//		printTree();
		transactions = null;
		
//		System.out.println("cct> depth without candidates:" + currentDepth);
		result = new ArrayList<AprioriResult>();
		findAprioriResult();
		return result;
	}
	
	@Override
	public Trie buildTree(){
		Node root = new NodeCCT(new ArrayList<Integer>(), dmqs.length, true);
		trie = new Trie(root);
		root = trie.getRoot();
		currentDepth = 0;

		Set<Integer> allItemsSet = new TreeSet<Integer>();
			for(Transaction transaction : DBHelper.getTransactionsFromDB()){
				for(Integer item : transaction.getItems()){
					allItemsSet.add(item);
				}
			}
		items = new ArrayList<Integer>();
		items.addAll(allItemsSet);
		
		root.setLabels(items);
		for(Integer value : items){
			((NodeCCT) root).addChildByLabel(value, true);
		}
		
		currentDepth++;
		return trie;

	}
	
	@Override
	public void countCandidatesSupport(){
		for(Transaction transaction : transactions){
			recursiveCounting(transaction.getItems(), 1, trie.getRoot());
		}
	}

	@Override
	public void recursiveCounting(List<Integer> transactionElements, int countingDepth, Node parent){
		int elementsNo = transactionElements.size();
		if((elementsNo + countingDepth >= currentDepth) && parent.getLabels() != null){
			for(Integer label : parent.getLabels()){
				for(int i = 0; i < elementsNo; i++){
					if(label.equals(transactionElements.get(i))){
						if(countingDepth == currentDepth){
							for(Integer queryIndex : validFromQuery){
								((NodeCCT)parent.findChildByLabel(label)).incrementCounter(queryIndex);
							}
						} else {
							recursiveCounting(transactionElements.subList(i + 1, elementsNo), countingDepth + 1, parent.findChildByLabel(label));
						}
					} 
				}
			}
		} 		
	}
	

//	@Override
//	public int generateCandidates(){
//		int candidatesNo = 0;
//		List<Integer> prefix = new ArrayList<Integer>();
//		List<NodeCCT>  nodesWithCommonPrefix = new ArrayList<NodeCCT>();
//			for(Node parent : trie.getNodesAtLevel(currentDepth)){
//				NodeCCT parentCCT = (NodeCCT) parent;
//				
//					if(prefix.equals(parent.getPrefix())){
//						for(NodeCCT node : nodesWithCommonPrefix){
//							for(int j = 0; j < dmqs.length; j++){
//								if(parentCCT.getFromQueryOnIndex(j) && parentCCT.getCounters()[j] >= dmqs[j].getMinsup()){
//
//									Integer labelToAdd = parent.getLastElement();
//									int labelIndex = node.getLabels().indexOf(labelToAdd);
//									
//									if(labelIndex >= 0){
//										node.setChildrenFrom(labelIndex, j);
//										
//									} else {
//										node.addLabel(labelToAdd);
//										node.addChildByLabel(labelToAdd, j);
//									}
//								candidatesNo++;
//								}
//							
//							}
//						}
//						
//						
//					} else {
//						prefix = parent.getPrefix();
//						nodesWithCommonPrefix = new ArrayList<NodeCCT>();
//					}
//					nodesWithCommonPrefix.add(parentCCT);
//				}
//		return candidatesNo;
//	}
	
	
	
	
	//*************************** using sort
	
	@Override
	public int generateCandidates(){
		int candidatesNo = 0;
		
		for(int j = 0; j < dmqs.length; j++){
			List<Integer> prefix = new ArrayList<Integer>();
			List<NodeCCT>  nodesWithCommonPrefix = new ArrayList<NodeCCT>();
			for(Node parent : trie.getNodesAtLevel(currentDepth, j)){

				NodeCCT parentCCT = (NodeCCT) parent;
				
				if(prefix.equals(parent.getPrefix())){
					for(NodeCCT node : nodesWithCommonPrefix){
						if(parentCCT.getCounters()[j] >= dmqs[j].getMinsup()){
							Integer labelToAdd = parent.getLastElement();
							int labelIndex = node.getLabels().indexOf(labelToAdd);
					
							if(labelIndex >= 0){
								node.setChildrenFrom(labelIndex, j);
							} else {
								node.addLabel(labelToAdd);
								node.addChildByLabelWithOrderCheck(labelToAdd, j);
							}
							candidatesNo++;
						}
					}
				}
					
				else {
					prefix = parent.getPrefix();
					nodesWithCommonPrefix = new ArrayList<NodeCCT>();
				}
				nodesWithCommonPrefix.add(parentCCT);
			}
		}
		return candidatesNo;
	}

	@Override
	public AprioriResult findAprioriResult() {
		for(int i = 0; i < dmqs.length; i++){
			result.add(new AprioriResult());
		}
		findFrequentItemsets(trie.getRoot());
		return null;
	}
	
	private List<Itemset> findFrequentItemsets(Node parent){
		List<Itemset> itemsets = new ArrayList<Itemset>();
		for(Node node : parent.getChildren()){
			int[] counters = ((NodeCCT) node).getCounters();
			for(int i = 0; i < dmqs.length; i++){
				if(counters[i] >= dmqs[i].getMinsup()){
					result.get(i).addFrequentItemset(node.getValue());
					findFrequentItemsets(node);
				}
			}
		}
		return itemsets;
	}
	
	
	

// **** console print functions ****
	protected void printTree(){
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
				for(int c : ((NodeCCT)child).getCounters()){
					output.append("|");
					output.append(c);
				}
				output.append("|");
				output.append(")");
				output.append(" ");
			}
		output.append("|");
		}
		System.out.println(output.toString());
		
	}
}
