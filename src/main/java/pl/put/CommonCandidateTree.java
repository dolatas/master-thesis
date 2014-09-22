package pl.put;

import java.util.List;

import pl.put.model.AprioriResult;
import pl.put.model.Dmq;
import pl.put.model.SelectionPredicate;
import pl.put.utils.DBHelper;

public class CommonCandidateTree extends CommonAlgorithm {

	public CommonCandidateTree(Dmq[] originalDmq) {
		super(originalDmq);
	}

	@Override
	public List<Integer> getResult() {

//		System.out.println("cct> start time");
//		long startTime = System.nanoTime();   
		
		
		/**** algorithm start ****/
		
		for(SelectionPredicate selectionPredicate : selectionPredicates){
			selectionPredicate.setTransactions(DBHelper.getTransactionsForS(selectionPredicate));
		}
		Apriori apriori = new AprioriCCT(originalDmq, selectionPredicates);
		List<AprioriResult> result = apriori.fastApriori();
		
		/**** algorithm stop ****/


//		long estimatedTime = System.nanoTime() - startTime;
//		String fileName = PropertiesLoader.getProperty("apriori.cct.result.file");
//		FileWriter.saveToFile(fileName, false, new Date().toString());
//		FileWriter.saveToFile(fileName, true, "time: " + estimatedTime);
		
//		for(AprioriResult aprioriResult : result){
//			FileWriter.saveToFile(fileName, true, aprioriResult.toString());
//		}
		
		return null;
	}

}
