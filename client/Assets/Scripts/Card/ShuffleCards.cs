using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class ShuffleCards : MonoBehaviour {

    public GameObject Card1;
    public GameObject Card2;
    public GameObject Card3;
    public GameObject card;
    public int sapce;
    
    private List<UISprite> cardsBg = new List<UISprite>();

    private int count = Manager.player0.ShufflePokerList.CardList.Count - 1; 
    
	// Use this for initialization
    void Start()
    {
        InitCards();
        RefreshCards();
        Manager.shuffleIndex = -1;
    }
	
	// Update is called once per frame
	void Update () 
    {
        if(Manager.player0 != null)
            count = Manager.player0.ShufflePokerList.CardList.Count - 1;
	}

    public void ClickShuffleCards()
    {     
        RefreshCards();
        if (count > 0)
        {
            Manager.ChoosedCardsReset();
            Manager.shuffleIndex++;
            ShowCards();            
        }
    }

    void RefreshCards()
    {
        Manager.shuffleCards[0].SetActive(false);
    }
    
    void ShowCards()
    {
        if (Manager.shuffleIndex >= count)
            Manager.shuffleIndex = 0;

        Manager.shuffleCards[0].SetActive(true);
        UISprite bg = Manager.shuffleCards[0].transform.GetComponentInChildren<UISprite>();
        Debug.Log(Manager.shuffleIndex);
        bg.spriteName = Manager.player0.ShufflePokerList.CardList[Manager.shuffleIndex].Number.ToString();        
    }

    void InitCards()
    {
        Manager.shuffleCards.Add(Card1);
        Manager.shuffleCardBg = Card1.transform.GetComponentInChildren<UISprite>();
        MethodAllCards.InitCardType(Manager.shuffleCards[0], 1);
    }

}
