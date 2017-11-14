package org.personal.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.personal.util.DButil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

public class UserService extends BaseService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public boolean isNull(String username){
		if(username.equals("") || username == null || username.length()<=0 ){
			logger.error("the username is null,username = {}",username);
			return false;
		}
		return true;
	}
	
	public  boolean isExist(String username){
		
		try {
			Connection connect = DButil.getConnect();
			Statement statement = (Statement)connect.createStatement();
			
			String sqlQuery = "select * from " + DButil.TABLE_USER + " where userId='" + username + "'";
			ResultSet test = statement.executeQuery(sqlQuery);
			if(test.next()){
				return true;
			}else{
				String sqlInsertPass = "insert into " + DButil.TABLE_USER + "(userId) values('"+ username + "')";
				statement.execute(sqlInsertPass);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return true;
	}
	
	
}
