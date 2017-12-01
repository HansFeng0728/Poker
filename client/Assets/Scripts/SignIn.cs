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
        SendPokers poker = new SendPokers("1", "2", 3, "4", 5);
        string jsonStr1 = JsonMapper.ToJson(poker);
        Debug.Log(jsonStr1);
        SendPokers poker1 = JsonMapper.ToObject<SendPokers>(jsonStr1);
        Debug.Log(poker1.MovepokerList);
        
    }

    public void Sign()
    {
        Manager.player0 = new PlayerInfo(inPutLabel.text);        
        //Manager.httpVar.Connect(Manager.player0.Name);
        
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
