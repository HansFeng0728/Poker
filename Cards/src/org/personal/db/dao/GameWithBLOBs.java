package org.personal.db.dao;

public class GameWithBLOBs extends Game {
    private String userid;

    private String pokersid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getPokersid() {
        return pokersid;
    }

    public void setPokersid(String pokersid) {
        this.pokersid = pokersid == null ? null : pokersid.trim();
    }
}