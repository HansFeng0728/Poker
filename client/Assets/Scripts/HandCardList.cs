using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class HandCardList : MonoBehaviour {

    public int type;
    public int index;
    public GameObject cardList;

    void Start()
    {       
    }

    //void InitCards()
    //{
    //    CardActivity[] trans = cardList.GetComponentsInChildren<CardActivity>();
    //    GameObject obj = null;
    //    UISprite bg = null;
    //    for (int i = 0; i < trans.Length; i++)
    //    {
    //        obj = trans[i].gameObject;
    //        bg = obj.GetComponentInChildren<UISprite>();
    //        Manager.handCardLists[i].Add(obj);
    //        Manager.handCardListBgs[i].Add(bg);
    //    }
    //}

    void RefreshCards()
    {
        for (int i = 0; i < Manager.handCardLists[index].Count; i++)
        {
            Manager.handCardLists[index][i].SetActive(false);
        }
    }


    public void ClickHandCards()
    {
        //接牌
        if (Manager.choosed)
        {
            int num;
            Card card;
            int nextIndex;            

            if (Manager.choosedCards.Type == 1 || Manager.choosedCards.Type == 2)
            {
                nextIndex = Manager.player0.HandCardsList[index].CardList.Count;
                num = Manager.choosedCards.CardList[0].Number;
                card = MethodAllCards.CreateCardInfo(num, type, index);

                //确保移动区第一张为K
                List<int> typeList = MethodAllCards.NumToType(num);
                bool CheckK = typeList[1] == 13 ? true : false;
                if (!CheckK)
                {
                    return;
                    Manager.ChoosedCardsReset();
                }
                    

                //移动区表现层
                Manager.handCardLists[index][nextIndex].SetActive(true);
                Manager.handCardListBgs[index][nextIndex].spriteName = num.ToString();

                //从洗牌堆传来的牌
                if (Manager.choosedCards.Type == 1)
                {
                    //数据层
                    MethodshuffleCards.RemoveCard(num);
                    MethodhandCards.AddCard(index, num);

                    //洗牌区表现层
                    Manager.shuffleCards[0].SetActive(false);

                    Debug.Log(Manager.player0.AllHandCards.Count);
                }
                //从存牌区传来的牌
                else
                {
                    int compIndex = Manager.choosedCards.CardList[0].Index;

                    //数据层
                    MethodcompleteCards.RemoveCard(compIndex, num);
                    MethodhandCards.AddCard(index, num);

                    //存牌区表现层
                    int compCount = Manager.player0.CompleteCardList[compIndex].CardList.Count;
                    if (compCount >= 1)
                    {
                        Manager.completeCardBgs[compIndex].spriteName = Manager.player0.CompleteCardList[compIndex].CardList[compCount - 1].Number.ToString();
                    }
                    else
                    {
                        Manager.completeCards[compIndex].SetActive(false);
                    }
                    Debug.Log(Manager.player0.AllHandCards.Count);
                }
            }
            //移动区相互传牌
            else
            {
                int chooseLength = Manager.choosedCards.CardList.Count;
                int chooseIndex = Manager.choosedCards.Index;
                int CurrentLength = Manager.player0.HandCardsList[index].CardList.Count;

                //是否移动区第一张为K
                List<int> typeList = MethodAllCards.NumToType(Manager.choosedCards.CardList[chooseLength - 1].Number);
                bool CheckK = typeList[1] == 13 ? true : false;
                if (!CheckK)
                {
                    return;
                    Manager.ChoosedCardsReset();
                }

                for (int i = chooseLength-1; i >=0; i--)
                {
                    int length = chooseLength + CurrentLength;
                    if (length < 13)
                    {
                        nextIndex = Manager.player0.HandCardsList[index].CardList.Count;
                        num = Manager.choosedCards.CardList[i].Number;
                        card = MethodAllCards.CreateCardInfo(num, type, index);
                        int choosePosition = i;
                        //被移动区表现层
                        Manager.handCardLists[index][nextIndex].SetActive(true);
                        Manager.handCardListBgs[index][nextIndex].spriteName = num.ToString();

                        //主动移动区表现层
                        Manager.handCardLists[chooseIndex][choosePosition].SetActive(false);

                        //数据层
                        MethodhandCards.RemoveCard(chooseIndex, num);
                        MethodhandCards.AddCard(index, num);

                    }
                }
            }
            Manager.ChoosedCardsReset();
        }
        else
        {
            Debug.Log("移动区没牌");
        }
    }
}
