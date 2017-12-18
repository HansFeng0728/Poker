using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class HandCards : MonoBehaviour {

    public GameObject[] cards;
    
    void Start()
    {
        InitCards();
        InitHasCards();
    }

    void InitCards()
    {
        InitHandCards();

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
                trans[j].subIndex = j;
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

    protected void InitHandCards()
    {

        for (int i = 0; i < cards.Length; i++)
        {
            Manager.handCards.Add(cards[i]);
        }
    }
}
