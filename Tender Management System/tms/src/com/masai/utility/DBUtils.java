package com.masai.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtils {
	static final String URL;
	static final String USERNAME;
	static final String PASSWORD;
	static Connection con = null;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResourceBundle rb = ResourceBundle.getBundle("com.masai.utility.dbDetails");
		
		URL = rb.getString("url");
		USERNAME = rb.getString("username");
		PASSWORD = rb.getString("password");
		
//		System.out.println(URL+" "+USERNAME+" "+PASSWORD);
	}
	
	public static Connection connectToDatabase() {
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void closeConnection(){
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	public static void main(String[] args) {
//		
//	}
	
}
