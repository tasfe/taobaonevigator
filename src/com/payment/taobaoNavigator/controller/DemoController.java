package com.payment.taobaoNavigator.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payment.taobaoNavigator.form.DemoForm;
import com.payment.taobaoNavigator.service.IDemoService;

@Controller
public class DemoController {

	@Autowired
	@Qualifier("demoServiceImpl")
	private IDemoService demoService;
	
	@RequestMapping("show.do")
	public ModelAndView show(ModelMap model) {
		DemoForm form = new DemoForm();
		return new ModelAndView("demo", "demoForm", form);
	}
	
	@RequestMapping("demo.do")
	public String doDemo(ModelMap model) {
		System.out.println("do demo in demo controller." + new Date());
		demoService.doDemo();
		return "demo_result";
	}
		
}
