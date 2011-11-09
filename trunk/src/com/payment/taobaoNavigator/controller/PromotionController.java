package com.payment.taobaoNavigator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payment.taobaoNavigator.constant.PromotionConstants;
import com.payment.taobaoNavigator.entity.PromotionEntity;
import com.payment.taobaoNavigator.service.PromotionService;

@Controller
public class PromotionController {

	@Autowired
	@Qualifier("promotionServiceImpl")
	private PromotionService promotionService;

	@RequestMapping("getPromotions.do")
	public ModelAndView getPromotions() {
		List<PromotionEntity> promotions = promotionService.getPromotions();
		return new ModelAndView("index", PromotionConstants.KEY_PROMOTIONS, promotions);
	}
	
	//add for mockito test.
	public void setPromotionService(PromotionService promotionService) {
		this.promotionService = promotionService;
	}
		
}
