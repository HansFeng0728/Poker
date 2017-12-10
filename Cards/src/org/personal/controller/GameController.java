package org.personal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.personal.service.CardService;
import org.personal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/game")
public class GameController {
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class.getName());
	
	UserService userService = new UserService();
	
	CardService cardService = new CardService();
	
	/**
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * 卡牌手牌区的移动
	 */
	@RequestMapping(value="/moveCardToPokerRoom",method = RequestMethod.GET)
	public void moveCardToCardHome(HttpServletRequest request, HttpServletResponse response,String requestStr) throws UnsupportedEncodingException, IOException{
//		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
//		String line = null;
//		while ((line = br.readLine()) == null) {  
//	           logger.error("moveCards json data is null, json = {}",line);  
//	    }
		
		// 将资料解码
		JsonParser parse = new JsonParser();
		JsonObject json = (JsonObject) parse.parse(requestStr); 
		
		String poker = json.get("UserId").getAsString();
		String targetPoker = json.get("targetPoker").getAsString();
		
	
	}
	
	
	

}
