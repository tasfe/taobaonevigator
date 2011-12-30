package com.walmart.fraudengine.dao;

import static com.walmart.fraudengine.util.FraudCheckConst.EXCLUDE_SOFT_DELETE;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.model.BusRulePropertyEntity;
import com.walmart.fraudengine.model.OrderDetailEntity;
import com.walmart.fraudengine.model.OrderStatusEntity;
import com.walmart.fraudengine.model.StatusLkEntity;
import com.walmart.fraudengine.util.FraudCheckConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
@TransactionConfiguration(transactionManager = "testjpaTxManager", defaultRollback = true)
@Transactional
public class GenericDaoJpaTest {

	@Autowired
	private FraudEngineDao fraudEngineDaoJpa;

	@PersistenceContext(unitName = "fraudPersistenceUnit")
	protected EntityManager em;

	private GenericDaoJpa dao = Mockito.mock(GenericDaoJpa.class, Mockito.CALLS_REAL_METHODS);

	@Before
	public void setUp() throws Exception {
		dao.em = em;
	}

	@Test
	public void testCreate() {
		// 1. create test data
		OrderDetailEntity orderDetailEntity = new OrderDetailEntity();

		orderDetailEntity.setCustomerEmail("testCustomerEmail");
		orderDetailEntity.setCustomerId("testCustomerId");
		orderDetailEntity.setIpAddress("testIpAddress");

		orderDetailEntity.setNumberOfLines(1);
		orderDetailEntity.setOmsOrderId("testOmsOrderId");
		orderDetailEntity.setOrderAmount(new BigDecimal(100));
		orderDetailEntity.setOrderPriority(new BigDecimal(1));
		orderDetailEntity.setOrderReceivedDate(new Timestamp(100));
		orderDetailEntity.setOrderRetryCount(2);
		orderDetailEntity.setPaymentType("Alipay");

		Set<OrderStatusEntity> orderStatuses = new HashSet<OrderStatusEntity>();
		OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
		orderStatusEntity.setBusRule(null);
		orderStatusEntity.setOrderDetail(orderDetailEntity);
		orderStatusEntity.setPosNegListFk(1L);
		orderStatusEntity.setReasonCode("1111");
		orderStatusEntity.setStatusLk(fraudEngineDaoJpa.findStatusLk("ACCEPT"));
		orderStatusEntity.setStatusReason(FraudCheckConst.NULL_VALUE);
		orderStatuses.add(orderStatusEntity);

		orderDetailEntity.setOrderStatuses(orderStatuses);

		orderDetailEntity.setTransactionId("testTransactionId");
		// 2. save data
		dao.create(orderDetailEntity);
	}

	@Test
	public void testFind() {
		StatusLkEntity entity = dao.find(1L, StatusLkEntity.class);
		assertNotNull(entity);
	}

	@Test
	public void testMerge() {
		StatusLkEntity entity = dao.find(1L, StatusLkEntity.class);
		String testCreatedBy = "test";
		entity.setCreatedBy(testCreatedBy);
		entity = dao.merge(entity);
		assertEquals(entity.getCreatedBy(), testCreatedBy);
	}

	@Test
	public void testFindByNameQueryByParameter() {
		final Map<String, Object> parameters = QueryParameter.with("softDelete", EXCLUDE_SOFT_DELETE).parameters();
		List<BusRulePropertyEntity> entityList = dao.findByNamedQuery(BusRulePropertyEntity.class, BusRulePropertyEntity.FETCH_ALL,
				parameters);
		assertNotNull(entityList);
		assertTrue(entityList.size() > 0);
	}

}
