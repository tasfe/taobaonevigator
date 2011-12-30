package com.walmart.fraudengine.rules;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.fraudengine.BaseTestCase;
import com.walmart.fraudengine.rules.engine.shell.EngineManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
public class EngineManagerTest extends BaseTestCase{

	
	@Before
	public void before() throws Exception{

	}
	
	@Test
	public void testReplaceTokens(){
		assertNotNull(EngineManager.getKnowledgeBase());
	}
	
	
}
