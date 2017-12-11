package org.personal.db.dao;

public class Poker {
	private String userId;

	private int PokerId;

	private int number;
	
	private int color;

	private String direction;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getPokerId() {
		return PokerId;
	}

	public void setPokerId(int pokerId) {
		PokerId = pokerId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
