using UnityEngine;
using System.Collections;

public class NDragDropSurface : MonoBehaviour {

    public int outIndex;
    private int type;
    private int state;
    private int index;
    public BoxCollider boxC;

    void Start()
    {
        type = Manager.player0.CompleteCardList[outIndex].Type;
        index = Manager.player0.CompleteCardList[outIndex].Index;
        state = Manager.player0.CompleteCardList[outIndex].State;
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

    public int State
    {
        get { return state; }
        set { state = value; }
    }

    public BoxCollider BoxC
    {
        get { return boxC; }
        set { boxC = value; }
    }

}