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
import pl.put.utils.DBHelper;

public class AprioriOA extends Apriori {

	public AprioriOA(Dmq[] dmqs){
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

			transactions = DBHelper.getTransactionsForDmq(dmq);
			minsup = dmq.getMinsup();
			trie = buildTree();
		
			int candidatesNo = items.size();
			while (candidatesNo > 0) {

				countCandidatesSupport();

				candidatesNo = generateCandidates();

				currentDepth++;
			}

//				printTree(trie);
			transactions = null;
			items = null;

			result.add(findAprioriResult());
		}

		return result;
	}

	@Override
	public Trie buildTree(){
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
		for(Transaction transaction : transactions){
			recursiveCounting(transaction.getItems(), 1, root);
		}
	}
	

	@Override
	public int generateCandidates(){
		int candidatesNo = 0;
		
		List<Integer> prefix = new ArrayList<Integer>();
		List<Node> nodesWithCommonPrefix = new ArrayList<Node>();
		List<Integer> possibleItems = new ArrayList<Integer>();
		
		
		for(Node parent : trie.getNodesAtLevel(currentDepth)){
			
			if(((NodeCC) parent).getCounter() >= minsup){
				
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
