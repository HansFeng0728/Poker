using UnityEngine;
using System.Collections;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System;

public class SocketClient : MonoBehaviour
{
    private byte[] result = new byte[4096];
    private Socket socket;

    //是否已链接的标识
    public bool IsConnected = false;

    void Awake()
    {
        Manager.socketClient = this;
    }

    public SocketClient()
    {
        socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        socket.ReceiveTimeout = 500;
    }

    public void ConnectServer(string ip, int port)
    {
        IPAddress mIp = IPAddress.Parse(ip);
        IPEndPoint ipEndPoint = new IPEndPoint(mIp, port);

        IAsyncResult connectState = socket.BeginConnect(ipEndPoint, new AsyncCallback(ConnectCallback), socket);
        bool success = connectState.AsyncWaitHandle.WaitOne(5000, true);
        if (!success)
        {
            socket.Close();
            Debug.Log("connect Time Out");
        }
        else
        {
            //服务器下发数据
            IsConnected = true;
            Debug.Log(socket.Connected);
            ByteBuffer buffer = new ByteBuffer(result);
            SendMessage("请求服务器连接");
            int receiveLength = socket.Receive(result);
            string data = buffer.ReadString();
            Debug.Log("服务器返回数据: " + data);
        }        
    }

    private void ConnectCallback(IAsyncResult asyncConnect)
    {
        Debug.Log("connect success");
    }


    /// <summary>
    /// 发送数据给服务器
    /// <summary>
    public void SendMessage(string data)
    {
        if (IsConnected == false)
            return;
        try
        {
            ByteBuffer buffer = new ByteBuffer();
            buffer.WriteString(data);
            socket.Send(WriteMessage(buffer.ToBytes()));
        }
        catch
        {
            IsConnected = false;
            socket.Shutdown(SocketShutdown.Both);
            socket.Close();
        }
    }

    /// <summary>  
    /// 数据转换，网络发送需要两部分数据，二是主体数据  
    /// </summary>
    private static byte[] WriteMessage(byte[] message)
    {
        MemoryStream ms = null;
        using (ms = new MemoryStream())
        {
            ms.Position = 0;
            BinaryWriter writer = new BinaryWriter(ms);
            ushort msglen = (ushort)message.Length;
            //writer.Write(msglen);
            writer.Write(message);
            writer.Flush();
            return ms.ToArray();
        }
    }
}
