package com.payment.taobaoNavigator.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.CatagoryEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class NavigatorDaoImplTest {
	@Autowired
	private NavigatorDao navigatorDaoImpl;

	// @Before
	public void setUp() throws Exception {
	}

	// @After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindNavigatorByParentId() {
		assertEquals(2,navigatorDaoImpl.findNavigatorByParentId(1).size());

	}

	@Test
	public void testCreateNavigator() {
		
		CatagoryEntity catagoryEntity = new CatagoryEntity();
		CatagoryEntity pcatagoryEntity=navigatorDaoImpl.findNavigatorById(1);
		catagoryEntity.setName("chair");
		catagoryEntity.setParentCatagory(pcatagoryEntity);
		 catagoryEntity.setRemark("11");
//		navigatorDaoImpl.createNavigator(catagoryEntity);
	}
//	
	@Test
	public void testgetAllChildCatagory(){
		List<CatagoryEntity>  catagorys=navigatorDaoImpl.getAllChildCatagory(1);
		
		assertEquals(14,catagorys.size());
		
		
		
	}

}
