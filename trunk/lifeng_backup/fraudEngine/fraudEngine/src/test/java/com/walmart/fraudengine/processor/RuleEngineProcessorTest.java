package com.walmart.fraudengine.processor;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.fraudengine.BaseTestCase;
import com.walmart.fraudengine.jaxb.oms.request.Extn;
import com.walmart.fraudengine.jaxb.oms.request.Item;
import com.walmart.fraudengine.jaxb.oms.request.LineCharge;
import com.walmart.fraudengine.jaxb.oms.request.LineCharges;
import com.walmart.fraudengine.jaxb.oms.request.LinePriceInfo;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.request.OrderDate;
import com.walmart.fraudengine.jaxb.oms.request.OrderDates;
import com.walmart.fraudengine.jaxb.oms.request.OrderLine;
import com.walmart.fraudengine.jaxb.oms.request.OrderLines;
import com.walmart.fraudengine.jaxb.oms.request.PaymentDetails;
import com.walmart.fraudengine.jaxb.oms.request.PaymentMethod;
import com.walmart.fraudengine.jaxb.oms.request.PaymentMethods;
import com.walmart.fraudengine.jaxb.oms.request.PersonInfoBillTo;
import com.walmart.fraudengine.jaxb.oms.request.PersonInfoShipTo;
import com.walmart.fraudengine.jaxb.oms.request.PriceInfo;
import com.walmart.fraudengine.rules.RuleOrderStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:fraudEngineMessagingContext.xml","classpath:testFraudEnginePersistance.xml"})
public class RuleEngineProcessorTest extends BaseTestCase {

	@Autowired
	RulesEngineProcessor ruleEngineProcessor;

	// RulesEngineProcessor ruleEngineProcessor = new RulesEngineProcessor();

	@Test
	public void testExecuteFailToBuildKnowledgeBase() {
		// prepare
		this.initOrder();

		// can not mock EngineManager, this part will be modified.
		// when(EngineManager.getKnowledgeBase()).thenReturn(null);

		// execute
		RuleOrderStatus ruleOrderStatus = null;

		ruleOrderStatus = ruleEngineProcessor.rulesEngineCheck(initOrder());
		/*
		 * ruleOrderStatus will not be null. because can not simulate the
		 * exception
		 */
		// assert
		// Assert.assertNull(ruleOrderStatus);
	}

	@Test
	public void testExecuteSucess() {

		this.initOrder();

		// execute
		RuleOrderStatus ruleOrderStatus = null;

		ruleOrderStatus = ruleEngineProcessor.rulesEngineCheck(initOrder());
		// assert
		Assert.assertNotNull(ruleOrderStatus);
		Assert.assertEquals("00001140818", ruleOrderStatus.getOrderNo());
		Assert.assertEquals("ACCEPT", ruleOrderStatus.getOrderScore());
		Assert.assertEquals("2000", ruleOrderStatus.getReasonCode());
		Assert.assertEquals(null, ruleOrderStatus.getTransId());
	}

