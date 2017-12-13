using System;
using UnityEngine;

/// <summary>
/// 第一个自己创建的拖拽功能
/// </summary>
public class MyFirstDragDropItem : UIDragDropItem
{

    private GameObject sourceParent;
    /// <summary>
    /// 重写父类的拖拽开始函数
    /// </summary>
    protected override void OnDragDropStart()
    {
        //当拖拽开始时存储原始的父对象
        this.sourceParent = this.transform.parent.gameObject;
        base.OnDragDropStart();
    }
    /// <summary>
    /// 重写父类的拖拽释放函数
    /// </summary>
    protected override void OnDragDropRelease(GameObject surface)
    {
        //如果不是拖拽到场景表面的话
        if (!surface.name.Equals("UI Root"))
        {
            //寻找surface对象的父对象
            GameObject cell = surface.transform.parent.gameObject;

            //判断当前单元格的对象姓名
            if (cell.name.Equals("PacketCell - Left"))
            {
                GMUserManager.User.Name = "Left";
            }
            if (cell.name.Equals("PacketCell - Right"))
            {
                GMUserManager.User.Name = "Right";
            }
        }
        else
        {
            //其他的错误位置时,重置父子关系
            this.transform.parent = this.sourceParent.transform;
        }

        //最终调用父类的功能
        base.OnDragDropRelease(surface);
        //调整位置
        this.transform.localPosition = new Vector3(0, 0, 0);
    }
}