package com.payment.taobaoNavigator.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payment.taobaoNavigator.util.NavigatorCache;

@Controller
public class NavigatorController {

	@RequestMapping("index.do")
	public ModelAndView index() {

		List list = NavigatorCache.processAction("query", 0);
		System.out.println(list.size() + "!!!!!!");
		return null;
	}
}
