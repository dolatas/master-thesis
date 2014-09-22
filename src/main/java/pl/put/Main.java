package pl.put;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import pl.put.model.AprioriResult;
import pl.put.utils.DBHelper;
import pl.put.utils.DataLoader;
import pl.put.utils.DmqGenerator;
import pl.put.utils.FileWriter;
import pl.put.utils.PropertiesLoader;

public class Main {

//	public static final boolean RESET_DB = true; 
	public static final boolean RESET_DB = false; 
	
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
		DmqGenerator dmqGenerator = new DmqGenerator(tIDsRange[0], tIDsRange[1]);
		
		System.out.println("main> cc");
		long startTime = System.nanoTime(); 
		CommonAlgorithm cc = new CommonCounting(dmqGenerator.getOriginalDmq());
		cc.getResult();
		long estimatedTimeCC = System.nanoTime() - startTime;
		String fileNameCC = PropertiesLoader.getProperty("apriori.cc.result.file");
		FileWriter.saveToFile(fileNameCC, false, new Date().toString());
		FileWriter.saveToFile(fileNameCC, true, "time: " + estimatedTimeCC);
		cc = null;
		System.out.println("CC done. " + estimatedTimeCC);

		
		System.out.println("main> cct");
		startTime = System.nanoTime(); 
		CommonAlgorithm cct = new CommonCandidateTree(dmqGenerator.getOriginalDmq());
		cct.getResult();
		long estimatedTimeCCT = System.nanoTime() - startTime;
		String fileNameCCT = PropertiesLoader.getProperty("apriori.cct.result.file");
		FileWriter.saveToFile(fileNameCCT, false, new Date().toString());
		FileWriter.saveToFile(fileNameCCT, true, "time: " + estimatedTimeCCT);
		cct = null;
		System.out.println("CCT done. " + estimatedTimeCCT);


		System.out.println("main> oa");
		startTime = System.nanoTime(); 
		Apriori oa = new AprioriOA(dmqGenerator.getOriginalDmq());
		List<AprioriResult> result =  oa.fastApriori();
		long estimatedTimeOA = System.nanoTime() - startTime;
		String fileNameOA = PropertiesLoader.getProperty("apriori.oa.result.file");
		FileWriter.saveToFile(fileNameOA, false, new Date().toString());
		FileWriter.saveToFile(fileNameOA, true, "time: " + estimatedTimeOA);
		oa = null;
		System.out.println("OA done. " + estimatedTimeOA);
		
		System.out.println("processing complete done.");
		
	}

}
