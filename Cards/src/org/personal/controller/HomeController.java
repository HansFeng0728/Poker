package org.personal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.personal.service.CardService;
import org.personal.service.UserService;
import org.personal.util.ConfigurationUtil;
import org.personal.util.DButil;
import org.personal.util.HttpUtil;
import org.personal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.Statement;



/***
 * 
 * @author 
 *
 */
@RequestMapping("/index")
@Controller
public class HomeController {
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class.getName());
	
	UserService userService = new UserService();
	
	CardService cardService = new CardService();
	
	private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	
	 /**
     * 1. 使用RequestMapping注解来映射请求的URL 2. 返回值会通过视图解析器解析为实际的物理视图,
     * 对于InternalResourceViewResolver视图解析器，会做如下解析 通过prefix+returnVal+suffix
     * 这样的方式得到实际的物理视图，然后会转发操作 "/WEB-INF/views/success.jsp"
     * 
     * @return
     */
    @RequestMapping("/helloworld")
    public String hello() {
        System.out.println("hello world");
        return "header";
    }
	
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	
	@RequestMapping("/initCards")
	public void initCards(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		// 读取请求内容  
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));  
        String line = null;  
        StringBuilder sb = new StringBuilder(); 
        
        while ((line = br.readLine()) == null) {  
           logger.error("initCards json data is null, json = {}",line);  
        } 
        
        JsonParser parse = new JsonParser();
        JsonObject json = (JsonObject)parse.parse(line);
        JsonArray trans = (JsonArray)json.get("user");
        JsonObject trans_result = (JsonObject)trans.get(0);
        String userid = trans_result.get("userId").getAsString();
        
		if(userService.isNull(userid)){
			return;
		}
//		if(userService.isExist(userid)){
//			logger.info("login success,username = {}",userid);
//		}
		
		Map<String, String> userJsonParam = new HashMap<String,String>();
		userJsonParam.put("userId", userid);//内容字符串
		userJsonParam.put("score", "0");
		userJsonParam.put("daojishiTime", new Date().toString());
		String userJson = JsonUtil.encodeJson(userJsonParam);
		
		Map<String, String> pokerJsonParam = new HashMap<String,String>();
		pokerJsonParam.put("userId", userid);
//		发牌在cardService里面
//		pokerJsonParam.put("shufflePokerList", "0");
//		pokerJsonParam.put("handPokerList", "0");
		pokerJsonParam.put("completeCardList", "0");
		String pokerJson = JsonUtil.encodeJson(pokerJsonParam);
		
		Map<String, String> body = new HashMap<String, String>();
		body.put("user", userJson);
		body.put("pokers", pokerJson + cardService.sendPoker(userid));
		String jsoncontent = JsonUtil.encodeJson(body);
		
		PrintWriter pw = response.getWriter();
		pw.println(jsoncontent);
//		pw.flush();
	}
	
//	@RequestMapping("/moveCards")
//	public void moveCards(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
//		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
//		String line = null;
//		
//		while ((line = br.readLine()) == null) {  
//	           logger.error("moveCards json data is null, json = {}",line);  
//	    }
//		JsonParser parse = new JsonParser();
//        JsonObject json = (JsonObject)parse.parse(line);
//        JsonArray trans = (JsonArray)json.get("sendPokers");
//        JsonObject trans_result = (JsonObject)trans.get(0);
//        String movePoker = trans_result.get("movepokerList").getAsString();
//        String targetPoker = trans_result.get("targetpoker").getAsString();
//        if()
//        String jsoncard = cardService.moveCards(movePoker, targetPoker);
//        
//        PrintWriter pw = response.getWriter();
//        pw.println();
//	}
	
	
	/**
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * 把卡牌移动到
	 */
	@RequestMapping("/moveCardToPokerRoom")
	public void moveCardToCardHome(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
		String line = null;
		
		while ((line = br.readLine()) == null) {  
	           logger.error("moveCards json data is null, json = {}",line);  
	    }
	}
	
	@RequestMapping(value="other",method=RequestMethod.POST)
	public ModelAndView other(){
		ModelAndView mav = new ModelAndView("succ");
		mav.addObject("username","test");
		mav.addObject("password", "test");
		return mav;
	}
	
	@RequestMapping(value="/loginTest", method = RequestMethod.GET)
	public void recieveRequest(HttpServletRequest request, HttpServletResponse response,String requestStr) throws Exception {
//		// 读取请求内容
//		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//		String line = null;
//		StringBuilder sb = new StringBuilder();
//		while ((line = br.readLine()) != null) {
//			sb.append(line);
//		}

		// 将资料解码
		JsonParser parse = new JsonParser();
//		String reqBody = sb.toString();
		String data = "{'isAdmin':'true', 'usename':wsf}";
		JsonObject json = (JsonObject) parse.parse(data); 
		
		String username = json.get("isAdmin").getAsString();
		if(username.equals("") || null == username || username.length()<=0 ){
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			return;
		}
		
		System.out.println("user's name is:" + username);
		ResultSet test;
		try {
			Connection connect = DButil.getConnect();
			Statement statement = (Statement)connect.createStatement();
		
			String sqlQuery = "select * from " + DButil.TABLE_USER + " where userName='" + username + "'";
			test = statement.executeQuery(sqlQuery);
			if(test.next()){
				
			}else{
				String sqlInsertPass = "insert into " + DButil.TABLE_USER + "(userName) values('"+ username + "')";
				statement.execute(sqlInsertPass);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		response.setContentType("text/html;charset=utf-8");
		if(userService.isLoginCards(username)){
//			String url =  ConfigurationUtil.ZONE_CHATSERVER_URL + "/index/initCards";	
			String jsonname = JsonUtil.encodeJson(username);
			Map<String, String> params = new HashMap<String, String>();
			params.put("data", jsonname);
//			String resultJson = 
		}
		// 要返回的json字符串
		PrintWriter writer = response.getWriter();
		writer.write(data); 
		writer.flush();
		writer.close();
		
		response.getWriter().append("\n").append("Served at: ").append(request.getContextPath());
	}
}
