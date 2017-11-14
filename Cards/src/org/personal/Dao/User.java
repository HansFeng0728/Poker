package org.personal.Dao;

import java.util.Date;

public class User {
	private Long id;

	private String userId;

	private String score;

	private String userpokerFront;

	private String userpokerOpposite;

	private Date daojishiTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUserpokerFront() {
		return userpokerFront;
	}

	public void setUserpokerFront(String userpokerFront) {
		this.userpokerFront = userpokerFront;
	}

	public String getUserpokerOpposite() {
		return userpokerOpposite;
	}

	public void setUserpokerOpposite(String userpokerOpposite) {
		this.userpokerOpposite = userpokerOpposite;
	}

	public Date getDaojishiTime() {
		return daojishiTime;
	}

	public void setDaojishiTime(Date daojishiTime) {
		this.daojishiTime = daojishiTime;
	}

	public User(Long id, String userId, String score, String userpokerFront, String userpokerOpposite,
			Date daojishiTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.score = score;
		this.userpokerFront = userpokerFront;
		this.userpokerOpposite = userpokerOpposite;
		this.daojishiTime = daojishiTime;
	}

}
