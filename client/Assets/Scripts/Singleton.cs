using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Singleton<T> where T : new()
{
    private static T instance = default(T);
    public static T GetInstance()
    {
        if (instance == null)
        {
            instance = new T();
        }
        return instance;
    }
}