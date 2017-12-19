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
import org.personal.db.DBUtil;
import org.personal.db.dao.Poker;
import org.personal.db.dao.PokerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class CardService extends BaseService{
	
	private static final int POKER_HANDLER = 28;
	
	public Container container=null;
	
	static Logger logger = LoggerFactory.getLogger(CardService.class);
	
	//---------------------------------------------------------------洗牌发牌逻辑----------------------------------------------------------------------------
	public Map<String,String> sendEasyPoker(String userId){
    	// 定义HashMap变量用于存储每张牌的编号以及牌型
		HashMap<Integer, Poker> hm = new HashMap<Integer, Poker>();
		// 定义变量存储洗牌区牌的编号
		ArrayList<Integer> array = new ArrayList<Integer>();
		// 定义数组存储牌的花色
		int[] colors = { 1, 2, 3, 4 };
		// 定义数组存储牌值
		int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		// 定义扑克牌的状态
		String[] state = { "front", "opposite" };

		int index = 8;
		// 定义编号
		for (int number : numbers) {
			if(number == 1 || number == 2){
				continue;
			}
			// 遍历排值数组
			for (int color : colors) {
				// 遍历花色
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
		for (int i = 0; i < hm.size(); i++) {
			DBUtil.GetInstance().savePoker(userId, hm.get(array.get(i)));
			System.out.println("pokerId:" + hm.get(array.get(i)).getPokerId() + "--pokerNumber"+ hm.get(array.get(i)).getNumber() + "--pokerColor" + hm.get(array.get(i)).getColor());
		}
		
		List<Poker> easyList = new ArrayList<>();
		int cc= 0;
		for(int color:colors){
			// 遍历花色
			Poker poker = new Poker();
			poker.setUserId(userId);
			poker.setPokerId(cc);
			poker.setNumber(1);
			poker.setColor(color);
			poker.setDirection("front");
			easyList.add(poker);
			DBUtil.GetInstance().savePoker(userId, poker);
			cc++;
		}
		int ce = 4;
		for(int color:colors){
			// 遍历花色
			Poker poker = new Poker();
			poker.setUserId(userId);
			poker.setPokerId(ce);
			poker.setNumber(2);
			poker.setColor(color);
			poker.setDirection("front");
			easyList.add(poker);
			DBUtil.GetInstance().savePoker(userId, poker);
			ce++;
		}
		
		// 打乱编号，重新排序
		Collections.shuffle(array);
		DBUtil.GetInstance().init();
		System.out.println(array);
		for(int i=0;i<array.size();i++){
			System.out.println("----pokerId"+hm.get(array.get(i)).getPokerId()+"----PokerNumber"+hm.get(array.get(i)).getNumber()+"-----pokerColor"+hm.get(array.get(i)).getColor());
		}
		
		
		List<String> pokerShuffle = new ArrayList<>();
		List<String> pokerHandler = new ArrayList<>();

		List<Poker> pokerFrontHandler = new ArrayList<Poker>();
		List<Poker> pokerOppositeHandler = new ArrayList<Poker>();
		for (int i = 0; i < array.size(); i++) {
			if (i < POKER_HANDLER) {
				if (i >= POKER_HANDLER - 7) {
					pokerFrontHandler.add(hm.get(array.get(i)));
					pokerHandler.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
					continue;
				}
				pokerOppositeHandler.add(hm.get(array.get(i)));
				hm.get(array.get(i)).setDirection("opposite");
				pokerHandler.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
			} else {
				pokerShuffle.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
				DBUtil.GetInstance().addPokerToShuffle(userId, hm.get(array.get(i)));
			}
		}
		
		for(Poker pp : easyList){
			pokerShuffle.add(pp.getPokerId()+ ":" + pp.getDirection());
			DBUtil.GetInstance().addPokerToShuffle(userId, pp);
		}
		
		List<String> pokerHandlerList1 = new ArrayList<String>();
		List<String> pokerHandlerList2 = new ArrayList<String>();
		List<String> pokerHandlerList3 = new ArrayList<String>();
		List<String> pokerHandlerList4 = new ArrayList<String>();
		List<String> pokerHandlerList5 = new ArrayList<String>();
		List<String> pokerHandlerList6 = new ArrayList<String>();
		List<String> pokerHandlerList7 = new ArrayList<String>();

		int frontNum = pokerFrontHandler.size();
		for (int i = 0; i < frontNum; i++) {
			// 使用明确数值比较的条件语句时，从效率跟时间上，switch效率要优于if
			switch (i) {
			case 0:
				pokerHandlerList1.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom1(userId, pokerFrontHandler.get(i));
				continue;
			case 1:
				pokerHandlerList2.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom2(userId, pokerFrontHandler.get(i));
				continue;
			case 2:
				pokerHandlerList3.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom3(userId, pokerFrontHandler.get(i));
				continue;
			case 3:
				pokerHandlerList4.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom4(userId, pokerFrontHandler.get(i));
				continue;
			case 4:
				pokerHandlerList5.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom5(userId, pokerFrontHandler.get(i));
				continue;
			case 5:
				pokerHandlerList6.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom6(userId, pokerFrontHandler.get(i));
				continue;
			default:
				pokerHandlerList7.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom7(userId, pokerFrontHandler.get(i));
			}
		}
		int oppositeNum = pokerOppositeHandler.size();
		for (int i = 0; i < oppositeNum; i++) {
			if (i >= oppositeNum - 6) {
				pokerHandlerList7.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom7(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 11) {
				pokerHandlerList6.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom6(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 15) {
				pokerHandlerList5.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom5(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 18) {
				pokerHandlerList4.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom4(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 20) {
				pokerHandlerList3.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom3(userId, pokerOppositeHandler.get(i));
			} else {
				pokerHandlerList2.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom2(userId, pokerOppositeHandler.get(i));
			}
		}

		Gson gson = new Gson();
		// 七个手牌区的数据
		String h1 = pokerHandlerList1.toString();
		String h2 = pokerHandlerList2.toString();
		String h3 = pokerHandlerList3.toString();
		String h4 = pokerHandlerList4.toString();
		String h5 = pokerHandlerList5.toString();
		String h6 = pokerHandlerList6.toString();
		String h7 = pokerHandlerList7.toString();

		// 洗牌区的json数据
		Map<String, String> pokerJsonParam = new HashMap<String, String>();
		String shuffle = pokerShuffle.toString();
		pokerJsonParam.put("shufflePokerList", shuffle);
		pokerJsonParam.put("handPokerList1", h1);
		pokerJsonParam.put("handPokerList2", h2);
		pokerJsonParam.put("handPokerList3", h3);
		pokerJsonParam.put("handPokerList4", h4);
		pokerJsonParam.put("handPokerList5", h5);
		pokerJsonParam.put("handPokerList6", h6);
		pokerJsonParam.put("handPokerList7", h7);
		pokerJsonParam.put("userId", userId);
		return pokerJsonParam;
	}
	
	public Map<String,String> sendHardPoker(String userId){
    	// 定义HashMap变量用于存储每张牌的编号以及牌型
		HashMap<Integer, Poker> hm = new HashMap<Integer, Poker>();
		// 定义变量存储洗牌区牌的编号
		ArrayList<Integer> array = new ArrayList<Integer>();
		// 定义数组存储牌的花色
		int[] colors = { 1, 2, 3, 4 };
		// 定义数组存储牌值
		int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		// 定义扑克牌的状态
		String[] state = { "front", "opposite" };

		int index = 0;
		// 定义编号
		for (int number : numbers) {
			if(number == 13){
				continue;
			}
			// 遍历排值数组
			for (int color : colors) {
				// 遍历花色
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
		for (int i = 0; i < hm.size(); i++) {
			DBUtil.GetInstance().savePoker(userId, hm.get(array.get(i)));
			System.out.println("pokerId:" + hm.get(array.get(i)).getPokerId() + "--pokerNumber"+ hm.get(array.get(i)).getNumber() + "--pokerColor" + hm.get(array.get(i)).getColor());
		}
		
		List<Poker> hardList = new ArrayList<>();
		int cc= 48;
		for(int color:colors){
			// 遍历花色
			Poker poker = new Poker();
			poker.setUserId(userId);
			poker.setPokerId(cc);
			poker.setNumber(13);
			poker.setColor(color);
			poker.setDirection("front");
			hardList.add(poker);
			DBUtil.GetInstance().savePoker(userId, poker);
			cc++;
		}
	
		
		// 打乱编号，重新排序
		Collections.shuffle(array);
		DBUtil.GetInstance().init();
		System.out.println(array);
		for(int i=0;i<array.size();i++){
			System.out.println("----pokerId"+hm.get(array.get(i)).getPokerId()+"----PokerNumber"+hm.get(array.get(i)).getNumber()+"-----pokerColor"+hm.get(array.get(i)).getColor());
		}
		
		
		List<String> pokerShuffle = new ArrayList<>();
		List<String> pokerHandler = new ArrayList<>();

		List<Poker> pokerFrontHandler = new ArrayList<Poker>();
		List<Poker> pokerOppositeHandler = new ArrayList<Poker>();
		for (int i = 0; i < array.size(); i++) {
			if (i < POKER_HANDLER) {
				if (i >= POKER_HANDLER - 7) {
					pokerFrontHandler.add(hm.get(array.get(i)));
					pokerHandler.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
					continue;
				}
				pokerOppositeHandler.add(hm.get(array.get(i)));
				hm.get(array.get(i)).setDirection("opposite");
				pokerHandler.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
			} else {
				pokerShuffle.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
				DBUtil.GetInstance().addPokerToShuffle(userId, hm.get(array.get(i)));
			}
		}
		
		for(Poker pp : hardList){
			pokerShuffle.add(pp.getPokerId()+ ":" + pp.getDirection());
			DBUtil.GetInstance().addPokerToShuffle(userId, pp);
		}
		
		List<String> pokerHandlerList1 = new ArrayList<String>();
		List<String> pokerHandlerList2 = new ArrayList<String>();
		List<String> pokerHandlerList3 = new ArrayList<String>();
		List<String> pokerHandlerList4 = new ArrayList<String>();
		List<String> pokerHandlerList5 = new ArrayList<String>();
		List<String> pokerHandlerList6 = new ArrayList<String>();
		List<String> pokerHandlerList7 = new ArrayList<String>();

		int frontNum = pokerFrontHandler.size();
		for (int i = 0; i < frontNum; i++) {
			// 使用明确数值比较的条件语句时，从效率跟时间上，switch效率要优于if
			switch (i) {
			case 0:
				pokerHandlerList1.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom1(userId, pokerFrontHandler.get(i));
				continue;
			case 1:
				pokerHandlerList2.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom2(userId, pokerFrontHandler.get(i));
				continue;
			case 2:
				pokerHandlerList3.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom3(userId, pokerFrontHandler.get(i));
				continue;
			case 3:
				pokerHandlerList4.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom4(userId, pokerFrontHandler.get(i));
				continue;
			case 4:
				pokerHandlerList5.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom5(userId, pokerFrontHandler.get(i));
				continue;
			case 5:
				pokerHandlerList6.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom6(userId, pokerFrontHandler.get(i));
				continue;
			default:
				pokerHandlerList7.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom7(userId, pokerFrontHandler.get(i));
			}
		}
		int oppositeNum = pokerOppositeHandler.size();
		for (int i = 0; i < oppositeNum; i++) {
			if (i >= oppositeNum - 6) {
				pokerHandlerList7.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom7(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 11) {
				pokerHandlerList6.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom6(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 15) {
				pokerHandlerList5.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom5(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 18) {
				pokerHandlerList4.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom4(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 20) {
				pokerHandlerList3.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom3(userId, pokerOppositeHandler.get(i));
			} else {
				pokerHandlerList2.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom2(userId, pokerOppositeHandler.get(i));
			}
		}

		Gson gson = new Gson();
		// 七个手牌区的数据
		String h1 = pokerHandlerList1.toString();
		String h2 = pokerHandlerList2.toString();
		String h3 = pokerHandlerList3.toString();
		String h4 = pokerHandlerList4.toString();
		String h5 = pokerHandlerList5.toString();
		String h6 = pokerHandlerList6.toString();
		String h7 = pokerHandlerList7.toString();

		// 洗牌区的json数据
		Map<String, String> pokerJsonParam = new HashMap<String, String>();
		String shuffle = pokerShuffle.toString();
		pokerJsonParam.put("shufflePokerList", shuffle);
		pokerJsonParam.put("handPokerList1", h1);
		pokerJsonParam.put("handPokerList2", h2);
		pokerJsonParam.put("handPokerList3", h3);
		pokerJsonParam.put("handPokerList4", h4);
		pokerJsonParam.put("handPokerList5", h5);
		pokerJsonParam.put("handPokerList6", h6);
		pokerJsonParam.put("handPokerList7", h7);
		pokerJsonParam.put("userId", userId);
		return pokerJsonParam;
	}
	
	/**为登录的用户进行洗牌和发牌 **/ 
	public Map<String, String> sendPoker(String userId) throws JsonGenerationException, JsonMappingException, IOException {
		// 定义HashMap变量用于存储每张牌的编号以及牌型
		HashMap<Integer, Poker> hm = new HashMap<Integer, Poker>();
		// 定义变量存储洗牌区牌的编号
		ArrayList<Integer> array = new ArrayList<Integer>();
		// 定义数组存储牌的花色
		int[] colors = { 1, 2, 3, 4 };
		// 定义数组存储牌值
		int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		// 定义扑克牌的状态
		String[] state = { "front", "opposite" };

		int index = 0;
		// 定义编号
		for (int number : numbers) {
			// 遍历排值数组
			for (int color : colors) {
				// 遍历花色
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
		for (int i = 0; i < hm.size(); i++) {
			DBUtil.GetInstance().savePoker(userId, hm.get(array.get(i)));
			System.out.println("pokerId:" + hm.get(array.get(i)).getPokerId() + "--pokerNumber"
					+ hm.get(array.get(i)).getNumber() + "--pokerColor" + hm.get(array.get(i)).getColor());
		}

		// 打乱编号，重新排序
		Collections.shuffle(array);
		DBUtil.GetInstance().init();

		if (DBUtil.GetInstance().getShuffleList(userId).size() != 0 && DBUtil.GetInstance().getPokerRoom1List(userId).size() != 0) {
			return getInitCards(userId);
		}
		List<String> pokerShuffle = new ArrayList<>();
		List<String> pokerHandler = new ArrayList<>();

		List<Poker> pokerFrontHandler = new ArrayList<Poker>();
		List<Poker> pokerOppositeHandler = new ArrayList<Poker>();

		// 分出洗牌区和手牌区的牌,并留出七张正面朝上的牌
		//区分难度，简单模式使用easyArray, 一般使用array
		for (int i = 0; i < array.size(); i++) {
			if (i < POKER_HANDLER) {
				if (i >= POKER_HANDLER - 7) {
					pokerFrontHandler.add(hm.get(array.get(i)));
					pokerHandler.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
					continue;
				}
				pokerOppositeHandler.add(hm.get(array.get(i)));
				hm.get(array.get(i)).setDirection("opposite");
				pokerHandler.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
			} else {
				pokerShuffle.add(hm.get(array.get(i)).getPokerId() + ":" + hm.get(array.get(i)).getDirection());
				DBUtil.GetInstance().addPokerToShuffle(userId, hm.get(array.get(i)));
			}
		}

		// //区分洗牌难度时用
		// for(int a = 0; a < 3; a++){
		// pokerShuffle.add(hm.get(easyArray.get(a)).getPokerId() + ":" +
		// hm.get(easyArray.get(a)).getDirection());
		// DBUtil.GetInstance().addPokerToShuffle(userId,
		// hm.get(easyArray.get(a)));
		// }

		// 把洗牌区的牌分为七份，存到redis中
		List<String> pokerHandlerList1 = new ArrayList<String>();
		List<String> pokerHandlerList2 = new ArrayList<String>();
		List<String> pokerHandlerList3 = new ArrayList<String>();
		List<String> pokerHandlerList4 = new ArrayList<String>();
		List<String> pokerHandlerList5 = new ArrayList<String>();
		List<String> pokerHandlerList6 = new ArrayList<String>();
		List<String> pokerHandlerList7 = new ArrayList<String>();

		int frontNum = pokerFrontHandler.size();
		for (int i = 0; i < frontNum; i++) {
			// 使用明确数值比较的条件语句时，从效率跟时间上，switch效率要优于if
			switch (i) {
			case 0:
				pokerHandlerList1.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom1(userId, pokerFrontHandler.get(i));
				continue;
			case 1:
				pokerHandlerList2.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom2(userId, pokerFrontHandler.get(i));
				continue;
			case 2:
				pokerHandlerList3.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom3(userId, pokerFrontHandler.get(i));
				continue;
			case 3:
				pokerHandlerList4.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom4(userId, pokerFrontHandler.get(i));
				continue;
			case 4:
				pokerHandlerList5.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom5(userId, pokerFrontHandler.get(i));
				continue;
			case 5:
				pokerHandlerList6.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom6(userId, pokerFrontHandler.get(i));
				continue;
			default:
				pokerHandlerList7.add(pokerFrontHandler.get(i).getPokerId() + ":" + pokerFrontHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom7(userId, pokerFrontHandler.get(i));
			}
		}
		int oppositeNum = pokerOppositeHandler.size();
		for (int i = 0; i < oppositeNum; i++) {
			if (i >= oppositeNum - 6) {
				pokerHandlerList7.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom7(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 11) {
				pokerHandlerList6.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom6(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 15) {
				pokerHandlerList5.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom5(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 18) {
				pokerHandlerList4.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom4(userId, pokerOppositeHandler.get(i));
			} else if (i >= oppositeNum - 20) {
				pokerHandlerList3.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom3(userId, pokerOppositeHandler.get(i));
			} else {
				pokerHandlerList2.add(
						pokerOppositeHandler.get(i).getPokerId() + ":" + pokerOppositeHandler.get(i).getDirection());
				DBUtil.GetInstance().addPokerToRoom2(userId, pokerOppositeHandler.get(i));
			}
		}

		Gson gson = new Gson();
		// 七个手牌区的数据
		String h1 = pokerHandlerList1.toString();
		String h2 = pokerHandlerList2.toString();
		String h3 = pokerHandlerList3.toString();
		String h4 = pokerHandlerList4.toString();
		String h5 = pokerHandlerList5.toString();
		String h6 = pokerHandlerList6.toString();
		String h7 = pokerHandlerList7.toString();

		// 洗牌区的json数据
		Map<String, String> pokerJsonParam = new HashMap<String, String>();
		String shuffle = pokerShuffle.toString();
		pokerJsonParam.put("shufflePokerList", shuffle);
		pokerJsonParam.put("handPokerList1", h1);
		pokerJsonParam.put("handPokerList2", h2);
		pokerJsonParam.put("handPokerList3", h3);
		pokerJsonParam.put("handPokerList4", h4);
		pokerJsonParam.put("handPokerList5", h5);
		pokerJsonParam.put("handPokerList6", h6);
		pokerJsonParam.put("handPokerList7", h7);
		pokerJsonParam.put("userId", userId);
		// String pokerJson = JsonUtil.encodeJson(pokerJsonParam);
		// String pokerJson = mapper.writeValueAsString(pokerJsonParam);
		// String pokerJson = pokerJsonParam.toString();

		return pokerJsonParam;
		// //二人斗地主的发牌 弃用
		// List<String> playerOne = new ArrayList<String>();
		// List<String> playerTwo = new ArrayList<String>();
		// List<String> dipai = new ArrayList<String>();
		// for(int x = 0; x < array.size(); x++){
		// if(x >= array.size() - 7){
		// dipai.add(hm.get(array.get(x)));
		// }else if( x % 2 == 0){
		// playerOne.add(hm.get(array.get(x)));
		// }else if(x % 2 == 1){
		// playerTwo.add(hm.get(array.get(x)));
		// }
		// }
		// //list转成json
		// String json1 =JSONArray.fromObject(playerOne).toString();
		//
		// String json2 = JSONArray.fromObject(playerTwo).toString();
		//
		// String json3 = JSONArray.fromObject(dipai).toString();
		//
		// String str = json1 + json2 + json3;
		// return str;

		// 之前的socket 时传byte[]的方法 弃用
		// //调用Collections集合的shuffle()方法，将array中存储的编号进行随机的置换，即打乱顺序
		// /* * 定义TreeSet集合的变量用于存储底牌编号以及玩家的牌的编号 *
		// 采用TreeSet集合是因为TreeSet集合可以实现自然排序 */
		// TreeSet<Integer> playerOne = new TreeSet<Integer>();
		// TreeSet<Integer> PlayerTwo = new TreeSet<Integer>();
		// TreeSet<Integer> dipai = new TreeSet<Integer>();
		// //遍历编号的集合，实现发牌
		// for(int x = 0; x < array.size(); x++){
		// if(x >= array.size() - 6){
		// dipai.add(array.get(x));
		// }else if( x % 2 == 0){
		// playerOne.add(array.get(x));
		// }else if(x % 2 == 1){
		// PlayerTwo.add(array.get(x));
		// }
		// }
		// //把结果存在String内
		// StringBuilder buf = new StringBuilder();
		// buf.append('[');
		//// buf.append(array.get(0));
		//
		// //把所有的卡牌放在String字符串里面
		// for(int i=0; i<array.size();i++){
		// buf.append(array.get(i));
		// buf.append(",");
		// if( i == array.size()-1){
		// buf.append(array.get(i));
		// }
		// System.out.println("----"+array.get(i)+"--第："+i+"个");
		// }
		// buf.append("]");
		// return buf.toString().getBytes();
	}

	/** * 遍历每个玩家的牌以及底牌 **/
	public static void lookPoker(String name, TreeSet<Integer> ts, HashMap<Integer, String> hm) {
		System.out.print(name + ":\t");
		for (Integer key : ts) {
			// 遍历玩家TreeSet集合，获得玩家的牌的编号
			String value = hm.get(key);
			// 根据玩家牌编号获取具体的牌值
			System.out.print(value + "  ");
		}
		System.out.println();
	}

	/** 已经洗牌并发牌了 直接读redis **/
	public Map<String, String> getInitCards(String userId) {
		List<String> room1 = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getPokerRoom1List(userId)) {
			room1.add(p.getPokerId() + ":" + p.getDirection());
		}
		List<String> room2 = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getPokerRoom2List(userId)) {
			room2.add(p.getPokerId() + ":" + p.getDirection());
		}
		List<String> room3 = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getPokerRoom3List(userId)) {
			room3.add(p.getPokerId() + ":" + p.getDirection());
		}
		List<String> room4 = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getPokerRoom4List(userId)) {
			room4.add(p.getPokerId() + ":" + p.getDirection());
		}
		List<String> room5 = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getPokerRoom5List(userId)) {
			room5.add(p.getPokerId() + ":" + p.getDirection());
		}
		List<String> room6 = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getPokerRoom6List(userId)) {
			room6.add(p.getPokerId() + ":" + p.getDirection());
		}
		List<String> room7 = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getPokerRoom7List(userId)) {
			room7.add(p.getPokerId() + ":" + p.getDirection());
		}
		List<String> shuffle = new ArrayList<>();
		for (Poker p : DBUtil.GetInstance().getShuffleList(userId)) {
			shuffle.add(p.getPokerId() + ":" + p.getDirection());
		}
		Map<String, String> pokerJsonParam = new HashMap<String, String>();
		pokerJsonParam.put("shufflePokerList", shuffle.toString());
		pokerJsonParam.put("handPokerList1", room1.toString());
		pokerJsonParam.put("handPokerList2", room2.toString());
		pokerJsonParam.put("handPokerList3", room3.toString());
		pokerJsonParam.put("handPokerList4", room4.toString());
		pokerJsonParam.put("handPokerList5", room5.toString());
		pokerJsonParam.put("handPokerList6", room6.toString());
		pokerJsonParam.put("handPokerList7", room7.toString());
		pokerJsonParam.put("userId", userId);
		pokerJsonParam.put("ErrorCode",
				"already init cards,if want to get another pair of deck,please finish the game or restart");

		PokerList pp = new PokerList();
		Map<String, String> dbpokerList = pokerJsonParam;
		dbpokerList.remove("ErrorCode");
		dbpokerList.remove("userId");
		pp.setContent(dbpokerList.toString());
		pp.setPokersId(userId + "_" + (int) (Math.random() * 10));
		DBUtil.GetInstance().savePokerList(pp);

		return pokerJsonParam;
	}

	// ----------------------------------------------------------移牌逻辑--------------------------------------------------
	// 移动卡牌到七个手牌区之一(1.从洗牌区移入 2.在手牌区互相移动)
	// position 只有在向空白的位置移动时才会进行判断
	public Map<String, Object> moveCards(String userId, int pokerId, int targetPokerId, int move_position,int target_position,List<Integer> pokerIds) {
		DBUtil.GetInstance().init();
		
		Poker targetPoker = null;
		HashMap<String, Object> result = new HashMap<>();
		if (targetPokerId != 1000) {
			targetPoker = DBUtil.GetInstance().getPoker(userId, targetPokerId);
			if ("opposite".equals(targetPoker.getDirection())) {
				logger.error("can't move the opposite card");
				result.put("ErrorCode", "opposite card can't move");
				return result;
			}
		}
		
		Poker poker = null;
		List<Poker> movePokers = new ArrayList<>();
		if( pokerId == 1000 && pokerIds.size() > 1){
			for(Integer pId : pokerIds){
				Poker pp = DBUtil.GetInstance().getPoker(userId, pId);
				movePokers.add(pp);
			}
		}else{
			poker = DBUtil.GetInstance().getPoker(userId, pokerId);
			if ("opposite".equals(poker.getDirection())) {
				logger.error("can't move the opposite card");
				result.put("ErrorCode", "opposite card can't move");
				return result;
			}
		}
		
		// 移动牌到手牌区
		if (target_position < 8) {
			return movePokerToRoom(userId, move_position, target_position, poker, targetPoker,movePokers);
		}
		// 移动牌到存牌区
		if (target_position >= 8) {
			return movePokerToHome(userId, move_position, target_position, poker, targetPoker);
		}
		result.put("CanSendPokers", "0");
		result.put("ErrorCode", "false target_position");
		return result;
	}

	// 移动牌到七个手牌区
	private Map<String, Object> movePokerToRoom(String userId, int move_position, int target_position, Poker poker,
			Poker targetPoker,List<Poker> movePokers) {
		Map<String, Object> mm = new HashMap<>();
		switch (target_position) {
		case 1:
			if(movePokers.size() > 1){
				Poker onePoker = movePokers.get(0);
				if(DBUtil.GetInstance().getPokerRoom1List(userId) == null || DBUtil.GetInstance().getPokerRoom1List(userId).size() == 0){
					if(onePoker.getNumber() == 13){
						for(Poker p : movePokers){
							removeCardFromHandlerOrShuffle(p, move_position, userId);
							DBUtil.GetInstance().addPokerToRoom1(userId, p);
							mm.put("CanSendPokers", "1");
						}
						logger.info("move to room1 successfully");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room1");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room1");
					return mm;
				}
				if(comparePokerOfHandle(onePoker, targetPoker)){
					for(Poker p : movePokers){
						removeCardFromHandlerOrShuffle(p, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom1(userId, p);
						mm.put("CanSendPokers", "1");
					}
					logger.info("move to room1 successfully");
					return mm;
				}
				logger.error("----can't put the card except 13 to the room1");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card except 13 to the room1");
				return mm;
			}else{
				if (DBUtil.GetInstance().getPokerRoom1List(userId) == null|| DBUtil.GetInstance().getPokerRoom1List(userId).size() == 0) {
					if (poker.getNumber() == 13) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom1(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room1");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room1");
					return mm;
				} else {
					if (comparePokerOfHandle(poker, targetPoker)) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom1(userId, poker);
						logger.info("move to room1 successfully");
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't move to the room1 because of the uncorrect number or color");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card with false number or color");
					return mm;
				}
			}
		case 2:
			if (movePokers.size() > 1) {
				Poker onePoker = movePokers.get(0);
				if (DBUtil.GetInstance().getPokerRoom2List(userId) == null
						|| DBUtil.GetInstance().getPokerRoom2List(userId).size() == 0) {
					System.out.println(DBUtil.GetInstance().getPokerRoom2List(userId));
					if (onePoker.getNumber() == 13) {
						for (Poker p : movePokers) {
							removeCardFromHandlerOrShuffle(p, move_position, userId);
							DBUtil.GetInstance().addPokerToRoom2(userId, p);
							mm.put("CanSendPokers", "1");
						}
						logger.info("move to room1 successfully");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room2");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room2");
					return mm;
				}
				if (comparePokerOfHandle(onePoker, targetPoker)) {
					for (Poker p : movePokers) {
						removeCardFromHandlerOrShuffle(p, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom2(userId, p);
						mm.put("CanSendPokers", "1");
					}
					logger.info("move to room2 successfully");
					return mm;
				}
				logger.error("----can't put the card except 13 to the room2");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card except 13 to the room2");
				return mm;
			} else {
				if (DBUtil.GetInstance().getPokerRoom2List(userId).size() == 0) {
					if (poker.getNumber() == 13) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom2(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room2");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room2");
					return mm;
				} else {
					if (comparePokerOfHandle(poker, targetPoker)) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom2(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't move to the room2 because of the uncorrect number or color");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card with false number or color");
					return mm;
				}
			}
		case 3:
			if (movePokers.size() > 1) {
				Poker onePoker = movePokers.get(0);
				if (DBUtil.GetInstance().getPokerRoom3List(userId) == null || DBUtil.GetInstance().getPokerRoom3List(userId).size() == 0) {
					if (onePoker.getNumber() == 13) {
						for (Poker p : movePokers) {
							removeCardFromHandlerOrShuffle(p, move_position, userId);
							DBUtil.GetInstance().addPokerToRoom3(userId, p);
							mm.put("CanSendPokers", "1");
						}
						logger.info("move to room1 successfully");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room3");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room3");
					return mm;
				}
				if (comparePokerOfHandle(onePoker, targetPoker)) {
					for (Poker p : movePokers) {
						removeCardFromHandlerOrShuffle(p, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom3(userId, p);
						mm.put("CanSendPokers", "1");
					}
					logger.info("move to room3 successfully");
					return mm;
				}
				logger.error("----can't put the card except 13 to the room3");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card except 13 to the room3");
				return mm;
			} else {
				if (DBUtil.GetInstance().getPokerRoom3List(userId).size() == 0) {
					if (poker.getNumber() == 13) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom3(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room3");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room3");
					return mm;
				} else {
					if (comparePokerOfHandle(poker, targetPoker)) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom3(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't move to the room3 because of the uncorrect number or color");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card with false number or color");
					return mm;
				}
			}
		case 4:
			if(movePokers.size() > 1){
				Poker onePoker = movePokers.get(0);
				if(DBUtil.GetInstance().getPokerRoom4List(userId) == null|| DBUtil.GetInstance().getPokerRoom4List(userId).size() == 0){
					if(onePoker.getNumber() == 13){
						for(Poker p : movePokers){
							removeCardFromHandlerOrShuffle(p, move_position, userId);
							DBUtil.GetInstance().addPokerToRoom4(userId, p);
							mm.put("CanSendPokers", "1");
						}
						logger.info("move to room4 successfully");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room4");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room4");
					return mm;
				}
				if(comparePokerOfHandle(onePoker, targetPoker)){
					for(Poker p : movePokers){
						removeCardFromHandlerOrShuffle(p, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom4(userId, p);
						mm.put("CanSendPokers", "1");
					}
					logger.info("move to room4 successfully");
					return mm;
				}
				logger.error("----can't put the card except 13 to the room4");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card except 13 to the room4");
				return mm;
			}else{
			if (DBUtil.GetInstance().getPokerRoom4List(userId).size() == 0) {
				if (poker.getNumber() == 13) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToRoom4(userId, poker);
					mm.put("CanSendPokers", "1");
					return mm;
				}
				logger.error("----can't put the card except 13 to the room4");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card except 13 to the room4");
				return mm;
			} else {
				if (comparePokerOfHandle(poker, targetPoker)) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToRoom4(userId, poker);
					mm.put("CanSendPokers", "1");
					logger.info("move to room4 successfully");
					return mm;
				}
				logger.error("----can't move  to the room4 because of the uncorrect number or color");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card with false number or color");
				return mm;
			}}
		case 5:
			if(movePokers.size() > 1){
				Poker onePoker = movePokers.get(0);
				if(DBUtil.GetInstance().getPokerRoom5List(userId) == null|| DBUtil.GetInstance().getPokerRoom5List(userId).size() == 0){
					if(onePoker.getNumber() == 13){
						for(Poker p : movePokers){
							removeCardFromHandlerOrShuffle(p, move_position, userId);
							DBUtil.GetInstance().addPokerToRoom5(userId, p);
							mm.put("CanSendPokers", "1");
						}
						logger.info("move to room5 successfully");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room5");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room5");
					return mm;
				}
				if(comparePokerOfHandle(onePoker, targetPoker)){
					for(Poker p : movePokers){
						removeCardFromHandlerOrShuffle(p, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom4(userId, p);
						mm.put("CanSendPokers", "1");
					}
					logger.info("move to room4 successfully");
					return mm;
				}
				logger.error("----can't put the card with wrong number or color to the room5");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card with wrong number or color to the room5");
				return mm;
			}else{
			if (DBUtil.GetInstance().getPokerRoom5List(userId).size() == 0) {
				if (poker.getNumber() == 13) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToRoom5(userId, poker);
					mm.put("CanSendPokers", "1");
					return mm;
				}
				logger.error("----can't put the card except 13 to the room5");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card except 13 to the room5");
				return mm;
			} else {
				if (comparePokerOfHandle(poker, targetPoker)) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToRoom5(userId, poker);
					mm.put("CanSendPokers", "1");
					return mm;
				}
				logger.error("----can't move  to the room5 because of the uncorrect number or color");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card with false number or color");
				return mm;
			}}
		case 6:
			if(movePokers.size() > 1){
				Poker onePoker = movePokers.get(0);
				if(DBUtil.GetInstance().getPokerRoom6List(userId) == null|| DBUtil.GetInstance().getPokerRoom6List(userId).size() == 0){
					if(onePoker.getNumber() == 13){
						for(Poker p : movePokers){
							removeCardFromHandlerOrShuffle(p, move_position, userId);
							DBUtil.GetInstance().addPokerToRoom6(userId, p);
							mm.put("CanSendPokers", "1");
						}
						logger.info("move to room6 successfully");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room6");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room6");
					return mm;
				}
				if(comparePokerOfHandle(onePoker, targetPoker)){
					for(Poker p : movePokers){
						removeCardFromHandlerOrShuffle(p, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom6(userId, p);
						mm.put("CanSendPokers", "1");
					}
					logger.info("move to room6 successfully");
					return mm;
				}
				logger.error("----can't put the card with wrong number or color to the room6");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card with wrong number or color to the room6");
				return mm;
			} else {
				if (DBUtil.GetInstance().getPokerRoom6List(userId).size() == 0) {
					if (poker.getNumber() == 13) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom6(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room6");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room6");
					return mm;
				} else {
					if (comparePokerOfHandle(poker, targetPoker)) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom6(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't move to the room6 because of the uncorrect number or color");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card with false number or color");
					return mm;
				}
			}
		case 7:
			if(movePokers.size() > 1){
				Poker onePoker = movePokers.get(0);
				if(DBUtil.GetInstance().getPokerRoom7List(userId) == null|| DBUtil.GetInstance().getPokerRoom7List(userId).size() == 0){
					if(onePoker.getNumber() == 13){
						for(Poker p : movePokers){
							removeCardFromHandlerOrShuffle(p, move_position, userId);
							DBUtil.GetInstance().addPokerToRoom7(userId, p);
							mm.put("CanSendPokers", "1");
						}
						logger.info("move to room4 successfully");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room7");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room7");
					return mm;
				}
				if(comparePokerOfHandle(onePoker, targetPoker)){
					for(Poker p : movePokers){
						removeCardFromHandlerOrShuffle(p, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom7(userId, p);
						mm.put("CanSendPokers", "1");
					}
					logger.info("move to room7 successfully");
					return mm;
				}
				logger.error("----can't put the card except 13 to the room7");
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card except 13 to the room7");
				return mm;
			} else {
				if (DBUtil.GetInstance().getPokerRoom7List(userId).size() == 0) {
					if (poker.getNumber() == 13) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom7(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't put the card except 13 to the room7");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except 13 to the room7");
					return mm;
				} else {
					if (comparePokerOfHandle(poker, targetPoker)) {
						removeCardFromHandlerOrShuffle(poker, move_position, userId);
						DBUtil.GetInstance().addPokerToRoom7(userId, poker);
						mm.put("CanSendPokers", "1");
						return mm;
					}
					logger.error("----can't move to the room7 because of the uncorrect number or color");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card with false number or color");
					return mm;
				}
			}
		}
		mm.put("CanSendPokers", "0");
		mm.put("Errorcode", "can't put the card with false number or color");
		return mm;
	}

	// 移动牌到四个存牌区
	public Map<String, Object> movePokerToHome(String userId, int move_position, int target_position, Poker poker,
			Poker targetPoker) {
		Map<String, Object> mm = new HashMap<>();
		switch (target_position) {
		case 8:
			if (DBUtil.GetInstance().getHome1List(userId).size() == 0) {
				if (poker.getNumber() != 1) {
					logger.error("----can't put the card except A in the empty PokerHome1");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except A in the empty PokerHome1");
					return mm;
				}
				removeCardFromHandlerOrShuffle(poker, move_position, userId);
				DBUtil.GetInstance().addPokerToHome1(userId, poker);
				mm.put("CanSendPokers", "1");
				return mm;
			} else {
				if (comparePokerOfHome(poker, targetPoker)) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToHome1(userId, poker);
					mm.put("CanSendPokers", "1");
					return mm;
				}
				logger.error("---");
			}
			break;
		case 9:
			if (DBUtil.GetInstance().getHome2List(userId).size() == 0) {
				if (poker.getNumber() != 1) {
					logger.error("----can't put the card except A in the empty PokerHome2");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except A in the empty PokerHome2");
					return mm;
				}
				DBUtil.GetInstance().addPokerToHome2(userId, poker);
				removeCardFromHandlerOrShuffle(poker, move_position, userId);
				mm.put("CanSendPokers", "1");
				return mm;
			} else {
				if (comparePokerOfHome(poker, targetPoker)) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToHome2(userId, poker);
					mm.put("CanSendPokers", "1");
					return mm;
				}
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card with false number or color");
				return mm;
			}
		case 10:
			if (DBUtil.GetInstance().getHome3List(userId).size() == 0) {
				if (poker.getNumber() != 1) {
					logger.error("----can't put the card except A in the empty PokerHome3");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except A in the empty PokerHome3");
					return mm;
				}
				DBUtil.GetInstance().addPokerToHome3(userId, poker);
				removeCardFromHandlerOrShuffle(poker, move_position, userId);
				mm.put("CanSendPokers", "1");
				return mm;
			} else {
				if (comparePokerOfHome(poker, targetPoker)) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToHome3(userId, poker);
					mm.put("CanSendPokers", "1");
					return mm;
				}
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card with false number or color");
				return mm;
			}
		case 11:
			if (DBUtil.GetInstance().getHome4List(userId).size() == 0) {
				if (poker.getNumber() != 1) {
					logger.error("----can't put the card except A in the empty PokerHome4");
					mm.put("CanSendPokers", "0");
					mm.put("Errorcode", "can't put the card except A in the empty PokerHome4");
					return mm;
				}
				DBUtil.GetInstance().addPokerToHome4(userId, poker);
				removeCardFromHandlerOrShuffle(poker, move_position, userId);
				mm.put("CanSendPokers", "1");
				return mm;
			} else {
				if (comparePokerOfHome(poker, targetPoker)) {
					removeCardFromHandlerOrShuffle(poker, move_position, userId);
					DBUtil.GetInstance().addPokerToHome4(userId, poker);
					mm.put("CanSendPokers", "1");
					return mm;
				}
				mm.put("CanSendPokers", "0");
				mm.put("Errorcode", "can't put the card with false number or color");
				return mm;
			}
		}
		mm.put("CanSendPokers", "0");
		mm.put("Errorcode", "false targetPosition");
		return mm;
	}

	private void removeCardFromHandlerOrShuffle(Poker poker, int move_position, String userId) {
		switch (move_position) {
		case 0:
			DBUtil.GetInstance().deletePokerFromShuffle(userId, poker);
			break;
		case 1:
			DBUtil.GetInstance().deletePokerFromRoom1(userId, poker);
			logger.info("delete poker from room1 success" + poker.getNumber() + poker.getColor());
			List<Poker> room1 = DBUtil.GetInstance().getPokerRoom1List(userId);
			if (room1.size() >= 1) {
				Poker changeCard = room1.get(0);
				DBUtil.GetInstance().deletePokerFromRoom1(userId, room1.get(0));
				changeCard.setDirection("front");
				DBUtil.GetInstance().addPokerToRoom1(userId, changeCard);
				logger.info("success move card {},position{}", poker.getPokerId(), "PokerRoom1");
			}
			break;
		case 2:
			DBUtil.GetInstance().deletePokerFromRoom2(userId, poker);
			List<Poker> room2 = DBUtil.GetInstance().getPokerRoom2List(userId);
			if (room2.size() >= 1) {
				Poker changeCard = room2.get(0);
				DBUtil.GetInstance().deletePokerFromRoom2(userId, room2.get(0));
				changeCard.setDirection("front");
				DBUtil.GetInstance().addPokerToRoom2(userId, changeCard);
				logger.info("success move card {},position{}", poker.getPokerId(), "PokerRoom2");
			}
			break;
		case 3:
			DBUtil.GetInstance().deletePokerFromRoom3(userId, poker);
			List<Poker> room3 = DBUtil.GetInstance().getPokerRoom3List(userId);
			if (room3.size() >= 1) {
				Poker changeCard = room3.get(0);
				DBUtil.GetInstance().deletePokerFromRoom3(userId, room3.get(0));
				changeCard.setDirection("front");
				DBUtil.GetInstance().addPokerToRoom3(userId, changeCard);
				logger.info("success move card {},position{}", poker.getPokerId(), "PokerRoom3");
			}
			break;
		case 4:
			DBUtil.GetInstance().deletePokerFromRoom4(userId, poker);
			List<Poker> room4 = DBUtil.GetInstance().getPokerRoom4List(userId);
			if (room4.size() >= 1) {
				Poker changeCard = room4.get(0);
				DBUtil.GetInstance().deletePokerFromRoom4(userId, room4.get(0));
				changeCard.setDirection("front");
				DBUtil.GetInstance().addPokerToRoom4(userId, changeCard);
				logger.info("success move card {},position{}", poker.getPokerId(), "PokerRoom4");
			}
			break;
		case 5:
			DBUtil.GetInstance().deletePokerFromRoom5(userId, poker);
			List<Poker> room5 = DBUtil.GetInstance().getPokerRoom5List(userId);
			if (room5.size() >= 1) {
				Poker changeCard = room5.get(0);
				DBUtil.GetInstance().deletePokerFromRoom5(userId, room5.get(0));
				changeCard.setDirection("front");
				DBUtil.GetInstance().addPokerToRoom5(userId, changeCard);
				logger.info("success move card {},position{}", poker.getPokerId(), "PokerRoom5");
			}
			break;
		case 6:
			DBUtil.GetInstance().deletePokerFromRoom6(userId, poker);
			List<Poker> room6 = DBUtil.GetInstance().getPokerRoom6List(userId);
			if (room6.size() >= 1) {
				Poker changeCard = room6.get(0);
				DBUtil.GetInstance().deletePokerFromRoom6(userId, room6.get(0));
				changeCard.setDirection("front");
				DBUtil.GetInstance().addPokerToRoom6(userId, changeCard);
				logger.info("success move card {},position{}", poker.getPokerId(), "PokerRoom6");
			}
			break;
		case 7:
			DBUtil.GetInstance().deletePokerFromRoom7(userId, poker);
			List<Poker> room7 = DBUtil.GetInstance().getPokerRoom7List(userId);
			if (room7.size() >= 1) {
				Poker changeCard = room7.get(0);
				DBUtil.GetInstance().deletePokerFromRoom7(userId, room7.get(0));
				changeCard.setDirection("front");
				DBUtil.GetInstance().addPokerToRoom7(userId, changeCard);
				logger.info("success move card {},position{}", poker.getPokerId(), "PokerRoom1");
			}
			break;
		}
	}

	// 比较牌的大小和花色（存牌区）
	public boolean comparePokerOfHome(Poker poker, Poker targetPoker) {
		if (poker.getNumber() == targetPoker.getNumber() + 1 && poker.getColor() == targetPoker.getColor()) {
			return true;
		} else {
			return false;
		}
	}

	// 手牌区
	public boolean comparePokerOfHandle(Poker poker, Poker targetPoker) {
		// 手牌区，只有花色不同才可以叠加
		// 判断数字和花色 与1进行按与运算，运算结果为1则是奇数，0则为偶数。
		if (poker.getNumber() == targetPoker.getNumber() - 1 && (poker.getColor() & 1) != (targetPoker.getColor() & 1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCorrenctCardHome(String pokerHomeList) {
		return false;
	}

	// -----------------------------------------------------胜利判断逻辑--------------------------------------------------------------
	// 判断是否胜利，七个手牌区和洗牌区的均没有卡牌
	public boolean isWon(String userId) {
		List<Poker> rList1 = DBUtil.GetInstance().getPokerRoom1List(userId);
		List<Poker> rList2 = DBUtil.GetInstance().getPokerRoom2List(userId);
		List<Poker> rList3 = DBUtil.GetInstance().getPokerRoom3List(userId);
		List<Poker> rList4 = DBUtil.GetInstance().getPokerRoom4List(userId);
		List<Poker> rList5 = DBUtil.GetInstance().getPokerRoom5List(userId);
		List<Poker> rList6 = DBUtil.GetInstance().getPokerRoom6List(userId);
		List<Poker> rList7 = DBUtil.GetInstance().getPokerRoom7List(userId);

		List<Poker> sList = DBUtil.GetInstance().getShuffleList(userId);
		if (rList1 == null && rList2 == null && rList3 == null && rList4 == null) {
			if (rList5 == null && rList6 == null && rList7 == null) {
				if (sList == null) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isFailed(String userId, List<Poker> allPokers) {

		return true;
	}

	// 洗牌 存入数据库
	public void initPokerList(String pokerId) {
		HashMap<Integer, Poker> hm = new HashMap<Integer, Poker>();
		// 定义变量存储洗牌区牌的编号
		ArrayList<Integer> array = new ArrayList<Integer>();
		// 定义数组存储牌的花色
		int[] colors = { 0, 1, 2, 3 };
		// 定义数组存储牌值
		int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		// 定义扑克牌的状态
		String[] state = { "front", "opposite" };

		int index = 0;
		// 定义编号
		for (int number : numbers) {
			// 遍历排值数组
			for (int color : colors) {
				// 遍历花色
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
	}
	
	public void gameOver(String userId){
		DBUtil.GetInstance().init();
		List<Poker> room1 = DBUtil.GetInstance().getPokerRoom1List(userId);
		if (room1.size() != 0) {
			for(Poker pp : room1){
				DBUtil.GetInstance().deletePokerFromRoom1(userId, pp);
			}
		}
		List<Poker> room2 = DBUtil.GetInstance().getPokerRoom2List(userId);
		if (room2.size() != 0) {
			for(Poker pp : room2){
				DBUtil.GetInstance().deletePokerFromRoom2(userId, pp);
			}
		}
		List<Poker> room3 = DBUtil.GetInstance().getPokerRoom3List(userId);
		if (room3.size() != 0) {
			for(Poker pp : room3){
				DBUtil.GetInstance().deletePokerFromRoom3(userId, pp);
			}
		}
		List<Poker> room4 = DBUtil.GetInstance().getPokerRoom4List(userId);
		if (room4.size() != 0) {
			for(Poker pp : room4){
				DBUtil.GetInstance().deletePokerFromRoom4(userId, pp);
			}
		}
		List<Poker> room5 = DBUtil.GetInstance().getPokerRoom5List(userId);
		if (room5.size() != 0) {
			for(Poker pp : room5){
				DBUtil.GetInstance().deletePokerFromRoom5(userId, pp);
			}
		}
		List<Poker> room6 = DBUtil.GetInstance().getPokerRoom6List(userId);
		if (room6.size() != 0) {
			for(Poker pp : room6){
				DBUtil.GetInstance().deletePokerFromRoom6(userId, pp);
			}
		}
		List<Poker> room7 = DBUtil.GetInstance().getPokerRoom7List(userId);
		if (room7.size() != 0) {
			for(Poker pp : room7){
				DBUtil.GetInstance().deletePokerFromRoom7(userId, pp);
			}
		}
		
		List<Poker> shuffle = DBUtil.GetInstance().getShuffleList(userId);
		if (shuffle.size() != 0) {
			for(Poker pp : shuffle){
				DBUtil.GetInstance().deletePokerFromShuffle(userId, pp);
			}
		}
		
		List<Poker> home1 = DBUtil.GetInstance().getHome1List(userId);
		if (home1.size() != 0) {
			for(Poker pp : home1){
				DBUtil.GetInstance().deletePokerFromHome1(userId, pp);
			}
		}
		
		List<Poker> home2 = DBUtil.GetInstance().getHome2List(userId);
		if (home2.size() != 0) {
			for(Poker pp : home2){
				DBUtil.GetInstance().deletePokerFromHome2(userId, pp);
			}
		}
		
		List<Poker> home3 = DBUtil.GetInstance().getHome3List(userId);
		if (home3.size() != 0) {
			for(Poker pp : home3){
				DBUtil.GetInstance().deletePokerFromHome3(userId, pp);
			}
		}
		
		List<Poker> home4 = DBUtil.GetInstance().getHome4List(userId);
		if (home4.size() != 0) {
			for(Poker pp : home4){
				DBUtil.GetInstance().deletePokerFromHome4(userId, pp);
			}
		}
	}
}
