using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Cards
{
    private int type;  //1:洗牌堆 2:存牌区 3:移动区    
    private List<Card> cardList = new List<Card>();
    private int index;
    //存牌区,则用来判定否已经结算完
    //洗牌堆默认为-1
    private int state;

    public int Type
    {
        get { return type; }
        set { type = value; }
    }

    public List<Card> CardList
    {
        get { return cardList; }
        set { cardList = value; }
    }

    public int State
    {
        get { return state; }
        set { state = value; }
    }

    public Cards(int nType, List<Card> nCardList ,int nIndex, int nState = -1)
    {
        type = nType;
        cardList = nCardList;
        index = nIndex;
        state = nState;
    }
}
