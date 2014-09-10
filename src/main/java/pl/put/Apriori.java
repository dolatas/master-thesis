package pl.put;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.put.model.Item;
import pl.put.model.Itemset;
import pl.put.model.Transaction;

public class Apriori {

	public static boolean REMOVE_INFREQUENT_ELEMENTS = true;
	
	public static List<Itemset> fastApriori(List<Transaction> transactions){
		if (REMOVE_INFREQUENT_ELEMENTS){
			transactions = removeInfrequentElements(transactions);
		}
		
		return null;
	}
	
	
	private static List<Transaction> removeInfrequentElements(List<Transaction> transactions){
		Map<Item, Long> itemToCounterMap = new HashMap<Item, Long>();
		
		for(Transaction t : transactions){
			List<Item> items = t.getItems();
			
		}
		
		
		return transactions;
	}
	
	private static List<Item> findFrequentElements(List<Transaction> transactions){
		List<Item> items = new ArrayList<Item>();

		
		return items;
	}
	
	private static void generateCandidates(int depth){
		
	}
	
	
	
}
