package org.personal.db.dao;

import org.personal.db.dao.Game;
import org.personal.db.dao.GameWithBLOBs;

public interface GameMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameWithBLOBs record);

    int insertSelective(GameWithBLOBs record);

    GameWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GameWithBLOBs record);

    int updateByPrimaryKey(Game record);
}