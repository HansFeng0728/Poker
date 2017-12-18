using UnityEngine;
using System.Collections;
using System.Text;
using LitJson;
using Newtonsoft.Json;
using System;

public class http : MonoBehaviour {

    void Awake()
    {
        Manager.httpVar = this;
    }

    public class MessageJson
    {
        public int intValue;
    }    

    public void ConnectRequest(string name)
    {
        User user = new User();
        user.UserId = name;
        user.UserState = 0;
        string userJson = JsonMapper.ToJson(user);
        string url = "http://192.168.90.126:8080/Cards/index/loginTest?requestStr=" + userJson;
        StartCoroutine(GetPlayerInfo(url));

    }

    IEnumerator GetPlayerInfo(string _url)
    {
        WWW getPlayerInfo = new WWW(_url);
        yield return getPlayerInfo;
        if (getPlayerInfo.error != null)
        {
            Debug.Log("http:ConnectRequest error: " + getPlayerInfo.error);
            Manager.InitWindow();
            Manager.windowLabel = "连接服务器失败";
        }
        else
        {
            Debug.Log("http:ConnectRequest success: " + getPlayerInfo.text);
            User user = JsonMapper.ToObject<User>(getPlayerInfo.text);
            Manager.player0.Name = user.UserId;
            Manager.player0.State = user.UserState;
            Manager.player0.Score = user.Score;
            Manager.player0.DaojishiTime = user.DaojishiTime;
            yield return StartCoroutine(GetAllCardsRequest(Manager.player0.Name));
        }
    }

    IEnumerator GetAllCardsRequest(string name)
    {
        Pokers pokers = new Pokers();
        pokers.UserId = name;
        string pokersJson = JsonMapper.ToJson(pokers);
        string url = "http://192.168.90.126:8080/Cards/index/initCards?requestStr=" + pokersJson;

        WWW getAllCards = new WWW(url);
        yield return getAllCards;
        if (getAllCards.error != null)
        {
            Debug.Log("http:GetAllCardsRequest error: " + getAllCards.error);
        }
        else
        {
            Debug.Log("http:GetAllCardsRequest success: " + getAllCards.text);
            JsonData AllCards = JsonMapper.ToObject(getAllCards.text);
            bool checkName = Manager.player0.Name == AllCards["userId"].ToString() ? true : false;
            if (checkName)
            {
                MethodAllCards.HttpInitPlayerInfo(AllCards);
                GameObject signIn = GameObject.Find("NetSignIn(Clone)");
                Destroy(signIn);
                Manager.InitLobby();
            }                
            else
                Debug.Log("CheckName is error!!!!!!!!!!!!!!");
        }

    }

    public bool SendCardsRequset(string movePoker, string targetPoker, int movepoker_Position, int targetPoker_Position, Callback callback, int pokerHome = -1)
    {
        SendPokers sendPokers = new SendPokers();
        sendPokers.UserId = Manager.player0.Name;
        sendPokers.MovePoker = movePoker;
        sendPokers.MovePoker_Position = movepoker_Position;
        sendPokers.TargetPoker = targetPoker;
        sendPokers.TargetPoker_Position = targetPoker_Position;
        //sendPokers.PokerHome = pokerHome;
        Manager.moveCardsHttp = false;
        string sendPokersJson = JsonMapper.ToJson(sendPokers);
        Debug.Log(sendPokersJson);
        string url = "http://192.168.90.126:8080/Cards/index/moveCards?requestStr=" + sendPokersJson;
        StartCoroutine(SendCards(url, callback));
        if (Manager.moveCardsHttp)
            return true;
        else
            return false;
    }

    IEnumerator SendCards(string _url, Callback callback)
    {
        WWW sendCardsResponse = new WWW(_url);
        yield return sendCardsResponse;
        if (sendCardsResponse.error != null)
        {
            Debug.Log("http:ConnectRequest error: " + sendCardsResponse.error);
        }
        else
        {
            Debug.Log("http:SendCardsRequset success: " + sendCardsResponse.text); 
            JsonData sendPokerJson = JsonMapper.ToObject(sendCardsResponse.text);
            Debug.Log(sendPokerJson["CanSendPokers"]);
            int canSendPokers = int.Parse(sendPokerJson["CanSendPokers"].ToString());
            if (canSendPokers == 1)
                Manager.moveCardsHttp = true;

            callback();
        }
    }

    public void QuitGameRequest(string name, Callback callback)
    {
        User user = new User();
        user.UserId = name;
        user.UserState = 0;
        string userJson = JsonMapper.ToJson(user);
        string url = "http://192.168.90.126:8080/Cards/index/GameClose?requestStr=" + userJson;
        StartCoroutine(QuitGame(url, callback));

    }

    IEnumerator QuitGame(string _url, Callback callback)
    {
        WWW quitgame = new WWW(_url);
        yield return quitgame;
        if (quitgame.error != null)
        {
            Debug.Log("http:QuitGame error: " + quitgame.error);
            Manager.InitWindow();
            Manager.windowLabel = "服务器异常";
        }
        else
        {
            Debug.Log("http:QuitGame success: " + quitgame.text);
            Manager.quit = true;

            callback();
        }
    }

    public string ReplaceStr(string str)
    {
        str = str.Substring(1, str.Length - 2);
        return str;
    }

}
