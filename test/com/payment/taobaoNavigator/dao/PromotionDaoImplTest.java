package com.payment.taobaoNavigator.dao;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;
import com.payment.taobaoNavigator.entity.PromotionEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PromotionDaoImplTest {
	@Autowired
	private PromotionDao promotionDaoImpl;

	// @Before
	public void setUp() throws Exception {
	}

	// @After
	public void tearDown() throws Exception {
	}

	// @Test
	public void testGetPromotions() {
		assertNotNull(promotionDaoImpl.getPromotions());

	}

	// @Test
	public void testGetEnabledPromotions() {
		promotionDaoImpl.getEnabledPromotions();
	}

	// @Test
	public void testGetPromotionById() {
		promotionDaoImpl.getPromotionById(1);
	}

	// @Test
	public void testGetPromotionByName() {
		promotionDaoImpl.getPromotionByName("1");
	}

	@Test
	public void testCreatePromotion() {
		
	}

	// @Test
	public void testUpdatePromotion() {
		PromotionEntity promotionEntity = new PromotionEntity();

	}

	// @Test
	public void testDeletePromotionById() {
		promotionDaoImpl.deletePromotionById(new Integer(2));
	}

	// @Test
	public void testDeletePromotionByIds() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(3);
		promotionDaoImpl.deletePromotionByIds(list);
	}

	// @Test
	public void testDeletePromotionByName() {
		promotionDaoImpl.deletePromotionByName("2");
	}

	// @Test
	public void testDeletePromotionByNames() {
		List<String> list = new ArrayList<String>();
		list.add("2");
		list.add("3");
		promotionDaoImpl.deletePromotionByNames(list);
	}

	// @Test
	public void testClearPromotions() {
		promotionDaoImpl.clearPromotions();
	}

}
