package org.personal.db.dao;

import org.personal.db.dao.PokerList;
import org.personal.db.dao.PokerListWithBLOBs;

public interface PokerListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PokerList record);

    int insertSelective(PokerList record);

    PokerList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PokerList record);

    int updateByPrimaryKeyWithBLOBs(PokerListWithBLOBs record);

    int updateByPrimaryKey(PokerList record);

	PokerList selectByPokerId(String pokerId);
}