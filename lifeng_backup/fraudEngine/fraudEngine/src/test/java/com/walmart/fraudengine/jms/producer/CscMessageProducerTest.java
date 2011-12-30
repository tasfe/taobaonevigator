package com.walmart.fraudengine.jms.producer;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.jaxb.csc.request.FraudReviewRequest;
import com.walmart.fraudengine.util.FraudCheckConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
@TransactionConfiguration(transactionManager = "testjpaTxManager")
@Transactional
public class CscMessageProducerTest {
	@Autowired
	private CscMessageProducer cscMessageProducer;

	private FraudReviewRequest forCscReview;

	@Before
	public void setUp() {
		forCscReview = createCscChallenge();
	}

	@Test
	public void testCscChallenge() throws Exception {
		cscMessageProducer.sendMessage(forCscReview);
	}

	private FraudReviewRequest createCscChallenge() {
		FraudReviewRequest fraudReviewRequest = new FraudReviewRequest();
		FraudReviewRequest.Request request = new FraudReviewRequest.Request();
		FraudReviewRequest.MetaInfo metaInfo = new FraudReviewRequest.MetaInfo();
		request.setOrderId("1234OrderNumber");
		request.setScoreReference("CHALLENGE");
		request.setChallengeCode("40001");
		request.setChallengeDescription("Test message");
		request.setRequestId("12433");
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		request.setRequestTime(now);
		fraudReviewRequest.setRequest(request);
		metaInfo.setServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewRequest.setMetaInfo(metaInfo);
		return fraudReviewRequest;
	}
}
