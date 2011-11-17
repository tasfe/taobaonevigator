package com.payment.taobaoNavigator.service;

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

import com.payment.taobaoNavigator.dao.ProductDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ProductServiceImplTest {

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Mock
	private ProductDao productDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		productServiceImpl.setProductDao(productDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLatestProductsReturnNull() {
		fail("not implemented");
	}

	@Test
	public void testGetLatestProductsReturnEmpty() {
		fail("not implemented");
	}

	@Test
	public void testGetLatestProductsReturnNotNullNotEmpty() {
		fail("not implemented");
	}
	
	@Test
	public void testGetHottestProductsReturnNull() {
		fail("not implemented");
	}

	@Test
	public void testGetHottestProductsReturnEmpty() {
		fail("not implemented");
	}

	@Test
	public void testGetHottestProductsReturnNotNullNotEmpty() {
		fail("not implemented");
	}

}
