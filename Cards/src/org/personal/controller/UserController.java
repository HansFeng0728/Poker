package org.personal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.personal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.personal.service.BaseService;

import net.sf.json.JSONObject;


/***
 * @author 
 *
 */
@Controller
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class.getName());
	
	UserService userService = new UserService();
	@RequestMapping(value="login",method=RequestMethod.POST)
	public ModelAndView login(String username,String password){
		if(this.checkParams(new String[]{username,password})){
			ModelAndView mav = new ModelAndView("succ");
			mav.addObject("username",username);
			mav.addObject("password", password);
			return mav;
		}
		return new ModelAndView("home");
	}
	
	@RequestMapping("loginAndSend")
	public void sendCards(HttpServletRequest request, HttpServletResponse response){
		String userid = request.getParameter("userId");
		if(userService.isNull(userid)){
			return;
		}
		if(userService.isExist(userid)){
		
		}
		
	}
	private boolean checkParams(String[] params){
		for(String param:params){
			if(param==""||param==null||param.isEmpty()){
				return false;
			}
		}
		return true;
	}
}
