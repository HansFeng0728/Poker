using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class PlayerInfo 
{
    private int state = 0;
    private string name = "";
    private Texture icon;
    private string path = "";
    private string score = "";
    private bool isLandLord;
    private int playerPosition;          
    private IList<Card> userpokerList;
    private int daojishiTime;
    private int sendCardInfo;


    public string Name
    {
        get { return name; }
        set { name = value; }
    }

    public Texture Icon
    {
        get { return icon; }
        set { icon = value; }
    }

    public int PlayerPosition
    {
        get { return playerPosition; }
        set { playerPosition = value; }
    }

    public int State
    {
        get { return state; }
        set { state = value; }
    }

    public int DaojishiTime
    {
        get { return daojishiTime; }
        set { daojishiTime = value; }
    }

    public bool IsLandLord
    {
        get { return isLandLord; }
        set { isLandLord = value; }
    }

    public PlayerInfo(string name)
    {
        Name = name;
        int num = Random.Range(0, 4);
        path = string.Format("Art/Icon/p{0}", num);
        Icon = Resources.Load<Texture>(path);
    }
}
