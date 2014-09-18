package pl.put;

import java.util.ArrayList;
import java.util.List;

import pl.put.model.Dmq;
import pl.put.utils.PropertiesLoader;

public abstract class CommonAlgorithm {

	public CommonAlgorithm(int minTID, int maxTID) {
		this.minsup = Integer.parseInt(PropertiesLoader.getProperty("apriori.minsup"));
		this.originalDmqNo = Integer.parseInt(PropertiesLoader.getProperty("apriori.dmq.size"));
		this.originalDmqOverlaping = Integer.parseInt(PropertiesLoader.getProperty("apriori.dmq.overlaping"));
		System.out.println("ca> generate orginal");
		this.originalDmq = generateOriginal(minTID, maxTID);
		System.out.println("ca> generate minimal");
		this.minimalDmq = generateMinimal();
	}
	
	protected Dmq[] originalDmq;
	protected Dmq[] minimalDmq;
	private int originalDmqNo;
	private int originalDmqOverlaping;
	private int minsup;
	
	
	private Dmq[] generateOriginal(int minTID, int maxTID){
		Dmq [] dmqs = new Dmq[originalDmqNo];
		int transactionNo = maxTID - minTID + 1;
		int dmqSize = transactionNo / originalDmqNo; 
		int dmqOverlapSize = dmqSize * originalDmqOverlaping / 100;
		
		for(int i = 0; i < originalDmqNo; i++){
			Dmq dmq = new Dmq();
			dmq.setMinsup(minsup);
			dmq.setFromExcluded(i * dmqSize - 1 + minTID);
			if(i < originalDmqNo - 1){
				dmq.setToIncluded((i + 1) * dmqSize - 1 + dmqOverlapSize + minTID);
			} else {
				dmq.setToIncluded(maxTID);
			}
			dmqs[i] = dmq;
		}
		
		
		return dmqs;
	}
	
	
	private Dmq[] generateMinimal(){
		List<Dmq> dmqs = new ArrayList<Dmq>();
		for(int i = 0; i < originalDmqNo; i++){
			Dmq dmq = new Dmq();
			dmq.setMinsup(minsup);
			//setFrom
			if (i == 0){
				dmq.setFromExcluded(originalDmq[i].getFromExcluded());
			} else {
				dmq.setFromExcluded(originalDmq[i - 1].getToIncluded());
			}
			//setTo
			if(i < originalDmqNo - 1){
				dmq.setToIncluded(originalDmq[i + 1].getFromExcluded());
				dmqs.add(dmq);
				//add overlapped part
				dmq = new Dmq();
				dmq.setMinsup(minsup);
				dmq.setFromExcluded(originalDmq[i + 1].getFromExcluded());
				dmq.setToIncluded(originalDmq[i].getToIncluded());
				dmqs.add(dmq);
			} else {
				dmq.setToIncluded(originalDmq[i].getToIncluded());
				dmqs.add(dmq);
			}
			
		}
		
		Dmq[] dmqsA = dmqs.toArray(new Dmq[dmqs.size()]);
		return dmqsA;
	}
	
	public abstract List<Integer> getResult();
}
