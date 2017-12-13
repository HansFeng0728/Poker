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
        InitChooseModel();
    }

    public static http httpVar = null;
    public static PlayerInfo player0 = null;   
    public static List<Card> allCardList = new List<Card>();
    public static UISprite shuffleCardBg;
    public static List<GameObject> shuffleCards = new List<GameObject>();

    public static List<GameObject> completeCards = new List<GameObject>();
    public static List<UISprite> completeCardBgs = new List<UISprite>();

    public static List<GameObject> handCards = new List<GameObject>();

    public static List<List<GameObject>> handCardLists = new List<List<GameObject>>();
    public static List<GameObject> handCardList1 = new List<GameObject>();
    public static List<GameObject> handCardList2 = new List<GameObject>();
    public static List<GameObject> handCardList3 = new List<GameObject>();
    public static List<GameObject> handCardList4 = new List<GameObject>();
    public static List<GameObject> handCardList5 = new List<GameObject>();
    public static List<GameObject> handCardList6 = new List<GameObject>();
    public static List<GameObject> handCardList7 = new List<GameObject>();

    public static List<List<UISprite>> handCardListBgs = new List<List<UISprite>>();
    public static List<UISprite> handCardListBg1 = new List<UISprite>();
    public static List<UISprite> handCardListBg2 = new List<UISprite>();
    public static List<UISprite> handCardListBg3 = new List<UISprite>();
    public static List<UISprite> handCardListBg4 = new List<UISprite>();
    public static List<UISprite> handCardListBg5 = new List<UISprite>();
    public static List<UISprite> handCardListBg6 = new List<UISprite>();
    public static List<UISprite> handCardListBg7 = new List<UISprite>();



    public static int shuffleIndex;
    public static int shuffleLevel; //洗牌区显示第几张牌,0为最底下,2为最上层
    public static bool choosed = false; //是否已经有牌被选中
    public static Cards choosedCards = new Cards(-1);
    public static bool moveCardsHttp = false;    //用于http的移牌

    public void InitChooseModel()
    {
        GameObject chooseModelPrefab = Resources.Load("Prefabs/ChooseModel") as GameObject;
        GameObject chooseModelPanel = MonoBehaviour.Instantiate(chooseModelPrefab) as GameObject;
        chooseModelPanel.transform.parent = GameObject.Find("Camera").transform;
        chooseModelPanel.transform.localScale = Vector3.one;
        chooseModelPanel.SetActive(true);
    }    

    public static void InitLobby()
    {
        GameObject lobbyPrefab = Resources.Load("Prefabs/Lobby") as GameObject;
        GameObject lobbypanel = MonoBehaviour.Instantiate(lobbyPrefab) as GameObject;
        lobbypanel.transform.parent = GameObject.Find("Camera").transform;
        lobbypanel.transform.localScale = Vector3.one;
        lobbypanel.SetActive(true);
    }

    public static void InitWindow()
    {
        GameObject windowPrefab = Resources.Load("Prefabs/Window") as GameObject;
        GameObject windowPanel = MonoBehaviour.Instantiate(windowPrefab) as GameObject;
        windowPanel.transform.parent = GameObject.Find("Camera").transform;
        windowPanel.transform.localScale = Vector3.one;
    }
    
     public static void InitHandCards()
    {
        handCardLists.Add(handCardList1);
        handCardLists.Add(handCardList2);
        handCardLists.Add(handCardList3);
        handCardLists.Add(handCardList4);
        handCardLists.Add(handCardList5);
        handCardLists.Add(handCardList6);
        handCardLists.Add(handCardList7);

        handCardListBgs.Add(handCardListBg1);
        handCardListBgs.Add(handCardListBg2);
        handCardListBgs.Add(handCardListBg3);
        handCardListBgs.Add(handCardListBg4);
        handCardListBgs.Add(handCardListBg5);
        handCardListBgs.Add(handCardListBg6);
        handCardListBgs.Add(handCardListBg7);
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
        handCardList1 = new List<GameObject>();
        handCardList2 = new List<GameObject>();
        handCardList3 = new List<GameObject>();
        handCardList4 = new List<GameObject>();
        handCardList5 = new List<GameObject>();
        handCardList6 = new List<GameObject>();
        handCardList7 = new List<GameObject>();

        handCardListBgs = new List<List<UISprite>>();
        handCardListBg1 = new List<UISprite>();
        handCardListBg2 = new List<UISprite>();
        handCardListBg3 = new List<UISprite>();
        handCardListBg4 = new List<UISprite>();
        handCardListBg5 = new List<UISprite>();
        handCardListBg6 = new List<UISprite>();
        handCardListBg7 = new List<UISprite>();

        InitHandCards();

        choosed = false; //是否已经有牌被选中
        choosedCards = new Cards(-1);
        moveCardsHttp = false;    //用于http的移牌
    }
}
