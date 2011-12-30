package com.walmart.fraudengine.jms;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

//@Component("jaxbMessageConverter")
public class JaxbMessageConverter implements MessageConverter, InitializingBean {

	protected Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
	protected Map<String, ?> marshallerProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.support.converter.MessageConverter#fromMessage
	 * (javax.jms.Message)
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		Source source;
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			source = new StreamSource(new ByteArrayInputStream(textMessage
					.getText().getBytes()));
		} else {
			throw new MessageConversionException(
					"Unsupported JMS Message type "
							+ message.getClass()
							+ ", expected instance of TextMessage or BytesMessage for "
							+ message);
		}
		Object result = jaxb2Marshaller.unmarshal(source);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.support.converter.MessageConverter#toMessage(
	 * java.lang.Object, javax.jms.Session)
	 */
	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		StringWriter out = new StringWriter();
		jaxb2Marshaller.marshal(object, new StreamResult(out));

		// create TextMessage result
		String text = out.toString();
		TextMessage textMessage = session.createTextMessage(text);

		postProcessResponseMessage(textMessage);

		return textMessage;
	}

	protected void postProcessResponseMessage(TextMessage textMessage) {
	}

	public void setClassesToBeBound(Class<?>[] classesToBeBound) {
		jaxb2Marshaller.setClassesToBeBound(classesToBeBound);
	}

	/**
	 * Sets the <code>JAXBContext</code> properties. These
	 * implementation-specific properties will be set on the
	 * <code>JAXBContext</code>.
	 * 
	 * @see Jaxb2Marshaller#setJaxbContextProperties(Map)
	 */
	public void setJaxbContextProperties(Map<String, ?> jaxbContextProperties) {
		jaxb2Marshaller.setJaxbContextProperties(jaxbContextProperties);
	}

	/**
	 * Sets the JAXB <code>Marshaller</code> properties. These properties will
	 * be set on the underlying JAXB <code>Marshaller</code>, and allow for
	 * features such as indentation.
	 * 
	 * @param properties
	 *            the properties
	 * @see javax.xml.bind.Marshaller#setProperty(String,Object)
	 * @see javax.xml.bind.Marshaller#JAXB_ENCODING
	 * @see javax.xml.bind.Marshaller#JAXB_FORMATTED_OUTPUT
	 * @see javax.xml.bind.Marshaller#JAXB_NO_NAMESPACE_SCHEMA_LOCATION
	 * @see javax.xml.bind.Marshaller#JAXB_SCHEMA_LOCATION
	 * @see AbstractJaxbMarshaller#setMarshallerProperties(Map)
	 */
	public void setMarshallerProperties(Map<String, ?> properties) {
		jaxb2Marshaller.setMarshallerProperties(properties);
		this.marshallerProperties = properties;
	}

	/**
	 * Sets the schema resource to use for validation.
	 * 
	 * @see Jaxb2Marshaller#setSchema(Resource)
	 */
	public void setSchema(Resource schemaResource) {
		jaxb2Marshaller.setSchema(schemaResource);
	}

	/**
	 * Sets the schema language. Default is the W3C XML Schema:
	 * <code>http://www.w3.org/2001/XMLSchema"</code>.
	 * 
	 * @see XMLConstants#W3C_XML_SCHEMA_NS_URI
	 * @see XMLConstants#RELAXNG_NS_URI
	 * @see Jaxb2Marshaller#setSchemaLanguage(String)
	 */
	public void setSchemaLanguage(String schemaLanguage) {
		jaxb2Marshaller.setSchemaLanguage(schemaLanguage);
	}

	/**
	 * Sets the schema resources to use for validation.
	 * 
	 * @see Jaxb2Marshaller#setSchemas(Resource[])
	 */
	public void setSchemas(Resource[] schemaResources) {
		jaxb2Marshaller.setSchemas(schemaResources);
	}

	/**
	 * Sets the <code>Unmarshaller.Listener</code> to be registered with the
	 * JAXB <code>Unmarshaller</code>.
	 * 
	 * @see Jaxb2Marshaller#setUnmarshallerListener(javax.xml.bind.Unmarshaller.Listener)
	 */
	public void setUnmarshallerListener(
			javax.xml.bind.Unmarshaller.Listener unmarshallerListener) {
		jaxb2Marshaller.setUnmarshallerListener(unmarshallerListener);
	}

	/**
	 * Sets the JAXB <code>Unmarshaller</code> properties. These properties will
	 * be set on the underlying JAXB <code>Unmarshaller</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @see javax.xml.bind.Unmarshaller#setProperty(String,Object)
	 * @see AbstractJaxbMarshaller#setUnmarshallerProperties(Map)
	 */
	public void setUnmarshallerProperties(Map<String, ?> properties) {
		jaxb2Marshaller.setUnmarshallerProperties(properties);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.jaxb2Marshaller.afterPropertiesSet();
	}

}
