package org.personal.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DButil {
	//table
	public static final String TABLE_USER = "user";
	public static final String TABLE_USER_INFO = "user_info";
	public static final String TABLE_POKER = "poker";
	
	public static Connection getConnect() throws SQLException{
		String url = "jdbc:mysql://localhost:3306/fightlandlords";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(url,"root","");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e){
			System.out.println("SQLException"+e.getMessage());
			System.out.println("SQLState"+e.getSQLState());
			System.out.println("VendorError"+e.getErrorCode());
		}
		return conn;
	}
	
}
