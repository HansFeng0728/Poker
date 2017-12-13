package org.personal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.personal.db.DBUtil;
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
 * @author Hans 
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
    
    private static ObjectMapper mapper = new ObjectMapper();
	
	 /**
     * 1. 使用RequestMapping注解来映射请求的URL 2. 返回值会通过视图解析器解析为实际的物理视图,
     * 对于InternalResourceViewResolver视图解析器，会做如下解析 通过prefix+returnVal+suffix
     * 这样的方式得到实际的物理视图，然后会转发操作 "/WEB-INF/views/success.jsp"
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
	public void initCards(HttpServletRequest request, HttpServletResponse response,String requestStr) throws UnsupportedEncodingException, IOException{
		DBUtil.GetInstance().init();
        JsonParser parse = new JsonParser();
        logger.info("--------requestStr"+requestStr);
        if("".equals(requestStr) || requestStr == null){
        	logger.error("error,{}",requestStr);
        	
        }
        JsonObject json = (JsonObject)parse.parse(requestStr);

        String userId = json.get("UserId").getAsString();
		
        Map<String, String> params = new HashMap<String, String>();
        if(DBUtil.GetInstance().getUser(userId) == null){
        	PrintWriter writer = response.getWriter();
        	params.put("userId", userId);
        	params.put("ErrorCode", "1");
//        	String jsonStr = JsonUtil.encodeJson(params);
        	String jsonStr = params.toString();
    		writer.write(jsonStr); 
    		writer.flush();
        }else{
        	params.put("userId", userId);//内容字符串
        	params.put("score", "0");
        	params.put("daojishiTime", new Date().toString());
//        	String userJson = JsonUtil.encodeJson(params);
//        	String userJson = mapper.writeValueAsString(params);
        	String userJson = params.toString();
        	
        	Map<String, String> pokerJsonParam = new HashMap<String,String>();
        	pokerJsonParam.put("userId", userId);
//			发牌在cardService里面
        	pokerJsonParam.put("completeCardList", "0");
//        	String pokerJson = JsonUtil.encodeJson(pokerJsonParam);
        	String pokerJson = pokerJsonParam.toString();
        	
        	Map<String, String> body = new HashMap<String, String>();
        	body.put("user", userJson);
        	body.put("pokers", pokerJson);
//        	String jsoncontent = JsonUtil.encodeJson(body);
         	String jsoncontent = mapper.writeValueAsString(cardService.sendPoker(userId));
        	
        	PrintWriter pw = response.getWriter();
        	pw.write(jsoncontent);
        	pw.flush();
    		pw.close();
    		logger.info("initCards response success--------------{}",jsoncontent);
        	pw.println(jsoncontent);
        }
	}
	
	@RequestMapping("/moveCards")
	public void moveCards(HttpServletRequest request, HttpServletResponse response, String requestStr) throws UnsupportedEncodingException, IOException{
		DBUtil.GetInstance().init();
		Map<String, Object> params = new HashMap<String, Object>();
		PrintWriter pw = response.getWriter();
		if("".equals(requestStr) || requestStr == null){
			logger.error("illegal requestStr of moveCards");
			params.put("ErrorCode", "illegal requestStr of moveCards");
			String error = mapper.writeValueAsString(params);
			pw.write(error);
			pw.flush();
			pw.close();
		}
		
		logger.info("-----request moveCards:{}",requestStr);
		JsonParser parse = new JsonParser();
		JsonObject json = (JsonObject) parse.parse(requestStr); 
//		JsonObject json = parse.parse(requestStr).getAsJsonObject();
		String userId = json.get("UserId").getAsString();
		if(userId == null || DBUtil.GetInstance().getUser(userId) == null){
			logger.error("illegal userId of moveCards");
			params.put("ErrorCode", "illegal userId of moveCards");
			String error = mapper.writeValueAsString(params);
			pw.write(error);
			pw.flush();
			pw.close();
		}
		
		String movePoker = json.get("MovePoker").getAsString();
		String targetPoker = json.get("TargetPoker").getAsString();
		int movepoker_Position = json.get("MovePoker_Position").getAsInt();
		int targetPoker_Position = json.get("TargetPoker_Position").getAsInt();
		
		String[] mp = movePoker.split("-");
		int pokerId = Integer.valueOf(mp[0]);
		
		if(movepoker_Position < 0 || movepoker_Position > 7){
			logger.error("illegal move_position");
			params.put("ErrorCode", "illegal move_position");
			String error = mapper.writeValueAsString(params);
			pw.write(error);
			pw.flush();
			pw.close();
		}
		int targetPokerId = 0;
		if(targetPoker != null && !"".equals(targetPoker)){
			String[] tp = targetPoker.split("-");
			targetPokerId = Integer.valueOf(tp[0]);
		}else{
			if(targetPoker_Position >= 0 && targetPoker_Position < 12){
				targetPokerId = 1000;
			}else{
				logger.error("illegal move_position");
				params.put("ErrorCode", "illegal targetmove_position");
				String error = mapper.writeValueAsString(params);
				pw.write(error);
				pw.flush();
				pw.close();
			}
		}
		Map<String, Object> moveResult = cardService.moveCards(userId, pokerId, targetPokerId, movepoker_Position, targetPoker_Position);
		String jsonStr = mapper.writeValueAsString(moveResult);
		pw.write(jsonStr); 
		pw.flush();
		pw.close();
		logger.info("moveCards response success--------{}",jsonStr);
		response.getWriter().append("\n").append("Served at: ").append(request.getContextPath());
	}
	
	@RequestMapping(value="otherTest",method=RequestMethod.POST)
	public ModelAndView other(){
		ModelAndView mav = new ModelAndView("succ");
		mav.addObject("username","test");
		mav.addObject("password", "test");
		return mav;
	}
	
	@RequestMapping(value="/loginTest", method = RequestMethod.GET)
	public void recieveRequest(HttpServletRequest request, HttpServletResponse response,String requestStr) throws Exception {
		JsonParser parse = new JsonParser();
		JsonObject json = (JsonObject) parse.parse(requestStr); 
		String userId = json.get("UserId").getAsString();
		if(userId.equals("") || null == userId || userId.length()<=0 ){
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			return;
		}
		System.out.println("userId is:" + userId);
		response.setContentType("text/html;charset=utf-8");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(userService.isLoginCards(userId)){
//			String jsonId = JsonUtil.encodeJson(userId);
			params.put("UserId", userId);
			params.put("Score", 0);
			params.put("UserState", 1);
		}
//		String jsonStr = JsonUtil.encodeJson(params);
		String jsonStr = mapper.writeValueAsString(params);
		PrintWriter writer = response.getWriter();
		writer.write(jsonStr); 
		writer.flush();
		writer.close();
		logger.info("login response success--------------{}",jsonStr);
		response.getWriter().append("\n").append("Served at: ").append(request.getContextPath());
	}
}