	private Order initOrder() {
		Extn extn = new Extn();
		extn.setCustomerAccountAge(1);
		extn.setCustomerIPAddr("172.36.22.01");
		extn.setFraudLastTransactionID("FRAUD_CHECK_TEST.01.exx0824");
		extn.setVisitorNo("000001");

		OrderDates orderDates = new OrderDates();
		OrderDate orderDate = new OrderDate();
		orderDate.setActualDate("2011-07-27T07:05:14-04:00");
		orderDate.setDateTypeId("DT001");
		orderDates.getOrderDate().add(orderDate);

		PersonInfoShipTo personInfoShipTo = new PersonInfoShipTo();
		personInfoShipTo.setAddressLine1("no123");
		personInfoShipTo.setAddressLine2("ChangNin Rd");
		personInfoShipTo.setAddressLine3("ChangNin");
		personInfoShipTo.setAddressLine4("ShangHai");
		personInfoShipTo.setAddressLine5("China");
		personInfoShipTo.setCity("shanghai");
		personInfoShipTo.setCompany("Microsoft.crp");
		personInfoShipTo.setCountry("China");
		personInfoShipTo.setDayPhone("13111111111");
		personInfoShipTo.setEMailID("michael.cash@microsoft.com");
		personInfoShipTo.setEveningPhone("");
		personInfoShipTo.setFirstName("Michael");
		personInfoShipTo.setLastName("Cash");
		personInfoShipTo.setMiddleName("");
		personInfoShipTo.setMobilePhone("13555555555");
		personInfoShipTo.setState("ShangHai");
		personInfoShipTo.setZipCode("200000");

		Item item = new Item();
		item.setCostCurrency("RMB");
		item.setItemCategory("electronic");
		item.setItemDesc("AKG earphone K420");
		item.setItemID("20110728000001");
		item.setUnitCost(5.00);
		item.setUnitOfMeasure("EACH");

		LineCharges lineCharges = new LineCharges();
		LineCharge lineCharge = new LineCharge();
		lineCharge.setChargeCategory("Wrap");
		lineCharge.setChargeName("WrapFeeOfWalmart");
		lineCharge.setChargePerLine(0.0);
		lineCharge.setChargePerUnit(0.0);
		lineCharge.setReference("we just need to charge it.");

		LinePriceInfo linePriceInfo = new LinePriceInfo();
		linePriceInfo.setLineTotal(5.00);
		linePriceInfo.setUnitPrice(5.00);

		OrderLines orderLines = new OrderLines();
		OrderLine orderLine = new OrderLine();
		orderLine.setItem(item);
		orderLine.setLineCharges(lineCharges);
		orderLine.setLinePriceInfo(linePriceInfo);
		orderLine.setOrderedQty(1.0);
		orderLine.setPersonInfoShipTo(personInfoShipTo);
		orderLine.setPrimeLineNo(1);
		orderLine.setSubLineNo(1);
		orderLines.getOrderLine().add(orderLine);

		PersonInfoBillTo personInfoBillTo = new PersonInfoBillTo();
		personInfoBillTo.setAddressLine1("no123");
		personInfoBillTo.setAddressLine2("ChangNin Rd");
		personInfoBillTo.setAddressLine3("ChangNin");
		personInfoBillTo.setAddressLine4("ShangHai");
		personInfoBillTo.setAddressLine5("China");
		personInfoBillTo.setCity("shanghai");
		personInfoBillTo.setCompany("Microsoft.crp");
		personInfoBillTo.setCountry("China");
		personInfoBillTo.setDayPhone("13111111111");
		personInfoBillTo.setEMailID("michael.cash@microsoft.com");
		personInfoBillTo.setEveningPhone("");
		personInfoBillTo.setFirstName("Michael");
		personInfoBillTo.setLastName("Cash");
		personInfoBillTo.setMiddleName("");
		personInfoBillTo.setMobilePhone("13555555555");
		personInfoBillTo.setState("ShangHai");
		personInfoBillTo.setZipCode("200000");

		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setAuditTransactionId("alipay00001");
		paymentDetails.setChargeType("AUTHORIZATION");
		paymentDetails.setProcessedAmount(0.0);
		paymentDetails.setRequestProcessed("N");

		PaymentMethods paymentMethods = new PaymentMethods();
		PaymentMethod pm = new PaymentMethod();
		pm.setCustomerAccountNo("ezeng_alipay");
		pm.setFirstName("Michael");
		pm.setLastName("Cash");
		pm.setPaymentDetails(paymentDetails);
		pm.setPaymentType("AUTHORIZATION");
		pm.setPersonInfoBillTo(personInfoBillTo);
		paymentMethods.getPaymentMethod().add(pm);

		PriceInfo priceInfo = new PriceInfo();
		priceInfo.setTotalAmount(0.0);

		Order order = new Order();
		order.setCustomerContactID("micheal li");
		order.setCustomerEMailID("michael.cash@microsoft.com");
		order.setDocumentType("0001");
		order.setEnterpriseCode("95588");
		order.setEntryType("ESTORE");
		order.setExtn(extn);
		order.setOrderDate("2011-07-27T07:05:14-04:00");
		order.setOrderDates(orderDates);
		order.setOrderLines(orderLines);
		order.setOrderNo("00001140818");
		order.setOrderType("ALIPAY");
		order.setPaymentMethods(paymentMethods);
		order.setPaymentStatus("AUTHORIZED");
		order.setPersonInfoBillTo(personInfoBillTo);
		order.setPriceInfo(priceInfo);
		order.setStatus("Created");

		return order;
	}

