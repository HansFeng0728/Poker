using UnityEngine;
using System.Collections;

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
}
