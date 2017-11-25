using UnityEngine;
using System.Collections;

public class Manager : MonoBehaviour
{
    public static Manager instance;    
    void Awake()
    {
        instance = this;
    }
    public static SocketClient socketClient = null;
    public static http httpVar = null;
    public static PlayerInfo player0 = null;
    public static PlayerInfo player1 = null;
}
