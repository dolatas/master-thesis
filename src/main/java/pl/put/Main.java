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

		
		int[] tIDsRange = DBHelper.getTIDsRange();
		
		CommonAlgorithm cc = new CommonCounting(tIDsRange[0], tIDsRange[1]);
		cc.getResult();
		
		CommonAlgorithm cct = new CommonCandidateTree(tIDsRange[0], tIDsRange[1]);
		cct.getResult();
		
	}

}
