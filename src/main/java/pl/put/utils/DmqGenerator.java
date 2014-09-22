package pl.put.utils;

import java.util.ArrayList;
import java.util.List;

import pl.put.model.Dmq;

public class DmqGenerator {
	
	private Dmq[] originalDmq;
	private int originalDmqNo;
	private int originalDmqOverlaping;
	private int minsup;

	public DmqGenerator(int minTID, int maxTID) {
		this.minsup = Integer.parseInt(PropertiesLoader.getProperty("apriori.minsup"));
		this.originalDmqNo = Integer.parseInt(PropertiesLoader.getProperty("apriori.dmq.size"));
		this.originalDmqOverlaping = Integer.parseInt(PropertiesLoader.getProperty("apriori.dmq.overlaping"));
		System.out.println("dmq> generate orginal");
		this.originalDmq = generateOriginal(minTID, maxTID);
	}
	
	
	private Dmq[] generateOriginal(int minTID, int maxTID){
		Dmq [] dmqs = new Dmq[originalDmqNo];
		int transactionNo = maxTID - minTID + 1;
		int dmqSize = transactionNo / originalDmqNo; 
		int dmqOverlapSize = dmqSize * originalDmqOverlaping / 100;
		
		for(int i = 0; i < originalDmqNo; i++){
			Dmq dmq = new Dmq();
			dmq.setMinsup(minsup);
			if (i > 0){
				dmq.setFromExcluded(dmqs[i - 1].getToIncluded() - dmqOverlapSize);
			} else {
				dmq.setFromExcluded(minTID - 1);
			}

			dmq.setToIncluded(dmq.getFromExcluded() + dmqSize);
			
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
	
	public Dmq[] getOriginalDmq() {
		return originalDmq;
	}
	
	
	
	
	
}
