using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class HandCards : MonoBehaviour {
    public GameObject cards1;
    public GameObject cards2;
    public GameObject cards3;
    public GameObject cards4;
    public GameObject cards5;
    public GameObject cards6;
    public GameObject cards7;
    
    void Start()
    {
        InitCards();
        InitHasCards();
    }

    void InitCards()
    {
        Manager.handCards.Add(cards1);
        Manager.handCards.Add(cards2);
        Manager.handCards.Add(cards3);
        Manager.handCards.Add(cards4);
        Manager.handCards.Add(cards5);
        Manager.handCards.Add(cards6);
        Manager.handCards.Add(cards7);

        GameObject obj = null;
        UISprite bg = null;
        CardActivity[] trans = null;
        HandCardList handCardList = null;

        for (int i = 0; i < Manager.handCards.Count; i++)
        {
            handCardList = Manager.handCards[i].GetComponent<HandCardList>();
            handCardList.type = 3;
            handCardList.index = i;
            trans = Manager.handCards[i].GetComponentsInChildren<CardActivity>();            
            for (int j = 0; j < trans.Length; j++)
            {
                obj = trans[j].gameObject;
                bg = obj.GetComponentInChildren<UISprite>();
                Manager.handCardLists[i].Add(obj);
                Manager.handCardListBgs[i].Add(bg);
                trans[j].type = 3;
                trans[j].index = i;
                Manager.handCardLists[i][j].SetActive(false);
            }
        }        
    }

    void InitHasCards()
    {
        for (int i = 0; i < 7; i++)
        {
            int length = Manager.player0.HandCardsList[i].CardList.Count;
            for (int j = 0; j < length; j++)
            {
                Manager.handCardLists[i][j].SetActive(true);
                int num = Manager.player0.HandCardsList[i].CardList[j].Number;                
                int index = MethodAllCards.FindPosition(num);
                int state = Manager.allCardList[index].State;
                string spriteName = Manager.allCardList[index].Number.ToString();
                Manager.handCardListBgs[i][j].spriteName = (state == 1) ? spriteName : "0";
                //Manager.handCardListBgs[i][j].spriteName = Manager.firstOpenCards.Contains(num) ? spriteName : "0";
            }
        }
    }
}
