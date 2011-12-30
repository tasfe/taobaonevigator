package com.walmart.fraudengine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRuleHitEntity;
import com.walmart.fraudengine.model.BusRulePriorityEntity;
import com.walmart.fraudengine.model.BusRulePropertyEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
@TransactionConfiguration(transactionManager = "testjpaTxManager", defaultRollback = true)
@Transactional
public class BusRuleDaoJpaTest {

	@Autowired
	private BusRuleDao busRuleDaoJpa;

	@Autowired
	private FraudEngineDao fraudEngineDaoJpa;

	@Test
	public void testFindBusRuleById() {
		BusRuleEntity busRuleEntity = fraudEngineDaoJpa.find(155L, BusRuleEntity.class);
		assertNotNull(busRuleEntity);
	}

	@Test
	public void testFindAllBusRule() {
		List<BusRuleEntity> busRuleEntityList = busRuleDaoJpa.findAllBusRule();
		assertFalse(busRuleEntityList.isEmpty());
		for (BusRuleEntity busRuleEntity : busRuleEntityList) {
			assertNotNull(busRuleEntity);
		}
	}

	@Test
	public void testFindAllBusRuleProperty() {
		List<BusRulePropertyEntity> busRulePropertyList = busRuleDaoJpa.findAllBusRuleProperty();
		assertFalse(busRulePropertyList.isEmpty());
		for (BusRulePropertyEntity busRulePropertyEntity : busRulePropertyList) {
			assertNotNull(busRulePropertyEntity);
		}
	}

	@Test
	public void testFindBusRulePropertyByRuleId() {
		List<BusRulePropertyEntity> busRulePropertyList = busRuleDaoJpa.findBusRulePropertyByRuleId(155L);
		assertFalse(busRulePropertyList.isEmpty());
		for (BusRulePropertyEntity busRulePropertyEntity : busRulePropertyList) {
			assertNotNull(busRulePropertyEntity);
		}
	}

	@Test
	public void testFindBusRuleHitByRuleId() {
		BusRuleHitEntity busRuleHitEntity = busRuleDaoJpa.findBusRuleHitByRuleId(155L);
		assertTrue(busRuleHitEntity == null ? true : busRuleHitEntity.getHitCount() > 0);
	}

	@Test
	public void testFindAllBusRulePriority() {
		List<BusRulePriorityEntity> busRulePriorityList = busRuleDaoJpa.findAllBusRulePriority();
		assertFalse(busRulePriorityList.isEmpty());
		for (BusRulePriorityEntity busRulePriorityEntity : busRulePriorityList) {
			assertNotNull(busRulePriorityEntity);
		}
	}

	@Test
	public void testFindBusRulePriorityByRuleId() {
		BusRulePriorityEntity busRulePriorityEntity = busRuleDaoJpa.findBusRulePriorityByRuleId(155L);
		assertNotNull(busRulePriorityEntity);
	}

	@Test
	public void testGetCountOfBusRule() {
		long result = busRuleDaoJpa.getCountOfBusRule();
		assertTrue(result > 0);
	}

	@Test
	public void testFindForBusRuleForm() throws FraudEngineApplicationException {
		Map<String, String> sortMap = new LinkedHashMap<String, String>();
		sortMap.put("priority", "asc");
		Long startIndex = 1L;
		Long size = 3L;
		List<?> result = busRuleDaoJpa.findForBusRuleForm(startIndex, size, sortMap);
		assertEquals(3L, result.size());
	}
}
