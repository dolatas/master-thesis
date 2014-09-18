package pl.put.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.put.model.TransactionItemDB;

public class DataLoader {
	
	public static boolean importGeneratedDataToDB(String filePath){
		System.out.println("DataLoader> importing data");
		List<TransactionItemDB> transactions = new ArrayList<TransactionItemDB>();
		BufferedReader reader = null;
		try {
			
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
