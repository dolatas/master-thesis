package pl.put.trie;

import java.util.List;

//http://ken-soft.com/2012/03/20/java-trie-prefix-tree/
public class Trie {
	 
	private ObjectTrie<List<Integer>> trie;
	
	public Trie(Node root) {
		trie = new ObjectTrie<List<Integer>>(root);
	}
 
	public void insert(List<Integer> node) {
		trie.insert(node);
	}
 
	public boolean search(List<Integer> node) {
		return trie.search(node);
	}
 
	public int numberEntries() {
		return trie.numberEntries();
	}
 
	public String toString() {
		return trie.toString();
	}
	
	public Node getRoot() {
		return trie.getRoot();
	}
	
	public List<Node> getNodesAtLevel(int level){
		return trie.getNodesAtLevel(level);
	}
	
	public List<Node> getNodesAtLevel(int level, int dmqIndex){
		return trie.getNodesAtLevel(level, dmqIndex);
	}
	
	public Node searchNode(List<Integer> values) {
		return trie.searchNode(values);
	}
 
}