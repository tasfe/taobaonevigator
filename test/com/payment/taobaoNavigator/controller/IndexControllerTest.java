package com.payment.taobaoNavigator.controller;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.taobaoNavigator.service.ProductService;
import com.payment.taobaoNavigator.service.PromotionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class IndexControllerTest {

	@Autowired
	private IndexController indexController;
	
	@Mock
	private PromotionService promotionService;
	
	@Mock
	private ProductService productService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController.setProductService(productService);
		indexController.setPromotionService(promotionService);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetIndexInfo() {
		fail();
	}

}
