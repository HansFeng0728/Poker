package org.personal.db.dao;

import java.util.Date;

public class Game {
    private Integer id;

    private Date completetime;

    private Byte result;

    private Integer movesteps;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
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
}