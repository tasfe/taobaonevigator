package com.walmart.fraudengine.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.walmart.fraudengine.jaxb.jpql.EntityMappings;
import com.walmart.fraudengine.jaxb.jquery.Rows;

@Component
public class JaxbXmlConverter  {
	
	@Autowired
	private Jaxb2Marshaller jaxb2Marshaller ;

	
	public String createJqueryXml(Rows rows) {
		StringWriter sr = new StringWriter();
		jaxb2Marshaller.marshal(rows,new StreamResult(sr));
		return sr.toString();
	}

	public EntityMappings convertJpql(String filePath) throws IOException {
		InputStream is = null;
		EntityMappings em = null;
		try{
			is = this.getClass().getResourceAsStream(filePath);
			em = (EntityMappings) jaxb2Marshaller.unmarshal(new StreamSource(is));
		}finally{
			if(is != null)
				is.close();
		}
		return em;
	}
	

}
