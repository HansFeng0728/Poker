using UnityEngine;
using System.Collections;
using System.Text;
using LitJson;

public class http : MonoBehaviour {

    void Awake()
    {
        Manager.httpVar = this;
    }

    public class MessageJson
    {
        public int intValue;
    }

    IEnumerator Get<T>(string _url)
    {
        string data = ""    ;
        WWW getData = new WWW(_url, Encoding.UTF8.GetBytes(data));
        yield return getData;
        if (getData.error != null)
        {
            Debug.Log("http:getData error: " + getData.error);
        }
        else
        {
            T message = JsonMapper.ToObject<T>(getData.text);
            Debug.Log("http:getData success: " + getData.text);
        }
    }

    IEnumerator Post(string _url,string data)
    {
        WWW postData = new WWW(_url,Encoding.UTF8.GetBytes(data));
        yield return postData;
        if (postData.error != null)
        {
            Debug.Log("postData error: " + postData.error);
        }
        else
        {
            Debug.Log("postData success: " + postData.text);
        }
    }

    //private void SendGet(string _url,string name)
    //{
    //    StartCoroutine(Get(_url));
    //}
    //public void Connect(string name)
    //{
    //    //测试GET方法
    //    SendGet("http://192.168.90.126:8080/ServletTest/Home/FirstServlet?uname=" + name);

    //    //测试POST方法
    //    //WWWForm form = new WWWForm();
    //    //form.AddField("int", "6");
    //    //StartCoroutine(SendPost("http://192.168.90.126:8080/ServletTest/Home/FirstServlet", form));
    //}



}
