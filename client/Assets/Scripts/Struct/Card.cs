using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Card
{
    private int number;
    private int color;          //{1,2,3,4} 花色
    private int num;       //     值
    private int type;
    private int index;
    //卡牌的状态:正反面    1正0反
    //正反通过allcards里的state来判断
    private int state;

    public int Number
    {
        get { return number; }
        set { number = value; }
    }
    public int Color
    {
        get { return color; }
        set { color = value; }
    }
    public int Type
    {
        get { return type; }
        set { type = value; }
    }

    public int Index
    {
        get { return index; }
        set { index = value; }
    }

    public int Num
    {
        get { return num; }
        set { num = value; }
    }

    public int State
    {
        get { return state; }
        set { state = value; }
    }

    public Card(int nNumber, int nColor, int nNum)
    {
        number = nNumber;
        color = nColor;
        num = nNum;
    }

    public Card(int nNumber, int nColor, int nNum,int nType,int nIndex)
    {
        number = nNumber;
        color = nColor;
        num = nNum;
        type = nType;
        index = nIndex;
    }

    public Card(int type,int currentNumber = 0,string StrTypeList = "")
    {
        List<int> typeList = new List<int>();
        if (type == 1)
        {
            number = currentNumber;
            typeList = MethodAllCards.NumToType(currentNumber);
            type = typeList[0];
            num = typeList[1];
        }
        else if (type == 2)
        {
            string[] strList = StrTypeList.Split(',');
            int.TryParse(strList[0], out type);
            int.TryParse(strList[1], out num);
            int.TryParse(strList[2], out state);
            typeList.Add(type);
            typeList.Add(num);
            number = MethodAllCards.TypeToNum(typeList);
        }
        
    }

    //public Card(string StrNumber)
    //{
    //    int.TryParse(StrNumber, out number);
    //    List<int> typeList = NumToType(number);
    //    type = typeList[0];
    //    num = typeList[1];
    //}
}
