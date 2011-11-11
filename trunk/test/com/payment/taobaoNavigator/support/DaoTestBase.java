package com.payment.taobaoNavigator.support;

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
		System.out.println("database initialization class name: " + testClass);
		
		String testMethod = con.getTestMethod().getName();
		System.out.println("database initialization method name: " + testMethod);
		
		DBInitializer.doInit(testClass, testMethod);
	}

	@Override
	public void prepareTestInstance(TestContext arg0) throws Exception {
	}

}