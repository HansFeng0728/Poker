using UnityEngine;
using System.Collections;

public class MethodhandCards : MonoBehaviour {

    public static void RemoveCard(int index, int num)
    {
        for (int i = 0; i < Manager.player0.HandCardsList[index].CardList.Count; i++)
        {
            if (Manager.player0.HandCardsList[index].CardList[i].Number == num)
            {
                Manager.player0.HandCardsList[index].CardList.RemoveAt(i);
                break;
            }
        }
        for (int i = 0; i < Manager.player0.AllHandCards.Count; i++)
        {
            if (Manager.player0.AllHandCards[i].Number == num)
            {
                Manager.player0.AllHandCards.RemoveAt(i);
                break;
            }
        }
    }

    public static void AddCard(int index, int num,int state = 0)
    {
        Card cardInfo = MethodAllCards.CreateCardInfo(num,2,index);
        cardInfo.State = state == 1 ? 1 : 0;  
        Manager.player0.AllHandCards.Add(cardInfo);
        Manager.player0.HandCardsList[index].CardList.Add(cardInfo);
    }

    public static int FindPosition(int index, int number)
    {
        int position;
        int length = Manager.player0.HandCardsList[index].CardList.Count;
        for (int i = 0; i < length; i++)
        {
            int num1 = Manager.player0.HandCardsList[index].CardList[i].Number;
            if (num1 == number)
                return i;
        }
        return -1111111;
    }
}
