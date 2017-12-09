package org.personal.controller;

import org.personal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


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

	private boolean checkParams(String[] params){
		for(String param:params){
			if(param==""||param==null||param.isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public void LoginCard(String userName){
		if(userName == null){
			logger.error("null of userName,{}",userName);
		}
		
		
	}
}
