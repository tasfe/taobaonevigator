package com.walmart.fraudengine.jms.consumer;

import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS;

public interface MessageConsumer {
	void receiveMessage(final FraudReviewResponse message);
	void receiveMessage(final Order message) ;
}
