using UnityEngine;
using System.Collections;

public class ClickCard : MonoBehaviour {

    bool clickAgain;
    void OnClick()
    {
        if (!clickAgain)
        {
            gameObject.transform.parent.transform.localPosition += new Vector3(0, 20, 0);
            clickAgain = !clickAgain;
        }
        else
        {
            gameObject.transform.parent.transform.localPosition -= new Vector3(0, 20, 0);
            clickAgain = !clickAgain;
        }
        
    }
}
