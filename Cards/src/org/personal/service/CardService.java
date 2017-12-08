package org.personal.service;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.personal.db.DBUtil;
import org.personal.db.dao.Poker;
import org.personal.db.dao.PokerList;
import org.personal.util.JsonUtil;

import com.google.gson.Gson;

public class CardService extends BaseService{
	
	private static final int POKER_SHUFFLE = 24;
	
	public Container container=null;
	
	public static void main(String[] args){
		CardService cc = new CardService();
		cc.sendPoker("1001");
	}
	
	public String sendPoker(String userId){
		  //定义HashMap变量用于存储每张牌的编号以及牌型  
	      HashMap<Integer,Poker> hm = new HashMap<Integer,Poker>();   
	      //定义变量存储洗牌区牌的编号  
	      ArrayList<Integer> array = new ArrayList<Integer>();  
	      //定义数组存储牌的花色  
	      String[] colors = {"0","1","2","3"};   
	      //定义数组存储牌值  
	      String[] numbers = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	      //定义扑克牌的状态
	      String[] state = {"front","opposite"};
	      
	      int index = 0; 
	      
	      //定义编号  
	      for(String number : numbers){    
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
	      
	      List<String> pokerHandler = new ArrayList<String>();
	      List<Poker> pokerShuffle = new ArrayList<>();
	      
	      DBUtil.GetInstance().init();
	      
	      
	      //分出洗牌区和手牌区的牌
	      for( int i = 0; i < array.size(); i++){
	    	  if( i < 28){
	    		  if( i >= 21){
	    			  DBUtil.GetInstance().addPokerToRoom1(userId, hm.get(array.get(i)));
		    		  continue;
		    	  }
		    	  hm.get(array.get(i)).setDirection("opposite");
		    	  DBUtil.GetInstance().addPokerToRoom1(userId, hm.get(array.get(i)));
		    	  continue;
	    	  }
	    	  pokerShuffle.add(hm.get(array.get(i)));
//	    	  System.out.println("----"+hm.get(array.get(i)).getNumber()+"----"+hm.get(array.get(i)).getColor()+"---"+hm.get(array.get(i)).getDirection()+"----0-0-"+hm.get(array.get(i)).getPokerId());
	    	  DBUtil.GetInstance().addPokerToShuffle(userId, hm.get(array.get(i)));
	      }
	      
	      Gson gson = new Gson();
	      //洗牌区的json数据
	      String jsonPokerShuffle = gson.toJson(pokerShuffle);
	      
	      //分出手牌区里面的7张朝上的牌和21张朝下的牌
	      for( int i = 0; i < 28; i++){
	    	 
	      }
	      //七个手牌区的数据
	      String jsonPokerHandler = gson.toJson(pokerHandler);

	      Map<String, String> pokerJsonParam = new HashMap<String, String>();
		  pokerJsonParam.put("shufflePokerList", jsonPokerShuffle);
		  pokerJsonParam.put("handPokerList", jsonPokerHandler);
		  String pokerJson = JsonUtil.encodeJson(pokerJsonParam);    
	      
	      return pokerJson;
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
//	      
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
	//    
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
	  
	  //移动卡牌到手牌区
	  public String moveCards(String cardList1,String cardList2){
		  String[] cardlist2 = cardList2.split("-");
		  String list2_color = cardlist2[0];
		  String list2_num = cardlist2[1];
		  
		  String[] cardlist1 = cardList1.split(",");
		  String cardlist_first = cardlist1[0];
		  String[] list1_color_num = cardlist_first.split("-");
		  String list1_color = list1_color_num[0];
		  String list1_num = list1_color_num[1];
		  
		  int listcolor1 = Integer.valueOf(list1_color);
		  int listcolor2 = Integer.valueOf(list2_color);
		  if((listcolor1%2) == (listcolor2%2) ){
			 System.out.println("error,the color is same, move_color = {},target_color = {}"+listcolor1+listcolor2);
		  }
		  
		  int listnum1 = Integer.valueOf(list1_num);
		  int listnum2 = Integer.valueOf(list2_num);
		  if(listnum1+1 == listnum2){
			  System.out.println("error,only can be moved when the target card is the next num of current card,move_num ="+listnum1+"target_num="+listnum2);
		  }
		  cardList2 = cardList2 + cardList1;
		  return cardList2;
	  }
	  
	  //移动卡牌到四个存牌区之一
	  public String moveToCardHome(String card,String cardHome){
		  String[] cardlist = card.split("-");
		  String cardnum = cardlist[1];
		  int num = Integer.valueOf(cardnum);
		  
		  String[] cardHomeList = cardHome.split(",");
		  if( cardHomeList.length < 1){
			  if( num != 1){
				  System.out.println("can't put card which is not A in the empty cardHome");
			  }
			  cardHome = cardHome.concat(",").concat(card);
			  return cardHome;
		  }
		  String currentCard = cardHomeList[cardHomeList.length-1];
		  String[] currentCardList =  currentCard.split("-");
		  String currentCardN = currentCardList[1];
		  int currentCardnum = Integer.valueOf(currentCardN);
		  
		  if( num != currentCardnum+1){
			  System.out.println("must put the card into the cardHome in order of num, the movecardnum:{}"+num);
		  }
		  cardHome = cardHome.concat(",").concat(card);
		  return cardHome;
	  }
	  
	  public boolean isCorrenctCardHome(String pokerHomeList){
		  
		  return false;
	  }
	  
	  public boolean isWon(String cardHome){
		for (int i = 0; i < 7; i++) {
		
		}
		return true;
	  }
	  
	  public void initPokerList(String pokerId){
		  
	      HashMap<Integer,Poker> hm = new HashMap<Integer,Poker>();   
	      //定义变量存储洗牌区牌的编号  
	      ArrayList<Integer> array = new ArrayList<Integer>();  
	      //定义数组存储牌的花色  
	      String[] colors = {"0","1","2","3"};   
	      //定义数组存储牌值  
	      String[] numbers = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
	      //定义扑克牌的状态
	      String[] state = {"front","opposite"};
	      
	      int index = 0; 
	      //定义编号  
	      for(String number : numbers){    
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
	      
	      
	  }
}
