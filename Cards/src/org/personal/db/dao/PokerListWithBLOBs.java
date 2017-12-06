package org.personal.db.dao;

public class PokerListWithBLOBs extends PokerList {
    private String pokersid;

    private String content;

    private String type;

    public String getPokersid() {
        return pokersid;
    }

    public void setPokersid(String pokersid) {
        this.pokersid = pokersid == null ? null : pokersid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}