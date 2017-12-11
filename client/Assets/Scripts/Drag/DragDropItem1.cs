using UnityEngine;
using System.Collections;

public class DragDropItem1 : NUIDragDropItem
{
    /// <summary>
    /// Prefab object that will be instantiated on the DragDropSurface if it receives the OnDrop event.
    /// </summary>

    public GameObject Card;    

    /// <summary>
    /// Drop a 3D game object onto the surface.
    /// </summary>

    protected override void OnDragDropRelease(GameObject surface)
    {
        //if (surface.name != "Card(Clone)")
        //{
            NDragDropSurface dds = surface.GetComponent<NDragDropSurface>();            

            if (dds != null)
            {
                GameObject ChooseCardList = dds.gameObject;
                GameObject child = NGUITools.AddChild(ChooseCardList, Card);
                child.transform.localScale = dds.transform.localScale;

                Transform trans = child.transform;
                //trans.localPosition = ChooseCardList.transform.localPosition;
                trans.localPosition = Vector3.zero;

                // Destroy this icon as it's no longer needed
                NGUITools.Destroy(gameObject);
                dds.boxC.enabled = false;
                return;
            }
        //}
        base.OnDragDropRelease(surface);
    }     
}
