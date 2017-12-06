package org.personal.db.dao;

import org.personal.db.dao.PokerList;
import org.personal.db.dao.PokerListWithBLOBs;

public interface PokerListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PokerListWithBLOBs record);

    int insertSelective(PokerListWithBLOBs record);

    PokerListWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PokerListWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PokerListWithBLOBs record);

    int updateByPrimaryKey(PokerList record);
}