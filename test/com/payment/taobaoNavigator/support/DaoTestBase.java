package com.payment.taobaoNavigator.support;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * this class is designed for dao's unit test which shall initialize 
 * the database before each test case executed.
 * 
 * @author rudy.ding
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestExecutionListeners({ BeforeMethod.class, DependencyInjectionTestExecutionListener.class })
public class DaoTestBase {
	
}

class BeforeMethod implements TestExecutionListener {
	
	public static final Logger logger = Logger.getLogger(BeforeMethod.class);

	@Override
	public void afterTestClass(TestContext arg0) throws Exception {
	}

	@Override
	public void afterTestMethod(TestContext arg0) throws Exception {
	}

	@Override
	public void beforeTestClass(TestContext arg0) throws Exception {
	}

	@Override
	public void beforeTestMethod(TestContext con) throws Exception {
		
		String testClass = con.getTestClass().getSimpleName();
		logger.debug("database initialization class name: " + testClass);
		
		String testMethod = con.getTestMethod().getName();
		logger.debug("database initialization method name: " + testMethod);
		
		DBInitializer.doInit(testClass, testMethod);
	}

	@Override
	public void prepareTestInstance(TestContext arg0) throws Exception {
	}

}