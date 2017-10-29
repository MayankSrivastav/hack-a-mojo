package com.util.sql;

import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class SqlConn {
	public static void main(String[] args) {

	     // Connect to database
	        String hostName = "hackdb.database.windows.net";
	        String dbName = "Pan_Details";
	        String user = "hacker1";
	        String password = "Admin123#";
	        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
	        Connection connection = null;

	        try {
	                connection = DriverManager.getConnection(url);
	                String schema = connection.getSchema();
	                System.out.println("Successful connection - Schema: " + schema);

	                System.out.println("Query data example:");
	                System.out.println("=========================================");

	                // Create and execute a SELECT SQL statement.
	                String selectSql = "SELECT * FROM Pan_Table";

	                try (Statement statement = connection.createStatement();
	                    ResultSet resultSet = statement.executeQuery(selectSql)) {

	                        // Print results from select statement
	                        System.out.println("printing all values.....");
	                        while (resultSet.next())
	                        {
	                            System.out.println(resultSet.getString(1) + " "
	                                + resultSet.getString(2)+ " "
	    	                                + resultSet.getString(3));
	                        }
	                 connection.close();
	                }                   
	        }
	        catch (Exception e) {
	                e.printStackTrace();
	        }
	    
	}
	
	
}
