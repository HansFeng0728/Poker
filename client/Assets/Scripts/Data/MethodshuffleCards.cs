using UnityEngine;
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

        List<int> typeList = MethodAllCards.NumToType(num);
        if (typeList[1] == 1)
        {
            for (int i = 0; i < completeCount; i++)
            {
                listCount = Manager.player0.CompleteCardList[i].CardList.Count;
                if (listCount == 0)
                {
                    //表现层
                    Manager.shuffleCards[0].SetActive(false);
                    Manager.completeCards[i].SetActive(true);
                    Manager.completeCardBgs[i].spriteName = num.ToString();

                    //数据层
                    MethodshuffleCards.RemoveCard(num);
                    MethodcompleteCards.AddCard(i, num);

                    Debug.Log("双击洗牌区");
                    return;
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
            if (sameColorType && compareNumIsLow)
            {
                //表现层
                Manager.shuffleCards[0].SetActive(false);
                Manager.completeCardBgs[i].spriteName = num.ToString();

                //数据层
                MethodshuffleCards.RemoveCard(num);
                MethodcompleteCards.AddCard(i, num);

                Debug.Log("双击洗牌区");
                return;
            }
        }
        Debug.Log("无法双击牌到存牌区");
    }
}
