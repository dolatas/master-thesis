package pl.put.model;

import java.util.List;

public class SelectionPredicate {
	
	private Integer fromExcluded;
	private Integer toIncluded;
	private List<Transaction> transactions;
	
	public Integer getFromExcluded() {
		return fromExcluded;
	}
	public void setFromExcluded(Integer fromExcluded) {
		this.fromExcluded = fromExcluded;
	}
	public Integer getToIncluded() {
		return toIncluded;
	}
	public void setToIncluded(Integer toIncluded) {
		this.toIncluded = toIncluded;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	@Override
	public String toString() {
		return String.format("sp(%d, %d]", fromExcluded, toIncluded);
	}
	
}
