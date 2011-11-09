package com.payment.taobaoNavigator.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import com.payment.taobaoNavigator.constant.PromotionConstants;
import com.payment.taobaoNavigator.entity.PromotionEntity;
import com.payment.taobaoNavigator.service.PromotionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PromotionControllerTest {

	@Autowired
	private PromotionController promotionController;
	
	@Mock
	private PromotionService promotionService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		promotionController.setPromotionService(promotionService);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPromotions() {
		List<PromotionEntity> promotions = new ArrayList<PromotionEntity>();
		PromotionEntity promotion = new PromotionEntity();
		promotion.setName("name");
		promotion.setPicture("picture");
		promotions.add(promotion);
		when(promotionService.getPromotions()).thenReturn(promotions);
		
		ModelAndView result = promotionController.getPromotions();
		
		verify(promotionService, times(1)).getPromotions();
		verifyNoMoreInteractions(promotionService);
		Assert.assertEquals("index", result.getViewName());
		Assert.assertNotNull(result.getModelMap().get(PromotionConstants.KEY_PROMOTIONS));
	}

}
