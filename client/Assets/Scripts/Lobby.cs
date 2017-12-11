using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;

public class Lobby : MonoBehaviour {

    public GameObject shuffleCards;     //洗牌牌堆里面的牌
    public GameObject handCards;        //六个手牌区里	面的牌
    public GameObject completeCards;     //四个存牌区域的卡牌
    public UILabel name;
    public UILabel ClickState;
  
    private GameObject shuffleCardList; //洗牌堆显示已有的牌
    private Vector3 localMousePosition;
    private Vector3 mousePosition;
    


    void Start()
    {
        name.text = Manager.player0.Name;
    }

    void Update()
    {
        ClickState.text = Manager.choosed.ToString();
    }

    //返回重新登陆
	public void BackView()
    {
        //退出游戏玩家头像和名字需要消失
        this.GetComponent<UIPanel>().alpha = 0;        
    }

    public void InitPanel()
    {
        //readyButton.SetActive(true);
    }

    
}
