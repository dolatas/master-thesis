package pl.put.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import pl.put.model.Transaction;

public class DBHelper {

	private static final String DB_DRIVER = PropertiesLoader
			.getProperty("db.driver");
	private static final String DB_CONNECTION = PropertiesLoader
			.getProperty("db.connection");
	private static final String DB_USER = PropertiesLoader
			.getProperty("db.user");
	private static final String DB_PASSWORD = PropertiesLoader
			.getProperty("db.password");
	private static final String DB_SCRIPT = PropertiesLoader
			.getProperty("db.script");

	private static Connection connection;

	// public DBHelper() {
	// // Exists only to defeat instantiation.
	// }

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				System.out.println("-------- PostgreSQL "
						+ "JDBC Connection Testing ------------");

				try {
					Class.forName(DB_DRIVER);
				} catch (ClassNotFoundException e) {
					System.out.println("JDBC Driver not found");
					e.printStackTrace();
					return null;
				}
				System.out.println("PostgreSQL JDBC Driver Registered!");
				// Connection connection = null;
				try {
					connection = DriverManager.getConnection(DB_CONNECTION,
							DB_USER, DB_PASSWORD);
				} catch (SQLException e) {
					System.out
							.println("Connection Failed! Check output console");
					e.printStackTrace();
					return null;
				}

				if (connection != null) {
					System.out.println("Connection succesful!");
				} else {
					System.out.println("Connection failed!");
				}

				return connection;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return null;
			}
			return null;
		}
		return connection;
	}

	public static boolean resetDB() {

		DBScriptRunner dsr = new DBScriptRunner(getConnection(), false, true);

		InputStream input = null;
		Reader reader = null;

		try {
			input = DBHelper.class.getClassLoader().getResourceAsStream(DB_SCRIPT);
			reader = new InputStreamReader(input);
			dsr.runScript(reader);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("DB resset successful.");
		return true;
	}
	
	public static void insertTransactions(List<Transaction> transactions){
		System.out.println("trans no: " + transactions.size());
		
		PreparedStatement insertTransaction = null;
		String insertTransactionSQL = "INSERT INTO transactions (id, item)  VALUES (?, ?)"; 
		try {

			insertTransaction = getConnection().prepareStatement(insertTransactionSQL);
		
			for(Transaction transaction : transactions){
				insertTransaction.setLong(1, transaction.getId());
				insertTransaction.setLong(2, transaction.getItem());
				insertTransaction.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (insertTransaction != null) {
					insertTransaction.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	} 
	

}
