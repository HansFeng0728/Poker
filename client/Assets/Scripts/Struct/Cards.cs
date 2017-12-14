using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Cards
{
    private int type;  //1:shuffle洗牌堆 2:complete存牌区 3:hand移动区    
    private List<Card> cardList = new List<Card>();

    //除了存牌区默认为-1
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

    public int Index
    {
        get { return index; }
        set { index = value; }
    }

    public int State
    {
        get { return state; }
        set { state = value; }
    }

    public Cards(int nType ,int nIndex = -1, int nState = -1)
    {
        type = nType;        
        index = nIndex;
        state = nState;
        //for (int i = 0; i < nCardList.Count; i++)
        //{
        //    Card card = new Card(1, nCardList[i]);
        //}
    }

    public void Reset()
    {
        type = 0;
        cardList.Clear();
        index = -1;
        state = -1;
    }
}
