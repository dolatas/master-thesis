package pl.put;

import java.util.ArrayList;
import java.util.List;


import pl.put.model.Dmq;
import pl.put.utils.DBHelper;

public class CommonCounting extends CommonAlgorithm {

	public CommonCounting(int minTID, int maxTID) {
		super(minTID, maxTID);

	}
	
	@Override
	public List<Integer> getResult(){
		List<Integer> result = new ArrayList<Integer>();
		for(Dmq dmq : minimalDmq){
			AprioriCC apriori = new AprioriCC(DBHelper.getTransactionsForDmq(dmq), dmq.getMinsup());
			result = apriori.fastApriori();
		}
		return result;
	}
	
	

}
