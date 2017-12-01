using UnityEngine;
using System.Collections;
using LitJson;
using System.Collections.Generic;

//public class Message
//{
//    //UDID
//    public string roomId { get; set; }
//    public string token { get; set; }
//    //预留聊天
//    public string text { get; set; }  
//}

public class User
{
    //UDID
    private string userId;
    private string score;
    public string daojishiTime;
    //预留聊天
    public string text { get; set; }

    public string UserId
    {
        get
        {
            return userId;
        }
        set
        {
            userId = value;
        }
    }

    public string Score
    {
        get
        {
            return score;
        }
        set
        {
            score = value;
        }
    }

    public string DaojishiTime
    {
        get
        {
            return daojishiTime;
        }
        set
        {
            daojishiTime = value;
        }
    }
}

//public class Pokers
//{
//    private List<string> shufflePokerList = new List<string>();
//    private List<string> handPokerList = new List<string>();
//    private List<string> completeCardList = new List<string>();
//    public string pokerId { get; set; }
//    //洗牌牌堆里面的牌
//    public List<string> ShufflePokerList
//    {
//        get
//        {
//            return shufflePokerList;
//        }
//        set
//        {
//            shufflePokerList = value;
//        }
//    }
//    //手牌里面的牌
//    public List<string> HandPokerList
//    {
//        get
//        {
//            return handPokerList;
//        }
//        set
//        {
//            handPokerList = value;
//        }
//    }
//    //四个存牌区域的卡牌
//    public List<string> CompleteCardList
//    {
//        get
//        {
//            return completeCardList;
//        }
//        set
//        {
//            completeCardList = value;
//        }
//    }
//}

//"pokerId": "0/1",  		//卡牌Id 共54个
//"pokerColor": "",  																				//颜色  四个
//"pokerNum":""3","4","5","6","7","8","9","10","J","Q","K","A","2","SJoker","BJoker"",     			//卡牌的牌面大小
//"state":"front","opposite",	//卡牌的状态  正面or反面
public class Pokers
{
    private string userId;
    private string shufflePokerList;
    private string handPokerList;
    private string completeCardList;    

    //洗牌牌堆里面的牌
    public string UserId
    {
        get
        {
            return userId;
        }
        set
        {
            userId = value;
        }
    }

    //洗牌牌堆里面的牌
    public string ShufflePokerList
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
    //手牌里面的牌
    public string HandPokerList
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
    //四个存牌区域的卡牌
    public string CompleteCardList
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
}

public class SendPokers{

    private string movepokerList;
    private string targetpoker;
    private int submit_time;
    private string pokerHome;    
    private int canSendPokers;

    //防止MissingMethodException: Method not found: 'Default constructor not found...ctor()   报错
    public SendPokers() {}

    public SendPokers(string movepokerList, string targetpoker, int submit_time, string pokerHome, int canSendPokers)
    {
        MovepokerList = movepokerList;
        Targetpoker = targetpoker;
        Submit_time = submit_time;
        PokerHome = pokerHome;
        CanSendPokers = canSendPokers;
    }

    //用户想要移动的牌
    public string MovepokerList
    {
        get
        {
            return movepokerList;
        }
        set
        {
            movepokerList = value;
        }
    }

    //移动的卡牌要放置在哪张卡牌上，或哪个位置上
    public string Targetpoker
    {
        get
        {
            return targetpoker;
        }
        set
        {
            targetpoker = value;
        }
    }

    //打出牌的时间
    public int Submit_time
    {
        get
        {
            return submit_time;
        }
        set
        {
            submit_time = value;
        }
    }

    public string PokerHome
    {
        get
        {
            return pokerHome;
        }
        set
        {
            pokerHome = value;
        }
    }   

    //能不能出牌
    public int CanSendPokers
    {
        get
        {
            return canSendPokers;
        }
        set
        {
            canSendPokers = value;
        }
    }
}
public class TipsPokers
{
    private string suggestPokers;
    //提示建议出的牌，如果为空则为不出
    public string SuggestPokers
    {
        get
        {
            return suggestPokers;
        }
        set
        {
            suggestPokers = value;
        }
    }
}


//public class Users
//{
//    public int userState { get; set; }  //0未进入游戏  1 进入游戏  2匹配中  3进入房间（未准备） 4进入房间（已准备）  5开始游戏 
//    public string userId { get; set; }
//    public string roomId { get; set; }
//    public int score { get; set; }
//    public int isLandLord { get; set; }
//    public int userPosition { get; set; }
//    public int userpokerList { get; set; }      //用户拥有的卡牌
//    public double daojishiTime { get; set; }    //倒计时准备时间
//}
