using UnityEngine;
using System.Collections;

public class MethodcompleteCards
{
    public static void RemoveCard(int index,int num)
    {
        for (int i = 0; i < Manager.player0.CompleteCardList[index].CardList.Count; i++)
        {
            if (Manager.player0.CompleteCardList[index].CardList[i].Number == num)
            {
                Manager.player0.CompleteCardList[index].CardList.RemoveAt(i);
                break;
            }
        }
        for (int i = 0; i < Manager.player0.AllCompleteCards.Count; i++)
        {
            if (Manager.player0.AllCompleteCards[i].Number == num)
            {
                Manager.player0.AllCompleteCards.RemoveAt(i);
                break;
            }
        }
    }

    public static void AddCard(int index,int num)
    {
        Card cardInfo = MethodAllCards.CreateCardInfo(num);
        Manager.player0.AllCompleteCards.Add(cardInfo);
        Manager.player0.CompleteCardList[index].CardList.Add(cardInfo);
    }

    public static bool Check13(int index)
    {
        int count = Manager.player0.CompleteCardList[index].CardList.Count;
        if (count == 13)
            return true;
        else
            return false;
    }
}
