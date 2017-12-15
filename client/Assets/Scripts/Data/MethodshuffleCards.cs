﻿using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class MethodshuffleCards
{
    public static void RemoveCard(int num)
    {
        for (int i = 0; i < Manager.player0.ShufflePokerList.CardList.Count; i++)
        {
            if (Manager.player0.ShufflePokerList.CardList[i].Number == num)
            {
                Manager.player0.ShufflePokerList.CardList.RemoveAt(i);
                break;
            }
        }
    }

    public static void AddCard(int num, int state = 0)
    {
        Card cardInfo = MethodAllCards.CreateCardInfo(num);
        cardInfo.State = state == 1 ? 1 : 0;
        Manager.player0.ShufflePokerList.CardList.Add(cardInfo);
    }

    public static void DoubleClick(int num)
    {
        int completeCount = Manager.player0.CompleteCardList.Count;
        int listCount;
        int number;
        bool sameColorType;
        bool compareNumIsLow;
        int positionNum;

        List<int> typeList = MethodAllCards.NumToType(num);
        if (typeList[1] == 1)
        {
            for (int i = 0; i < completeCount; i++)
            {
                string movePoker = (num-1).ToString() + "-"+"1";

                listCount = Manager.player0.CompleteCardList[i].CardList.Count;
                if (listCount == 0)
                {
                    //联机版
                    if (Manager.httpVar != null)
                    {
                        Manager.httpVar.SendCardsRequset(movePoker, "", 0, 8 + i, delegate()
                        {
                            if (!Manager.moveCardsHttp)
                            {
                                Debug.Log("不能移牌");
                                Manager.ChoosedCardsReset();  
                                return;
                            }

                            DoubleClickACardDefine(i, num);
                        });
                        
                        return;   
                    }                                                       
                }
            }            
        }

        for (int i = 0; i < completeCount; i++)
        {
            listCount = Manager.player0.CompleteCardList[i].CardList.Count;
            if (listCount == 0)
                continue;
            number = Manager.player0.CompleteCardList[i].CardList[listCount - 1].Number;
            sameColorType =MethodAllCards.SameColorType(number, num);
            compareNumIsLow =MethodAllCards.CompareNumIsLow(number, num);

            string movePoker = (num - 1).ToString() + "-" + "1";
            int positionIndex = MethodAllCards.FindPosition(number);
            int positionState = Manager.allCardList[positionIndex].State;
            string targetPoker = (number - 1).ToString() + "-" + positionState.ToString();

            if (sameColorType && compareNumIsLow)
            {
                //联机版
                if (Manager.httpVar != null)
                {
                    Manager.httpVar.SendCardsRequset(movePoker, targetPoker, 0, 8 + i, delegate()
                    {
                        if (!Manager.moveCardsHttp)
                        {
                            Debug.Log("不能移牌");
                            Manager.ChoosedCardsReset();  
                            return;
                        }

                        DoubleClickCardDefine(i, num);
                    });
                    return;
                }                
            }
        }
    }

     public static void DoubleClickACardDefine(int index,int num)
    {
        //表现层        
        Manager.completeCards[index].SetActive(true);
        Manager.completeCardBgs[index].spriteName = num.ToString();

        //数据层
        MethodshuffleCards.RemoveCard(num);
        MethodcompleteCards.AddCard(index, num);

        //额外表现层
        if (Manager.shuffleIndex >= Manager.player0.ShufflePokerList.CardList.Count - 1)
        {
            Manager.shuffleIndex = 0;
            Manager.shuffleCards[0].SetActive(false);
        }
        Manager.shuffleCards[0].SetActive(true);
        Manager.shuffleCardBg.spriteName = Manager.player0.ShufflePokerList.CardList[Manager.shuffleIndex].Number.ToString();

        Debug.Log("双击洗牌区");

        Manager.ChoosedCardsReset();
    }

    public static void DoubleClickCardDefine(int index,int num)
     {
         //表现层
         Manager.shuffleCards[0].SetActive(false);
         Manager.completeCardBgs[index].spriteName = num.ToString();

         //数据层
         MethodshuffleCards.RemoveCard(num);
         MethodcompleteCards.AddCard(index, num);

         //额外表现层
         if (Manager.shuffleIndex >= Manager.player0.ShufflePokerList.CardList.Count - 1)
         {
             Manager.shuffleIndex = 0;
             Manager.shuffleCards[0].SetActive(false);
         }
         Manager.shuffleCards[0].SetActive(true);
         Manager.shuffleCardBg.spriteName = Manager.player0.ShufflePokerList.CardList[Manager.shuffleIndex].Number.ToString();

         Debug.Log("双击洗牌区");
         Manager.ChoosedCardsReset();
     }

    public static void ChangeColor()
    {
        Manager.shuffleCardBg.color = new Color(225 / 255f, 220 / 255f, 150 / 255f);
    }
}
