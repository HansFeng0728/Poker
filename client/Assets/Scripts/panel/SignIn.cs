using UnityEngine;
using System.Collections;
using LitJson;
using System.Collections.Generic;

public class SignIn : MonoBehaviour {

    public UILabel inPutLabel;
    public GameObject selectLevel;

    void Start()
    {
        selectLevel.SetActive(false);
        //SendPokers sendPokers = new SendPokers();
        //sendPokers.UserId = "abc";
        //sendPokers.MovePoker_Position = 0;
        //sendPokers.MovePoker = "20-1";
        //sendPokers.TargetPoker = "30-1";
        //string json = JsonMapper.ToJson(sendPokers);
        //SendPokers send = JsonMapper.ToObject<SendPokers>(json);
        //Debug.Log(json);
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
            selectLevel.SetActive(true);                       
            //MethodAllCards.InitPlayerInfo();   //单机版用   
            //Manager.InitLobby();
        }     
    }

    public void Back()
    {
        Destroy(this.gameObject);
    }

    public void Easy()
    {
        Manager.level = "initEasyCards";
        Manager.player0 = new PlayerInfo();
        Manager.httpVar.ConnectRequest(inPutLabel.text);
        selectLevel.SetActive(false);
    }

    public void Normal()
    {
        Manager.level = "initNormalCards";
        Manager.player0 = new PlayerInfo();
        Manager.httpVar.ConnectRequest(inPutLabel.text);
        selectLevel.SetActive(false);
    }

    public void Hard()
    {
        Manager.level = "initHardCards";
        Manager.player0 = new PlayerInfo();
        Manager.httpVar.ConnectRequest(inPutLabel.text);
        selectLevel.SetActive(false);
    }
}
