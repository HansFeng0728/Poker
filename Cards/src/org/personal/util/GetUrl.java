package org.personal.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;  
  
public class GetUrl {  
	public JSONObject getUrl(String getURL) throws IOException{  
		URL getUrl =new URL(getURL);  
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();    
        connection.connect();  
        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));  
        String lines=reader.readLine();   
        JSONObject js=JSONObject.fromObject(lines);  
        reader.close();  
        connection.disconnect();  
        return js;  
  }  
  
  public JSONObject postUrl(String getURL) throws IOException {    
      // Post请求的url，与get不同的是不需要带参数    
      URL postUrl = new URL(getURL);    
      // 打开连接    
      HttpURLConnection connection = (HttpURLConnection) postUrl    
              .openConnection();      
      // 设置是否向connection输出，因为这个是post请求，参数要放在    
      // http正文内，因此需要设为true    
      connection.setDoOutput(true);    
      // Read from the connection. Default is true.    
      connection.setDoInput(true);    
      // Set the post method. Default is GET    
      connection.setRequestMethod("POST");    
      // Post cannot use caches    
      // Post 请求不能使用缓存    
      connection.setUseCaches(false);    
         
      connection.setInstanceFollowRedirects(true);    
      // Set the content type to urlencoded,    
      // because we will write    
      // some URL-encoded content to the    
      // connection. Settings above must be set before connect!    
      // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的    
      // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode    
      // 进行编码    
      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
      // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，    
      // 要注意的是connection.getOutputStream会隐含的进行connect。    
      connection.connect();    
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());    
         
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));    
      String lines=reader.readLine();   
      JSONObject js=JSONObject.fromObject(lines);  
      reader.close();    
      connection.disconnect();  
      return js;  
  }    
}  
