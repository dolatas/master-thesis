package pl.put;

import java.util.Date;
import java.util.List;

import pl.put.model.AprioriResult;
import pl.put.model.Dmq;
import pl.put.utils.DBHelper;
import pl.put.utils.FileWriter;
import pl.put.utils.PropertiesLoader;

public class CommonCandidateTree extends CommonAlgorithm {

	public CommonCandidateTree(int minTID, int maxTID) {
		super(minTID, maxTID);
	}

	@Override
	public List<Integer> getResult() {
		
		for(Dmq dmq : minimalDmq){
			dmq.setTransactions(DBHelper.getTransactionsForDmq(dmq));
		}
		
		Apriori apriori = new AprioriCCT(minimalDmq);
		List<AprioriResult> result = apriori.fastApriori();

		String fileName = PropertiesLoader.getProperty("apriori.cct.result.file");
		FileWriter.saveToFile(fileName, false, new Date().toString());
		
		for(AprioriResult aprioriResult : result){
			FileWriter.saveToFile(fileName, true, aprioriResult.toString());
		}
		
		return null;
	}

}
