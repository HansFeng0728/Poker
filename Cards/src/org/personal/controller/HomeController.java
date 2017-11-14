package org.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/***
 * 
 * @author 
 *
 */
@Controller
@RequestMapping("/index")
public class HomeController {
	
	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	
	@RequestMapping(value="other",method=RequestMethod.POST)
	public ModelAndView other(){
		ModelAndView mav = new ModelAndView("succ");
		mav.addObject("username","test");
		mav.addObject("password", "test");
		return mav;
	}
	
	
}
