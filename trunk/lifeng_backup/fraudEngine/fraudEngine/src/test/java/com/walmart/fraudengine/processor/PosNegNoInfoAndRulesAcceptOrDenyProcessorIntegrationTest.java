/**
 * 
 */
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

import com.walmart.fraudengine.jaxb.oms.request.Order;
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
public class PosNegNoInfoAndRulesAcceptOrDenyProcessorIntegrationTest {

	String posNegNolnfoAndRulesAcceptXml;
	String posNegNolnfoAndRulesDenyXml;
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
		posNegNolnfoAndRulesAcceptXml = ConvertTestXmlToString.convertTestXmltoString(this.getClass().getResource("/testfile/from_oms/noinfo_accept.xml").getPath());
		posNegNolnfoAndRulesDenyXml = ConvertTestXmlToString.convertTestXmltoString(this.getClass().getResource("/testfile/from_oms/noinfo_deny.xml").getPath());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testPosNegNoInfoAndRulesAcceptProcessor() throws Exception{
		fraudCheckProcessor.omsProcess(getMessage(posNegNolnfoAndRulesAcceptXml));
	}
	
	@Test
	public void testPosNegNoInfoAndRulesDenyProcessor() throws Exception{
		fraudCheckProcessor.omsProcess(getMessage(posNegNolnfoAndRulesDenyXml));
	}

	private Order getMessage(String xml) throws JMSException {
		Session session = jmsTemplate.getConnectionFactory().createConnection().createSession(true, 0); 
		TextMessage message = session.createTextMessage();
		message.setText(xml);
		return (Order)converter.fromMessage(message);
	}
	
}
