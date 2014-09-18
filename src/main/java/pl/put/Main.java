package pl.put;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import pl.put.utils.DBHelper;
import pl.put.utils.DataLoader;
import pl.put.utils.PropertiesLoader;

public class Main {

	public static final boolean RESET_DB = true; 
//	public static final boolean RESET_DB = false; 
	
	public static void main(String[] args) {
		
		if(RESET_DB){
			String filePath;
			try {
				filePath = Paths.get(new File(".").getCanonicalPath(), PropertiesLoader.getProperty("data.path")).toString();
				DBHelper.resetDB();
				DataLoader.importGeneratedDataToDB(filePath);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed to create file path.");
			}
		}

		int[] tIDsRange = DBHelper.getTIDsRange();
		

		System.out.println("main> cc");
		CommonAlgorithm cc = new CommonCounting(tIDsRange[0], tIDsRange[1]);
		cc.getResult();
		cc = null;
		System.out.println("CC done.");

		System.out.println("main> cct");
		CommonAlgorithm cct = new CommonCandidateTree(tIDsRange[0], tIDsRange[1]);
		cct.getResult();
		cct = null;
		System.out.println("CCT done.");


		System.out.println("processing complete done.");
		
	}

}
