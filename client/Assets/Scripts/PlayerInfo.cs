using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class PlayerInfo 
{    
    private string name = "";   //玩家名字
    private int state = 0;  //用户登录状态
    private int score = 0;  //分数
    private int daojishiTime;   //倒计时时间
    private Cards shufflePokerList;     //洗牌牌堆里面的牌    
    private List<Card> allHandCards = new List<Card>(); //手牌区所有牌
    private List<Cards> handCardsList = new List<Cards>();        //手牌区里面的牌
    private List<Card> allCompleteCards = new List<Card>(); //存牌区所有牌
    private List<Cards> completeCardList = new List<Cards>();     //四个存牌区域的卡牌

    public int Score
    {
        get { return score; }
        set { score = value; }
    }

    public string Name
    {
        get { return name; }
        set { name = value; }
    }
   
    public int State
    {
        get { return state; }
        set { state = value; }
    }

    public int DaojishiTime
    {
        get { return daojishiTime; }
        set { daojishiTime = value; }
    }

    public Cards ShufflePokerList
    {
        get
        {
            return shufflePokerList;
        }
        set
        {
            shufflePokerList = value;
        }
    }

    public List<Card> AllHandCards
    {
        get
        {
            return allHandCards;
        }
        set
        {
            allHandCards = value;
        }
    }

    public List<Cards> HandCardsList
    {
        get
        {
            return handCardsList;
        }
        set
        {
            handCardsList = value;
        }
    }

    public List<Card> AllCompleteCards
    {
        get
        {
            return allCompleteCards;
        }
        set
        {
            allCompleteCards = value;
        }
    }

    public List<Cards> CompleteCardList
    {
        get
        {
            return completeCardList;
        }
        set
        {
            completeCardList = value;
        }
    }

    //public PlayerInfo(string name = "")
    //{
    //    Name = name;
    //    shufflePokerList = new Cards(1);
        
    //    for (int i = 0; i <4; i++)
    //    {
    //        Cards cards = new Cards(2, i,-1);
    //        completeCardList.Add(cards);//存牌
    //    }
    //    for (int i = 0; i < 7; i++)
    //    {
    //        Cards cards = new Cards(3, i, -1);
    //        handCardsList.Add(cards);//手牌
    //    }
    //    state = 1;
    //}

    public PlayerInfo()
    {
        shufflePokerList = new Cards(1);
        for (int i = 0; i <4; i++)
        {
            Cards cards = new Cards(2, i, -1);
            completeCardList.Add(cards);//存牌
        }
        for (int i = 0; i < 7; i++)
        {
            Cards cards = new Cards(3, i, -1);
            handCardsList.Add(cards);//手牌
        }
    }
}
