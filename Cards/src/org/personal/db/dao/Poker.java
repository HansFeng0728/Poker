package org.personal.db.dao;

public class Poker {
	private String userId;

	private String PokerId;

	private String number;
	
	private String color;
	
	private String direction;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPokerId() {
		return PokerId;
	}

	public void setPokerId(String pokerId) {
		PokerId = pokerId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
