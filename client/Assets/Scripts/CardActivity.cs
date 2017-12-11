﻿using UnityEngine;
using System.Collections;

public class CardActivity : MonoBehaviour {

    public int type;

    public int index;

    private int previewCount;
    private int positionCount;
    private int num;
    private int chooseIndex;
    private int chooseCount;
    private int mIndex;
    private int positionNum;
    private int previewNum;

    public void ClickShuffleCard()
    {
        mIndex = Manager.shuffleIndex;
        previewNum = Manager.player0.ShufflePokerList.CardList[mIndex].Number;        
        if (!Manager.choosed)
        {
            Card card = MethodAllCards.CreateCardInfo(previewNum, type, index);
            Manager.choosedCards.Type = type;
            Manager.choosedCards.CardList.Add(card);
            Manager.choosed = true;
        }
        else
        {
            Debug.Log("不能放牌到洗牌堆");
        }
    }    

    public void ClickCompleteCard()
    {        
        if (!Manager.choosed)
        {
            //因为在存牌区点击卡牌表示一定有牌,所以不做count大于0的判断
            ClickCompleteCardNoChoose();            
        }
        else
        {
            //获取洗牌堆的牌
            if (Manager.choosedCards.Type == 1)
            {
                ClickCompleteCardType1();
            }

            //获取存牌区的牌
            if (Manager.choosedCards.Type == 2)
            {
                ClickCompleteCardType2();
                Debug.Log(Manager.player0.ShufflePokerList.CardList.Count);
            }

            //从移动区获得牌
            if (Manager.choosedCards.Type == 3)
            {
                ClickCompleteCardType3();
                Debug.Log(Manager.player0.ShufflePokerList.CardList.Count);
            }
            Manager.ChoosedCardsReset();
        }
    }

    public void ClickHandCard()
    {
        positionCount = Manager.player0.HandCardsList[index].CardList.Count;
        
        if (!Manager.choosed)
        {
            ClickHandCardNoChoose();            
        }
        else
        {
            //从洗牌堆传来的牌
            if (Manager.choosedCards.Type == 1)
            {
                ClickHandCardType1();
                Debug.Log(Manager.player0.AllHandCards.Count);
            }

            //从存牌区传来的牌
            if (Manager.choosedCards.Type == 2)
            {
                //存牌区不向移牌区传牌
                //ClickHandCardType2(); 
                Debug.Log("存牌区不能向移牌区传牌");
            }

            //移动区相互传牌
            if(Manager.choosedCards.Type == 3)
            {
                ClickHandCardType3();    
            }
            Manager.ChoosedCardsReset();
            Debug.Log(Manager.player0.AllHandCards.Count);            
        }
    }

    public void ClickCompleteCardNoChoose()
    {
        positionCount = Manager.player0.CompleteCardList[index].CardList.Count;
        previewNum = Manager.player0.CompleteCardList[index].CardList[positionCount - 1].Number;

        Card card = MethodAllCards.CreateCardInfo(previewNum, type, index);
        Manager.choosedCards.CardList.Add(card);
        Manager.choosedCards.Type = type;
        Manager.choosedCards.Index = index;

        Manager.choosed = true;
    }

    public void ClickCompleteCardType1()
    {
        chooseIndex = Manager.choosedCards.Index;
        positionCount = Manager.player0.CompleteCardList[index].CardList.Count;
        //previewCount = Manager.player0.CompleteCardList[chooseIndex].CardList.Count;
        previewNum = Manager.choosedCards.CardList[0].Number;

        positionNum = Manager.player0.CompleteCardList[index].CardList[positionCount - 1].Number;
        bool sameColorType = MethodAllCards.SameColorType(positionNum, previewNum);
        bool compareNum = MethodAllCards.CompareNumIsLow(positionNum, previewNum);
        //如果同花色
        if (sameColorType && compareNum)
        {
            //表现层
            Manager.shuffleCards[0].SetActive(false);
            Manager.completeCardBgs[index].spriteName = previewNum.ToString();

            //数据层
            MethodshuffleCards.RemoveCard(previewNum);
            MethodcompleteCards.AddCard(index, previewNum);
        }
        else
        {
            Debug.Log("洗牌堆和存牌堆选定牌的花色不同");
        }
    }

