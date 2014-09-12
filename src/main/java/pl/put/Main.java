package pl.put;

import pl.put.utils.DBHelper;
import pl.put.utils.DataLoader;
import pl.put.utils.PropertiesLoader;

public class Main {

	public static final boolean RESET_DB = true; 
	
	public static void main(String[] args) {
		
		System.out.println(PropertiesLoader.getProperty("db.connection"));
		
		if(RESET_DB){
			DBHelper.resetDB();
			DataLoader.importGeneratedDataToDB();
		}
		
		
		
		
		
		Apriori apriori = new Apriori();
		apriori.setTransactions(DBHelper.getTransactionsFromDB());
		apriori.fastApriori();
		
	}

}
