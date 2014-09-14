package pl.put;

import java.util.Date;
import java.util.List;

import pl.put.model.AprioriResult;
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
		
		for(Dmq dmq : minimalDmq){
			dmq.setTransactions(DBHelper.getTransactionsForDmq(dmq));
		}
		
		Apriori apriori = new AprioriCC(minimalDmq);
		List<AprioriResult> result = apriori.fastApriori();

		String fileName = PropertiesLoader.getProperty("apriori.cc.result.file");
		FileWriter.saveToFile(fileName, false, new Date().toString());
		for(AprioriResult aprioriResult : result){

			FileWriter.saveToFile(fileName, true, aprioriResult.toString());
		}
		
		return null;
	}
	
	

}
