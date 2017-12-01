package org.personal.util;

import com.google.gson.Gson;

public class JsonUtil {
	public static String encodeJson(Object obj){
		Gson gson = new Gson();
		String str = gson.toJson(obj);
		return str;
	}
	
	public static <T> T decodeObj(String jsonStr, Class<T> clazz){
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clazz);
	}
	
	/** 
     * 将json转换成bean对象 
     * @author fuyzh 
     * @param jsonStr 
     * @return 
     */  
    public static Object jsonToBean(String jsonStr, Class<?> cl) {  
        Object obj = null; 
        Gson gson = new Gson();
        if (gson != null) {  
            obj = gson.fromJson(jsonStr, cl);  
        }  
        return obj;  
    }  
    /** 
     * 将对象转换成json格式 
     * @author fuyzh
     * @param ts 
     * @return 
     */  
    public static String objectToJson(Object ts) {  
        String jsonStr = null;  
        Gson gson = new Gson();
        if (gson != null) {  
            jsonStr = gson.toJson(ts);  
        }  
        return jsonStr;  
    }
}
