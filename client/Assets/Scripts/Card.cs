using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Card
{
    private int number;
    private int type;          //{1,2,3,4} 花色
    private int num;       //{1,2}     颜色

    public int Number
    {
        get { return number; }
        set { number = value; }
    }
    public int Type
    {
        get { return type; }
        set { type = value; }
    }
    public int Num
    {
        get { return num; }
        set { num = value; }
    }

    public Card(string StrTypelist)
    {
        string[] strList = StrTypelist.Split(',');
        List<int> typeList = new List<int>();
        int.TryParse(strList[0],out type);
        int.TryParse(strList[0], out num);
        typeList.Add(type);
        typeList.Add(num);
        number= TypeToNum(typeList);
    }

    //public Card(string StrNumber)
    //{
    //    int.TryParse(StrNumber, out number);
    //    List<int> typeList = NumToType(number);
    //    type = typeList[0];
    //    num = typeList[1];
    //}

    //根据花色和值获取num
    public int TypeToNum(List<int> typeList)
    {
        int num = typeList[0] + (typeList[1] - 1) * 4;
        return num;
    }

    //根据num获取花色和值
    public List<int> NumToType(int num)
    {
        List<int> typeList = new List<int>();
        //获取花色
        if (num % 4 == 0)
            typeList.Add(4);
        else
            typeList.Add(num % 4);
        //获取值
        if (num % 13 == 0)
            typeList.Add(13);
        else
            typeList.Add(num % 13);
        return typeList;
    }  
}
