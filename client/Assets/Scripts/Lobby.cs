using UnityEngine;
using System.Collections;

public class Lobby : MonoBehaviour {
    
    public int multiple = 10;

    public UILabel SigninLabel;

    public UITexture icon0;
    public UITexture icon1;
    public UITexture icon2;
    public UILabel name0;
    public UILabel name1;
    public GameObject readyButton;
    public GameObject ready0;
    public GameObject ready1;
    public Animation cardA1;
    public Animation cardA2;
    public Animation SendCardmask;
    public GameObject sendCard;
    public UILabel multipleShow;
    public GameObject gamble;
    public GameObject notGamble;
    public GameObject pushCard;

    public UISprite[] cardlist0;
    public UISprite[] cardlist1;

    private bool gameStart;
    private ArrayList cardList = new ArrayList();
    private int cardNum;
    private int[] cardNum0 = new int[17];
    private int[] cardNum1 = new int[17];

    void Start()
    {
        for (int i = 1; i <= 54; i++)
            cardList.Add(i);        
    }

    void Update()
    {
        if (!gameStart && this.GetComponent<UIPanel>().alpha == 1)
        {
            InitPlayerInfo();
            multipleShow.text = multiple.ToString() + "倍";
            if (Manager.player0.State == 3)
                ready0.SetActive(true);
            else
                ready0.SetActive(false);

            if (Manager.player1.State == 3)
                ready1.SetActive(true);
            else
                ready1.SetActive(false);

            if (Manager.player0.State == 3 && Manager.player1.State == 3)
            {
                InitCard(cardlist0, cardNum0);
                InitCard(cardlist1, cardNum1);
                //SignIn.player0.StartGame = false;
                //SignIn.player1.StartGame = false;
                Play();
            }
        }        
    }

    //返回重新登陆
	public void BackView()
    {
        //退出游戏玩家头像和名字需要消失
        this.GetComponent<UIPanel>().alpha = 0;        
    }
    
    public void Play()
    {        
        sendCard.active = false;
        cardA1.Play();
        cardA2.Play();
        SendCardmask.Play();
    }

    public void Gamble()
    {
        multiple *= 2;
        gamble.SetActive(false);
        notGamble.SetActive(false);
        pushCard.SetActive(true);
    }

    public void NotGamble()
    {
        gamble.SetActive(false);
        notGamble.SetActive(false);
        pushCard.SetActive(true);
    }
    public void Ready()
    {
        Manager.player0.State = 3;
        gamble.SetActive(true);
        notGamble.SetActive(true);
        readyButton.SetActive(false);        
    }

    public void Sort()
    {
        for (int i = 0; i < cardNum0.Length; i++)
        {
            Debug.Log(cardNum0[i]);
            cardlist0[i].spriteName = cardNum0[i].ToString();
            cardlist1[i].spriteName = cardNum1[i].ToString();
        }
    }

    public void InitCard(UISprite[] cList,int[] cNum)
    {
        for (int i = 0; i < cList.Length; i++)
        {
            cardNum = Random.Range(0, cardList.Count);
            cList[i].spriteName = cardList[cardNum].ToString();
            cNum[i] = (int)cardList[cardNum];
            cardList.Remove(cardList[cardNum]);
        }
        System.Array.Sort(cNum);
        System.Array.Reverse(cNum);
    }

    public void InitPanel()
    {
        readyButton.SetActive(true);
    }
    void InitPlayerInfo()
    {
        name0.text = Manager.player0.Name;
        name1.text = Manager.player1.Name;
        icon0.mainTexture = Manager.player0.Icon;
        icon1.mainTexture = Manager.player1.Icon;
    }
}
