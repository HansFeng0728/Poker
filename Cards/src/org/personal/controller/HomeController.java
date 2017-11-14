package org.personal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.personal.service.CardService;
import org.personal.service.UserService;
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
		
		Map<String, String> msgJsonParam = new HashMap<String,String>();
		msgJsonParam.put("content", content);//内容字符串
		msgJsonParam.put("userId", "0");
		msgJsonParam.put("userLevel", "0");
		msgJsonParam.put("userName", "");
		msgJsonParam.put("vipLevel", "0");
		msgJsonParam.put("allFightingNum", "0");
		msgJsonParam.put("guildId", "0");
		msgJsonParam.put("guildName", "");
		msgJsonParam.put("zoneId", String.valueOf(ConfigurationUtil.ZONE_ID));
		msgJsonParam.put("icon", "");
		msgJsonParam.put("iconKuang", "");
		
		
		
	}
	
	@RequestMapping(value="other",method=RequestMethod.POST)
	public ModelAndView other(){
		ModelAndView mav = new ModelAndView("succ");
		mav.addObject("username","test");
		mav.addObject("password", "test");
		return mav;
	}
	
	
}
