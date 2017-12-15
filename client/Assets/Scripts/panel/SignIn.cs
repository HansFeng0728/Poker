using UnityEngine;
using System.Collections;
using LitJson;
using System.Collections.Generic;

public class SignIn : MonoBehaviour {

    public UILabel inPutLabel;

    void Start()
    {
        SendPokers sendPokers = new SendPokers();
        sendPokers.UserId = "abc";
        sendPokers.MovePoker_Position = 0;
        sendPokers.MovePoker = "20-1";
        sendPokers.TargetPoker = "30-1";
        //sendPokers.PokerHome = 1;
        string json = JsonMapper.ToJson(sendPokers);
        SendPokers send = JsonMapper.ToObject<SendPokers>(json);
        Debug.Log(json);
    }

    public void Sign()
    {
        if (inPutLabel.text == "")
        {
            Manager.windowLabel = "请输入玩家姓名";
            Manager.InitWindow();
        }
        else
        {
            Manager.player0 = new PlayerInfo();
            Manager.httpVar.ConnectRequest(inPutLabel.text);            
            //MethodAllCards.InitPlayerInfo();   //单机版用   
            //Manager.InitLobby();
        }     
    }

    public void Back()
    {
        Destroy(this.gameObject);
    }
}
