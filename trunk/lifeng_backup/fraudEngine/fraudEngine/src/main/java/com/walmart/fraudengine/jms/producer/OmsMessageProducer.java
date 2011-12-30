package com.walmart.fraudengine.jms.producer;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("omsMessageProducer")
public class OmsMessageProducer extends BaseMessageProducer {

	@Autowired
	@Qualifier("messageQueueToOms")
	protected Destination destination;

	@Override
	@Transactional
	public void sendMessage(Object targetObject) {
		this.jmsTemplate.convertAndSend(destination, targetObject);
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

}
