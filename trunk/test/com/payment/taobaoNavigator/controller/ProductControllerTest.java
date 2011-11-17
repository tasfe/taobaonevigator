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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ProductControllerTest {

	@Autowired
	private ProductController productController;
	
	@Mock
	private ProductService productService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		productController.setProductService(productService);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLatestProducts() {
		fail("not implemented");
	}
	
	@Test
	public void testGetHottestProducts() {
		fail("not implemented");
	}

}
