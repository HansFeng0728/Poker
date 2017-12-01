using UnityEngine;
using System.Collections;

public class Back : MonoBehaviour {

    private UIPanel panel;
    void Start()
    {
        panel = this.transform.parent.GetComponent<UIPanel>();
    }
	public void backView()
    {
        panel.alpha = 0;
    }
}
