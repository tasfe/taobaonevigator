package com.walmart.fraudengine.web;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.fraudengine.jaxb.jpql.EntityMappings;
import com.walmart.fraudengine.jaxb.jquery.Row;
import com.walmart.fraudengine.jaxb.jquery.Rows;
import com.walmart.fraudengine.web.JaxbXmlConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
	"classpath:testFraudEnginePersistance.xml" })
public class JaxbXmlConverterTest {

	@Autowired
	private JaxbXmlConverter jaxbXmlConverter;

	private static final String TESTFILE_CORRECT_XML = "/META-INF/jpql/FraudEngineFormNativeQueries.xml";

	@Test
	public void testJaxbHttpResponseConvert() throws IOException {
		Rows rows = new Rows();
		rows.setRecords(1L);
		rows.setTotal(100D);
		Row row = new Row();
		row.setId("1");
		row.getCell().add("b");
		row.getCell().add("c");
		row.getCell().add("d");
		Row row2 = new Row();
		row2.setId("1");
		row2.getCell().add("b");
		row2.getCell().add("c");
		row2.getCell().add("d");
		rows.getRow().add(row);
		rows.getRow().add(row2);
		String xml = jaxbXmlConverter.createJqueryXml(rows);
		System.out.println(xml);
		Assert.assertNotNull(xml);
	}
	
	@Test
	public void testJpqlConvert() throws IOException {
		EntityMappings em = jaxbXmlConverter.convertJpql(TESTFILE_CORRECT_XML);
		Assert.assertTrue(em.getNamedQuery().size()>0);
	}
}
