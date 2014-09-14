package pl.put.model;

public class Dmq {

	private Integer fromExcluded;
	private Integer toIncluded;
	private int minsup;
	
	public int getMinsup() {
		return minsup;
	}
	public void setMinsup(int minsup) {
		this.minsup = minsup;
	}
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
	@Override
	public String toString() {
		return String.format("dmq(%d, %d]", fromExcluded, toIncluded);
	}
	
}
