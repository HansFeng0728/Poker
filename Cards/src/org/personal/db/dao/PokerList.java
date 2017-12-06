package org.personal.db.dao;

import java.util.Date;

public class PokerList {
    private Integer id;

    private Date shortestcompletetime;

    private Byte successrate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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