using UnityEngine;
using System.Collections;
using System.Collections.Generic;

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

    public static void ChangeColor(int index, int nextIndex,string type="noChoose")
    {
        if(type == "choose")
            Manager.handCardListBgs[index][nextIndex].color = new Color(225 / 255f, 220 / 255f, 150 / 255f);
        else
            Manager.handCardListBgs[index][nextIndex].color = new Color(1,1,1);
    }    

    public static void DoubleClick(int num,int index)
    {
        int completeCount = Manager.player0.CompleteCardList.Count;
        int listCount;
        int number;
        bool sameColorType;
        bool compareNumIsLow;
        List<int> typeList = MethodAllCards.NumToType(num);
        int choosePosition = MethodhandCards.FindPosition(index, num);

        

        if (typeList[1] == 1)
        {
            for (int i = 0; i < completeCount; i++)
            {
                listCount = Manager.player0.CompleteCardList[i].CardList.Count;
                if (listCount == 0)
                {
                    string movePoker = (typeList[1] - 1).ToString() + "-" + "1";

                    //联机版
                    if (Manager.httpVar != null)
                    {
                        Manager.httpVar.SendCardsRequset(movePoker, "", index + 1, 8 + i, delegate()
                        {
                            //表现层
                            Manager.completeCards[i].SetActive(true);
                            Manager.completeCardBgs[i].spriteName = num.ToString();

                            int length = Manager.player0.HandCardsList[index].CardList.Count;
                            Manager.handCardLists[index][length - 1].SetActive(false);

                            if (choosePosition >= 1)
                            {
                                int pPreviewNum = Manager.player0.HandCardsList[index].CardList[choosePosition - 1].Number;
                                MethodAllCards.ChangeState0(pPreviewNum);
                                Manager.handCardListBgs[index][choosePosition - 1].spriteName = pPreviewNum.ToString();
                            }

                            //数据层
                            MethodhandCards.RemoveCard(index, num);
                            MethodcompleteCards.AddCard(i, num);

                            Debug.Log("双击移动区");

                            Manager.ChoosedCardsReset();
                        });
                        return;
                    }
                }
            }            
        }

        for (int i = 0; i < completeCount; i++)
        {
            listCount = Manager.player0.CompleteCardList[i].CardList.Count;
            if (listCount == 0)
                continue;
            number = Manager.player0.CompleteCardList[i].CardList[listCount - 1].Number;
            sameColorType = MethodAllCards.SameColorType(number, num);
            compareNumIsLow = MethodAllCards.CompareNumIsLow(number, num);

            string movePoker = (typeList[1] - 1).ToString() + "-" + "1";
            int positionIndex = MethodAllCards.FindPosition(number);
            int positionState = Manager.allCardList[positionIndex].State;
            string targetPoker = (number - 1).ToString() + "-" + positionState.ToString();

            if (Manager.httpVar != null)
            {
                Manager.httpVar.SendCardsRequset(movePoker, targetPoker, index + 1, 8 + i, delegate()
                {
                    if (sameColorType && compareNumIsLow)
                    {
                        if (Manager.moveCardsHttp)
                        {
                            Debug.Log("无法双击牌到存牌区");
                            Manager.ChoosedCardsReset();  
                            return;
                        }
                        //表现层
                        Manager.completeCardBgs[i].spriteName = num.ToString();

                        int length = Manager.player0.HandCardsList[index].CardList.Count;
                        Manager.handCardLists[index][length - 1].SetActive(false);

                        if (choosePosition >= 1)
                        {
                            int pPreviewNum = Manager.player0.HandCardsList[index].CardList[choosePosition - 1].Number;
                            MethodAllCards.ChangeState0(pPreviewNum);
                            Manager.handCardListBgs[index][choosePosition - 1].spriteName = pPreviewNum.ToString();
                        }

                        //数据层
                        MethodhandCards.RemoveCard(index, num);
                        MethodcompleteCards.AddCard(i, num);

                        Debug.Log("双击移动区");
                    }
                });
            }
        }        
    }
}
