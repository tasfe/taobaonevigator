package com.walmart.fraudengine.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.walmart.fraudengine.BaseTestCase;
import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.web.form.BusRuleForm;
import com.walmart.fraudengine.web.processor.BusRuleProcessor;
import com.walmart.fraudengine.web.validation.ValidateErrors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:fraudEngineMessagingContext.xml","classpath:testFraudEnginePersistance.xml"})
public class BusRuleControllerTest extends BaseTestCase {

	@Autowired
	private BusRuleController busRuleController;

	@Mock
	private MockHttpServletRequest request;

	@Mock
	private MockHttpServletResponse response;

	@Mock
	private BusRuleProcessor busRuleProcessor;

	@Mock
	private BusRuleForm busRuleForm;

	@Mock
	private ValidateErrors errors;

	@Mock
	private HttpSession session;

	@Mock
	private ModelMap model;

	@Before
	public void setUp() {
		busRuleController.setBusRuleProcessor(busRuleProcessor);
	}

	@Test
	public void testShowBusRule() {
		ModelAndView mav = null;
		when(request.getSession()).thenReturn(session);
		try {
			mav = busRuleController.showBusRule(model, request, response);
		} catch (Exception e) {
			fail();
		}
		assertEquals("rule/listBusRules", mav.getViewName());
		verifyZeroInteractions(busRuleProcessor);
	}

	/**
	 * initialize a list of business rule form object.
	 * 
	 * @return
	 */
	private List<BusRuleForm> constructFormList() {
		int count = 5;
		List<BusRuleForm> formList = null;
		for (int i = 0; i < count; i++) {
			if (i == 0) {
				formList = new ArrayList<BusRuleForm>();
			}
			BusRuleForm form = new BusRuleForm();
			form.setRulePK(i + 1L);
			form.setDescription("description" + i);
			form.setPriority(i);
			formList.add(form);
		}
		return formList;	
	}

	@Test
	public void testListBusRules() {
		when(request.getParameter("page")).thenReturn("1");
		when(request.getParameter("max")).thenReturn("5");
		when(request.getParameter("sord")).thenReturn("desc");
		when(request.getParameter("sidx")).thenReturn("busRulePk");
		try {
			when(busRuleProcessor.getBusRuleCount()).thenReturn(6L);
		} catch (FraudEngineApplicationException e1) {
			fail();
		}
		try {
			when(
					busRuleProcessor.findBusRules(anyLong(), anyLong(),
							anyMapOf(String.class, String.class))).thenReturn(
					constructFormList());
		} catch (FraudEngineApplicationException e1) {
			fail();
		}
		String responseStr = null;
		try {
			responseStr = busRuleController.listBusRules(model, request,
					response);
		} catch (Exception e) {
			fail();
		}
		Assert.assertNotNull(responseStr);
		try {
			verify(busRuleProcessor).getBusRuleCount();
		} catch (FraudEngineApplicationException e) {
			fail();
		}
	}

	@Test
	public void testAddBusRuleValidateError() {

		when(busRuleForm.validate()).thenReturn(errors);
		when(errors.hasErrors()).thenReturn(true);
		ModelAndView mav = null;
		try {
			mav = busRuleController.addBusRule(model, request, response,
					busRuleForm);
		} catch (Exception e) {
			fail();
		}

		Assert.assertNotNull(mav);
		Assert.assertEquals("busRule/addBusRule", mav.getViewName());
	}

