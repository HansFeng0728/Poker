using UnityEngine;
using System.Collections;

public class End : MonoBehaviour {

    //public BoxCollider huaji;
    public BoxCollider end;
    public UITexture mTexture;
    public UILabel label;
    public GameObject back;

    private int rate;

    void Start()
    {
        back.SetActive(false);
        mTexture.mainTexture = Resources.Load("Art/End/endPic") as Texture;
        rate = 0;
        //huaji.enabled = false;
        end.enabled = false;
        label.text = "";
    }

    public void OpenCollider()
    {
        //huaji.enabled = true;
        end.enabled = true;
    }
    public void Click1()
    {
        if (rate == 0)
            label.text = "朕的大秦都亡了,你获胜有什么用";
        else if (rate == 1)
            label.text = "还想退出？";
        else if (rate == 2)
        {
            back.SetActive(true);
            mTexture.mainTexture = Resources.Load("Art/End/gun") as Texture;
            label.text = "";
        }

        rate++;
    }    

    public void Back()
    {
        GameObject lobby = GameObject.Find("Lobby(Clone)");
        Destroy(lobby);
        Destroy(this.gameObject);
        if (!Manager.openSolo)
        {
            Manager.httpVar.QuitGameRequest(Manager.player0.Name, delegate()
            {
                Manager.InitNetSignIn();
            });
        }
        else
        {
            Manager.InitSolo();
            Manager.openSolo = false;
        }

        Manager.ResetCards();
    }
}
