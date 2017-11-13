package org.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OtherController {


	@RequestMapping("other")
	public String other(){
		return "other";
	}
	
}