	@Test
	public void testAddBusRuleSuccess() {
		when(busRuleForm.validate()).thenReturn(errors);
		when(errors.hasErrors()).thenReturn(false);
		ModelAndView mav = null;
		try {
			mav = busRuleController.addBusRule(model, request, response,
					busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		try {
			verify(busRuleProcessor).addBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		Assert.assertNotNull(mav);
		Assert.assertEquals("redirect:listBusRule.html", mav.getViewName());

	}

	@Test
	public void testAddBusRuleFail() {

		ModelAndView mav = null;
		when(busRuleForm.validate()).thenReturn(errors);

		when(errors.hasErrors()).thenReturn(true);
		try {
			mav = busRuleController.addBusRule(model, request, response,
					busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		verify(model).addAttribute(anyString(), any());
		when(errors.hasErrors()).thenReturn(false);			
		verify(model).addAttribute(anyString(), any());
		Assert.assertNotNull(mav);
		Assert.assertEquals("busRule/addBusRule", mav.getViewName());
		verifyZeroInteractions(busRuleProcessor);
	}

	@Test
	public void testDeleteBusRulesSuccess() {

		when(request.getParameter("rulePKs")).thenReturn("");
		ModelAndView mav = null;
		try {
			mav = busRuleController.deleteBusRules(model, request, response);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		Assert.assertNotNull(mav);
		Assert.assertEquals("rule/listBusRules", mav.getViewName());
		try {
			verify(busRuleProcessor).deleteBusRules(anyString());
		} catch (FraudEngineApplicationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteBusRulesFail() {
		when(request.getParameter("rulePKs")).thenReturn("");
		try {
			when(busRuleProcessor.deleteBusRules(anyString()))
					.thenReturn(false);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		ModelMap model = new ModelMap();
		ModelAndView mav = null;
		try {
			mav = busRuleController.deleteBusRules(model, request, response);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		Assert.assertEquals("rule/listBusRules", mav.getViewName());
		try {
			verify(busRuleProcessor).deleteBusRules(anyString());
		} catch (FraudEngineApplicationException e) {
			fail();
		}
	}

	@Test
	public void testShowUpdateBusRulePkWrongFormat() {
		when(request.getParameter("rulePKs")).thenReturn("pk");
		ModelAndView mav = null;
		try {
			mav = busRuleController.showUpdateBusRule(model, request, response);
		} catch (Exception e) {
			Assert.assertNotNull(e);
		}
		verifyZeroInteractions(busRuleProcessor);	
	}

	@Test
	public void testShowUpdateBusRuleSuccess() {

		when(request.getParameter("rulePKs")).thenReturn("1");
		try {
			when(busRuleProcessor.findBusRule(anyLong())).thenReturn(
					busRuleForm);
		} catch (FraudEngineApplicationException e1) {
			fail();
		}
		ModelAndView mav = null;
		try {
			mav = busRuleController.showUpdateBusRule(model, request, response);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		verify(model).addAttribute(anyString(), any());
		Assert.assertNotNull(mav);
		Assert.assertEquals("rule/updateRule", mav.getViewName());
		try {
			verify(busRuleProcessor).findBusRule(anyLong());
		} catch (FraudEngineApplicationException e) {
			fail();
		}
	}

	@Test
	public void testShowUpdateBusRuleFailToFindRule() {

		when(request.getParameter("rulePKs")).thenReturn("1");
		try {
			when(busRuleProcessor.findBusRule(anyLong())).thenReturn(null);
		} catch (FraudEngineApplicationException e1) {
			fail();
		}

		ModelAndView mav = null;
		try {
			mav = busRuleController.showUpdateBusRule(model, request, response);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		Assert.assertEquals("rule/updateRule", mav.getViewName());
		try {
			verify(busRuleProcessor).findBusRule(anyLong());
		} catch (FraudEngineApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUpdateBusRuleValidateError() {
		when(busRuleForm.validate()).thenReturn(errors);
		when(errors.hasErrors()).thenReturn(true);
		ModelAndView mav = null;
		try {
			mav = busRuleController.updateBusRule(model, request, response,
					busRuleForm);
		} catch (Exception e) {
			fail();
		}
		Assert.assertNotNull(mav);
		Assert.assertEquals("busRule/updateBusRule", mav.getViewName());
		verifyZeroInteractions(busRuleProcessor);
	}

	@Test
	public void testUpdateBusRuleSuccess() {

		when(busRuleForm.validate()).thenReturn(errors);
		when(errors.hasErrors()).thenReturn(false);
		try {
			when(busRuleProcessor.updateBusRule(busRuleForm)).thenReturn(true);
		} catch (FraudEngineApplicationException e1) {
			fail();
		}

		ModelAndView mav = null;

		try {
			mav = busRuleController.updateBusRule(model, request, response,
					busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		verify(model).addAttribute(anyString(), any());
		Assert.assertNotNull(mav);
		Assert.assertEquals("rule/updateRule", mav.getViewName());
		try {
			verify(busRuleProcessor).updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
	}

	@Test
	public void testUpdateBusRuleFail() {

		when(busRuleForm.validate()).thenReturn(errors);
		when(errors.hasErrors()).thenReturn(false);
		ModelMap model = new ModelMap();
		ModelAndView mav = null;
		try {
			mav = busRuleController.updateBusRule(model, request, response,
					busRuleForm);
		} catch (Exception e) {
			fail();
		}
		try {
			verify(busRuleProcessor).updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
	}

}
