package pl.put.trie;

//http://ken-soft.com/2012/03/20/java-trie-prefix-tree/
public class Trie {
	 
	private ObjectTrie<Integer> trie;
 
	public Trie() {
		trie = new ObjectTrie<Integer>(null);
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
 
}