    public void ClickCompleteCardType2()
    {
        positionCount = Manager.player0.CompleteCardList[index].CardList.Count;
        chooseIndex = Manager.choosedCards.Index;
        previewCount = Manager.player0.CompleteCardList[chooseIndex].CardList.Count;
        previewNum = Manager.choosedCards.CardList[0].Number;

        positionNum = Manager.player0.CompleteCardList[index].CardList[positionCount - 1].Number;
        bool sameColorType = MethodAllCards.SameColorType(previewNum, positionNum);
        bool compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);
        if (sameColorType && compareNum)
        {
            //数据层
            MethodcompleteCards.RemoveCard(chooseIndex, previewNum);
            MethodcompleteCards.AddCard(index, previewNum);

            //表现层

            Manager.completeCardBgs[index].spriteName = previewNum.ToString();     //被移牌区

            //主动移牌区
            previewCount = Manager.player0.CompleteCardList[chooseIndex].CardList.Count;
            if (previewCount > 1)
            {
                previewNum = Manager.player0.CompleteCardList[chooseIndex].CardList[previewCount - 1].Number;
                Manager.completeCardBgs[chooseIndex].spriteName = previewNum.ToString();
            }
            else
            {
                Manager.completeCards[chooseIndex].SetActive(false);
            }
        }
        else
        {
            Debug.Log("存牌堆选定牌之间花色不同");
        }
    }

    public void ClickCompleteCardType3()
    {
        chooseIndex = Manager.choosedCards.Index;
        chooseCount = Manager.choosedCards.CardList.Count;
        previewNum = Manager.choosedCards.CardList[chooseCount - 1].Number;

        positionCount = Manager.player0.CompleteCardList[index].CardList.Count;
        positionNum = Manager.player0.CompleteCardList[index].CardList[positionCount - 1].Number;
        bool sameColorType = MethodAllCards.SameColorType(previewNum, positionNum);
        bool compareNum = MethodAllCards.CompareNumIsLow(positionNum, previewNum);

        if (sameColorType && compareNum)
        {
            //表现层
            Manager.completeCardBgs[index].spriteName = previewNum.ToString();

            int length = Manager.player0.HandCardsList[chooseIndex].CardList.Count;
            Manager.handCardLists[chooseIndex][length - 1].SetActive(false);

            //数据层
            MethodhandCards.RemoveCard(chooseIndex, previewNum);
            MethodcompleteCards.AddCard(index, previewNum);
        }
        else
        {
            Debug.Log("移动区和存牌堆选定牌花色不同");
        }
    }

    public void ClickHandCardNoChoose()
    {
        Manager.choosedCards.Type = type;
        Manager.choosedCards.Index = index;
        positionNum = Manager.player0.HandCardsList[index].CardList[positionCount - 1].Number;
        Card card = MethodAllCards.CreateCardInfo(positionNum, type, index);
        Manager.choosedCards.CardList.Add(card);

        //判断是否反面
        if (CheckState1(positionNum) == 1)
        {
            Manager.handCardListBgs[index][positionCount - 1].spriteName = positionNum.ToString();
            Manager.ChoosedCardsReset(); 
            return;
        }
            

        for (int i = positionCount - 1; i >= 1 && positionCount > 1; i--)
        {
            positionNum = Manager.player0.HandCardsList[index].CardList[i].Number;
            previewNum = Manager.player0.HandCardsList[index].CardList[i - 1].Number;

            if (!CheckState2(positionNum, previewNum))
            {
                Manager.choosed = true;
                return;
            }

            bool sameColor = MethodAllCards.SameColor(previewNum, positionNum);
            if (!sameColor)
            {
                card = MethodAllCards.CreateCardInfo(previewNum, type, index);
                Manager.choosedCards.CardList.Add(card);
            }
            else
                break;
        }
        Manager.choosed = true;
    }

    public void ClickHandCardType1()
    {
        positionNum = Manager.player0.HandCardsList[index].CardList[positionCount - 1].Number;
        chooseIndex = Manager.choosedCards.Index;
        chooseCount = Manager.choosedCards.CardList.Count;
        previewNum = Manager.choosedCards.CardList[chooseCount - 1].Number;
        bool sameColor = MethodAllCards.SameColor(previewNum, positionNum); //同色
        bool compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);
        int previewIndex = MethodAllCards.FindPosition(previewNum);
        string movePoker = (previewNum-1).ToString() + "-"+"1";

        //int positionState = 
        //string targetPoker = (positionNum-1).ToString() + "-"+state.ToString()
        //Manager.httpVar.SendCardsRequset(previewNum,movePoker)

        if (!sameColor && compareNum)
        {
            //数据层
            MethodshuffleCards.RemoveCard(previewNum);
            MethodhandCards.AddCard(index, previewNum);

            //表现层
            Manager.shuffleCards[0].SetActive(false);    //洗牌区

            if (positionCount < 13)
            {
                Manager.handCardLists[index][positionCount].SetActive(true);
                Manager.handCardListBgs[index][positionCount].spriteName = previewNum.ToString();
            }
            else
            {
                Debug.Log("移动区超长了!!!!!!!");
            }
        }
    }

    public void ClickHandCardType2()
    {
        positionNum = Manager.player0.HandCardsList[index].CardList[positionCount - 1].Number;
        chooseIndex = Manager.choosedCards.Index;
        chooseCount = Manager.choosedCards.CardList.Count;
        previewNum = Manager.choosedCards.CardList[chooseCount - 1].Number;
        bool sameColor = MethodAllCards.SameColor(previewNum, positionNum);
        bool compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);

        if (!sameColor && compareNum)
        {
            //数据层
            MethodcompleteCards.RemoveCard(chooseIndex, previewNum);
            MethodhandCards.AddCard(index, previewNum);

            //存牌区表现层                
            int chooseLength = Manager.player0.CompleteCardList[chooseIndex].CardList.Count;
            if (chooseLength > 1)
            {
                Manager.completeCardBgs[chooseIndex].spriteName = Manager.player0.CompleteCardList[chooseIndex].CardList[chooseLength - 1].Number.ToString();
            }
            else
            {
                Manager.completeCards[chooseIndex].SetActive(false);
            }

            if (positionCount < 13)
            {
                Manager.handCardLists[index][positionCount].SetActive(true);
                Manager.handCardListBgs[index][positionCount].spriteName = previewNum.ToString();
            }
            else
            {
                Debug.Log("移动区超长了!!!!");
            }
        }
    }

    public void ClickHandCardType3()
    {
        chooseCount = Manager.choosedCards.CardList.Count;
        chooseIndex = Manager.choosedCards.Index;
        positionNum = Manager.player0.HandCardsList[index].CardList[positionCount - 1].Number;
        previewNum = Manager.choosedCards.CardList[chooseCount - 1].Number;
        bool sameColor = MethodAllCards.SameColor(previewNum, positionNum);
        bool compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);
        int choosePosition = MethodhandCards.FindPosition(chooseIndex, previewNum);

        if (!sameColor && compareNum)
        {
            for (int i = chooseCount-1; i >=0; i--)
            {
                int length = chooseCount + positionCount;
                if (length <= 13)
                {
                    int nextIndex = Manager.player0.HandCardsList[index].CardList.Count;
                    previewNum = Manager.choosedCards.CardList[i].Number;
                    Card card = MethodAllCards.CreateCardInfo(previewNum, type, index);

                    //被移动区表现层
                    Manager.handCardLists[index][nextIndex].SetActive(true);
                    Manager.handCardListBgs[index][nextIndex].spriteName = previewNum.ToString();

                    //主动移动区表现层
                    Manager.handCardLists[chooseIndex][choosePosition+i].SetActive(false);

                    //数据层
                    MethodhandCards.RemoveCard(chooseIndex, previewNum);
                    MethodhandCards.AddCard(index, previewNum);
                }
                else
                {
                    Debug.Log("移动区超长！！！！！");
                    break;
                }
            }
        }
    }

    public int CheckState1(int num)
    {
        int index = MethodAllCards.FindPosition(num);
        //反面转正只会1次,所以转过后会变成特殊的2,暂时处理
        Manager.allCardList[index].State = (Manager.allCardList[index].State == 0) ? 1 : 2;
        int state = Manager.allCardList[index].State;
        return state;
    }

    public bool CheckState2(int num1,int num2)
    {
        int index1 = MethodAllCards.FindPosition(num1);
        int index2 = MethodAllCards.FindPosition(num2);
        //反面转正只会1次,所以转过后会变成特殊的2,暂时处理
        int state1 = Manager.allCardList[index1].State;
        int state2 = Manager.allCardList[index2].State;
        if (state1 == 0 || state2 == 0)
            return false;
        else
            return true;
    }
}
