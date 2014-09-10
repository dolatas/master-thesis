package pl.put.model;

public class Item {
	private long id;
	private long counter;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}
	
	public void incrementCounter(){
		counter++;
	}
}
