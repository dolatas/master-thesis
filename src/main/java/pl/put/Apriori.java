package pl.put;

import java.util.List;

import pl.put.model.AprioriResult;
import pl.put.trie.Node;

public abstract class Apriori {

	public abstract List<AprioriResult> fastApriori();

	protected abstract void buildTree();
	
	protected abstract void recursiveCounting(List<Integer> transactionElements, int countingDepth, Node parent);

	protected abstract void countCandidatesSupport();
	
	protected abstract int generateCandidates();
	
	protected abstract AprioriResult findAprioriResult();
	
}
