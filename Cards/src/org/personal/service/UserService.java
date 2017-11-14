package org.personal.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.personal.Dao.User;
import org.personal.util.DButil;

import com.mysql.jdbc.Statement;

public class UserService {
	
	
	public boolean isExist(String username){
		try {
			Connection connect = DButil.getConnect();
			Statement statement = (Statement)connect.createStatement();
			
			String sqlQuery = "select * from " + DButil.TABLE_USER + " where userName='" + username + "'";
			ResultSet test = statement.executeQuery(sqlQuery);
			if(test.next()){
				return true;
			}else{
				String sqlInsertPass = "insert into " + DButil.TABLE_USER + "(userName) values('"+ username + "')";
				statement.execute(sqlInsertPass);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return true;
	}
	
	
}
