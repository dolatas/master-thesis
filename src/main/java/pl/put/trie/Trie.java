package pl.put.trie;

import java.util.List;

//http://ken-soft.com/2012/03/20/java-trie-prefix-tree/
public class Trie {
	 
	private ObjectTrie<Integer> trie;
	
	public Trie(Integer root) {
		trie = new ObjectTrie<Integer>(root);
	}
 
	public void insert(Integer[] node) {
		trie.insert(node);
	}
 
	public boolean search(Integer[] node) {
		return trie.search(node);
	}
 
	public int numberEntries() {
		return trie.numberEntries();
	}
 
	public String toString() {
		return trie.toString();
	}
	
	public Node<Integer> getRoot() {
		return trie.getRoot();
	}
	
	public List<Node<Integer>> getNodesAtLevel(int level){
		return trie.getNodesAtLevel(level);
	}
	
	public Node<Integer> searchNode(Integer[] values) {
		return trie.searchNode(values);
	}
 
}