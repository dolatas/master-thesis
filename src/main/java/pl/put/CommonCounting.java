package pl.put;

import java.util.List;

import pl.put.model.AprioriResult;
import pl.put.model.Dmq;
import pl.put.model.SelectionPredicate;
import pl.put.utils.DBHelper;

public class CommonCounting extends CommonAlgorithm {
	
	public CommonCounting(Dmq[] originalDmq) {
		super(originalDmq);
	}

	@Override
	public List<Integer> getResult(){

		/**** algorithm start ****/
		
		for(SelectionPredicate selectionPredicate : selectionPredicates){
			selectionPredicate.setTransactions(DBHelper.getTransactionsForS(selectionPredicate));
		}
		Apriori apriori = new AprioriCC(originalDmq, selectionPredicates);
		List<AprioriResult> result = apriori.fastApriori();
		
		/**** algorithm stop ****/

//		for(AprioriResult aprioriResult : result){
//			FileWriter.saveToFile(fileName, true, aprioriResult.toString());
//		}
		
		return null;
	}
	
	

}
