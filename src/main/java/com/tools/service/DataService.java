package com.tools.service;

import java.sql.Connection;
import java.sql.DriverManager;


public class DataService {

	//TODO: Need persistent connection pool
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//TODO: Environment file or something
			String url = "jdbc:mysql://localhost:3306/tools";
			String username = "root";
			String password = "root";
			Connection connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		

}
