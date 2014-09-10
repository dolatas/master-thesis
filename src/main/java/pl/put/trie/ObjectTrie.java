package pl.put.trie;

public class ObjectTrie<T> {
	 
	private Node<T> root;
 
	private int numberEntries;
 
	public ObjectTrie(T rootNodeValue) {
		root = new Node<T>(rootNodeValue); // "empty value", usually some "null"  value or "empty string"
		numberEntries = 0;
	}
 
	public void insert(T[] values) {
		Node<T> current = root;
		if (values != null) {
			if (values.length == 0) { // "empty value"
				current.setEndMarker(true);
			}
			for (int i = 0; i < values.length; i++) {
				Node<T> child = current.findChild(values[i]);
				if (child != null) {
					current = child;
				} else {
					current = current.addChild(values[i]);
				}
				if (i == values.length - 1) {
					if (!current.isEndMarker()) {
						current.setEndMarker(true);
						numberEntries++;
					}
				}
			}
		} else {
			System.out.println("Not adding anything");
		}
	}
 
	public boolean search(T[] values) {
		Node<T> current = root;
		for (int i = 0; i < values.length; i++) {
			if (current.findChild(values[i]) == null) {
				return false;
			} else {
				current = current.findChild(values[i]);
			}
		}
		/*
		 * Array T[] values found in ObjectTrie. Must verify that the "endMarker" flag
		 * is true
		 */
		if (current.isEndMarker()) {
			return true;
		} else {
			return false;
		}
	}
 
	public int numberEntries() {
		return numberEntries;
	}
 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("number entries: ");
		sb.append(numberEntries);
 
		return sb.toString();
	}
 
}
