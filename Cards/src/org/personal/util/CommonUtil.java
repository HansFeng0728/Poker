package org.personal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class CommonUtil {
	public static DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static String generatRoomId(){
	return UUID.randomUUID().toString();
	}
	public static void checkNull(Object obj) throws Exception{
		if(obj == null) 
			throw new NullPointerException();
	}
	public static void checkStrNull(String str) throws Exception{
		if(str == null || str.equals("")) 
			throw new NullPointerException();
	}
}
