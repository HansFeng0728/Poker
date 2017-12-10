package org.personal.service;

import java.awt.Container;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.personal.db.DBUtil;
import org.personal.db.dao.Poker;
import org.personal.db.dao.PokerList;
import org.personal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

public class CardService extends BaseService{
	
	private static final int POKER_HANDLER = 28;
	
	public Container container=null;
	
	static Logger logger = LoggerFactory.getLogger(CardService.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	public  Map<String, String> sendPoker(String userId) throws JsonGenerationException, JsonMappingException, IOException{
		  //定义HashMap变量用于存储每张牌的编号以及牌型  
	      HashMap<Integer,Poker> hm = new HashMap<Integer,Poker>();   
	      //定义变量存储洗牌区牌的编号  
	      ArrayList<Integer> array = new ArrayList<Integer>();  
	      //定义数组存储牌的花色  
	      String[] colors = {"0","1","2","3"};   
	      //定义数组存储牌值  
	      int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	      //定义扑克牌的状态
	      String[] state = {"front","opposite"};
	      
	      int index = 0; 
	      //定义编号  
	      for(int number : numbers){    
	          //遍历排值数组  
	          for(String color : colors){   
	              //遍历花色  
	        	  Poker poker = new Poker();
	        	  poker.setUserId(userId);
	        	  poker.setPokerId(index);
	        	  poker.setNumber(number);
	        	  poker.setColor(color);
	        	  poker.setDirection("front");
	        	  
	        	  hm.put(index, poker);
	              array.add(index);   
	              index++;
	          }  
	      }
	      Collections.shuffle(array);

	      DBUtil.GetInstance().init();
	      List<String> pokerShuffle = new ArrayList<>();
	      List<String> pokerHandler = new ArrayList<>();
	      
	      List<Poker> pokerFrontHandler = new ArrayList();
	      //分出洗牌区24和手牌区28的牌,并留出七张正面朝上的牌
	      for( int i = 0; i < array.size(); i++){
	    	  if( i < POKER_HANDLER){
	    		  if( i >= POKER_HANDLER - 7){
	    			  pokerFrontHandler.add(hm.get(array.get(i)));
	    			  pokerHandler.add(hm.get(array.get(i)).getPokerId()+":"+hm.get(array.get(i)).getDirection());
	    			  DBUtil.GetInstance().addPokerToRoom1(userId, hm.get(array.get(i)));
		    		  continue;
		    	  }
	    		  
	    		  hm.get(array.get(i)).setDirection("opposite");
	    		  pokerHandler.add(hm.get(array.get(i)).getPokerId()+":"+hm.get(array.get(i)).getDirection());
		    	  DBUtil.GetInstance().addPokerToRoom1(userId, hm.get(array.get(i)));
		    	  continue;
	    	  }
	    	  pokerShuffle.add(hm.get(array.get(i)).getPokerId()+":"+hm.get(array.get(i)).getDirection());
	    	  DBUtil.GetInstance().addPokerToShuffle(userId, hm.get(array.get(i)));
	      }
	      //把洗牌区的牌分为七份，存到redis中
	      for(int i = 0; i < pokerFrontHandler.size();i++){
	      	  DBUtil.GetInstance().addPokerToRoom1(userId, hm.get(pokerHandler.get(0)));
	      }
	      
	      Gson gson = new Gson();
	      //七个手牌区的数据
//	      String jsonPokerHandler = gson.toJson(pokerHandler);
	      String jsonPokerHandler = pokerHandler.toString();
	      //洗牌区的json数据
//	      String jsonPokerShuffle = gson.toJson(pokerShuffle);
	      String jsonPokerShuffle = pokerShuffle.toString();
	      Map<String, String> pokerJsonParam = new HashMap<String, String>();
		  pokerJsonParam.put("shufflePokerList", jsonPokerShuffle);
		  pokerJsonParam.put("handPokerList", jsonPokerHandler);
		  pokerJsonParam.put("userId", userId);
//		  String pokerJson = JsonUtil.encodeJson(pokerJsonParam); 
//		  String pokerJson = mapper.writeValueAsString(pokerJsonParam);
//		  String pokerJson = pokerJsonParam.toString();
	      
	      return pokerJsonParam;
//	      //二人斗地主的发牌  弃用
//	      List<String> playerOne = new ArrayList<String>();
//	      List<String> playerTwo = new ArrayList<String>();
//	      List<String> dipai = new ArrayList<String>();
//	      for(int x = 0; x < array.size(); x++){
//	        if(x >= array.size() - 7){  
//	        dipai.add(hm.get(array.get(x)));  
//	        }else if( x % 2 == 0){  
//	            playerOne.add(hm.get(array.get(x)));  
//	            }else if(x % 2 == 1){  
//	                playerTwo.add(hm.get(array.get(x)));  
//	                }
//	      }
//	      //list转成json
//	      String json1 =JSONArray.fromObject(playerOne).toString();
//	  
//	      String json2 = JSONArray.fromObject(playerTwo).toString();
//	      
//	      String json3 = JSONArray.fromObject(dipai).toString();
//	      
//	      String str = json1 + json2 + json3;
//	      return str;
	      
	  //之前的socket 时传byte[]的方法  弃用    
//	      //调用Collections集合的shuffle()方法，将array中存储的编号进行随机的置换，即打乱顺序  
//	      /* * 定义TreeSet集合的变量用于存储底牌编号以及玩家的牌的编号 * 采用TreeSet集合是因为TreeSet集合可以实现自然排序 */  
//	      TreeSet<Integer> playerOne = new TreeSet<Integer>();  
//	      TreeSet<Integer> PlayerTwo = new TreeSet<Integer>();  
//	      TreeSet<Integer> dipai = new TreeSet<Integer>();  
//	      //遍历编号的集合，实现发牌  
//	      for(int x = 0; x < array.size(); x++){  
//	          if(x >= array.size() - 6){  
//	              dipai.add(array.get(x));  
//	              }else if( x % 2 == 0){  
//	                  playerOne.add(array.get(x));  
//	                  }else if(x % 2 == 1){  
//	                      PlayerTwo.add(array.get(x));  
//	                      }
//	      } 
//	      //把结果存在String内
//	      StringBuilder buf = new StringBuilder();
//	      buf.append('[');
////	    buf.append(array.get(0));
	//
//	      //把所有的卡牌放在String字符串里面
//	      for(int i=0; i<array.size();i++){
//	    	  buf.append(array.get(i));
//	    	  buf.append(",");
//	    	  if( i == array.size()-1){
//	    		  buf.append(array.get(i));
//	    	  }
//	    	  System.out.println("----"+array.get(i)+"--第："+i+"个");
//	      }
//	      buf.append("]");
//	      return buf.toString().getBytes();
	  }
	  /** * 遍历每个玩家的牌以及底牌 **/  
	  public static void lookPoker(String name,TreeSet<Integer> ts,HashMap<Integer,String> hm){  
	  System.out.print(name+":\t");    
      for(Integer key : ts){    
          //遍历玩家TreeSet集合，获得玩家的牌的编号  
          String value = hm.get(key);  
          //根据玩家牌编号获取具体的牌值  
          System.out.print(value+"  ");  
          }  
      System.out.println();  
      }
	  
	  
//----------------------------------------------------------移牌逻辑--------------------------------------------------	  
	//移动卡牌到七个手牌区之一(1.从洗牌区移入 2.在手牌区互相移动)
	//position 只有在向空白的位置移动时才会进行判断
	//这里的poker不用判断为空，是在上一步解析中拼接的
	public boolean moveCardsToPokerRoom(Poker poker, Poker targetPoker, String position) {
		if ("opposite".equals(poker.getDirection()) || "opposite".equals(targetPoker.getDirection())) {
			logger.error("can't move the opposite card");
			return false;
		}
		
		if(!poker.getColor().equals(targetPoker.getColor())){
			return false;
		}
		
		if(!(poker.getNumber() == targetPoker.getNumber()-1)){
			logger.error("can't put the poker into the false number");
			return false;
		}
		return true;
	}
	  
	  //移动卡牌到四个存牌区之一
	  //空位置只能从A开始放，从小到大依次放
	  public boolean moveToCardHome(Poker poker,Poker targetPoker, String position, String cardHome){
		  if(targetPoker == null){
			  if(poker.getNumber() != 1){
				  logger.error("can't put number except A to the null position");
				  return false;
			  }
		  }else{
			  if(poker.getNumber()-1 != targetPoker.getNumber()){
				  logger.error("must put the card in order of number");
				  return false;
			  }
		  }
		  return true;
	  }
	  public boolean isCorrenctCardHome(String pokerHomeList){
		  return false;
	  }

//-----------------------------------------------------胜利判断逻辑--------------------------------------------------------------
	  //判断是否胜利，七个手牌区和洗牌区的均没有卡牌
	  public boolean isWon(String userId){
		List<Poker> rList1 = DBUtil.GetInstance().getPokerRoom1List(userId);
		List<Poker> rList2 = DBUtil.GetInstance().getPokerRoom2List(userId);
		List<Poker> rList3 = DBUtil.GetInstance().getPokerRoom3List(userId);
		List<Poker> rList4 = DBUtil.GetInstance().getPokerRoom4List(userId);
		List<Poker> rList5 = DBUtil.GetInstance().getPokerRoom5List(userId);
		List<Poker> rList6 = DBUtil.GetInstance().getPokerRoom6List(userId);
		List<Poker> rList7 = DBUtil.GetInstance().getPokerRoom7List(userId);
		
		List<Poker> sList = DBUtil.GetInstance().getShuffleList(userId);
		if(rList1 == null && rList2 == null && rList3 == null && rList4 == null){
			if(rList5 == null && rList6 == null && rList7 == null){
				if(sList == null){
					return true;
				}
			}
		}
		return false;
	  }
	  
	  public boolean isFailed(String userId,List<Poker> allPokers){
		  
		  return true;
	  }
	  
	  //洗牌  存入数据库  
	  public void initPokerList(String pokerId){
	      HashMap<Integer,Poker> hm = new HashMap<Integer,Poker>();   
	      //定义变量存储洗牌区牌的编号  
	      ArrayList<Integer> array = new ArrayList<Integer>();  
	      //定义数组存储牌的花色  
	      String[] colors = {"0","1","2","3"};   
	      //定义数组存储牌值  
	      int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	      //定义扑克牌的状态
	      String[] state = {"front","opposite"};
	      
	      int index = 0; 
	      //定义编号  
	      for(int number : numbers){    
	          //遍历排值数组  
	          for(String color : colors){   
	              //遍历花色  
	        	  Poker poker = new Poker();
	        	  poker.setPokerId(index);
	        	  poker.setNumber(number);
	        	  poker.setColor(color);
	        	  poker.setDirection("front");
	        	  
	        	  hm.put(index, poker);
	              array.add(index);   
	              index++;
	          }  
	      }
	      Collections.shuffle(array);
	      PokerList pp = new PokerList();
	      pp.setContent(hm.toString());
	      pp.setPokerId(pokerId);
	  }
}
