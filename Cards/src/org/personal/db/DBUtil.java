package org.personal.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.personal.db.dao.Poker;
import org.personal.db.dao.PokerList;
import org.personal.db.dao.PokerListMapper;
import org.personal.db.dao.User;
import org.personal.db.dao.UserMapper;
import org.personal.util.Constant;
import org.personal.util.FastConsumeTask;
import org.personal.util.TaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {
	private Logger logger = LoggerFactory.getLogger(DBUtil.class.getName());
	public static enum DBEnvironment 
	{  
		DB_CHAT(new DB("carddb", Constant.DIR_CONFIG + "carddb_mbconfig.xml"));

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
		dbwork.start(3);
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
	
	//-----------------------------------------------用户相关--------------------------------------
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
			System.out.println("DBUtil:---"+e);;
		}
		finally
		{
			session.close();
		}
		return user;
	}
	
	public void saveUser(final User user)
	{	
		redisUtil.set(user.getUserId(), user);
		dbwork.produce(new TaskHandler()
		{
			@Override
			public void onEvent() 
			{
				saveUserByDB(user);
			}
		});
	}
	
	public void saveUserByDB(User user)
	{
		if(user == null)
		{
			logger.error("saveUserByDB user is null!");
			return ;
		}

		SqlSession session = DBEnvironment.DB_CHAT.getDB().getSession();
		try
		{
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.insert(user);
			session.commit();
		}
		catch (Exception e) {
			logger.error("DBUtil", e);
		}
		finally
		{
			session.close();
		}
	}
	
	public void updateUser(final User user)
	{
		redisUtil.set(user.getUserId(), user);
		dbwork.produce(new TaskHandler()
		{
			@Override
			public void onEvent() 
			{
				updateUserByDB(user);
			}
		});
	}
	
	public void updateUserByDB(User user)
	{
		if(user == null)
		{
			logger.error("updateUserToDB user is null!");
			return ;
		}

		SqlSession session = DBEnvironment.DB_CHAT.getDB().getSession();
		try
		{
			UserMapper userMapper = session.getMapper(UserMapper.class);
			userMapper.updateByPrimaryKeySelective(user);
			session.commit();
		}
		catch (Exception e) {
			logger.error("DBUtil", e);
		}
		finally
		{
			session.close();
		}
	}

//---------------------------------------------------牌库相关
//	public PokerList getPokerList(String userId)
//	{
//		PokerList pokerList = redisUtil.get(userId + RedisKeys.POKERLIST, PokerList.class);
//		if(pokerList == null)
//		{
//			pokerList = getUserByDB(userId);
//			if(pokerList != null)
//			{
//				redisUtil.set(pokerList.getPokerId(), pokerList);
//			}
//		}
//		return pokerList;
//	}
//	
//	public PokerList getPokerListByDB(String userId)
//	{
//		PokerList pokerList = null;
//		SqlSession session = DBEnvironment.DB_CHAT.getDB().getSession();
//		try
//		{
//			PokerListMapper pokerListMapper = session.getMapper(PokerListMapper.class);
//			pokerList = PokerListMapper.selectByPokerId(userId);
//			session.commit();
//		}
//		catch (Exception e) {
//			System.out.println("DBUtil:---"+e);;
//		}
//		finally
//		{
//			session.close();
//		}
//		return pokerList;
//	}
//	
//	public void savePokerList(final PokerList pokerList)
//	{	
//		redisUtil.set(pokerList.getUserId(), pokerList);
//		dbwork.produce(new TaskHandler()
//		{
//			@Override
//			public void onEvent() 
//			{
//				saveUserByDB(user);
//			}
//		});
//	}
//	
//	public void saveUserByDB(User user)
//	{
//		if(user == null)
//		{
//			logger.error("saveUserByDB user is null!");
//			return ;
//		}
//
//		SqlSession session = DBEnvironment.DB_CHAT.getDB().getSession();
//		try
//		{
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//			userMapper.insert(user);
//			session.commit();
//		}
//		catch (Exception e) {
//			logger.error("DBUtil", e);
//		}
//		finally
//		{
//			session.close();
//		}
//	}
//	
//	public void updateUser(final User user)
//	{
//		redisUtil.set(user.getUserId(), user);
//		dbwork.produce(new TaskHandler()
//		{
//			@Override
//			public void onEvent() 
//			{
//				updateUserByDB(user);
//			}
//		});
//	}
//	
//	public void updateUserByDB(User user)
//	{
//		if(user == null)
//		{
//			logger.error("updateUserToDB user is null!");
//			return ;
//		}
//
//		SqlSession session = DBEnvironment.DB_CHAT.getDB().getSession();
//		try
//		{
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//			userMapper.updateByPrimaryKeySelective(user);
//			session.commit();
//		}
//		catch (Exception e) {
//			logger.error("DBUtil", e);
//		}
//		finally
//		{
//			session.close();
//		}
//	}
	
	
//---------------------------------牌
	public Map<String, Poker> getPokerMap(String userId)
	{
		return redisUtil.hashGetAll(RedisKeys.POKER + userId, Poker.class);
	}
	
	public Poker getPoker(String userId, int pokerId)
	{
		return redisUtil.hashGet(RedisKeys.POKER + userId, String.valueOf(pokerId), Poker.class);
	}
	
	public void savePoker(String userId, Poker poker)
	{	
		String pokerId = String.valueOf(poker.getPokerId());
		redisUtil.hashSet(RedisKeys.POKER + userId, pokerId, poker);
	}
	
	public void deletePoker(String userId, String pokerId)
	{
		redisUtil.hashDel(RedisKeys.POKER + userId, pokerId);
	}
	
	
