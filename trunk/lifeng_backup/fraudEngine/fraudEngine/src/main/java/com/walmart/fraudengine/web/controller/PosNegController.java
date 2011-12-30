package com.walmart.fraudengine.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class PosNegController {

	// TODO
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public ModelAndView show(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("index");
	}
}
