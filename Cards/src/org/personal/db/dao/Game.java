package org.personal.db.dao;

import java.util.Date;

public class Game {
    private Integer id;
    
    private String userId;

	private String pokersId;

    private Date entertime;

    private Byte result;

    private Integer movesteps;

    private Date completetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPokersId() {
		return pokersId;
	}

	public void setPokersId(String pokersId) {
		this.pokersId = pokersId;
	}

    public Date getEntertime() {
        return entertime;
    }

    public void setEntertime(Date entertime) {
        this.entertime = entertime;
    }

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public Integer getMovesteps() {
        return movesteps;
    }

    public void setMovesteps(Integer movesteps) {
        this.movesteps = movesteps;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }
}