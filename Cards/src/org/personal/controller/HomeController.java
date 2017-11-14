package org.personal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.personal.service.CardService;
import org.personal.service.UserService;
import org.personal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/***
 * 
 * @author 
 *
 */
@Controller
@RequestMapping("/index")
public class HomeController {
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class.getName());
	
	UserService userService = new UserService();
	
	CardService cardService = new CardService();
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	
	
	@RequestMapping("/loginAndSend")
	public void sendCards(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		
		// 读取请求内容  
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));  
        String line = null;  
        StringBuilder sb = new StringBuilder(); 
        
        while ((line = br.readLine()) == null) {  
           logger.error("json data is null, json = {}",line);  
        } 
        
        JsonParser parse = new JsonParser();
        JsonObject json = (JsonObject) parse.parse(line);
        String userid = json.get("userId").getAsString();
        
		if(userService.isNull(userid)){
			return;
		}
		if(userService.isExist(userid)){
			logger.info("login success,username = {}",userid);
		}
		
		Map<String, String> userJsonParam = new HashMap<String,String>();
		userJsonParam.put("userId", userid);//内容字符串
		userJsonParam.put("score", "0");
		userJsonParam.put("daojishiTime", "0");
		String userJson = JsonUtil.encodeJson(userJsonParam);
		
		Map<String, String> pokerJsonParam = new HashMap<String,String>();
		pokerJsonParam.put("userId", userid);
		pokerJsonParam.put("shufflePokerList", "0");
		pokerJsonParam.put("userpokerFront", "0");
		pokerJsonParam.put("userpokerOpposite", "0");
		pokerJsonParam.put("completeCardList", "0");
		String pokerJson = JsonUtil.encodeJson(pokerJsonParam);
		
		Map<String, String> body = new HashMap<String, String>();
		body.put("user", userJson);
		body.put("pokers", pokerJson);
		String jsoncontent = JsonUtil.encodeJson(body);
		
		PrintWriter pw = response.getWriter();
		pw.println(jsoncontent);
		pw.flush();

		
	}
	
	@RequestMapping(value="other",method=RequestMethod.POST)
	public ModelAndView other(){
		ModelAndView mav = new ModelAndView("succ");
		mav.addObject("username","test");
		mav.addObject("password", "test");
		return mav;
	}
	
	
}
