package org.personal.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.personal.db.DBUtil;
import org.personal.db.dao.User;
import org.personal.util.DButil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

public class UserService extends BaseService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public boolean isNull(String username){
		if("".equals(username) || username == null || username.length()<=0 ){
			logger.error("the username is null,username = {}",username);
			return false;
		}
		return true;
	}
	
//	public  boolean isExist(String username){
//		try {
//			Connection connect = DButil.getConnect();
//			Statement statement = (Statement)connect.createStatement();
//			
//			String sqlQuery = "select * from " + DButil.TABLE_USER + " where userId='" + username + "'";
//			ResultSet test = statement.executeQuery(sqlQuery);
//			if(test.next()){
//				return true;
//			}else{
//				String sqlInsertPass = "insert into " + DButil.TABLE_USER + "(userId) values('"+ username + "')";
//				statement.execute(sqlInsertPass);
//				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}  
//		return true;
//	}
	
	public boolean isLoginCards(String userId){
		DBUtil.GetInstance().init();
		if(userId == null){
			logger.error("null of userName,{}",userId);
			return false;
		}else{
			if(DBUtil.GetInstance().getUser(userId) != null){
				logger.info("login success");
			}else{
				User user = new User();
				user.setUserId(userId);
				user.setLastLoginTime(new Date());
				if(user != null){
					DBUtil.GetInstance().saveUser(user);
				}
			}
			return true;
		}
	}
	
	public void gameOver(String userId){
		
	}
	
	public static void main(String[] args){
		UserService uu = new UserService();
		String a = "1002";
		uu.isLoginCards(a);
	}
	
}
