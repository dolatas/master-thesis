package pl.put.model;

import java.util.List;

public class Transaction {
	private int id;
	private List<Integer> items;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getItems() {
		return items;
	}

	public void setItems(List<Integer> items) {
		this.items = items;
	}
	
	
}
