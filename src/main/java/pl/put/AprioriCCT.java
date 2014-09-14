package pl.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pl.put.model.Dmq;
import pl.put.model.Transaction;
import pl.put.trie.Node;
import pl.put.trie.NodeCC;
import pl.put.trie.NodeCCT;
import pl.put.trie.Trie;
import pl.put.utils.FileWriter;

public class AprioriCCT extends Apriori{

	private int minsup[];
	List<Dmq> dmqList;
	
	public AprioriCCT(List<Transaction> transactions, List<Dmq> dmqList, int minsup[]){
		super(transactions, minsup[0]);
		this.transactions = transactions;
		this.minsup = minsup;
		this.dmqList = dmqList;
	}
	
	private int dmqIndex;
	private Trie trie;
	private Node root;
	private int currentDepth;
	private List<Transaction> transactions;
	private List<Integer> items;
	
	public List<Integer> fastApriori(){
		
		buildTree();
		int candidatesNo = items.size();
		
		while(currentDepth <= items.size()){

			countCandidatesSupport();
			
			printTree();
			
			candidatesNo = generateCandidates();
			currentDepth++;
		}
		
		
		return null;
	}
	
	private void buildTree(){
		root = new NodeCCT(new ArrayList<Integer>(), dmqList.size());
		trie = new Trie(root);
		root = trie.getRoot();
		currentDepth = 0;

		Set<Integer> allItemsSet = new TreeSet<Integer>();
		for(Transaction transaction : transactions){
			for(Integer item : transaction.getItems()){
				allItemsSet.add(item);
			}
		}
		items = new ArrayList<Integer>();
		items.addAll(allItemsSet);
		
		root.setLabels(items);
		for(Integer value : items){
			root.addChildByLabel(value);
		}
		
		currentDepth++;

	}
	
	private void recursiveCounting(List<Integer> transactionElements, int countingDepth, Node parent){
		int elementsNo = transactionElements.size();
		if((elementsNo + countingDepth >= currentDepth) && parent.getLabels() != null){
			for(Integer label : parent.getLabels()){
				for(int i = 0; i < elementsNo; i++){
					if(label.equals(transactionElements.get(i))){
						if(countingDepth == currentDepth){
							((NodeCCT)parent.findChildByLabel(label)).incrementCounter(dmqIndex);
						} else {
							recursiveCounting(transactionElements.subList(i + 1, elementsNo), countingDepth + 1, parent.findChildByLabel(label));
						}
					} 
				}
			}
		} 		
	}
	

	private void countCandidatesSupport(){
		for(Transaction transaction : transactions){
			recursiveCounting(transaction.getItems(), 1, root);
		}
	}

	
	
	private int generateCandidates(){
		int candidatesNo = 0;
		for(Node parent : trie.getNodesAtLevel(currentDepth)){
			
			//add labels
			int elementPos = items.indexOf(parent.getLastElement());
			if (elementPos != items.size() - 1){
				for(int i = elementPos + 1; i < items.size(); i++){
					parent.addLabel(items.get(i));
				}
				
				//add children
				for(Integer label : parent.getLabels()){
					parent.addChildByLabel(label);
					candidatesNo++;
				}
			}
		}
		return candidatesNo;
	}

	@Override
	public void saveResultsToFile(String fileName, Node parent){
		if(parent == null){
			parent = root;
		}
		
		for (Node node : parent.getChildren()){
			int[] nodeCounters = ((NodeCCT) node).getCounters();
			for(int i = 0; i < nodeCounters.length; i++){
				if (nodeCounters[i] >= minsup[i]){
					FileWriter.saveToFile(fileName, true, node.toString());
					saveResultsToFile(fileName, node);
				}
			}
			
		}
	}
	
}
