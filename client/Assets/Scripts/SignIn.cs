using UnityEngine;
using System.Collections;

public class SignIn : MonoBehaviour {

    public GameObject lobby;
    public void Sign()
    {
        Instantiate(lobby);
    }
}
