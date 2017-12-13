package org.personal.db.dao;

import java.util.Date;

public class PokerList {
    private Integer id;
    
    private String pokersId;

	private String content;
    
    private int type;

	private Date shortestCompleteTime;

    private Byte successrate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	public String getPokersId() {
		return pokersId;
	}

	public void setPokersId(String pokersId) {
		this.pokersId = pokersId;
	}
    
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    public Date getShortestCompleteTime() {
		return shortestCompleteTime;
	}

	public void setShortestCompleteTime(Date shortestCompleteTime) {
		this.shortestCompleteTime = shortestCompleteTime;
	}

    public Byte getSuccessrate() {
        return successrate;
    }

    public void setSuccessrate(Byte successrate) {
        this.successrate = successrate;
    }
}