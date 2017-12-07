package org.personal.db.dao;

import java.util.List;

/**
 * @author Hans
 *	纸牌游戏中的洗牌区域
 */
public class ShuffleRoom {
	private String Id;
	
	private String userId;
	
	private List<Poker> pokerList;
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Poker> getPokerList() {
		return pokerList;
	}

	public void setPokerList(List<Poker> pokerList) {
		this.pokerList = pokerList;
	}
}
