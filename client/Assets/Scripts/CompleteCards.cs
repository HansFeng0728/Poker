using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class CompleteCards : MonoBehaviour {

    public GameObject card;
    public int index;

    private UISprite cardbg;
    private int count;
    private int num;
    private int length;
    private int previewNum;
    private int chooseIndex;
    private int chooseType;

    void Start()
    {
        InitCards();
    }

    void Update()
    {
    }

    public void ClickCards()
    {
        if (Manager.choosed)
        {
            count = Manager.choosedCards.CardList.Count;
            num = Manager.choosedCards.CardList[count - 1].Number;
            List<int> typeList = MethodAllCards.NumToType(num);
            bool CheckA = typeList[1] == 1 ? true : false;                     
            
            //从洗牌堆获取牌
            if (Manager.choosedCards.Type == 1 && CheckA)
            {
                ClickCardsType1();      
                Debug.Log(Manager.player0.ShufflePokerList.CardList.Count);
            }

            //存牌区相互传牌
            if (Manager.choosedCards.Type == 2 && CheckA)
            {
                ClickCardsType2();
                Debug.Log(Manager.player0.ShufflePokerList.CardList.Count);
            }

            //从移动区获得牌
            if (Manager.choosedCards.Type == 3 && CheckA)
            {
                ClickCardsType3();              
                Debug.Log(Manager.player0.ShufflePokerList.CardList.Count);
            }
            Manager.ChoosedCardsReset();
        }
        else
        {
            Debug.Log("!!!!!!!!!!!!!!!");
        }
    }    

    void InitCards()
    {
        cardbg = card.GetComponentInChildren<UISprite>();
        card.SetActive(false);
    }

    public void ClickCardsType1()
    {
        card.SetActive(true);        
        length = Manager.player0.CompleteCardList[index].CardList.Count;
        cardbg.spriteName = num.ToString();

        //表现层
        Manager.shuffleCards[0].SetActive(false);

        //数据层
        MethodshuffleCards.RemoveCard(num);
        MethodcompleteCards.AddCard(index, num);   
    }

    public void ClickCardsType2()
    {
        card.SetActive(true);        
        length = Manager.player0.CompleteCardList[index].CardList.Count;
        cardbg.spriteName = num.ToString();
        chooseIndex = Manager.choosedCards.Index;
        chooseType = Manager.choosedCards.Type;

        //数据层
        MethodcompleteCards.RemoveCard(chooseIndex, num);
        MethodcompleteCards.AddCard(index, num);

        //表现层
        length = Manager.player0.CompleteCardList[chooseIndex].CardList.Count;
        if (length >= 1)
        {
            previewNum = Manager.player0.CompleteCardList[chooseIndex].CardList[length - 1].Number;
            Manager.completeCardBgs[chooseIndex].spriteName = previewNum.ToString();
        }
        else
        {
            Manager.completeCards[chooseIndex].SetActive(false);
        }
    }

    public void ClickCardsType3()
    {
        card.SetActive(true);
        length = Manager.player0.CompleteCardList[index].CardList.Count;
        cardbg.spriteName = num.ToString();
        chooseIndex = Manager.choosedCards.Index;
        chooseType = Manager.choosedCards.Type;

        //表现层
        length = Manager.player0.HandCardsList[chooseIndex].CardList.Count;
        Manager.handCardLists[chooseIndex][length - 1].SetActive(false);

        //数据层
        MethodhandCards.RemoveCard(chooseIndex, num);
        MethodcompleteCards.AddCard(index, num);
    }
}
