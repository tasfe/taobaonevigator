package com.walmart.fraudengine.jms.producer;


public interface MessageProducer {
	public void sendMessage(final Object targetObject);  
}
