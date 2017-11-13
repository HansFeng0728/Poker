package org.personal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 
 * @author 
 *
 */
@Controller
public class HomeController {
	
	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	
	
	
}
