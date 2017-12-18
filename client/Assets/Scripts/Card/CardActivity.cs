using UnityEngine;
using System.Collections;

public delegate void Callback();
public delegate void Callback<T>(T arg1);
public delegate void Callback<T, U>(T arg1, U arg2);
public delegate void Callback<T, U, V>(T arg1, U arg2, V arg3);

public class CardActivity : MonoBehaviour {

    public int type;
    public int index;
    public int subIndex;

    private int previewCount;
    private int positionCount;
    private int num;
    private int chooseIndex;
    private int chooseCount;
    private int mIndex;
    private int positionNum;
    private int previewNum;
    private int previewIndex;

    private bool sameColor;
    private bool sameColorType;
    private bool compareNum;
    private string movePoker;
    private int positionIndex;
    private int positionState;
    private string targetPoker;
    private int choosePosition;
    private int mPreviewNum;
    private int minusLength;

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
            MethodshuffleCards.ChangeColor();
        }
        else
        {            
            previewNum = Manager.choosedCards.CardList[0].Number;
            MethodshuffleCards.DoubleClick(previewNum);                        
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
                //ClickCompleteCardType2();
                Debug.Log("存牌区之间不传牌");
            }

            //从移动区获得牌
            if (Manager.choosedCards.Type == 3)
            {
                ClickCompleteCardType3();
                Debug.Log(Manager.player0.ShufflePokerList.CardList.Count);
            }            
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
        MethodcompleteCards.ChangeColor(index);

        Manager.choosed = true;
    }

    public void ClickCompleteCardType1()
    {
        chooseIndex = Manager.choosedCards.Index;
        positionCount = Manager.player0.CompleteCardList[index].CardList.Count;
        //previewCount = Manager.player0.CompleteCardList[chooseIndex].CardList.Count;
        previewNum = Manager.choosedCards.CardList[0].Number;

        positionNum = Manager.player0.CompleteCardList[index].CardList[positionCount - 1].Number;
        sameColorType = MethodAllCards.SameColorType(positionNum, previewNum);
        compareNum = MethodAllCards.CompareNumIsLow(positionNum, previewNum);

        movePoker = (previewNum - 1).ToString() + "-" + "1";
        positionIndex = MethodAllCards.FindPosition(positionNum);
        positionState = Manager.allCardList[positionIndex].State;
        targetPoker = (positionNum - 1).ToString() + "-" + positionState.ToString();

        //联机版
        if(Manager.httpVar!= null)
        {
            Manager.httpVar.SendCardsRequset(movePoker, targetPoker,0, 8+index, delegate()
            {
                if (!Manager.moveCardsHttp)
                {
                    Debug.Log("不能移牌");
                    Manager.ChoosedCardsReset();  
                    return;
                }

                ClickCompleteCardType1Define();
            });
        }
        
    }

    public void ClickCompleteCardType1Define()
    {
        //如果同花色
        if (sameColorType && compareNum)
        {

            //表现层
            Manager.shuffleCards[0].SetActive(false);
            Manager.completeCardBgs[index].spriteName = previewNum.ToString();

            

            //数据层
            MethodshuffleCards.RemoveCard(previewNum);
            MethodcompleteCards.AddCard(index, previewNum);

            //额外表现层
            if (Manager.shuffleIndex >= Manager.player0.ShufflePokerList.CardList.Count - 1)
            {
                Manager.shuffleIndex = 0;
                Manager.shuffleCards[0].SetActive(false);
            }
            Manager.shuffleCards[0].SetActive(true);
            Manager.shuffleCardBg.spriteName = Manager.player0.ShufflePokerList.CardList[Manager.shuffleIndex].Number.ToString();
        }
        else
        {
            Debug.Log("洗牌堆和存牌堆选定牌的花色不同");
        }

        Manager.ChoosedCardsReset();
    }

    /// <summary>
    /// 这个方法暂时不用
    /// </summary>
    public void ClickCompleteCardType2()
    {        
        positionCount = Manager.player0.CompleteCardList[index].CardList.Count;
        chooseIndex = Manager.choosedCards.Index;
        previewCount = Manager.player0.CompleteCardList[chooseIndex].CardList.Count;
        previewNum = Manager.choosedCards.CardList[0].Number;

        positionNum = Manager.player0.CompleteCardList[index].CardList[positionCount - 1].Number;
        sameColorType = MethodAllCards.SameColorType(previewNum, positionNum);
        compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);        

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
        previewNum = Manager.choosedCards.CardList[0].Number;

        positionCount = Manager.player0.CompleteCardList[index].CardList.Count;
        positionNum = Manager.player0.CompleteCardList[index].CardList[positionCount - 1].Number;
        sameColorType = MethodAllCards.SameColorType(previewNum, positionNum);
        compareNum = MethodAllCards.CompareNumIsLow(positionNum, previewNum);
        choosePosition = MethodhandCards.FindPosition(chooseIndex, previewNum);
        movePoker = "";

        movePoker = "";
        for (int i = chooseCount - 1; i >= 0; i--)
        {
            int num1 = Manager.choosedCards.CardList[i].Number;
            if (movePoker == "")
                movePoker = (num1 - 1).ToString() + "-" + "1";
            else
                movePoker = movePoker + "," + (num1 - 1).ToString() + "-" + "1";
        }
        positionIndex = MethodAllCards.FindPosition(positionNum);
        positionState = Manager.allCardList[positionIndex].State;
        targetPoker = (positionNum - 1).ToString() + "-" + positionState.ToString();

        previewNum = Manager.choosedCards.CardList[0].Number;

        //联机版
        if (Manager.httpVar != null)
        {
            Manager.httpVar.SendCardsRequset(movePoker, targetPoker,chooseIndex+1, 8 + index, delegate()
            {
                if (!Manager.moveCardsHttp)
                {
                    Debug.Log("不能移牌");
                    Manager.ChoosedCardsReset();  
                    return;
                }

                ClickCompleteCardType3Define();                
            });
        }        
    }

    public void ClickCompleteCardType3Define()
    {
        //如果同花色
        if (sameColorType && compareNum)
        {
            //表现层
            Manager.completeCardBgs[index].spriteName = previewNum.ToString();

            int length = Manager.player0.HandCardsList[chooseIndex].CardList.Count;
            Manager.handCardLists[chooseIndex][length - 1].SetActive(false);

            if (choosePosition >= 1)
            {
                int pPreviewNum = Manager.player0.HandCardsList[chooseIndex].CardList[choosePosition - 1].Number;
                MethodAllCards.ChangeState0(pPreviewNum);
                Manager.handCardListBgs[chooseIndex][choosePosition - 1].spriteName = pPreviewNum.ToString();
            }

            //数据层
            MethodhandCards.RemoveCard(chooseIndex, previewNum);
            MethodcompleteCards.AddCard(index, previewNum);
        }
        else
        {
            Debug.Log("移动区和存牌堆选定牌花色不同");
        }

        Manager.ChoosedCardsReset();
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
            if (Manager.choosedCards.Type == 3)
            {
                ClickHandCardType3();
            }            
            Debug.Log(Manager.player0.AllHandCards.Count);
        }
    }

    public void ClickHandCardNoChoose()
    {
        Manager.choosedCards.Type = type;
        Manager.choosedCards.Index = index;
        positionNum = Manager.player0.HandCardsList[index].CardList[positionCount - 1].Number;
        Card card = MethodAllCards.CreateCardInfo(positionNum, type, index);
        MethodhandCards.ChangeColor(index, positionCount - 1, "choose");
        Manager.choosedCards.CardList.Add(card);
        //Debug.Log("!!!!!" + positionNum);
        Debug.Log("subIndex : " + subIndex);

        for (int i = positionCount - 1; i >= 1 && positionCount > 1; i--)
        {
            positionNum = Manager.player0.HandCardsList[index].CardList[i].Number;
            previewNum = Manager.player0.HandCardsList[index].CardList[i - 1].Number;

            if (!MethodAllCards.CheckState2(positionNum, previewNum))
            {
                Manager.choosed = true;
                return;
            }

            bool sameColor = MethodAllCards.SameColor(previewNum, positionNum);
            if (!sameColor)
            {
                card = MethodAllCards.CreateCardInfo(previewNum, type, index);
                MethodhandCards.ChangeColor(index, i-1, "choose");
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
        sameColor = MethodAllCards.SameColor(previewNum, positionNum); //同色
        compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);
        previewIndex = MethodAllCards.FindPosition(previewNum);

        movePoker = (previewNum-1).ToString() + "-"+"1";
        positionIndex = MethodAllCards.FindPosition(positionNum);
        positionState = Manager.allCardList[positionIndex].State;
        targetPoker = (positionNum-1).ToString() + "-"+positionState.ToString();

        //联机版
        if (Manager.httpVar != null)
        {
            Manager.httpVar.SendCardsRequset(movePoker, targetPoker, 0, index + 1, delegate()
            {
                if (!Manager.moveCardsHttp)
                {
                    Debug.Log("不能移牌");
                    Manager.ChoosedCardsReset();  
                    return;
                }

                ClickHandCardType1Define();
            });
        }        
    }

    public void ClickHandCardType1Define()
    {
        if (!sameColor && compareNum)
        {
            //数据层
            MethodshuffleCards.RemoveCard(previewNum);
            MethodhandCards.AddCard(index, previewNum);

            //表现层
            Manager.shuffleCards[0].SetActive(false);    //洗牌区

            //额外表现层
            if (Manager.shuffleIndex >= Manager.player0.ShufflePokerList.CardList.Count - 1)
            {
                Manager.shuffleIndex = 0;
                Manager.shuffleCards[0].SetActive(false);
            }
            Manager.shuffleCards[0].SetActive(true);
            Manager.shuffleCardBg.spriteName = Manager.player0.ShufflePokerList.CardList[Manager.shuffleIndex].Number.ToString();


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
        Manager.ChoosedCardsReset();
    }

    /// <summary>
    /// 存牌区不向移牌区移牌,暂时不用
    /// </summary>
    public void ClickHandCardType2()
    {
        positionNum = Manager.player0.HandCardsList[index].CardList[positionCount - 1].Number;
        chooseIndex = Manager.choosedCards.Index;
        chooseCount = Manager.choosedCards.CardList.Count;
        previewNum = Manager.choosedCards.CardList[chooseCount - 1].Number;
        sameColor = MethodAllCards.SameColor(previewNum, positionNum);
        compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);

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
        sameColor = MethodAllCards.SameColor(previewNum, positionNum); ;
        compareNum = MethodAllCards.CompareNumIsLow(previewNum, positionNum);
        choosePosition = MethodhandCards.FindPosition(chooseIndex, previewNum);

        int length = chooseCount + positionCount;
        int previewNum1 = Manager.choosedCards.CardList[0].Number;
        if (positionNum == previewNum1)
        {
            MethodhandCards.DoubleClick(previewNum1, index);
            return;
        }
        if (length >= 20)
        {
            Debug.Log("移动区超长！！！！！");
            return;
        }

        minusLength = 1;
        for (int i = chooseCount - 1; i >= 0; i--)
        {
            mPreviewNum = Manager.choosedCards.CardList[i].Number;
            sameColor = MethodAllCards.SameColor(mPreviewNum, positionNum);
            compareNum = MethodAllCards.CompareNumIsLow(mPreviewNum, positionNum);
            if (!sameColor && compareNum)
            {                
                break;
            }
            minusLength++;
        }

        movePoker = "";
        for (int i = chooseCount - 1; i >= 0; i--)
        {
            int num1 = Manager.choosedCards.CardList[i].Number;
            if (movePoker == "")
                movePoker = (num1 - 1).ToString() + "-" + "1";
            else
                movePoker = movePoker + "," + (num1 - 1).ToString() + "-" + "1";
        }

        positionIndex = MethodAllCards.FindPosition(positionNum);
        positionState = Manager.allCardList[positionIndex].State;
        targetPoker = (positionNum - 1).ToString() + "-" + positionState.ToString();

        //联机版
        if (Manager.httpVar != null)
        {
            Manager.httpVar.SendCardsRequset(movePoker, targetPoker, chooseIndex+1, index + 1, delegate()
            {
                if (!Manager.moveCardsHttp)
                {
                    Debug.Log("移牌请求未通过");
                    Manager.ChoosedCardsReset();  
                    return;
                }

                ClickHandCardType3Define();
            });
        }
                                
    }
    
    public void ClickHandCardType3Define()
    {
        for (int i = chooseCount - minusLength; i >= 0; i--)
        {
            int nextIndex = Manager.player0.HandCardsList[index].CardList.Count;
            previewNum = Manager.choosedCards.CardList[i].Number;
            Card card = MethodAllCards.CreateCardInfo(previewNum, type, index);

            //被移动区表现层
            Manager.handCardLists[index][nextIndex].SetActive(true);
            Manager.handCardListBgs[index][nextIndex].spriteName = previewNum.ToString();

            //主动移动区表现层
            if (minusLength > 1)
            {
                previewCount = Manager.player0.HandCardsList[chooseIndex].CardList.Count;
                Manager.handCardLists[chooseIndex][previewCount - 1].SetActive(false);
            }
            else
                Manager.handCardLists[chooseIndex][choosePosition + i].SetActive(false);

            //数据层
            MethodhandCards.RemoveCard(chooseIndex, previewNum);
            MethodhandCards.AddCard(index, previewNum);
        }

        if (minusLength == 1 && choosePosition >= 1)
        {
            int pPreviewNum = Manager.player0.HandCardsList[chooseIndex].CardList[choosePosition - 1].Number;
            MethodAllCards.ChangeState0(pPreviewNum);
            Manager.handCardListBgs[chooseIndex][choosePosition - 1].spriteName = pPreviewNum.ToString();
        }
        Manager.ChoosedCardsReset();  
    }
}
