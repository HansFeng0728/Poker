using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;
using LitJson;

public class MethodAllCards
{
    //根据num获取花色和值
    public static List<int> NumToType(int num)
    {
        List<int> typeList = new List<int>();
        //获取花色
        if (num % 4 == 0)
            typeList.Add(4);
        else
            typeList.Add(num % 4);
        //获取值
        float number = Mathf.Ceil((float)num/4);
        typeList.Add((int)number);
        return typeList;
    }

    //根据num判断是否为红或者黑
    public static bool SameColor(int previewNum,int currentNum)
    {
        List<int> previewTypeList = NumToType(previewNum);
        List<int> currentTypeList = NumToType(currentNum);
        if (previewTypeList[0] % 2 == currentTypeList[0] % 2) //同色
        {
            return true;
        }
        else    
        {
            return false;
        }
    }

    //根据num判断是否能存牌（存牌区用）
    public static bool SameColorType(int previewNum, int currentNum)
    {
        List<int> previewTypeList = NumToType(previewNum);
        List<int> currentTypeList = NumToType(currentNum);
        if (previewTypeList[0]  == currentTypeList[0] ) //同花色
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //根据比大小,是否preview比current小
    public static bool CompareNumIsLow(int previewNum, int currentNum)
    {
        List<int> previewTypeList = NumToType(previewNum);
        List<int> currentTypeList = NumToType(currentNum);
        if (previewTypeList[1] +1 == currentTypeList[1]) //同色
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //根据花色和值获取num
    public static int TypeToNum(List<int> typeList)
    {
        int num = typeList[0] + (typeList[1] - 1) * 4;
        return num;
    }

    public static Card CreateCardInfo(int currentNumber = 0, int type = -1, int index = -1)
    {
        List<int> typeList = new List<int>();
        typeList = NumToType(currentNumber);
        Card card = new Card(currentNumber, typeList[0], typeList[1],type,index);
        return card;
    }

    //public static Card CreateCardInfo(int currentNumber,int type,int index = -1)
    //{
    //    List<int> typeList = new List<int>();
    //    typeList = NumToType(currentNumber);
    //    Card card = new Card(currentNumber, typeList[0], typeList[1],type,index);
    //    return card;
    //}

    //判断卡片是否可以被放置
    //public static
     
    //制造一个随机卡组
    public static List<int> InitCardList()
    {
        List<int> list1 = new List<int>();
        List<int> cardlist = new List<int>();
        for (int i = 1; i <= 52; i++)
        {
            list1.Add(i);
        }
        for (int i = 1; i <= 52; i++)
        {
            System.Random ro = new System.Random();
            int num = ro.Next(0, list1.Count);
            cardlist.Add(list1[num]);
            list1.RemoveAt(num);
        }
        return cardlist;
    }

    //登录初始化 随机52张牌,32张给移动堆,剩下的给洗牌堆
    public static void InitSoloPlayerInfo()
    {
        List<int> cardNumberList = MethodAllCards.InitCardList();
        Manager.firstOpenCards = new List<int>();

        int count = 0;        
        for (int i = 0; i < 7; i++)
        {
            for (int j = 1; j <= i + 1; j++)
            {
                Card cardInfo = MethodAllCards.CreateCardInfo(cardNumberList[count], 2, i);
                cardInfo.State = 0;               
                
                Manager.allCardList.Add(cardInfo);
                MethodhandCards.AddCard(i, cardNumberList[count], cardInfo.State);
                count++;
                Debug.Log("!!!!!");
            }
        }

        int length = 0;
        Manager.allCardList[length].State =1;
        Manager.firstOpenCards.Add(Manager.allCardList[length].Number);
        for (int i = 2; i <= 7; i++)
        {
            length += i;
            Manager.allCardList[length].State = 1;
            Manager.firstOpenCards.Add(Manager.allCardList[length].Number);
        }

        for (int i = count; i < 52; i++)
        {
            Card cardInfo = MethodAllCards.CreateCardInfo(cardNumberList[i], 1);
            cardInfo.State = 1;
            Manager.allCardList.Add(cardInfo);
            MethodshuffleCards.AddCard(cardNumberList[i], 1);
        }               
    }
  
    public static void InitCardType(GameObject card, int type,int index = -1)
    {
        CardActivity cardActivity = card.GetComponent<CardActivity>();
        cardActivity.type = type;
        cardActivity.index = index;
    }

    public static void CheckCompleteOneList()
    {
        for (int i = 0; i < 4; i++)
        {

        }
    }

    public static void HttpInitPlayerInfo(JsonData jsondata)
    {        
        //增加移动区数据
        for (int i = 0; i < 7; i++)
        {
            string handCards = jsondata["handPokerList" + (i + 1)].ToString();
            handCards = handCards.Substring(1, handCards.Length - 2);
            string[] handCardsList = handCards.Split(',');
            for (int j = handCardsList.Length-1; j >= 0; j--)
            {
                string[] subList = handCardsList[j].Split(':');
                int num = (int.Parse(subList[0])) + 1;
                Card cardInfo = MethodAllCards.CreateCardInfo(num, 2, i);
                cardInfo.State = subList[1] == "front" ? 1 : 0;
                Manager.allCardList.Add(cardInfo);
                MethodhandCards.AddCard(i, num, 0);
            }
        }

        //增加洗牌堆数据
        string shuffleCards = jsondata["shufflePokerList"].ToString();
        shuffleCards = shuffleCards.Substring(1, shuffleCards.Length - 2);
        string[] shuffleCardsList = shuffleCards.Split(',');
        for (int i = 0; i < shuffleCardsList.Length; i++)
        {
            string[] subList = shuffleCardsList[i].Split(':');
            int num = (int.Parse(subList[0])) + 1;
            Card cardInfo = MethodAllCards.CreateCardInfo(num, 1, i);
            cardInfo.State = subList[1] == "front" ? 1 : 0;
            Manager.allCardList.Add(cardInfo);
            MethodshuffleCards.AddCard(num,1);
        }
    }

    public static int FindPosition(int number)
    {
        int position;
        int length = Manager.allCardList.Count;
        for (int i = 0; i < length; i++)
        {
            int num1 = Manager.allCardList[i].Number;
            if (num1 == number)
                return i;
        }
        return -1111111;
    }

    //如果主动移动区下面还有反着的牌,那么将其正反状态改变
    public static void ChangeState0(int num)
    {
        int index = MethodAllCards.FindPosition(num);
        Manager.allCardList[index].State = 1;
    }

    public static int CheckState1(int num)
    {
        int index = MethodAllCards.FindPosition(num);
        //反面转正只会1次,所以转过后会变成特殊的2,暂时处理
        Manager.allCardList[index].State = (Manager.allCardList[index].State == 0) ? 1 : 2;
        int state = Manager.allCardList[index].State;
        return state;
    }

    public static bool CheckState2(int num1, int num2)
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

    public static int CheckState3(int num)
    {
        int index = MethodAllCards.FindPosition(num);
        return Manager.allCardList[index].State;
    }

    public static void ResetColor()
    {
         //洗牌区颜色置零
        Manager.shuffleCardBg.color = new Color(1, 1, 1);

        //存牌区颜色置零
        int count2 = Manager.completeCards.Count;
        for (int i = 0; i < count2; i++)
        {
            Manager.completeCardBgs[i].color = new Color(1, 1, 1);
        }

        //移动区颜色置零
        int count3 = Manager.handCardListBgs.Count;
        for (int i = 0; i < count3; i++)
        {
            int count = Manager.handCardListBgs[i].Count;
            for (int j = 0; j < count; j++)
            {
                Manager.handCardListBgs[i][j].color = new Color(1, 1, 1);
            }
        }
    }
}
