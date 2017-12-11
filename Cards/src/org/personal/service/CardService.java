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
	      int[] colors = {0,1,2,3};   
	      //定义数组存储牌值  
	      int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	      //定义扑克牌的状态
	      String[] state = {"front","opposite"};
	      
	      int index = 0; 
	      //定义编号  
	      for(int number : numbers){    
	          //遍历排值数组  
	          for(int color : colors){   
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
	      for(int i = 0; i < hm.size();i++){
	    	  DBUtil.GetInstance().savePoker(userId, hm.get(array.get(i)));
	    	  System.out.println(hm.get(array.get(i)).getPokerId()+"--"+hm.get(array.get(i)).getNumber()+"--"+hm.get(array.get(i)).getColor());
	      }
	      Collections.shuffle(array);

	      DBUtil.GetInstance().init();
	      List<String> pokerShuffle = new ArrayList<>();
	      List<String> pokerHandler = new ArrayList<>();
	      
	      List<Poker> pokerFrontHandler = new ArrayList<Poker>();
	      List<Poker> pokerOppositeHandler = new ArrayList<Poker>();
	      //分出洗牌区24和手牌区28的牌,并留出七张正面朝上的牌
	      for( int i = 0; i < array.size(); i++){
	    	  if( i < POKER_HANDLER){
	    		  if( i >= POKER_HANDLER - 7){
	    			  pokerFrontHandler.add(hm.get(array.get(i)));
//	    			  System.out.println(hm.get(array.get(i)).getNumber()+":"+hm.get(array.get(i)).getDirection()+":"+hm.get(array.get(i)).getColor());
	    			  pokerHandler.add(hm.get(array.get(i)).getPokerId()+":"+hm.get(array.get(i)).getDirection());
//	    			  DBUtil.GetInstance().addPokerToRoom1(userId, hm.get(array.get(i)));
	    			  continue;
		    	  }
	    		  pokerOppositeHandler.add(hm.get(array.get(i)));
	    		  hm.get(array.get(i)).setDirection("opposite");
	    		  pokerHandler.add(hm.get(array.get(i)).getPokerId()+":"+hm.get(array.get(i)).getDirection());
//		    	  DBUtil.GetInstance().addPokerToRoom1(userId, hm.get(array.get(i)));
	    	  }else{
	    		  pokerShuffle.add(hm.get(array.get(i)).getPokerId()+":"+hm.get(array.get(i)).getDirection());
		    	  DBUtil.GetInstance().addPokerToShuffle(userId, hm.get(array.get(i)));
	    	  }
	      }
	    //把洗牌区的牌分为七份，存到redis中
	    List<String> pokerHandlerList1 = new ArrayList<String>();
	    List<String> pokerHandlerList2 = new ArrayList<String>();
	    List<String> pokerHandlerList3 = new ArrayList<String>();
	    List<String> pokerHandlerList4 = new ArrayList<String>();
	    List<String> pokerHandlerList5 = new ArrayList<String>();
	    List<String> pokerHandlerList6 = new ArrayList<String>();
	    List<String> pokerHandlerList7 = new ArrayList<String>();
	    
	    int frontNum = pokerFrontHandler.size();
		for (int i = 0; i < frontNum; i++) {
			//使用明确数值比较的条件语句时，从效率跟时间上，switch效率要优于if
			switch (i) {
			case 0:
				pokerHandlerList1.add(pokerFrontHandler.get(i).getPokerId()+":"+pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom7(userId, pokerFrontHandler.get(i));
				continue;
			case 1:
				pokerHandlerList2.add(pokerFrontHandler.get(i).getPokerId()+":"+pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom6(userId, pokerFrontHandler.get(i));
				continue;
			case 2:
				pokerHandlerList3.add(pokerFrontHandler.get(i).getPokerId()+":"+pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom5(userId, pokerFrontHandler.get(i));
				continue;
			case 3:
				pokerHandlerList4.add(pokerFrontHandler.get(i).getPokerId()+":"+pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom4(userId, pokerFrontHandler.get(i));
				continue;
			case 4:
				pokerHandlerList5.add(pokerFrontHandler.get(i).getPokerId()+":"+pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom3(userId, pokerFrontHandler.get(i));
				continue;
			case 5:
				pokerHandlerList6.add(pokerFrontHandler.get(i).getPokerId()+":"+pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom2(userId, pokerFrontHandler.get(i));
				continue;
			default:
				pokerHandlerList7.add(pokerFrontHandler.get(i).getPokerId()+":"+pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom1(userId, pokerFrontHandler.get(i));
			}
		}
	      int oppositeNum = pokerOppositeHandler.size();
	      for(int i = 0; i < oppositeNum;i++){
	    	  if( i >= oppositeNum-6){
	    		  pokerHandlerList7.add(pokerOppositeHandler.get(i).getPokerId()+":"+pokerOppositeHandler.get(i).getDirection());
	    		  DBUtil.GetInstance().addPokerToRoom7(userId, pokerOppositeHandler.get(i));
	    	  }
	    	  else if( i >= oppositeNum-11){
	    		  pokerHandlerList6.add(pokerOppositeHandler.get(i).getPokerId()+":"+pokerOppositeHandler.get(i).getDirection());
	    		  DBUtil.GetInstance().addPokerToRoom6(userId, pokerOppositeHandler.get(i));
	    	  }
	    	  else if( i >= oppositeNum-15){
	    		  pokerHandlerList5.add(pokerOppositeHandler.get(i).getPokerId()+":"+pokerOppositeHandler.get(i).getDirection());
	    		  DBUtil.GetInstance().addPokerToRoom5(userId, pokerOppositeHandler.get(i));
	    	  }
	    	  else if( i >= oppositeNum-18){
	    		  pokerHandlerList4.add(pokerOppositeHandler.get(i).getPokerId()+":"+pokerOppositeHandler.get(i).getDirection());
	    		  DBUtil.GetInstance().addPokerToRoom4(userId, pokerOppositeHandler.get(i));
	    	  }
	    	  else if( i >= oppositeNum-20){
	    		  pokerHandlerList3.add(pokerOppositeHandler.get(i).getPokerId()+":"+pokerOppositeHandler.get(i).getDirection());
	    		  DBUtil.GetInstance().addPokerToRoom3(userId, pokerOppositeHandler.get(i));
	    	  }
	    	  else{
	    		  pokerHandlerList2.add(pokerOppositeHandler.get(i).getPokerId()+":"+pokerOppositeHandler.get(i).getDirection());
	    		  DBUtil.GetInstance().addPokerToRoom2(userId, pokerOppositeHandler.get(i));
	    	  }
	      }
	      
	      Gson gson = new Gson();
	      //七个手牌区的数据
//	      String jsonPokerHandler = gson.toJson(pokerHandler);
//	      String jsonPokerHandler = pokerHandler.toString();
	      String h1 = pokerHandlerList1.toString();
	      String h2 = pokerHandlerList2.toString();
	      String h3 = pokerHandlerList3.toString();
	      String h4 = pokerHandlerList4.toString();
	      String h5 = pokerHandlerList5.toString();
	      String h6 = pokerHandlerList6.toString();
	      String h7 = pokerHandlerList7.toString();
	      
	      //洗牌区的json数据
//	      String jsonPokerShuffle = gson.toJson(pokerShuffle);
	      String jsonPokerShuffle = pokerShuffle.toString();
	      Map<String, String> pokerJsonParam = new HashMap<String, String>();
		  pokerJsonParam.put("shufflePokerList", jsonPokerShuffle);
		  pokerJsonParam.put("handPokerList1", h1);
		  pokerJsonParam.put("handPokerList2", h2);
		  pokerJsonParam.put("handPokerList3", h3);
		  pokerJsonParam.put("handPokerList4", h4);
		  pokerJsonParam.put("handPokerList5", h5);
		  pokerJsonParam.put("handPokerList6", h6);
		  pokerJsonParam.put("handPokerList7", h7);
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
	public boolean moveCardsToPokerRoom(String userId,String pokerId, String targetPokerId, int move_position,int target_position) {
		Poker poker = DBUtil.GetInstance().getPoker(userId, pokerId);
		Poker targetPoker = DBUtil.GetInstance().getPoker(userId, targetPokerId);
		
		if ("opposite".equals(poker.getDirection()) || "opposite".equals(targetPoker.getDirection())) {
			logger.error("can't move the opposite card");
			return false;
		}
		//移动牌到手牌区
		if(target_position < 8){
			movePokerToRoom(userId, target_position, move_position, poker, targetPoker);
		}
		//移动牌到存牌区
		if(target_position >= 8){
			movePokerToHome(target_position, move_position, userId, poker, targetPoker);
		}
		return true;
	}
	
	private void movePokerToRoom(String userId, int move_position, int target_position, Poker poker,Poker targetPoker) {
		switch(target_position){
		case 1:
			if(DBUtil.GetInstance().getPokerRoom1List(userId).size() == 0){
				if(poker.getNumber() == 13){
					DBUtil.GetInstance().addPokerToRoom1(userId, poker);
					break;
				}
				logger.error("");//TODO 不同的errorcode
				break;
			}else{
				if(comparePokerOfHandle(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToRoom1(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 2:
			if(DBUtil.GetInstance().getPokerRoom2List(userId).size() == 0){
				if(poker.getNumber() == 13){
					DBUtil.GetInstance().addPokerToRoom2(userId, poker);
					break;
				}
				logger.error("");//TODO 不同的errorcode
				break;
			}else{
				if(comparePokerOfHandle(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToRoom2(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 3:
			if(DBUtil.GetInstance().getPokerRoom3List(userId).size() == 0){
				if(poker.getNumber() == 13){
					DBUtil.GetInstance().addPokerToRoom3(userId, poker);
					break;
				}
				logger.error("");//TODO 不同的errorcode
				break;
			}else{
				if(comparePokerOfHandle(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToRoom3(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 4:
			if(DBUtil.GetInstance().getPokerRoom4List(userId).size() == 0){
				if(poker.getNumber() == 13){
					DBUtil.GetInstance().addPokerToRoom4(userId, poker);
					break;
				}
				logger.error("");//TODO 不同的errorcode
				break;
			}else{
				if(comparePokerOfHandle(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToRoom4(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 5:
			if(DBUtil.GetInstance().getPokerRoom5List(userId).size() == 0){
				if(poker.getNumber() == 13){
					DBUtil.GetInstance().addPokerToRoom5(userId, poker);
					break;
				}
				logger.error("");//TODO 不同的errorcode
				break;
			}else{
				if(comparePokerOfHandle(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToRoom5(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 6:
			if(DBUtil.GetInstance().getPokerRoom6List(userId).size() == 0){
				if(poker.getNumber() == 13){
					DBUtil.GetInstance().addPokerToRoom6(userId, poker);
					break;
				}
				logger.error("");//TODO 不同的errorcode
				break;
			}else{
				if(comparePokerOfHandle(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToRoom6(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 7:
			if(DBUtil.GetInstance().getPokerRoom7List(userId).size() == 0){
				if(poker.getNumber() == 13){
					DBUtil.GetInstance().addPokerToRoom7(userId, poker);
					break;
				}
				logger.error("");//TODO 不同的errorcode
				break;
			}else{
				if(comparePokerOfHandle(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToRoom7(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		}
		
	}
	//移动牌到存牌区
	public void movePokerToHome(int target_position, int move_position, String userId,Poker poker,Poker targetPoker){
		switch(target_position){
		case 8:
			if(DBUtil.GetInstance().getHome1List(userId).size() == 0){
				if(poker.getNumber() != 1){
					logger.error("");//TODO 不同的errorcode
					break;
				}
				DBUtil.GetInstance().addPokerToHome1(userId, poker);
			}else{
				if(comparePokerOfHome(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToHome1(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 9:
			if(DBUtil.GetInstance().getHome2List(userId).size() == 0){
				if(poker.getNumber() != 1){
					logger.error("");
					break;
				}
				DBUtil.GetInstance().addPokerToHome2(userId, poker);
			}else{
				if(comparePokerOfHome(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToHome2(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 10:
			if(DBUtil.GetInstance().getHome3List(userId).size() == 0){
				if(poker.getNumber() != 1){
					logger.error("");
					break;
				}
				DBUtil.GetInstance().addPokerToHome3(userId, poker);
			}else{
				if(comparePokerOfHome(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToHome3(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		case 11:
			if(DBUtil.GetInstance().getHome4List(userId).size() == 0){
				if(poker.getNumber() != 1){
					logger.error("");
					break;
				}
				DBUtil.GetInstance().addPokerToHome4(userId, poker);
			}else{
				if(comparePokerOfHome(poker,targetPoker)){
					removeCardFromHandlerOrShuffle(poker,move_position,userId);
					DBUtil.GetInstance().addPokerToHome4(userId, poker);
					break;
				}
				logger.error("");
			}
		break;
		}
	}
	
	private void removeCardFromHandlerOrShuffle(Poker poker, int move_position,String userId) {
		switch(move_position){
		case 0:
			DBUtil.GetInstance().deletePokerFromShuffle(userId, poker);
			break;
		case 1:
			DBUtil.GetInstance().deletePokerFromRoom1(userId, poker);
			break;
		case 2:
			DBUtil.GetInstance().deletePokerFromRoom2(userId, poker);
			break;
		case 3:
			DBUtil.GetInstance().deletePokerFromRoom3(userId, poker);
			break;
		case 4:
			DBUtil.GetInstance().deletePokerFromRoom4(userId, poker);
			break;
		case 5:
			DBUtil.GetInstance().deletePokerFromRoom5(userId, poker);
			break;
		case 6:
			DBUtil.GetInstance().deletePokerFromRoom6(userId, poker);
			break;
		case 7:
			DBUtil.GetInstance().deletePokerFromRoom7(userId, poker);
			break;
		}
	
	}
	//比较牌的大小和花色（存牌区）
	public boolean comparePokerOfHome(Poker poker,Poker targetPoker){
		if(poker.getNumber() == targetPoker.getNumber() - 1 && poker.getColor() == targetPoker.getColor()){
			return true;
		}else{
			return false;
		}
	}
	
	//手牌区
	public boolean comparePokerOfHandle(Poker poker,Poker targetPoker){
		//判断数字和花色  与1进行按与运算，运算结果为1则是奇数，0则为偶数。
		if(poker.getNumber() == targetPoker.getNumber() - 1 && (poker.getColor() & 1) == (targetPoker.getColor() & 1)){
			return true;
		}else{
			return false;
		}
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
	      int[] colors = {0,1,2,3};   
	      //定义数组存储牌值  
	      int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	      //定义扑克牌的状态
	      String[] state = {"front","opposite"};
	      
	      int index = 0; 
	      //定义编号  
	      for(int number : numbers){    
	          //遍历排值数组  
	          for(int color : colors){   
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
