package org.personal.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.personal.db.dao.Poker;
import org.personal.db.dao.PokerRoom;
import org.personal.db.dao.ShuffleRoom;
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
	
//--------------------------------------------七个移牌区的牌----------------------------------------------
	//--------------------------------------------------------room1
	public void addPokerToRoom1(final PokerRoom pokerRoom,final Poker poker)
	{	
		redisUtil.listAdd(pokerRoom.getUserId() + RedisKeys.POKERHOME_1,poker);
	}
	
	public List<Poker> getPokerRoom1List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_1, Poker.class);
	}
	
	public void deletePokerFromRoom1(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_1, poker);
	}
	
	//-------------------------------------------------------room2
	public void addPokerToRoom2(final PokerRoom pokerRoom,final Poker poker)
	{	
		redisUtil.listAdd(pokerRoom.getUserId() + RedisKeys.POKERHOME_2,poker);
	}
	
	public List<Poker> getPokerRoom2List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_2, Poker.class);
	}
	
	public void deletePokerFromRoom2(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_2, poker);
	}
	
	//--------------------------------------------------------room3
	public void addPokerToRoom3(final PokerRoom pokerRoom,final Poker poker)
	{	
		redisUtil.listAdd(pokerRoom.getUserId() + RedisKeys.POKERHOME_3,poker);
	}
	
	public List<Poker> getPokerRoom3List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_3, Poker.class);
	}
	
	public void deletePokerFromRoom3(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_3, poker);
	}
	
	//--------------------------------------------------------room4
	public void addPokerToRoom4(final PokerRoom pokerRoom,final Poker poker)
	{	
		redisUtil.listAdd(pokerRoom.getUserId() + RedisKeys.POKERHOME_4,poker);
	}
	
	public List<Poker> getPokerRoom4List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_4, Poker.class);
	}
	
	public void deletePokerFromRoom4(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_4, poker);
	}
	
	//--------------------------------------------------------room5
	public void addPokerToRoom5(final PokerRoom pokerRoom,final Poker poker)
	{	
		redisUtil.listAdd(pokerRoom.getUserId() + RedisKeys.POKERHOME_5,poker);
	}
	
	public List<Poker> getPokerRoom5List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_5, Poker.class);
	}
	
	public void deletePokerFromRoom5(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_5, poker);
	}
	
	//-------------------------------------------------------room6
	public void addPokerToRoom6(final PokerRoom pokerRoom,final Poker poker)
	{	
		redisUtil.listAdd(pokerRoom.getUserId() + RedisKeys.POKERHOME_6,poker);
	}
	
	public List<Poker> getPokerRoom8List(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_6, Poker.class);
	}
	
	public void deletePokerFromRoom6(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_6, poker);
	}
	
	//-------------------------------------------------------room7
	public void addPokerToRoom7(final PokerRoom pokerRoom,final Poker poker)
	{	
		redisUtil.listAdd(pokerRoom.getUserId() + RedisKeys.POKERHOME_7,poker);
	}
	
	public List<Poker> getPokerRoomList(String userId)
	{
		return redisUtil.listGet(userId + RedisKeys.POKERHOME_7, Poker.class);
	}
	
	public void deletePokerFromRoom7(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.POKERHOME_7, poker);
	}

//-----------------------------------------洗牌区的牌------------------------------------------------------
	
	public void addPokerToShuffle(final ShuffleRoom shuffleRoom,final Poker poker){
		redisUtil.listAdd(shuffleRoom.getUserId() + RedisKeys.SHUFFLE, poker);
	}
	
	public List<Poker> getShuffleList(String userId){
		return redisUtil.listGet(userId + RedisKeys.SHUFFLE, Poker.class);
	}
	
	public void deletePokerFromShuffle(String userId,Poker poker){
		redisUtil.listDel(userId + RedisKeys.SHUFFLE, poker);
	}
}
