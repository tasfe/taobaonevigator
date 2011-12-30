package com.walmart.fraudengine.jms.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.processor.FraudCheckProcessor;

@Component("baseMessageConsumer")
public class BaseMessageConsumer implements MessageConsumer {

	@Autowired
	private FraudCheckProcessor fraudProcessor;

	@Override
	@Transactional
	public void receiveMessage(FraudReviewResponse message) {
		fraudProcessor.cscProcess(message);
	}

	@Override
	@Transactional
	public void receiveMessage(Order message) {
		fraudProcessor.omsProcess(message);
	}
}
