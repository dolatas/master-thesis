package pl.put;

import pl.put.utils.DBHelper;
import pl.put.utils.DataLoader;
import pl.put.utils.PropertiesLoader;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World");
		
		System.out.println(PropertiesLoader.getProperty("db.connection"));
		DBHelper.resetDB();
		DataLoader.importGeneratedDataToDB();
	}

}
