package com.payment.taobaoNavigator.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;
import com.payment.taobaoNavigator.entity.PromotionEntity;
import com.payment.taobaoNavigator.support.DaoTestBase;

public class PromotionDaoImplTest extends DaoTestBase{
	
	@Autowired
	private PromotionDao promotionDaoImpl;

	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPromotions() {
		assertNotNull(promotionDaoImpl.getPromotions());
	}

	// @Test
	public void testGetEnabledPromotions() {
		assertNotNull(promotionDaoImpl.getEnabledPromotions());
	}

	// @Test
	public void testGetPromotionById() {
		assertNotNull(promotionDaoImpl.getPromotionById(1));
	}

	// @Test
	public void testGetPromotionByName() {
		assertNotNull(promotionDaoImpl.getPromotionByName("1"));
	}

	// @Test
	public void testCreatePromotion() {
		PromotionEntity promotionEntity = new PromotionEntity();
		CatagoryEntity catagoryEntity = new CatagoryEntity();
		catagoryEntity.setName("chair");
		catagoryEntity.setParentCatagory(catagoryEntity);
		// catagoryEntity.setRemark("11");
		ProductEntity productEntity = new ProductEntity();
		productEntity.setName("wood");
		productEntity.setPrice(12.0);
		promotionEntity.setCatagory(catagoryEntity);
		promotionEntity.setProduct(productEntity);
		promotionDaoImpl.createPromotion(promotionEntity);
	}
	/*
	 * // @Test public void testUpdatePromotion() { PromotionEntity
	 * promotionEntity = new PromotionEntity(); }
	 * 
	 * // @Test public void testDeletePromotionById() { assertEquals(true,
	 * promotionDaoImpl.deletePromotionById(new Integer(2))); }
	 * 
	 * // @Test public void testDeletePromotionByIds() { List<Integer> list =
	 * new ArrayList<Integer>(); list.add(2); list.add(3);
	 * promotionDaoImpl.deletePromotionByIds(list); }
	 * 
	 * // @Test public void testDeletePromotionByName() { assertEquals(true,
	 * promotionDaoImpl.deletePromotionByName("2")); }
	 * 
	 * // @Test public void testDeletePromotionByNames() { List<String> list =
	 * new ArrayList<String>(); list.add("2"); list.add("3"); assertEquals(true,
	 * promotionDaoImpl.deletePromotionByNames(list)); }
	 * 
	 * // @Test public void testClearPromotions() { assertEquals(true,
	 * promotionDaoImpl.clearPromotions()); }
	 */
}

