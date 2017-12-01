using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class PlayerInfo 
{    
    private string name = "";
    private int state = 0;
    private string score = "";
    private int daojishiTime;
    private Cards shufflePokerList;
    private Cards handPokerList;
    private Cards completeCardList;


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

    public Cards HandPokerList
    {
        get
        {
            return handPokerList;
        }
        set
        {
            handPokerList = value;
        }
    }

    public Cards CompleteCardList
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

    public PlayerInfo(string name)
    {
        Name = name;
        int state = 0;
    }
}
