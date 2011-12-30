package com.walmart.fraudengine.web.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.fraudengine.web.form.BusRuleForm;
import com.walmart.fraudengine.web.util.ObjectConvertXmlHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
	"classpath:testFraudEnginePersistance.xml" })
public class ObjectConvertXmlHelperTest {
	
	@Autowired
	private ObjectConvertXmlHelper objectConvertXmlHelper;
	
	@Test
	public void testCreateObjectXml() throws IOException {
		List<BusRuleForm> list = new ArrayList<BusRuleForm>();
		BusRuleForm form = new BusRuleForm();
		form.setDescription("djkfasjdfh");
		form.setEndDate(new Timestamp(System.currentTimeMillis()));
		form.setStartDate(new Timestamp(System.currentTimeMillis()));
		form.setHit(1);
		form.setPriority(12);
		form.setStatus("aaaa<><><><><><aaannnnaaaaa");
		BusRuleForm form2 = new BusRuleForm();
		form2.setDescription("djkfsdfsdfsdfasjdfh");
		form2.setEndDate(new Timestamp(System.currentTimeMillis()));
		form2.setStartDate(new Timestamp(System.currentTimeMillis()));
		form2.setHit(1);
		form2.setPriority(112);
		form2.setStatus("aaaaa1212aaaaaaa");
		list.add(form);
		list.add(form2);
		String xml = objectConvertXmlHelper.createObjectXml(list, 100L, 50L);
		System.out.println(xml);
	}
}
