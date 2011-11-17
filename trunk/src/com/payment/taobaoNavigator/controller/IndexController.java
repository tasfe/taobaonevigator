package com.payment.taobaoNavigator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payment.taobaoNavigator.constant.ProductConstants;
import com.payment.taobaoNavigator.constant.PromotionConstants;
import com.payment.taobaoNavigator.entity.ProductEntity;
import com.payment.taobaoNavigator.entity.PromotionEntity;
import com.payment.taobaoNavigator.service.ProductService;
import com.payment.taobaoNavigator.service.PromotionService;

@Controller
public class IndexController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("promotionServiceImpl")
	private PromotionService promotionService;

	@RequestMapping("getIndexInfo.do")
	public ModelAndView getIndexInfo(ModelMap model) {
		//latest products
		List<ProductEntity> latestProducts = productService.getLatestProducts(null);
		model.put(ProductConstants.KEY_LATEST_PRODUCTS, latestProducts);
		//hottest products
		List<ProductEntity> hottestProducts = productService.getHottestProducts(null);
		model.put(ProductConstants.KEY_HOTTEST_PRODUCTS, hottestProducts);
		//promotions
		List<PromotionEntity> promotions = promotionService.getPromotions();
		model.put(PromotionConstants.KEY_PROMOTIONS, promotions);
		
		return new ModelAndView("index");	
	}

	// add for mockito test.
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// add for mockito test.
	public void setPromotionService(PromotionService promotionService) {
		this.promotionService = promotionService;
	}

}
