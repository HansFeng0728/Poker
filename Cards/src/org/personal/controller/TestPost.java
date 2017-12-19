package org.personal.controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;  
public class TestPost {  
	  
    public static int postBody(String urlPath, String data) throws Exception {  
        try{    
                    // Configure and open a connection to the site you will send the request    
                    URL url = new URL(urlPath);    
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();    
                    // 设置doOutput属性为true表示将使用此urlConnection写入数据    
                    urlConnection.setDoOutput(true);    
                    // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型    
                    urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");    
                    // 得到请求的输出流对象    
                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());    
                    // 把数据写入请求的Body    
                    out.write(data);    
                    out.flush();    
                    out.close();   
                        
                    // 从服务器读取响应    
                    InputStream inputStream = urlConnection.getInputStream();    
                    String encoding = urlConnection.getContentEncoding();    
                    String body = IOUtils.toString(inputStream, encoding);    
                    if(urlConnection.getResponseCode()==200){  
                    return 200;  
                    }else{  
                    throw new Exception(body);  
                    }  
                }catch(IOException e){  
                throw e;  
                }  
        }  
      
        
      public static JSONObject doPost(String url,JSONObject json){  
        DefaultHttpClient client = new DefaultHttpClient();  
        HttpPost post = new HttpPost(url);  
        JSONObject response = null;  
        try {  
          StringEntity s = new StringEntity(json.toString());  
          s.setContentEncoding("UTF-8");  
          s.setContentType("application/json");//发送json数据需要设置contentType  
          post.setEntity(s);  
          HttpResponse res = client.execute(post);  
          if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
            HttpEntity entity = res.getEntity();  
            String result = EntityUtils.toString(res.getEntity());// 返回json格式：  
            response = JSONObject.fromObject(result);  
          }  
        } catch (Exception e) {  
          throw new RuntimeException(e);  
        }  
        return response;  
      }  
        public static void main(String[] args) {  
        try {  
             String url = "http://localhost:8009/wechatyeexun/loginTest.do";  
                JSONObject params = new JSONObject();  
                params.put("username", "wsf");  
                params.put("password", "123");  
                String ret = doPost(url, params).toString();  
                System.out.println(ret);  
        } catch (Exception e) {  
        e.printStackTrace();  
        }  
        }  
}  

