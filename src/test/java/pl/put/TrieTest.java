package pl.put;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
 
import org.junit.Test;

import pl.put.trie.ObjectTrie;
import pl.put.trie.Trie;
 
public class TrieTest {
 
	@Test
	public void testStringTrie() {
		Trie trie = new Trie();
		assertEquals(0, trie.numberEntries());
		trie.insert(new Integer[] {1, 2});
		assertEquals(1, trie.numberEntries());
		trie.insert(new Integer[] {1, 2});
		assertEquals(1, trie.numberEntries());
		trie.insert(new Integer[] {3, 4});
		assertEquals(2, trie.numberEntries());	
 
		assertTrue(trie.search(new Integer[] {1,2}));		// should find it
		assertTrue(trie.search(new Integer[] {3, 4}));		// should find it
		assertFalse(trie.search(new Integer[] {1}));		// not a full word
	}
 
 
	@Test
	public void testObjectTrie() {	
		ObjectTrie<Object> trie = new ObjectTrie<Object>(new Object());
		Object o1 = 12;
		Object o2 = "ASFAS";
		Object o3 = new Character('X');
		Object o4 = new HashMap<String, String>();
 
		Object[] oArray1 = new Object[] {o1, o1, o2, o3, o3, o4};
		Object[] oArray2 = new Object[] {o1, o2, o3, o4, o4, o1};
		Object[] oArray3 = new Object[] {o1, o1, o2, o2};
 
		assertEquals(0, trie.numberEntries());
		trie.insert(oArray1);
		assertEquals(1, trie.numberEntries());
		trie.insert(oArray1);					// duplicate words do not count
		assertEquals(1, trie.numberEntries());
		trie.insert(oArray2);
		assertEquals(2, trie.numberEntries());	
 
		assertTrue(trie.search(oArray1));		// should find it
		assertTrue(trie.search(oArray2));		// should find it
		assertFalse(trie.search(oArray3));		// not a full word
	}
 
}