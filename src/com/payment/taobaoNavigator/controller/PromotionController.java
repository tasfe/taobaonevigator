package com.payment.taobaoNavigator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.payment.taobaoNavigator.service.PromotionService;

@Controller
public class PromotionController {

	@Autowired
	@Qualifier("promotionServiceImpl")
	private PromotionService promotionService;
	
	public boolean clearPromotions() {
		return promotionService.clearPromotions();
	}
	
	//add for mockito test.
	public void setPromotionService(PromotionService promotionService) {
		this.promotionService = promotionService;
	}
		
}
