package org.personal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import net.sf.json.JSONArray;

public class CardService extends BaseService{
	
	public  String sendPoker(){
		  //定义HashMap变量用于存储每张牌的编号以及牌型  
	      HashMap<Integer,String> hm = new HashMap<Integer,String>();   
	      //定义ArrayList变量存储牌的编号  
	      ArrayList<Integer> array = new ArrayList<Integer>();  
	      //定义数组存储牌的花色  
	      String[] colors = {"0","1","2","3"};   
	      //定义数组存储牌值  
	      String[] numbers = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};  
	      
	      int index = 0;    
	      //定义编号  
	      for(String number : numbers){    
	          //遍历排值数组  
	          for(String color : colors){   
	              //遍历花色  
	              hm.put(index, color.concat(":").concat(number));  
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
	      
	      List<String> playerOne = new ArrayList<String>();
	      List<String> playerTwo = new ArrayList<String>();
	      List<String> dipai = new ArrayList<String>();
	      for(int x = 0; x < array.size(); x++){
	        if(x >= array.size() - 7){  
	        dipai.add(hm.get(array.get(x)));  
	        }else if( x % 2 == 0){  
	            playerOne.add(hm.get(array.get(x)));  
	            }else if(x % 2 == 1){  
	                playerTwo.add(hm.get(array.get(x)));  
	                }
	      }
	      
	      //list转成json
	      String json1 =JSONArray.fromObject(playerOne).toString();
	  
	      String json2 = JSONArray.fromObject(playerTwo).toString();
	      
	      String json3 = JSONArray.fromObject(dipai).toString();
	      
	      String str = json1 + json2 + json3;
	      
//	      Map<String, String> msgJsonParam = new HashMap<>();
	      
	      return str;
	      
	      
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
	      //打印玩家名称  
	      for(Integer key : ts){    
	          //遍历玩家TreeSet集合，获得玩家的牌的编号  
	          String value = hm.get(key);  
	          //根据玩家牌编号获取具体的牌值  
	          System.out.print(value+"  ");  
	          //打印  
	          }  
	      System.out.println();  
	      } 
	  
}
