using UnityEngine;
using System.Collections;
using LitJson;
using System.Collections.Generic;

public class Message
{
    //UDID
    public string roomId { get; set; }
    public string token { get; set; }
    //预留聊天
    public string text { get; set; }  
}

public class Pokers
{
    private List<string> pokerList = new List<string>();
    public string pokerId { get; set; }
    //分给每个用户的卡牌list 花色:数字
    public List<string> PokerList
    {
        get
        {
            return pokerList;
        }
        set
        {
            pokerList = value;
        }
    }
}

public class Users
{
    public int userState { get; set; }  //0未进入游戏  1 进入游戏  2匹配中  3进入房间（未准备） 4进入房间（已准备）  5开始游戏 
    public string userId { get; set; }
    public string roomId { get; set; }
    public int score { get; set; }
    public int isLandLord { get; set; }
    public int userPosition { get; set; }
    public int userpokerList { get; set; }      //用户拥有的卡牌
    public double daojishiTime { get; set; }    //倒计时准备时间
}


public class SendPokers
{
    public int sendpokerList { get; set; }   //打出的牌数
    public string submit_time { get; set; } //打出牌的时间
    public int canSendPokers { get; set; }   //能不能出牌
    public int sendPosition { get; set; }   //用户位置
    public int isLandLord { get; set; }
}
public class TipsPokers
{
    public int isOnTip { get; set; }
    //提示建议出的牌，如果为空则为不出
    public List<string> suggestPokersList
    {
        get
        {
            return suggestPokersList;
        }
        set
        {
            suggestPokersList = value;
        }
    }
}
