package com.walmart.fraudengine.processor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.jaxb.oms.request.Extn;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.request.PaymentMethod;
import com.walmart.fraudengine.jaxb.oms.request.PaymentMethods;
import com.walmart.fraudengine.jaxb.oms.request.PersonInfoBillTo;
import com.walmart.fraudengine.jaxb.oms.request.PersonInfoShipTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
@TransactionConfiguration(transactionManager = "testjpaTxManager", defaultRollback = false)
@Transactional
public class NegativeListFilterProcessorTest {

	@Autowired
	private PosNegRulesProcessor negativeListFilterService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFilterNegativeList() throws Exception {
		Order order = new Order();
		PersonInfoBillTo personInfoBill = new PersonInfoBillTo();
		personInfoBill.setAddressLine1("test");
		personInfoBill.setAddressLine3("test again");
		PersonInfoShipTo personInfoShip = new PersonInfoShipTo();
		personInfoShip.setMobilePhone("1367");
		personInfoShip.setEMailID("walmart@test");
		personInfoShip.setAddressLine1("test");
		PaymentMethod method = new PaymentMethod();
		method.setPaymentType("COD");
		PaymentMethods methods = new PaymentMethods();
		methods.getPaymentMethod().add(method);
		Extn extn = new Extn();
		extn.setCustomerAccountAge(15);
		order.setPaymentMethods(methods);
		order.setExtn(extn);
		order.setPersonInfoBillTo(personInfoBill);
		order.setPersonInfoShipTo(personInfoShip);
		negativeListFilterService.posNegRuleCheck(order);
	}

}
