package org.personal.service;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.personal.db.dao.Poker;
import org.personal.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;

public class CardService extends BaseService{
	
	private static final Logger logger = LoggerFactory.getLogger(CardService.class);
	
	private static final int POKER_SHUFFLE = 24;
	
	public Container container=null;
	public static List<Poker> tablelist[] = new ArrayList[7];//装未发的牌堆 7堆
	public static List<Poker> waitlist = new ArrayList<Poker>();//装未发的牌堆
	public static List<Poker> dragList=new ArrayList<Poker>();
	
	public  String sendPoker(){
		  //定义HashMap变量用于存储每张牌的编号以及牌型  
	      HashMap<Integer,String> hm = new HashMap<Integer,String>();   
	      //定义ArrayList变量存储牌的编号  
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
	              hm.put(index, color.concat("-").concat(number));  
	              //将花色与牌值拼接，并将编号与拼接后的结果存储到hm中  
	              array.add(index);   
	              //将编号存储到array中  
	              index++;}  
	          }
	      
	      /* * 将小王和大王存储到hm中 */  
//	      hm.put(index, "4".concat(":").concat("SJoker"));    
//	      array.add(index);  
//	      index++;  
//	      hm.put(index, "4".concat(":").concat("BJoker"));  
//	      array.add(index);  
	      Collections.shuffle(array);   
	      
	      List<String> pokerShuffle = new ArrayList<String>();
	      List<String> pokerHandler = new ArrayList<String>();
	      
	      //先留出在洗牌区的24张牌
	      for( int i =0; i < POKER_SHUFFLE; i++){
	    	  pokerShuffle.add(hm.get(array.get(i)));
	      }
	      String jsonPokerShuffle = JSONArray.fromObject(pokerShuffle).toString();  
	      
	      //分出手牌区里面的7张朝上的牌和21张朝下的牌
	      for( int i = POKER_SHUFFLE; i < array.size(); i++){
	    	  if( i >= array.size() - 7){
	    		  pokerHandler.add(hm.get(array.get(i)));
	    	  }else{
	    		  pokerHandler.add(hm.get(array.get(i)).concat("-").concat("opposite"));
	    	  }
	      }
	      String jsonPokerHandler = JSONArray.fromObject(pokerHandler).toString();

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
			 logger.error("error,the color is same, move_color = {},target_color = {}",listcolor1,listcolor2);
		  }
		  
		  int listnum1 = Integer.valueOf(list1_num);
		  int listnum2 = Integer.valueOf(list2_num);
		  if(listnum1+1 == listnum2){
			  logger.error("error,only can be moved when the target card is the next num of current card,move_num = {},target_num={}",listnum1,listnum2);
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
				  logger.error("can't put card which is not A in the empty cardHome");
			  }
			  cardHome = cardHome.concat(",").concat(card);
			  return cardHome;
		  }
		  String currentCard = cardHomeList[cardHomeList.length-1];
		  String[] currentCardList =  currentCard.split("-");
		  String currentCardN = currentCardList[1];
		  int currentCardnum = Integer.valueOf(currentCardN);
		  
		  if( num != currentCardnum+1){
			  logger.error("must put the card into the cardHome in order of num, the movecardnum:{}",num);
		  }
		  cardHome = cardHome.concat(",").concat(card);
		  return cardHome;
	  }
	  
	  public boolean isCorrenctCardHome(String pokerHomeList){
		  
		  return false;
	  }
	  
	  public boolean isWon(String cardHome){
		for (int i = 0; i < 7; i++) {
			if(!CardMain.tablelist[i].isEmpty())
				return false;
		}
		return true;
	  }
}
