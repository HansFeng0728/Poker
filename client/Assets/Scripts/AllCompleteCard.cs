using UnityEngine;
using System.Collections;

public class AllCompleteCard : MonoBehaviour {

    public GameObject cards1;
    public GameObject cards2;
    public GameObject cards3;
    public GameObject cards4;

    void Start()
    {
        InitCards();
    }

    void InitCards()
    {
        Manager.completeCards.Add(cards1);
        Manager.completeCards.Add(cards2);
        Manager.completeCards.Add(cards3);
        Manager.completeCards.Add(cards4);

        UISprite bg = null;
        CardActivity cardActivity = null;
        for(int i = 0;i< 4;i++)
        {
            bg = Manager.completeCards[i].GetComponentInChildren<UISprite>();
            cardActivity = Manager.completeCards[i].GetComponentInChildren<CardActivity>();
            cardActivity.type = 2;
            cardActivity.index = i;
            Manager.completeCardBgs.Add(bg);
        }
        
    }
}
