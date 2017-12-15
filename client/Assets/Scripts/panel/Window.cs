using UnityEngine;
using System.Collections;

public class Window : MonoBehaviour {

    public UILabel windowLabel;

    void Update()
    {
        windowLabel.text = Manager.windowLabel;
    }
}
