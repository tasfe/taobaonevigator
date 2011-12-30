package com.walmart.fraudengine.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.model.OrderDetailEntity;
import com.walmart.fraudengine.model.OrderStatusEntity;
import com.walmart.fraudengine.model.PosNegListView;
import com.walmart.fraudengine.model.StatusLkEntity;
import com.walmart.fraudengine.util.FraudCheckConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
@TransactionConfiguration(transactionManager = "testjpaTxManager", defaultRollback = true)
@Transactional
public class FraudEngineDaoJpaTest {

	@Autowired
	private FraudEngineDao fraudEngineDaoJpa;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateOrder() {
		// preparation
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

		// execution
		fraudEngineDaoJpa.create(orderDetailEntity);

		assertNotNull(fraudEngineDaoJpa.find(orderDetailEntity.getOrderDetailPk(), OrderDetailEntity.class));

	}

	@Test
	public void testFindStatusLk() {
		// preparation

		// execution
		StatusLkEntity statusLk = fraudEngineDaoJpa.findStatusLk("DENY");

		// assertion
		assertNotNull(statusLk);
		assertEquals("DENY", statusLk.getName());
	}

	@Test
	public void testFindFromView() {
		Map<String, String> fieldNameValue = new HashMap<String, String>();
		fieldNameValue.put("EMAIL_ADDRESS", "ggao@walmart.com");
		fieldNameValue.put("CUSTOMER_ID", "Rainie.Liu");
		fieldNameValue.put(FraudCheckConst.CUSTOMER_ADDRESS, "");
		fieldNameValue.put(FraudCheckConst.CUSTOMER_ACCOUNT_AGE, "");
		fieldNameValue.put(FraudCheckConst.IP_ADDRESS, "");
		fieldNameValue.put(FraudCheckConst.SHIPPING_ADDRESS, "");
		fieldNameValue.put(FraudCheckConst.SHIPPING_PHONE, "2222-2222");
		PosNegListView posNegListView = fraudEngineDaoJpa.findFromView(fieldNameValue);
		assertEquals(FraudCheckConst.ACCEPT_STATUS, posNegListView.getStatus());
	}

}
