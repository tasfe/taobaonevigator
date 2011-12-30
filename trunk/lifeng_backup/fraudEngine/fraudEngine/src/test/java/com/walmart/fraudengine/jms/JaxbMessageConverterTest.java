package com.walmart.fraudengine.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS.MetaInfo;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS.Response;
import com.walmart.fraudengine.jms.JaxbMessageConverter;
import com.walmart.fraudengine.processor.ConvertTestXmlToString;
import com.walmart.fraudengine.util.FraudCheckConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
@TransactionConfiguration(transactionManager = "testjpaTxManager", defaultRollback = true)
@Transactional
public class JaxbMessageConverterTest {

	private static final String TESTFILE_FROM_OMS_ACCEPT_XML = "/testfile/from_oms/order_sample_for_converter.xml";
	private static final String TESTFILE_FROM_CSC_ACCEPT_XML = "/testfile/from_csc/cscReviewResponse_Accept.xml";

	@Autowired
	protected ConnectionFactory connectionFactory;
	@Autowired
	@Qualifier("jaxbMessageConverter")
	protected JaxbMessageConverter jaxbMessageConverter;
	protected Connection connection;
	protected Session session;

	@Before
	public void setUp() throws Exception {
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	@After
	public void after() throws Exception {
		JmsUtils.closeSession(session);
		JmsUtils.closeConnection(connection);
	}

	@Test
	public void testFromOmsMessage() throws Exception {

		final String path = this.getClass()
				.getResource(TESTFILE_FROM_OMS_ACCEPT_XML).getPath();
		final String xml = ConvertTestXmlToString.convertTestXmltoString(path);
		Message message = session.createTextMessage(xml);
		final Order actualOrder = (Order) jaxbMessageConverter
				.fromMessage(message);
		assertNotNull(actualOrder);
		assertEquals("FRAUD_CHECK_TEST.01.ex1", actualOrder.getExtn()
				.getFraudLastTransactionID());
	}

	@Test
	public void testFromCscMessage() throws Exception {

		final String path = this.getClass()
				.getResource(TESTFILE_FROM_CSC_ACCEPT_XML).getPath();
		final String xml = ConvertTestXmlToString.convertTestXmltoString(path);
		Message message = session.createTextMessage(xml);
		final FraudReviewResponse cscResponse = (FraudReviewResponse) jaxbMessageConverter
				.fromMessage(message);
		assertNotNull(cscResponse);
		assertEquals("ACCEPT", cscResponse.getResponse().getAction());
	}

	@Test(expected = MessageConversionException.class)
	public void testFromMessageException() throws Exception {
		Message message = session.createMapMessage();
		jaxbMessageConverter.fromMessage(message);
	}

	@Test
	public void testToMessage() throws MessageConversionException, JMSException {
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		response.setAction("ACCEPT");
		response.setAgentId("James Bond - 007");
		response.setOrderId("Order 1234");
		response.setRequestId("R1121");
		response.setTransactionId("TRAN_ID_1234");
		MetaInfo metaInfo = new MetaInfo();
		metaInfo.setFraudServerName("test server");
		response.setAction("ACCEPT");
		response.setAgentId("SYSTEM"); // order status created
										// by
		response.setDescription("ACCEPT IT!"); // order status
												// reason code
		response.setOrderId("12334");
		response.setRequestId("1223"); // order detail pk
		response.setTransactionId("TRAN_ID_121");
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		final TextMessage actualMessage = (TextMessage) jaxbMessageConverter
				.toMessage(fraudReviewResponseOMS, session);
		assertNotNull(actualMessage);
		System.out.println(actualMessage.getText());
		String exptectedMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><fraudReviewResponse xmlns:ns2=\"http://chinaGM.walmart.com/fraudreview\"><metaInfo><fraudServerName>FRAUD_MACHINE</fraudServerName></metaInfo><response requestId=\"1223\"><orderId>12334</orderId><transactionId>TRAN_ID_121</transactionId><agentId>SYSTEM</agentId><action>ACCEPT</action><time xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><description>ACCEPT IT!</description></response></fraudReviewResponse>";
		assertEquals(exptectedMessage, actualMessage.getText());
	}
}
