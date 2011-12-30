/**
 * 
 */
package com.walmart.fraudengine.processor;


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
import com.walmart.fraudengine.processor.FraudCheckProcessor;

/**
 * @author wchen
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:fraudEngineMessagingContext.xml","classpath:testFraudEnginePersistance.xml"})
@TransactionConfiguration(transactionManager = "testjpaTxManager", defaultRollback = true)
@Transactional
public class CscCancelProcessorIntegrationTest {
	String cscCancelXml;
	
	@Autowired
	@Qualifier("fraudCheckProcessor")
	private FraudCheckProcessor fraudCheckProcessor;
	
	@Autowired
	@Qualifier("jaxbMessageConverter")
	private JaxbMessageConverter converter;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	/**
	 * @throws java.lang.Exception
	 */
	
	@Before
	public void setUp() throws Exception {
		cscCancelXml = ConvertTestXmlToString.convertTestXmltoString(this.getClass().getResource("/testfile/from_csc/cscReviewResponse_Cancel.xml").getPath());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCscCancelProcessor()throws Exception{
		Session session = jmsTemplate.getConnectionFactory().createConnection().createSession(true, 0); 
		TextMessage message = session.createTextMessage();
		message.setText(cscCancelXml);
		fraudCheckProcessor.cscProcess((FraudReviewResponse) converter.fromMessage(message));
	}

}
