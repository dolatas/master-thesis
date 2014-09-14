package pl.put;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import pl.put.trie.NodeCC;
import pl.put.trie.Trie;
 
public class TrieTest {
 
	@SuppressWarnings("serial")
	@Test
	public void testTrie() {
		Trie trie = new Trie(new NodeCC(new ArrayList<Integer>()));
		trie.getRoot().addChildByLabel(1);
		trie.getRoot().getChildren().get(0).addChildByLabel(2);
		trie.insert(new ArrayList<Integer>(){{add(1); add(2); }});
		trie.insert(new ArrayList<Integer>(){{add(3); add(4); }});
		trie.insert(new ArrayList<Integer>(){{add(5); }});
		assertEquals(5, trie.numberEntries());
		
		assertTrue(trie.search(new ArrayList<Integer>(){{add(1); add(2); }}));		// should find it
	}
 
}