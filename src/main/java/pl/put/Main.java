package pl.put;

import pl.put.utils.DBHelper;
import pl.put.utils.DataLoader;

public class Main {

	public static final boolean RESET_DB = true; 
	
	public static void main(String[] args) {
		
		if(RESET_DB){
			DBHelper.resetDB();
			DataLoader.importGeneratedDataToDB();
		}

		int[] tIDsRange = DBHelper.getTIDsRange();
		
		CommonAlgorithm cc = new CommonCounting(tIDsRange[0], tIDsRange[1]);
		cc.getResult();
		System.out.println("CC done.");
		
		CommonAlgorithm cct = new CommonCandidateTree(tIDsRange[0], tIDsRange[1]);
		cct.getResult();
		System.out.println("CCT done.");


		System.out.println("processing complete done.");
		
	}

}
