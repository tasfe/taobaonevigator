package com.payment.taobaoNavigator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.payment.taobaoNavigator.constant.ProductConstants;
import com.payment.taobaoNavigator.entity.ProductEntity;
import com.payment.taobaoNavigator.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@RequestMapping("getLatestProducts.do")
	public ModelAndView getLatestProducts() {
		List<ProductEntity> latestProducts = productService.getLatestProducts(null);
		//TODO decide the request manner and response view
		return new ModelAndView("index", ProductConstants.KEY_LATEST_PRODUCTS, latestProducts);
	}
	
	@RequestMapping("getHottestProducts.do")
	public ModelAndView getHottestProducts() {
		List<ProductEntity> hottestProducts = productService.getHottestProducts(null);
		//TODO decide the request manner and response view
		return new ModelAndView("index", ProductConstants.KEY_HOTTEST_PRODUCTS, hottestProducts);
	}
	
	//add for mockito test.
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
		
}
