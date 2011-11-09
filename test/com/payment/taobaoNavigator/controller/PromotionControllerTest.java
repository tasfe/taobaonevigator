package com.payment.taobaoNavigator.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	public void test() {
		System.out.println(promotionController.toString());
		Mockito.when(promotionService.clearPromotions()).thenReturn(true);
		boolean result = promotionController.clearPromotions();
		Assert.assertEquals(true, result);
	}

}
