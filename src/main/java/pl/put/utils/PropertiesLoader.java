package pl.put.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesLoader {
	
	private static final String PROPERTIES_FILE_NAME = "application.properties";
	
	public static String getProperty(String arg) {
		 
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
			input = PropertiesLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
			
			// load a properties file
			prop.load(input);
			
			return prop.getProperty(arg);
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
	  }
	
	
}
