package org.personal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.personal.db.dao.Poker;

public class PokerClass {
	private static ObjectMapper mapper = new ObjectMapper();
	
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException{
    	String userID = null;
    	int a = 0;
        String[] numbers = {"1","2","3","4","5"};
        List<String> pokers = new ArrayList<>();
        for(String number : numbers){    
            //遍历排值数组  
                //遍历花色  
          	 pokers.add(a+number);
          	 a++;
        }
        Map<String,String> mm = new HashMap<>();
        String qwqq = pokers.toString();
        mm.put("aa", qwqq);
        mm.put("bb", qwqq);
        mm.put("cc", qwqq);
        System.out.println("-----"+mapper.writeValueAsString(mm));
    }
}