//--------------------------------------------七个移牌区的牌----------------------------------------------
	//--------------------------------------------------------room1
	public void addPokerToRoom1(final String userId,final Poker poker)
	{	
		redisUtil.listAdd(userId + RedisKeys.POKERHOME_1,poker);
	}
	
	public List<Poker> getPokerRoom1List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_1, Poker.class);
	}
	
	public void deletePokerFromRoom1(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_1, poker);
	}
	
	//-------------------------------------------------------room2
	public void addPokerToRoom2(final String userId,final Poker poker)
	{	
		redisUtil.listAdd(userId + RedisKeys.POKERHOME_2,poker);
	}
	
	public List<Poker> getPokerRoom2List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_2, Poker.class);
	}
	
	public void deletePokerFromRoom2(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_2, poker);
	}
	
	//--------------------------------------------------------room3
	public void addPokerToRoom3(final String userId,final Poker poker)
	{	
		redisUtil.listAdd(userId + RedisKeys.POKERHOME_3,poker);
	}
	
	public List<Poker> getPokerRoom3List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_3, Poker.class);
	}
	
	public void deletePokerFromRoom3(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_3, poker);
	}
	
	//--------------------------------------------------------room4
	public void addPokerToRoom4(final String userId,final Poker poker)
	{	
		redisUtil.listAdd(userId + RedisKeys.POKERHOME_4,poker);
	}
	
	public List<Poker> getPokerRoom4List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_4, Poker.class);
	}
	
	public void deletePokerFromRoom4(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_4, poker);
	}
	
	//--------------------------------------------------------room5
	public void addPokerToRoom5(final String userId,final Poker poker)
	{	
		redisUtil.listAdd(userId + RedisKeys.POKERHOME_5,poker);
	}
	
	public List<Poker> getPokerRoom5List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_5, Poker.class);
	}
	
	public void deletePokerFromRoom5(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_5, poker);
	}
	
	//-------------------------------------------------------room6
	public void addPokerToRoom6(final String userId,final Poker poker)
	{	
		redisUtil.listAdd(userId + RedisKeys.POKERHOME_6,poker);
	}
	
	public List<Poker> getPokerRoom6List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_6, Poker.class);
	}
	
	public void deletePokerFromRoom6(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_6, poker);
	}
	
	//-------------------------------------------------------room7
	public void addPokerToRoom7(final String userId,final Poker poker)
	{	
		redisUtil.listAdd(userId + RedisKeys.POKERHOME_7,poker);
	}
	
	public List<Poker> getPokerRoom7List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_7, Poker.class);
	}
	
	public void deletePokerFromRoom7(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_7, poker);
	}

//-----------------------------------------洗牌区的牌------------------------------------------------------
	
	public void addPokerToShuffle(final String userId,final Poker poker){
		redisUtil.listAdd(userId + RedisKeys.SHUFFLE, poker);
	}
	
	public List<Poker> getShuffleList(String userId){
		return redisUtil.listGet(userId + RedisKeys.SHUFFLE, Poker.class);
	}
	
	public void deletePokerFromShuffle(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.SHUFFLE, poker);
	}
	
//---------------------------------------四个存牌区的牌--------------------------------------------
	// --------------存牌区1
	public void addPokerToHome1(final String userId, final Poker poker) {
		redisUtil.listAdd(userId + RedisKeys.SHUFFLE, poker);
	}

	public List<Poker> getHome1List(String userId) {
		return redisUtil.listGet(userId + RedisKeys.SHUFFLE, Poker.class);
	}

	public void deletePokerFromHome1(String userId, Poker poker) {
		redisUtil.listDel(userId + RedisKeys.SHUFFLE, poker);
	}

	// --------------存牌区2
	public void addPokerToHome2(final String userId, final Poker poker) {
		redisUtil.listAdd(userId + RedisKeys.SHUFFLE, poker);
	}

	public List<Poker> getHome2List(String userId) {
		return redisUtil.listGet(userId + RedisKeys.SHUFFLE, Poker.class);
	}

	public void deletePokerFromHome2(String userId, Poker poker) {
		redisUtil.listDel(userId + RedisKeys.SHUFFLE, poker);
	}

	// --------------存牌区3
	public void addPokerToHome3(final String userId, final Poker poker) {
		redisUtil.listAdd(userId + RedisKeys.SHUFFLE, poker);
	}

	public List<Poker> getHome3List(String userId) {
		return redisUtil.listGet(userId + RedisKeys.SHUFFLE, Poker.class);
	}

	public void deletePokerFromHome3(String userId, Poker poker) {
		redisUtil.listDel(userId + RedisKeys.SHUFFLE, poker);
	}

	// --------------存牌区4
	public void addPokerToHome4(final String userId, final Poker poker) {
		redisUtil.listAdd(userId + RedisKeys.SHUFFLE, poker);
	}

	public List<Poker> getHome4List(String userId) {
		return redisUtil.listGet(userId + RedisKeys.SHUFFLE, Poker.class);
	}

	public void deletePokerFromHome4(String userId, Poker poker) {
		redisUtil.listDel(userId + RedisKeys.SHUFFLE, poker);
	}
}
