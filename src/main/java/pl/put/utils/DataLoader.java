package pl.put.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pl.put.model.TransactionItemDB;

public class DataLoader {
	
	public static boolean importGeneratedDataToDB(){
		
		List<TransactionItemDB> transactions = new ArrayList<TransactionItemDB>();
		BufferedReader reader = null;
		try {
			String filePath = Paths.get(new File(".").getCanonicalPath(), PropertiesLoader.getProperty("data.path")).toString();
			reader = new BufferedReader(new FileReader(filePath));
		
			String line = null;
			while ((line = reader.readLine()) != null) {
				try{
					long id = Long.valueOf(line.split(" ")[0]);
					long item = Long.valueOf(line.split(" ")[1]);
					TransactionItemDB transaction = new TransactionItemDB(id, item);
					transactions.add(transaction);
				} catch (NumberFormatException e){
					e.printStackTrace();
					continue;
				}
			}
			DBHelper.insertTransactionItems(transactions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}

}
