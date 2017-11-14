package org.personal.Dao;

public class Poker {
	
	private Long Id;
	
	private String userId;
	
	private String pokerId;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPokerId() {
		return pokerId;
	}

	public void setPokerId(String pokerId) {
		this.pokerId = pokerId;
	}

	public Poker(Long id, String userId, String pokerId) {
		super();
		Id = id;
		this.userId = userId;
		this.pokerId = pokerId;
	}

	
}
