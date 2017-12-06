package org.personal.db.dao;

import java.util.Date;

public class User {
    private Integer Id;

	private Date lastlogintime;

    private Date lastlogouttime;

    private Integer score;

    private String userId;

    public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public Date getLastlogouttime() {
        return lastlogouttime;
    }

    public void setLastlogouttime(Date lastlogouttime) {
        this.lastlogouttime = lastlogouttime;
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