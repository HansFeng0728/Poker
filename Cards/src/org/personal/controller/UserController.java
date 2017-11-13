package org.personal.controller;

import javax.servlet.http.HttpServletRequest;

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

//	/***
//	 * @param username
//	 * @param password
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="login",method=RequestMethod.POST)
//	public ModelAndView login(String username,String password,HttpServletRequest request){
//		request.setAttribute("username", username);
//		request.setAttribute("password", password);
//		return new ModelAndView("succ");
//	}
	
	private boolean checkParams(String[] params){
		for(String param:params){
			if(param==""||param==null||param.isEmpty()){
				return false;
			}
		}
		return true;
	}
}
