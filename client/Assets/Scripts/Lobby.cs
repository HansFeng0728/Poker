using UnityEngine;
using System.Collections;

public class Lobby : MonoBehaviour {
    
    public int multiple = 10;

    public UILabel SigninLabel;

    public UILabel name0;
    public GameObject readyButton;
    public GameObject ready0;
    public UILabel multipleShow;

    private bool gameStart;

    void Start()
    {
                
    }

    void Update()
    {
       
    }

    //返回重新登陆
	public void BackView()
    {
        //退出游戏玩家头像和名字需要消失
        this.GetComponent<UIPanel>().alpha = 0;        
    }

    public void InitPanel()
    {
        readyButton.SetActive(true);
    }

    void InitPlayerInfo()
    {
        name0.text = Manager.player0.Name;
    }
}
