package pl.put;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import pl.put.model.Dmq;
import pl.put.utils.DBHelper;
import pl.put.utils.FileWriter;
import pl.put.utils.PropertiesLoader;

public class CommonCounting extends CommonAlgorithm {

	public CommonCounting(int minTID, int maxTID) {
		super(minTID, maxTID);

	}
	
	@Override
	public List<Integer> getResult(){
		String fileName = PropertiesLoader.getProperty("apriori.cc.result.file");
		
		FileWriter.saveToFile(fileName, false, new Date().toString());
		
		List<Integer> result = new ArrayList<Integer>();
		for(Dmq dmq : minimalDmq){
			AprioriCC apriori = new AprioriCC(DBHelper.getTransactionsForDmq(dmq), dmq.getMinsup());
			result = apriori.fastApriori();
			apriori.saveResultsToFile(fileName, null);
		}
		
		return result;
	}
	
	

}
