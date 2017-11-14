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
}
