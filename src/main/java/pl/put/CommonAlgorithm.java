package pl.put;

import java.util.ArrayList;
import java.util.List;

import pl.put.model.Dmq;
import pl.put.model.SelectionPredicate;

public abstract class CommonAlgorithm {

	public CommonAlgorithm(Dmq[] originalDmq){
		this.originalDmq = originalDmq;
		originalDmqNo = originalDmq.length;
		selectionPredicates = determineMinS();
	}
	
	protected SelectionPredicate[] selectionPredicates;
	private int originalDmqNo;
	protected Dmq[] originalDmq;
	
	private SelectionPredicate[] determineMinS(){
		List<SelectionPredicate> spList = new ArrayList<SelectionPredicate>();
		for(int i = 0; i < originalDmqNo; i++){
			SelectionPredicate selectionPredicate = new SelectionPredicate();
			//setFrom
			if (i == 0){
				selectionPredicate.setFromExcluded(originalDmq[i].getFromExcluded());
			} else {
				selectionPredicate.setFromExcluded(originalDmq[i - 1].getToIncluded());
			}
			//setTo
			if(i < originalDmqNo - 1){
				selectionPredicate.setToIncluded(originalDmq[i + 1].getFromExcluded());
				spList.add(selectionPredicate);
				//add overlapped part
				selectionPredicate = new SelectionPredicate();
				selectionPredicate.setFromExcluded(originalDmq[i + 1].getFromExcluded());
				selectionPredicate.setToIncluded(originalDmq[i].getToIncluded());
				spList.add(selectionPredicate);
			} else {
				selectionPredicate.setToIncluded(originalDmq[i].getToIncluded());
				spList.add(selectionPredicate);
			}
			
		}
		
		SelectionPredicate[] spA = spList.toArray(new SelectionPredicate[spList.size()]);
		return spA;
	}
	
	
	public abstract List<Integer> getResult();
}
