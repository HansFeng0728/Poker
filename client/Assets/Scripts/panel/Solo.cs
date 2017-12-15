using UnityEngine;
using System.Collections;

public class Solo : MonoBehaviour {

    public UILabel inPutLabel;

	// Use this for initialization
	void Start () 
    {         
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    public void Back() 
    {
        Destroy(this.gameObject);
    }

    public void SignSolo()
    {
        if (inPutLabel.text == "")
        {
            Manager.windowLabel = "请输入玩家姓名";
            Manager.InitWindow();
        }
        else
        {
            Manager.player0 = new PlayerInfo();
            Manager.player0.Name = inPutLabel.text;
            MethodAllCards.InitSoloPlayerInfo();   //单机版用  
            Manager.InitLobby();
        }
    } 
}
