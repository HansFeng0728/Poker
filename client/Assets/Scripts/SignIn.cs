using UnityEngine;
using System.Collections;
using LitJson;
using System.Collections.Generic;

public class SignIn : MonoBehaviour {

    public UILabel inPutLabel;

    void Start()
    {
        //SendPokers sendPokers = new SendPokers();
        //sendPokers.UserId = "abc";
        //sendPokers.Movepoker_position = 0;
        //sendPokers.Movepoker = "20:1";
        //sendPokers.Targetpoker = "30:1";
        //sendPokers.PokerHome = 1;
        //string json = JsonMapper.ToJson(sendPokers);
        //Debug.Log(json);
    }

    public void Sign()
    {
        if (inPutLabel.text == "")
        {
            Manager.InitWindow();
        }
        else
        {
            Manager.httpVar.ConnectRequest(inPutLabel.text);
            Manager.player0 = new PlayerInfo();
            //MethodAllCards.InitPlayerInfo();   //单机版用   
            //Manager.InitLobby();
        }     
    }    
}
