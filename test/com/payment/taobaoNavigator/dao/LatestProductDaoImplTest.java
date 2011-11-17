package com.payment.taobaoNavigator.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.payment.taobaoNavigator.support.DaoTestBase;

public class LatestProductDaoImplTest extends DaoTestBase {
	@Autowired
	private LatestProductDao latestProductDaoImpl;

	 @Before
	public void setUp() throws Exception {
	}

	 @After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLatestProductions() {
		assertNotNull(latestProductDaoImpl.getLatestProductions(2));
	}
	
	@Test
		public void testGetHotSells() {
			assertNotNull(latestProductDaoImpl.getHotSells(1));
		}
}