	/*
	 * <?xml version="1.0" encoding="UTF-8"?> <!-- Last modified by Rudy, hit by
	 * RuleEngine(accept amount less than 10) --> <!-- Sample XML file generated
	 * by XMLSpy v2010 rel. 3 sp1 (http://www.altova.com)--> <!-- Status can be
	 * as followed Created, Reserved, Cancelled and so on. PaymentStatus can be
	 * AUTHORIEZED, PAID, or NOT_APPLICABLE. Date in Sterling is in GMT format.
	 * --> <Order Status="Created" OrderDate="2011-07-27T07:05:14-04:00"
	 * EntryType="ESTORE" OrderType="ALIPAY" DocumentType="0001"
	 * OrderNo="00001140818" CustomerEMailID="michael.cash@microsoft.com"
	 * PaymentStatus="AUTHORIZED" EnterpriseCode="95588"
	 * CustomerContactID="micheal li"
	 * xsi:noNamespaceSchemaLocation="FraudOrder.xsd"
	 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> <Extn
	 * FraudLastTransactionID="FRAUD_CHECK_TEST.01.exx0824" VisitorNo="000001"
	 * CustomerIPAddr="172.36.22.01" CustomerAccountAge="1"/> <!-- amount is a
	 * double --> <PriceInfo TotalAmount="5.00"/> <OrderLines> <!-- OrderedQty
	 * is a double, for cancelled line, it should be 0.00 --> <OrderLine
	 * SubLineNo="1" OrderedQty="1.00" PrimeLineNo="1"> <Item
	 * ItemCategory="electronic" CostCurrency="RMB" UnitCost="5.00"
	 * ItemID="20110728000001" UnitOfMeasure="EACH"
	 * ItemDesc="AKG earphone K420"/> <PersonInfoShipTo Country="China"
	 * MiddleName="" FirstName="Michael" MobilePhone="13555555555"
	 * AddressLine1="no123" EveningPhone="13666666666" City="shanghai"
	 * AddressLine2="ChangNin Rd" ZipCode="200000" LastName="Cash"
	 * EMailID="michael.cash@microsoft.com" AddressLine3="ChangNin"
	 * AddressLine4="ShangHai" DayPhone="13111111111" AddressLine5="China"
	 * State="ShangHai"/> <!--
	 * LineTotal=(UnitPrice+ChargePerUnit)*10+ChargePerLine --> <LinePriceInfo
	 * UnitPrice="5.00" LineTotal="5.00"/> <LineCharges> <LineCharge
	 * Reference="we just need to charge it." ChargePerUnit="0"
	 * ChargeName="WrapFeeOfWalmart" ChargeCategory="Wrap" ChargePerLine="0"/>
	 * </LineCharges> </OrderLine> </OrderLines> <PersonInfoShipTo
	 * Country="China" Company="walmart" MiddleName="" FirstName="Michael"
	 * MobilePhone="13555555555" AddressLine1="no123" EveningPhone="13666666666"
	 * City="shanghai" AddressLine2="ChangNin Rd" ZipCode="200000"
	 * LastName="Cash" EMailID="michael.cash@microsoft.com"
	 * AddressLine3="ChangNin" AddressLine4="ShangHai" DayPhone="13111111111"
	 * AddressLine5="China" State="ShangHai"/> <PersonInfoBillTo Country="China"
	 * Company="walmart" MiddleName="" FirstName="Michael"
	 * MobilePhone="13555555555" AddressLine1="no123" EveningPhone="13666666666"
	 * City="shanghai" AddressLine2="ChangNin Rd" ZipCode="200000"
	 * LastName="Cash" EMailID="michael.cash@microsoft.com"
	 * AddressLine3="ChangNin" AddressLine4="ShangHai" DayPhone="13111111111"
	 * AddressLine5="China" State="ShangHai"/> <PaymentMethods> <PaymentMethod
	 * CustomerAccountNo="ezeng_alipay" LastName="Cash" FirstName="Michael"
	 * PaymentType="ALIPAY"> <!-- ChargeType can be AUTHORIZATION or CHARGE -->
	 * <PaymentDetails ChargeType="AUTHORIZATION" RequestProcessed="N"
	 * ProcessedAmount="0" AuditTransactionId="alipay00001"/> <PersonInfoBillTo
	 * Country="China" MiddleName="" FirstName="Michael"
	 * MobilePhone="13555555555" AddressLine1="no123" EveningPhone="13666666666"
	 * City="shanghai" AddressLine2="ChangNin Rd" ZipCode="200000"
	 * LastName="Cash" EMailID="michael.cash@microsoft.com"
	 * AddressLine3="ChangNin" AddressLine4="ShangHai" DayPhone="13111111111"
	 * AddressLine5="China" State="ShangHai"/> </PaymentMethod>
	 * </PaymentMethods> <OrderDates> <OrderDate
	 * ActualDate="2011-07-27T07:05:14-04:00" DateTypeId="DT001"/> </OrderDates>
	 * </Order>
	 */

}
