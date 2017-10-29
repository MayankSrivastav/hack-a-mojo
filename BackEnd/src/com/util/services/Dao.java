package com.util.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.util.pojo.PanDetails;

public class Dao {

	// Connect to database
	String hostName = "hackdb.database.windows.net";
	String dbName = "Pan_Details";
	String user = "hacker1";
	String password = "Admin123#";
	String url = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
			hostName, dbName, user, password);
	Connection connection = null;

	public int verifyInDb(byte[] imgBytes) {
		try {
			PanDetails panDetails=Extractor.ExtractData(imgBytes);
			connection = DriverManager.getConnection(url);
			String schema = connection.getSchema();
			System.out.println("Successful connection - Schema: " + schema);

			System.out.println("Query data example:");
			System.out.println("=========================================");

			// Create and execute a SELECT SQL statement.
			String selectSql = "SELECT * FROM Pan_Table where Pan_Card_No ="+panDetails.getPanCardNumber();

			try (Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(selectSql)) {

				// Print results from select statement
				System.out.println("printing all values.....");
				while (resultSet.next()) {
					//System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
					if(resultSet.getString(1).equals(panDetails.getPanCardNumber()) && resultSet.getString(2).equalsIgnoreCase(panDetails.getName()) && resultSet.getString(3).equalsIgnoreCase(panDetails.getDob()))	
					{
						System.out.println("Suuccessfully verified......");
						System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+" " + resultSet.getString(2));
						return 1;
					}
				}
				
				connection.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

}
