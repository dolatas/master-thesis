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
import pl.put.trie.NodeCC;
import pl.put.trie.Trie;

public class AprioriCC extends Apriori {

	public AprioriCC(Dmq[] dmqs){
		this.dmqs = dmqs;
	}
	
	private Trie trie;
	private Node root;
	private int currentDepth;
	private List<Transaction> transactions;
	private List<Integer> items;
	private Dmq[] dmqs;
	private int minsup;
	
	@Override
	public List<AprioriResult> fastApriori(){

		List<AprioriResult> result = new ArrayList<AprioriResult>();
		
		for(Dmq dmq : dmqs){
			
			transactions = dmq.getTransactions();
			minsup = dmq.getMinsup();
			
			buildTree();
			
			int candidatesNo = items.size();
			
			while(candidatesNo > 0){
				countCandidatesSupport();
//				printTree();
				candidatesNo = generateCandidates();
				currentDepth++;
			}
			
			result.add(findAprioriResult());
		}
		
		return result;
	}
	
	@Override
	protected void buildTree(){
		root = new NodeCC(new ArrayList<Integer>());
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
	
	@Override
	protected void recursiveCounting(List<Integer> transactionElements, int countingDepth, Node parent){
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
	protected void countCandidatesSupport(){
		for(Transaction transaction : transactions){
			recursiveCounting(transaction.getItems(), 1, root);
		}
	}

	@Override
	protected int generateCandidates(){
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
	protected AprioriResult findAprioriResult() {
		AprioriResult aprioriResult = new AprioriResult();
		aprioriResult.addFrequentItemsets(findFrequentItemsets(root));
		return aprioriResult;
	}
	
	private List<Itemset> findFrequentItemsets(Node parent){
		List<Itemset> itemsets = new ArrayList<Itemset>();
		for (Node node : parent.getChildren()){
			if (((NodeCC) node).getCounter() >= minsup){
				itemsets.add(new Itemset(node.getValue()));
				itemsets.addAll(findFrequentItemsets(node));
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
				output.append(((NodeCC)child).getCounter());
				output.append(")");
				output.append(" ");
			}
		output.append("|");
		}
		System.out.println(output.toString());
		
	}
}
