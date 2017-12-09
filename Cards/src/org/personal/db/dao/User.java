package org.personal.db.dao;

import java.util.Date;

public class User {
    private Integer Id;

	private Date lastLoginTime;

	private Date lastLogoutTime;

    private Integer score;

    private String userId;

    public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

    public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}