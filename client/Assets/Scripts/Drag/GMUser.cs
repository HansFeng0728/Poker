using System;

/// <summary>
/// 用户管理器
/// </summary>
public class GMUserManager
{
    //存储当前正在玩游戏的玩家信息
    private static GMUser user = null;
    //公开访问器
    public static GMUser User
    {
        get
        {
            if (GMUserManager.user == null)
            {
                GMUserManager.user = new GMUser();
            }

            return GMUserManager.user;
        }
    }
}

public class GMUser
{
    //游戏用户的姓名
    public string Name { set; get; }
    public GMUser()
    {
        //设置每个用户的默认姓名是Right
        this.Name = "Right";
    }
}