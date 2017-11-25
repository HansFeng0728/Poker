using UnityEngine;
using System.Collections;
using LitJson;
using System.Collections.Generic;

public class SignIn : MonoBehaviour {

    public UILabel inPutLabel;
    public GameObject lobby;    
    public UIPanel window;

    void Start()
    {
        Message mes = new Message();
        mes.token = "aaa";
        mes.roomId = "ddd";
        mes.text = "sss";
        string jsonStr = JsonMapper.ToJson(mes);
        Debug.Log(jsonStr);
        Pokers pokers = new Pokers();
        pokers.pokerId = "32";
        List<string> list1 = new List<string>();
        list1.Add("11111111");
        list1.Add("22222222");
        pokers.PokerList = list1;
        string jsonStr2 = JsonMapper.ToJson(pokers);
        Debug.Log(jsonStr2);
    }

    public void Sign()
    {
        Manager.player0 = new PlayerInfo(inPutLabel.text);
        Manager.player1 = new PlayerInfo("电脑1");
        Manager.player1.State = 3;        
        Manager.httpVar.Connect(Manager.player0.Name);
        
        //发送信息向服务器端
        //s.SendMessage("cardlist");

        if (Manager.player0.Name == "")
        {
            window.alpha = 1;
        }
        else
        {
            lobby.GetComponent<UIPanel>().alpha = 1;            
        }     
    }
}
