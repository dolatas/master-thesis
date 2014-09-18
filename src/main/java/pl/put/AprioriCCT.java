package pl.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pl.put.model.AprioriResult;
import pl.put.model.Dmq;
import pl.put.model.Itemset;
import pl.put.model.Transaction;
import pl.put.trie.Node;
import pl.put.trie.NodeCCT;
import pl.put.trie.Trie;

public class AprioriCCT extends Apriori {

	public AprioriCCT(Dmq[] dmqs){
		this.dmqs = dmqs;
		this.minsup = new int[dmqs.length];
	}
	
	private Trie trie;
	private Node root;
	private int currentDepth;
	private List<Transaction> transactions;
	private List<Integer> items;
	private Dmq[] dmqs;
	private int[] minsup;
	private int dmqIndex;
	private List<AprioriResult> result;
	
	@Override
	public List<AprioriResult> fastApriori(){
		
		for(int i = 0; i < dmqs.length; i++){
			Dmq dmq = dmqs[i];
			minsup[i] = dmq.getMinsup();
		}
		
		buildTree();
		int candidatesNo = items.size();
			
		while(candidatesNo > 0){
			for(int j = 0; j < dmqs.length; j++){
				dmqIndex = j;
				transactions = dmqs[j].getTransactions();
				countCandidatesSupport();
			}
//			printTree();
			candidatesNo = generateCandidates();
			currentDepth++;
		}
			
		transactions = null;
		items = null;
		
		System.out.println("cct> depth without candidates:" + currentDepth);
		findAprioriResult();
		return result;
	}
	
	@Override
	protected void buildTree(){
		root = new NodeCCT(new ArrayList<Integer>(), dmqs.length);
		trie = new Trie(root);
		root = trie.getRoot();
		currentDepth = 0;

		Set<Integer> allItemsSet = new TreeSet<Integer>();
		for(Dmq dmq : dmqs){
			for(Transaction transaction : dmq.getTransactions()){
				for(Integer item : transaction.getItems()){
					allItemsSet.add(item);
				}
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
	
	@Override
	protected void recursiveCounting(List<Integer> transactionElements, int countingDepth, Node parent){
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
	

	@Override
	protected void countCandidatesSupport(){
		for(Transaction transaction : transactions){
			recursiveCounting(transaction.getItems(), 1, root);
		}
	}

	@Override
	protected int generateCandidates(){
		int candidatesNo = 0;
		for(Node parent : trie.getNodesAtLevel(currentDepth)){
			int[] counters = ((NodeCCT) parent).getCounters();
			for(int j = 0; j < counters.length; j++){
				if(counters[j] >= minsup[j]){
					//add labels
					int elementPos = items.indexOf(parent.getLastElement());
					if(elementPos != items.size() - 1){
						for(int i = elementPos + 1; i < items.size(); i++){
							parent.addLabel(items.get(i));
						}
						
						//add children
						for(Integer label : parent.getLabels()){
							parent.addChildByLabel(label);
							candidatesNo++;
						}
					}
					break;
				}
			}
		}
		return candidatesNo;
	}

	

	@Override
	protected AprioriResult findAprioriResult() {
		result = new ArrayList<AprioriResult>();
		for(int i = 0; i < dmqs.length; i++){
			result.add(new AprioriResult());
		}
		findFrequentItemsets(root);
		return null;
	}
	
	private List<Itemset> findFrequentItemsets(Node parent){
		List<Itemset> itemsets = new ArrayList<Itemset>();
		for(Node node : parent.getChildren()){
			int[] counters = ((NodeCCT) node).getCounters();
			for(int i = 0; i < counters.length; i++){
				if(counters[i] >= minsup[i]){
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
