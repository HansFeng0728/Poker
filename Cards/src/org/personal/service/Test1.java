package org.personal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.personal.db.DBUtil;
import org.personal.db.dao.Poker;
import org.personal.db.dao.PokerList;

import com.google.gson.Gson;

public class Test1 {
	private static final int POKER_HANDLER = 28;
    public static void main(String[] args) {
    	Test1 tt = new Test1();
    	tt.easyShuffle("asd3232");
    }
    public void easyShuffle(String userId){
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
		List<Poker> easyList = new ArrayList<>();
		int cc= 48;
		for(int color:colors){
			// 遍历花色
			Poker poker = new Poker();
			poker.setUserId(userId);
			poker.setPokerId(cc);
			poker.setNumber(1);
			poker.setColor(color);
			poker.setDirection("front");
			easyList.add(poker);
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
		

		System.out.println("-=-=-=-="+pokerJsonParam);

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
}
