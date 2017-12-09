package org.personal.db.dao;

import java.util.Date;

public class PokerList {
    private Integer id;
    
    private String pokerId;
    
	private String content;
    
    private int type;
    
    private Date shortestcompletetime;

    private Byte successrate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getPokerId() {
		return pokerId;
	}

	public void setPokerId(String pokerId) {
		this.pokerId = pokerId;
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


    public Date getShortestcompletetime() {
        return shortestcompletetime;
    }

    public void setShortestcompletetime(Date shortestcompletetime) {
        this.shortestcompletetime = shortestcompletetime;
    }

    public Byte getSuccessrate() {
        return successrate;
    }

    public void setSuccessrate(Byte successrate) {
        this.successrate = successrate;
    }
}