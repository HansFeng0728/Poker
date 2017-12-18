using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class HandCardList : MonoBehaviour
{

    public int type;
    public int index;
    public GameObject cardList;

    private int num;
    private Card card;
    private int nextIndex;

    private List<int> typeList = null;
    private bool CheckK;

    private int chooseLength;
    private int chooseIndex;
    private int CurrentLength;
    private int previewNum;

    private bool sameColor;
    private bool sameColorType;
    private bool compareNum;
    private string movePoker;
    private int positionIndex;
    private int positionState;
    private string targetPoker;
    private int choosePosition;
    private int mPreviewNum;

    void Start()
    {
    }

    void RefreshCards()
    {
        for (int i = 0; i < Manager.handCardLists[index].Count; i++)
        {
            Manager.handCardLists[index][i].SetActive(false);
        }
    }


    public void ClickHandCards()
    {
        //接牌
        if (Manager.choosed)
        {
            if (Manager.choosedCards.Type == 1)
            {
                nextIndex = Manager.player0.HandCardsList[index].CardList.Count;
                num = Manager.choosedCards.CardList[0].Number;
                card = MethodAllCards.CreateCardInfo(num, type, index);

                //确保移动区第一张为K
                typeList = MethodAllCards.NumToType(num);
                CheckK = typeList[1] == 13 ? true : false;
                if (!CheckK)
                {
                    return;
                    Manager.ChoosedCardsReset();
                }

                movePoker = (num - 1).ToString() + "-" + "1";

                //联机版
                if (Manager.httpVar != null)
                {
                    Manager.httpVar.SendCardsRequset(movePoker, "", 0, 1 + index, delegate()
                    {
                        if (!Manager.moveCardsHttp)
                        {
                            Debug.Log("不能移牌");
                            Manager.ChoosedCardsReset();  
                            return;
                        }
                        //移动区表现层
                        Manager.handCardLists[index][nextIndex].SetActive(true);
                        Manager.handCardListBgs[index][nextIndex].spriteName = num.ToString();

                        //从洗牌堆传来的牌
                        if (Manager.choosedCards.Type == 1)
                        {
                            //数据层
                            MethodshuffleCards.RemoveCard(num);
                            MethodhandCards.AddCard(index, num);

                            //洗牌区表现层
                            Manager.shuffleCards[0].SetActive(false);

                            Debug.Log(Manager.player0.AllHandCards.Count);
                       }
                        Manager.ChoosedCardsReset();
                    });
                }
            }

            //存牌区不能向移动区传牌

            //if (Manager.choosedCards.Type == 2)
            //{
            //    nextIndex = Manager.player0.HandCardsList[index].CardList.Count;
            //    num = Manager.choosedCards.CardList[0].Number;
            //    card = MethodAllCards.CreateCardInfo(num, type, index);

            //    //确保移动区第一张为K
            //    typeList = MethodAllCards.NumToType(num);
            //    CheckK = typeList[1] == 13 ? true : false;
            //    if (!CheckK)
            //    {
            //        return;
            //        Manager.ChoosedCardsReset();
            //    }

            //    movePoker = (num - 1).ToString() + "-" + "1";                

            //    //联机版
            //    if(Manager.httpVar!= null)
            //    {
            //        Manager.httpVar.SendCardsRequset(movePoker, "",chooseIndex+1, 8+index, delegate()
            //        {
            //            if(!Manager.moveCardsHttp)
            //            {
            //                Debug.Log("不能移牌");
            //                Manager.ChoosedCardsReset();  
            //                return;
            //            }

            //            //移动区表现层
            //            Manager.handCardLists[index][nextIndex].SetActive(true);
            //            Manager.handCardListBgs[index][nextIndex].spriteName = num.ToString();

            //            int compIndex = Manager.choosedCards.CardList[0].Index;

            //            //数据层
            //            MethodcompleteCards.RemoveCard(compIndex, num);
            //            MethodhandCards.AddCard(index, num);

            //            //存牌区表现层
            //            int compCount = Manager.player0.CompleteCardList[compIndex].CardList.Count;
            //            if (compCount >= 1)
            //            {
            //                Manager.completeCardBgs[compIndex].spriteName = Manager.player0.CompleteCardList[compIndex].CardList[compCount - 1].Number.ToString();
            //            }
            //            else
            //            {
            //                Manager.completeCards[compIndex].SetActive(false);
            //            }
            //            Debug.Log(Manager.player0.AllHandCards.Count);
            //        });               
            //    }
            //}            

            //移动区相互传牌
            if (Manager.choosedCards.Type == 3)
            {
                chooseLength = Manager.choosedCards.CardList.Count;
                chooseIndex = Manager.choosedCards.Index;
                CurrentLength = Manager.player0.HandCardsList[index].CardList.Count;
                previewNum = Manager.choosedCards.CardList[chooseLength - 1].Number;
                choosePosition = MethodhandCards.FindPosition(chooseIndex, previewNum);

                //是否移动区第一张为K
                typeList = MethodAllCards.NumToType(Manager.choosedCards.CardList[chooseLength - 1].Number);
                CheckK = typeList[1] == 13 ? true : false;
                if (!CheckK)
                {
                    return;
                    Manager.ChoosedCardsReset();
                }

                movePoker = "";
                for (int i = chooseLength - 1; i >= 0; i--)
                {
                    int num1 = Manager.choosedCards.CardList[i].Number;
                    if (movePoker == "")
                        movePoker = (num1 - 1).ToString() + "-" + "1";
                    else
                        movePoker = movePoker + "," + (num1 - 1).ToString() + "-" + "1";
                }

                //联机版
                if (Manager.httpVar != null)
                {
                    Manager.httpVar.SendCardsRequset(movePoker, "", chooseIndex + 1, index+1, delegate()
                    {
                        if (!Manager.moveCardsHttp)
                        {
                            Debug.Log("不能移牌");
                            Manager.ChoosedCardsReset();  
                            return;
                        }

                        for (int i = chooseLength - 1; i >= 0; i--)
                        {
                            nextIndex = Manager.player0.HandCardsList[index].CardList.Count;
                            previewNum = Manager.choosedCards.CardList[i].Number;
                            card = MethodAllCards.CreateCardInfo(previewNum, type, index);

                            //被移动区表现层
                            Manager.handCardLists[index][nextIndex].SetActive(true);
                            Manager.handCardListBgs[index][nextIndex].spriteName = previewNum.ToString();

                            //主动移动区表现层
                            Manager.handCardLists[chooseIndex][choosePosition + i].SetActive(false);

                            //数据层
                            MethodhandCards.RemoveCard(chooseIndex, previewNum);
                            MethodhandCards.AddCard(index, previewNum);
                        }
                        if (choosePosition >= 1)
                        {
                            int pPreviewNum = Manager.player0.HandCardsList[chooseIndex].CardList[choosePosition - 1].Number;
                            MethodAllCards.ChangeState0(pPreviewNum);
                            Manager.handCardListBgs[chooseIndex][choosePosition - 1].spriteName = pPreviewNum.ToString();
                        }
                        Manager.ChoosedCardsReset();
                    });
                }
            }            
        }
    }

    public void ClickHandCardsType1()
    {

    }

    public void ClickHandCardsType2()
    {

    }

    public void ClickHandCardsType3()
    {

    }
}
