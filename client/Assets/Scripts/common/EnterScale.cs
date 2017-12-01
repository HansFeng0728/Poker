using UnityEngine;
using System.Collections;

public class EnterScale : MonoBehaviour
{
	void OnPress(bool isPressed)
    {
        if (isPressed)
            gameObject.transform.localScale = new Vector3(0.9f, 0.9f, 0.9f);
        else
            gameObject.transform.localScale = Vector3.one;
    }

}
