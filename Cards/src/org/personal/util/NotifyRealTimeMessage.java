package org.personal.util;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class NotifyRealTimeMessage implements Serializable {   
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8414215359454143747L;

	private static ObjectMapper mapper = new ObjectMapper();
	
 
    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
    }
         
    @JsonProperty("messageType")
    private int type;   
 
    @JsonProperty("geoData")
    private Object message;
     
    @JsonProperty("time")
    private Calendar time;
     
    public int getType() {
        return type;
    }
     
    public void setType(int type) {
        this.type = type;
    }
     
    public Object getMessage() {
        return message;
    }
     
    public void setMessage(Object message) {
        this.message = message;
    }
     
    public Calendar getTime() {
        return time;
    }
     
    public void setTime(Calendar time) {
        this.time = time;
    }
     
    /**
     * 产生Json串
     * 
     */
    public static String toJson(String a) throws JsonGenerationException,
            JsonMappingException, IOException {
 
        return mapper.writeValueAsString(a);
    }
 
    /**
     * 从Json字符串构建NotifyRealTimeMessage对象
     * 
     */
    public static NotifyRealTimeMessage fromJson(String json) throws JsonParseException,
            JsonMappingException, IOException {
        if (json == null) {
            return null;
        } else {
            return mapper.readValue(json, NotifyRealTimeMessage.class);
        }
    }
    
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException{
    	String userId = "1213";
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("UserId", userId);
		params.put("Score", 0);
		params.put("UserState", 1);
    	System.out.println(mapper.writeValueAsString(params));
    }
 
}
