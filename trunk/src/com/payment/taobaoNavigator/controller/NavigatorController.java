package com.payment.taobaoNavigator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payment.taobaoNavigator.form.DemoForm;
import com.payment.taobaoNavigator.service.NavigatorService;
import com.payment.taobaoNavigator.util.NavigatorCache;

@Controller
public class NavigatorController {

	@Autowired
	@Qualifier("navigatorCache")
	private NavigatorCache navigatorCache;
	
	@RequestMapping("index.do")
	public ModelAndView index() {
		
	List list=navigatorCache.processAction("query",0);
	System.out.println(list.size()+"!!!!!!");
	DemoForm demoForm =new DemoForm();
	
	
		return new ModelAndView("demo", "demoForm", demoForm);
	}
}
