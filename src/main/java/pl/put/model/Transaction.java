package pl.put.model;

public class Transaction {
	
	public Transaction(){}
	
	public Transaction(long id, long item){
		this.id = id;
		this.item = item;
	}
	
	private long id;
	private long item;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItem() {
		return item;
	}
	public void setItem(long item) {
		this.item = item;
	}
	
	

}
