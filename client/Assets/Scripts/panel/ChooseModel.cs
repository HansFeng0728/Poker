using UnityEngine;
using System.Collections;

public class ChooseModel : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    public void InitSolo()
    {
        GameObject soloPrefab = Resources.Load("Prefabs/Solo") as GameObject;
        GameObject soloPanel = MonoBehaviour.Instantiate(soloPrefab) as GameObject;
        soloPanel.transform.parent = GameObject.Find("Camera").transform;
        soloPanel.transform.localScale = Vector3.one;
        soloPanel.transform.localScale = new Vector3(1.4f, 1.24f, 1);
    }

    public void InitNetSignIn()
    {
        GameObject netSignInPrefab = Resources.Load("Prefabs/NetSignIn") as GameObject;
        GameObject netSignInPanel = MonoBehaviour.Instantiate(netSignInPrefab) as GameObject;
        netSignInPanel.transform.parent = GameObject.Find("Camera").transform;
        netSignInPanel.transform.localScale = Vector3.one;
        netSignInPanel.transform.localScale = new Vector3(1.4f, 1.24f, 1);
    }
}
