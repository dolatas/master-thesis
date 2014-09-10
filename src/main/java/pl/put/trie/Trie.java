package pl.put.trie;

//http://ken-soft.com/2012/03/20/java-trie-prefix-tree/
public class Trie {
	 
	private ObjectTrie<Long> trie;
 
	public Trie() {
		trie = new ObjectTrie<Long>(null);
	}
 
	public void insert(Long[] node) {
		trie.insert(node);
	}
 
	public boolean search(Long[] node) {
		return trie.search(node);
	}
 
	public int numberEntries() {
		return trie.numberEntries();
	}
 
	public String toString() {
		return trie.toString();
	}
 
}