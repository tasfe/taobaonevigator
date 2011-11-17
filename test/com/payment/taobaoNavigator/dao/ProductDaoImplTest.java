package com.payment.taobaoNavigator.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.payment.taobaoNavigator.support.DaoTestBase;

public class ProductDaoImplTest extends DaoTestBase {
	@Autowired
	private ProductDao productDaoImpl;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLatestProductions() {
		assertNotNull(productDaoImpl.getLatestProducts(2));
	}

	@Test
	public void testGetHotClicked() {
		assertNotNull(productDaoImpl.getHottestProducts(1));
	}
}
