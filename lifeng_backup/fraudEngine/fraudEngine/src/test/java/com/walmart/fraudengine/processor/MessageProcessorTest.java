package com.walmart.fraudengine.processor;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.fraudengine.jaxb.csc.request.FraudReviewRequest;
import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jaxb.oms.request.Extn;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS.MetaInfo;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS.Response;
import com.walmart.fraudengine.jms.producer.MessageProducer;
import com.walmart.fraudengine.util.FraudCheckConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
public class MessageProcessorTest {
	String orderXmlfromOms;
	String cscResponseXml;
	@Autowired
	private MessageProducer cscMessageProducer;

	@Autowired
	private MessageProducer omsMessageProducer;

	@Before
	public void setUp() throws Exception {
		orderXmlfromOms = ConvertTestXmlToString.convertTestXmltoString(this.getClass().getResource("/testfile/from_oms/accept.xml")
				.getPath());
		cscResponseXml = ConvertTestXmlToString.convertTestXmltoString(this.getClass()
				.getResource("/testfile/from_csc/cscReviewResponse_Accept.xml").getPath());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOrderFromOmsXml() throws Exception {
		Order order = new Order();
		InputStream is = new ByteArrayInputStream(orderXmlfromOms.getBytes("UTF-8"));
		JAXBContext context = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.oms.request");
		Unmarshaller u = context.createUnmarshaller();
		order = (Order) u.unmarshal(is);
		assertNotNull(order);

	}

	@Test
	public void testGetCscResponseFromCscXml() throws Exception {
		FraudReviewResponse fraudReviewResponse = new FraudReviewResponse();
		InputStream is = new ByteArrayInputStream(cscResponseXml.getBytes("UTF-8"));
		JAXBContext context = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.csc.response");
		Unmarshaller u = context.createUnmarshaller();
		fraudReviewResponse = (FraudReviewResponse) u.unmarshal(is);
		assertNotNull(fraudReviewResponse);
	}

	@Test
	public void testCreateOmsRespXmlByPosNeg() throws Exception {
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction("CHALLENGE");
		response.setAgentId("SYSTEM"); // order status created by
		response.setDescription("CHALLENGE DESCRIPTION"); // order status reason
															// code
		response.setOrderId("100000000000");
		response.setRequestId("125"); // order detail pk
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		response.setTime(calendar); // create timestamp
		response.setTransactionId("transactionID");
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		try {
			JAXBContext jc = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.oms.response");
			Marshaller m = jc.createMarshaller();
			StringWriter writer = new StringWriter();
			m.marshal(fraudReviewResponseOMS, writer);
			assertNotNull(writer.toString());
		} catch (JAXBException e) {
			throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
		}
	}

	@Test
	public void testCreateOmsRespXmlByCscResp() throws Exception {
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction("ACCEPT");
		response.setAgentId("SYSTEM"); // order status created by
		response.setDescription("ACCEPT DESCRIPTION"); // order status reason
														// code
		response.setOrderId("200000000000");
		response.setRequestId("125"); // order detail pk
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		response.setTime(calendar); // create timestamp
		response.setTransactionId("transactionID");
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		try {
			JAXBContext jc = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.oms.response");
			Marshaller m = jc.createMarshaller();
			StringWriter writer = new StringWriter();
			m.marshal(fraudReviewResponseOMS, writer);
			assertNotNull(writer.toString());
		} catch (JAXBException e) {
			throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
		}
	}

	@Test
	public void testGetTransactionId() throws Exception {
		Order order = new Order();
		Extn extn = new Extn();
		extn.setCustomerAccountAge(25);
		extn.setCustomerIPAddr("1722122");
		extn.setVisitorNo("001");
		extn.setFraudLastTransactionID("0000001");
		order.setExtn(extn);
		assertNotNull(order.getExtn().getFraudLastTransactionID());
	}

	@Test
	public void testCreateCscRequestXml() throws Exception {
		FraudReviewRequest fraudReviewRequest = new FraudReviewRequest();
		FraudReviewRequest.Request request = new FraudReviewRequest.Request();
		FraudReviewRequest.MetaInfo metaInfo = new FraudReviewRequest.MetaInfo();
		request.setOrderId("13761999986");
		request.setChallengeCode("CHALLANGE");
		request.setChallengeDescription("CHALLENGE DESCRIPTION");
		request.setRequestId("125");
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		request.setRequestTime(now);
		fraudReviewRequest.setRequest(request);
		metaInfo.setServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewRequest.setMetaInfo(metaInfo);
		try {
			JAXBContext jc = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.csc.request");
			Marshaller m = jc.createMarshaller();
			StringWriter writer = new StringWriter();
			m.marshal(fraudReviewRequest, writer);
			assertNotNull(writer.toString());
		} catch (JAXBException e) {
			throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
		}
	}

	@Test
	public void testCreateOmsAcceptRespBySwitchOff() throws Exception {
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction(FraudCheckConst.ACCEPT_STATUS);
		response.setAgentId(FraudCheckConst.CREATE_BY); // order status created
														// by
		response.setDescription(FraudCheckConst.ACCEPT_REASON_BY_SWITCH_OFF); // order
																				// status
																				// reason
																				// code
		response.setOrderId("200000000000");
		response.setRequestId("125"); // order detail pk
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		response.setTime(calendar); // create timestamp
		response.setTransactionId("transactionID");
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		try {
			JAXBContext jc = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.oms.response");
			Marshaller m = jc.createMarshaller();
			StringWriter writer = new StringWriter();
			m.marshal(fraudReviewResponseOMS, writer);
			assertNotNull(writer.toString());
		} catch (JAXBException e) {
			throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
		}
	}

	@Test
	public void testCreateErrorMessageResponseToOms() throws Exception {
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction(FraudCheckConst.ERROR_MESSAGE);
		response.setAgentId(FraudCheckConst.CREATE_BY); // order status created
														// by
		response.setDescription(FraudCheckConst.NULL_VALUE); // order status
																// reason code
		response.setOrderId("300000000000");
		response.setRequestId("125"); // order detail pk
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		response.setTime(calendar); // create timestamp
		response.setTransactionId("transactionID");
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		try {
			JAXBContext jc = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.oms.response");
			Marshaller m = jc.createMarshaller();
			StringWriter writer = new StringWriter();
			m.marshal(fraudReviewResponseOMS, writer);
			assertNotNull(writer.toString());
		} catch (JAXBException e) {
			throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
		}
	}

	@Test
	public void testCreateCscCancelXml() throws Exception {
		FraudReviewRequest fraudReviewRequest = new FraudReviewRequest();
		FraudReviewRequest.Request request = new FraudReviewRequest.Request();
		FraudReviewRequest.MetaInfo metaInfo = new FraudReviewRequest.MetaInfo();
		request.setOrderId("400000000");
		request.setChallengeCode(FraudCheckConst.CANCELLED_STATUS);
		request.setChallengeDescription("");
		request.setRequestId("125");
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		request.setRequestTime(now);
		fraudReviewRequest.setRequest(request);
		metaInfo.setServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewRequest.setMetaInfo(metaInfo);
		try {
			JAXBContext jc = JAXBContext.newInstance("com.walmart.fraudengine.jaxb.csc.request");
			Marshaller m = jc.createMarshaller();
			StringWriter writer = new StringWriter();
			m.marshal(fraudReviewRequest, writer);
			assertNotNull(writer.toString());
		} catch (JAXBException e) {
			throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
		}
	}

	@Test
	public void testSendChallengeMessageToCsc() throws Exception {
		FraudReviewRequest cscRequest = new FraudReviewRequest();
		cscMessageProducer.sendMessage(cscRequest);
	}

	@Test
	public void testSendFraudCheckResponseToOms() throws Exception {
		FraudReviewResponseOMS orderResponse = new FraudReviewResponseOMS();
		omsMessageProducer.sendMessage(orderResponse);
	}

	@Test
	public void testSendAcceptResponseBySwitchOffToOms() throws Exception {
		FraudReviewResponseOMS orderResponse = new FraudReviewResponseOMS();
		omsMessageProducer.sendMessage(orderResponse);
	}

	@Test
	public void testSendErrorMessageToOms() throws Exception {
		FraudReviewResponseOMS orderResponse = new FraudReviewResponseOMS();
		omsMessageProducer.sendMessage(orderResponse);
	}

	@Test
	public void testSendCscResponseToOms() throws Exception {
		FraudReviewResponseOMS orderResponse = new FraudReviewResponseOMS();
		omsMessageProducer.sendMessage(orderResponse);
	}

}
