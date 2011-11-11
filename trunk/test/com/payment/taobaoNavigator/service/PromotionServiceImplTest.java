package com.payment.taobaoNavigator.service;

import static org.junit.Assert.fail;
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

import com.payment.taobaoNavigator.dao.PromotionDao;
import com.payment.taobaoNavigator.entity.PromotionEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PromotionServiceImplTest {

	@Autowired
	private PromotionServiceImpl promotionServiceImpl;

	@Mock
	private PromotionDao promotionDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		promotionServiceImpl.setPromotionDao(promotionDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPromotionsReturnNull() {
		when(promotionDao.getPromotions()).thenReturn(null);
		List<PromotionEntity> result = promotionServiceImpl.getPromotions();
		verify(promotionDao, times(1)).getPromotions();
		verifyNoMoreInteractions(promotionDao);
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testGetPromotionsReturnEmpty() {
		List<PromotionEntity> promotions = new ArrayList<PromotionEntity>();
		when(promotionDao.getPromotions()).thenReturn(promotions);
		List<PromotionEntity> result1 = promotionServiceImpl.getPromotions();
		verify(promotionDao, times(1)).getPromotions();
		verifyNoMoreInteractions(promotionDao);
		Assert.assertEquals(0, result1.size());
	}

	@Test
	public void testGetPromotionsReturnNotNullNotEmpty() {
		List<PromotionEntity> promotions = new ArrayList<PromotionEntity>();
		PromotionEntity promotion = new PromotionEntity();
		promotion.setName("name");
		promotion.setPicture("picture");
		promotions.add(promotion);
		when(promotionDao.getPromotions()).thenReturn(promotions);

		List<PromotionEntity> result = promotionServiceImpl.getPromotions();

		verify(promotionDao, times(1)).getPromotions();
		verifyNoMoreInteractions(promotionDao);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("name", result.get(0).getName());
		Assert.assertEquals("picture", result.get(0).getPicture());
	}

	/*
	 * @Test public void testGetEnabledPromotions() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetPromotionById() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetPromotionByName() { fail("Not yet implemented");
	 * }
	 * 
	 * @Test public void testCreatePromotion() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testUpdatePromotion() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testDeletePromotionById() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testDeletePromotionByIds() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testDeletePromotionByName() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testDeletePromotionByNames() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testClearPromotions() { fail("Not yet implemented"); }
	 */
}
