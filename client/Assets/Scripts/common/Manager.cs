using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;

public class Manager : MonoBehaviour
{
    public static Manager instance;

    void Awake()
    {
        instance = this;
    }

    void Start()
    {
        InitHandCards();
        InitPanel("ChooseModel");
    }

    public static http httpVar = null;
    public static PlayerInfo player0 = null;   
    public static List<Card> allCardList = new List<Card>();
    public static List<int> firstOpenCards = new List<int>();
    public static UISprite shuffleCardBg;
    public static List<GameObject> shuffleCards = new List<GameObject>();

    public static List<GameObject> completeCards = new List<GameObject>();
    public static List<UISprite> completeCardBgs = new List<UISprite>();

    public static List<GameObject> handCards = new List<GameObject>();

    public static List<List<GameObject>> handCardLists = new List<List<GameObject>>();
    public static List<List<UISprite>> handCardListBgs = new List<List<UISprite>>();

    public static int shuffleIndex;
    public static int shuffleLevel; //洗牌区显示第几张牌,0为最底下,2为最上层
    public static bool choosed = false; //是否已经有牌被选中
    public static Cards choosedCards = new Cards(-1);
    public static bool moveCardsHttp = false;    //用于http的移牌

    public static string windowLabel = "";

    public void InitPanel(string panelName)
    {
        GameObject Prefab = Resources.Load("Prefabs/" + panelName) as GameObject;
        GameObject Panel = MonoBehaviour.Instantiate(Prefab) as GameObject;
        Panel.transform.parent = GameObject.Find("Camera").transform;
        Panel.transform.localScale = Vector3.one;
        Panel.transform.localScale = new Vector3(1.4f, 1.24f, 1);
        Panel.SetActive(true);
    }    

    public static void InitLobby()
    {
        GameObject lobbyPrefab = Resources.Load("Prefabs/Lobby") as GameObject;
        GameObject lobbypanel = MonoBehaviour.Instantiate(lobbyPrefab) as GameObject;
        lobbypanel.transform.parent = GameObject.Find("Camera").transform;
        lobbypanel.transform.localScale = Vector3.one;
        lobbypanel.transform.localScale = new Vector3(1.4f, 1.24f, 1);
        lobbypanel.SetActive(true);
    }

    public static void InitWindow()
    {
        GameObject windowPrefab = Resources.Load("Prefabs/Window") as GameObject;
        GameObject windowPanel = MonoBehaviour.Instantiate(windowPrefab) as GameObject;
        windowPanel.transform.parent = GameObject.Find("Camera").transform;
        windowPanel.transform.localScale = Vector3.one;
        windowPanel.transform.localScale = new Vector3(1.4f, 1.24f, 1);
    }

    public static void InitSolo()
    {
        GameObject soloPrefab = Resources.Load("Prefabs/Solo") as GameObject;
        GameObject soloPanel = MonoBehaviour.Instantiate(soloPrefab) as GameObject;
        soloPanel.transform.parent = GameObject.Find("Camera").transform;
        soloPanel.transform.localScale = Vector3.one;
        soloPanel.transform.localScale = new Vector3(1.4f, 1.24f, 1);
    }

    public static void InitNetSignIn()
    {
        GameObject netSignInPrefab = Resources.Load("Prefabs/NetSignIn") as GameObject;
        GameObject netSignInPanel = MonoBehaviour.Instantiate(netSignInPrefab) as GameObject;
        netSignInPanel.transform.parent = GameObject.Find("Camera").transform;
        netSignInPanel.transform.localScale = Vector3.one;
        netSignInPanel.transform.localScale = new Vector3(1.4f, 1.24f, 1);
    }

     public static void InitHandCards()
    {
        InitHandCardLists();
        InitHandCardListBgs();
    }

    public static void ChoosedCardsReset()
     {
         MethodAllCards.ResetColor();
         Manager.choosed = false;
         Manager.choosedCards.Reset();
     }

    public static void ResetCards()
    {
        player0 = null;
        allCardList = new List<Card>();
        shuffleCards = new List<GameObject>();
            
        completeCards = new List<GameObject>();
        completeCardBgs = new List<UISprite>();

        handCards = new List<GameObject>();
        handCardLists = new List<List<GameObject>>();
        handCardListBgs = new List<List<UISprite>>();

        InitHandCards();

        choosed = false; //是否已经有牌被选中
        choosedCards = new Cards(-1);
        moveCardsHttp = false;    //用于http的移牌
    }

    public static void InitHandCardLists()
    {
        
        for (int i = 0; i < 7; i++)
        {
            List<GameObject> handCardList = new List<GameObject>();
            handCardLists.Add(handCardList);
        }
    }

    public static void InitHandCardListBgs()
    {
        
        for (int i = 0; i < 7; i++)
        {
            List<UISprite> handCardListBg = new List<UISprite>();
            handCardListBgs.Add(handCardListBg);
        }
    }
}
