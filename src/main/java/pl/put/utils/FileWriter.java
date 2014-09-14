package pl.put.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

	public static void saveToFile(String fileName, boolean append, String content){

		File file = new File(fileName);
		
		try (FileOutputStream fop = new FileOutputStream(file, append)) {
	
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			// get the content in bytes
			byte[] contentInBytes = (content+ "\n").getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
	
//			System.out.println("Done");
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
