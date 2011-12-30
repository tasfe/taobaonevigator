package com.walmart.fraudengine.jms.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public abstract class BaseMessageProducer implements MessageProducer {

	@Autowired 
	protected JmsTemplate jmsTemplate; 
	
	@Override
    public abstract void sendMessage(final Object targetObject);  

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
