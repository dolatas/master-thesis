package pl.put.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.put.model.Transaction;
import pl.put.model.TransactionItemDB;

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
	
	public static void insertTransactionItems(List<TransactionItemDB> transactionItems){
		System.out.println("transaction_items no: " + transactionItems.size());
		
		PreparedStatement insertTransactionItem = null;
		String insertTransactionItemSQL = "INSERT INTO transaction_items (id, item)  VALUES (?, ?)"; 
		try {

			insertTransactionItem = getConnection().prepareStatement(insertTransactionItemSQL);
		
			for(TransactionItemDB transactionItem : transactionItems){
				insertTransactionItem.setLong(1, transactionItem.getId());
				insertTransactionItem.setLong(2, transactionItem.getItem());
				insertTransactionItem.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (insertTransactionItem != null) {
					insertTransactionItem.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	} 
	
	public static List<Transaction> getTransactionsFromDB(){
		System.out.println("loading transactions");
		
		PreparedStatement selectTransactions = null;
		String selectTransactiosnSQL = "SELECT id, item FROM transaction_items"; 
		try {
			List<Transaction> transactions = new ArrayList<Transaction>();

			selectTransactions = getConnection().prepareStatement(selectTransactiosnSQL);
			ResultSet rs = selectTransactions.executeQuery();
			long currentTransactionId = -1;
			Transaction transaction = null;
			List<Long> items = null;
			while (rs.next()) {
				long transactionId = rs.getLong(1);
				if (transactionId != currentTransactionId){
					if (transaction != null && items != null){
						transaction.setItems(items);
						transactions.add(transaction);
					}
					transaction = new Transaction();
					items = new ArrayList<Long>();
					transaction.setId(transactionId);
					currentTransactionId = transactionId;
				} 
				Long item = rs.getLong(2);
				items.add(item);
			}
			System.out.println("transactions loaded");
			return transactions;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (selectTransactions != null) {
					selectTransactions.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	
}

