package org.personal.db;

import org.apache.ibatis.session.SqlSession;
import org.personal.db.dao.User;
import org.personal.db.dao.UserMapper;
import org.personal.util.Constant;
import org.personal.util.FastConsumeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {
	static Logger logger = LoggerFactory.getLogger(DBUtil.class.getName());
	
	public static enum DBEnvironment 
	{  
		DB_CHAT(new DB("chatdb", Constant.DIR_CONFIG + "chatdb_mbconfig.xml"));

		private DB db;

		private DBEnvironment(DB db)
		{
			this.db = db;
		}

		public DB getDB() 
		{
			return db;
		}
	}

	private static Object obj = new Object();

	private static DBUtil dbUtil = null;
	public static DBUtil GetInstance() {
		synchronized (obj) {
			if (dbUtil == null) 
			{
				dbUtil = new DBUtil();
			}
		}
		return dbUtil;
	}
	
	private RedisUtil redisUtil = null;

	private FastConsumeTask dbwork;

	DBUtil()
	{

	}

	public void init()
	{
		getRedis();

		removeMailTimeout();
//		loadPayer();

		dbwork = new FastConsumeTask();
		dbwork.start(2);
	}
	
	public int taskCount()
	{
		return dbwork.hasCount();
	}
	
	public static int index = 1;
	
	public RedisUtil getRedis() {
		if (redisUtil == null) {
			redisUtil = new RedisUtil();
		}
		return redisUtil;
	}
	
	
	public int removeMailTimeout()
	{
//		SqlSession session = DBEnvironment.DB_GAME.getDB().getSession();
//		try
//		{
//			MailMapper mailMapper = session.getMapper(MailMapper.class);
//			int result = mailMapper.deleteTimeOut();
//			session.commit();
//			return result;
//		}
//		catch (Exception e) {
//			Log.log.error("DBUtil", e);
//		}
//		finally
//		{
//			session.close();
//		}
		return -1;
	}
	
	public User getUser(String userId)
	{
		User user = redisUtil.get(userId, User.class);
		if(user == null)
		{
			user = getUserByDB(userId);
			if(user != null)
			{
				redisUtil.set(user.getUserId(), user);
			}
		}
		return user;
	}
	
	public User getUserByDB(String userId)
	{
		User user = null;
		SqlSession session = DBEnvironment.DB_CHAT.getDB().getSession();
		try
		{
			UserMapper userMapper = session.getMapper(UserMapper.class);
			user = userMapper.selectByUserId(userId);
			session.commit();
		}
		catch (Exception e) {
			logger.error("DBUtil", e);
		}
		finally
		{
			session.close();
		}
		return user;
	}
	
	

}
