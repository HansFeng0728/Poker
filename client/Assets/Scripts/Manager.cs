using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Manager : MonoBehaviour
{
    public static Manager instance;    
    void Awake()
    {
        instance = this;
    }

    //public static SocketClient socketClient = null;

    public static http httpVar = null;
    public static PlayerInfo player0 = null;

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

    //根据花色和值获取num
    public int TypeToNum(List<int> typeList)
    {
        int num = typeList[0] + (typeList[1] - 1) * 4;
        return num;
    }
}
