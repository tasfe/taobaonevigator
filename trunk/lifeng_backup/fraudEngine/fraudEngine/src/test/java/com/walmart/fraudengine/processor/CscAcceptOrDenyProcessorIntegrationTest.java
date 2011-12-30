package com.walmart.fraudengine.processor;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jms.JaxbMessageConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
@TransactionConfiguration(transactionManager = "testjpaTxManager", defaultRollback = true)
@Transactional
public class CscAcceptOrDenyProcessorIntegrationTest {
	@Autowired
	@Qualifier("fraudCheckProcessor")
	private FraudCheckProcessor fraudCheckProcessor;

	@Autowired
	@Qualifier("jaxbMessageConverter")
	private JaxbMessageConverter jaxbMessageConverter;

	@Autowired
	private JmsTemplate jmsTemplate;
	/**
	 * @throws java.lang.Exception
	 */
	String cscAcceptXml;
	String cscDenyXml;

	@Before
	public void setUp() throws Exception {
		// load cscReviewResponse_Accept.xml file
		cscAcceptXml = ConvertTestXmlToString.convertTestXmltoString(this.getClass()
				.getResource("/testfile/from_csc/cscReviewResponse_Accept.xml").getPath());
		cscDenyXml = ConvertTestXmlToString.convertTestXmltoString(this.getClass()
				.getResource("/testfile/from_csc/cscReviewResponse_Deny.xml").getPath());

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testCscAcceptProcessor() throws Exception {
		TextMessage message = getMessage(cscAcceptXml);
		fraudCheckProcessor.cscProcess((FraudReviewResponse) jaxbMessageConverter.fromMessage(message));
	}

	@Test
	public void testCscDenyProcessor() throws Exception {
		TextMessage message = getMessage(cscDenyXml);
		fraudCheckProcessor.cscProcess((FraudReviewResponse) jaxbMessageConverter.fromMessage(message));
	}

	private TextMessage getMessage(String xml) throws JMSException {
		Session session = jmsTemplate.getConnectionFactory().createConnection().createSession(true, 0);
		TextMessage message = session.createTextMessage();
		message.setText(xml);
		return message;
	}

}
