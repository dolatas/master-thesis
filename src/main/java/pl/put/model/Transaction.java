package pl.put.model;

import java.util.List;

public class Transaction {
	private long id;
	private List<Long> items;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Long> getItems() {
		return items;
	}

	public void setItems(List<Long> items) {
		this.items = items;
	}
	
	
}
