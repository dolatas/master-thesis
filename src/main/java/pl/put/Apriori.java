package pl.put;

import java.util.List;

import pl.put.model.AprioriResult;
import pl.put.trie.Node;
import pl.put.trie.Trie;

public abstract class Apriori {

	public abstract List<AprioriResult> fastApriori();

	public abstract Trie buildTree();
	
	public abstract void recursiveCounting(List<Integer> transactionElements, int countingDepth, Node parent);

	public abstract void countCandidatesSupport();
	
	public abstract int generateCandidates();
	
	public abstract AprioriResult findAprioriResult();
	
}
