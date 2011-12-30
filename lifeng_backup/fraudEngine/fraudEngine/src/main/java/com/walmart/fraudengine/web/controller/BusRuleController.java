package com.walmart.fraudengine.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.LogSupport;
import com.walmart.fraudengine.web.form.BusPropertyForm;
import com.walmart.fraudengine.web.form.BusRuleForm;
import com.walmart.fraudengine.web.processor.BusRuleProcessor;
import com.walmart.fraudengine.web.util.ObjectConvertXmlHelper;
import com.walmart.fraudengine.web.validation.ValidateErrors;

/**
 * The controller class dealing with the request 
 * for adding/listing/deleting/modifying operations regarding business rule
 */
@Controller
@Transactional
public class BusRuleController { //TODO the exception should be processed in a particular page or controller

	@Autowired
	private BusRuleProcessor busRuleProcessor;
	
	@Autowired
	private ObjectConvertXmlHelper objectConvertXmlHelper;
	
	/**
	 * show the bus_rule presentation page 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/showBusRule.html", method = RequestMethod.GET)
	public ModelAndView showBusRule(ModelMap model, HttpServletRequest request, 
			HttpServletResponse response) {
		//a session attribute to store the status list.
		Map<String, String> statusMap = new HashMap<String, String>();
		statusMap.put(FraudCheckConst.STATUS_ACTIVE, FraudCheckConst.STATUS_ACTIVE);
		statusMap.put(FraudCheckConst.STATUS_INACTIVE, FraudCheckConst.STATUS_INACTIVE);
		request.getSession().setAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_STATUSMAP, statusMap);
		return new ModelAndView("rule/listBusRules");
	}
	
	/**
	 * list all of the business rules in database.
	 * @param map
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @ 
	 * @throws FraudEngineApplicationException 
	 */
	@RequestMapping(value = "/listBusRule.html", method = RequestMethod.GET)
	public @ResponseBody String listBusRules(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		 try {
			 String strPage = request.getParameter(FraudCheckConst.WEB_PARAM_BUSRULE_PAGE);
			 String strMax = request.getParameter(FraudCheckConst.WEB_PARAM_BUSRULE_MAX);
			 // "asc" or "desc"
			 String sord = request.getParameter(FraudCheckConst.WEB_PARAM_BUSRULE_SORD);
			 // "grid_attrName" , please get the the attrName at first
			 String sidx = request.getParameter(FraudCheckConst.WEB_PARAM_BUSRULE_SORX);
			 if(sidx!=null)sidx = sidx.replace(FraudCheckConst.WEB_PARAM_BUSRULE_SORX_TOKEN, FraudCheckConst.WEB_PARAM_EMPTY_STRING);
			 String sort = FraudCheckConst.ORDER_DESC;
			 if(FraudCheckConst.ORDER_ASC.equalsIgnoreCase(sord)){
				 sort = FraudCheckConst.ORDER_ASC;
			 }
			 Map<String,String> sortMap = new HashMap<String,String>();
			 sortMap.put(sidx, sort);
	         
			 //get count of rules
			 final Long count = busRuleProcessor.getBusRuleCount();
	         model.addAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_COUNT, count);
	         
	         //get data
	         final long page = Long.parseLong(strPage);
			 final long max = Long.parseLong(strMax);
			 final long startIdx = (page - 1) * max;
			 List<BusRuleForm> rules = busRuleProcessor.findBusRules(startIdx, max,sortMap);
			 
			 //TODO _solution is in discussion
			 String jsonRules = objectConvertXmlHelper.createObjectXml(rules, count, max);
			 LogSupport.debug("rules content is " + jsonRules);
			 //TODO_ravindra_review_comments - use unmarshaller, refer - http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/mvc.html#mvc-ann-responsebody 
	         //TODO _rudy_comments -As I know, front end may use the json string in particular format. Gabriel may give you the details.
			 return jsonRules;
			 
         } catch (Exception e) {
        	 LogSupport.error("Exception occurs when getting the pagination data for listBusRules...", e);
        	 return null;
         }
	}
	
	/**
	 * get the bus_rule 's properties
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fetchProperty.html", method = RequestMethod.GET)
	public @ResponseBody String fetchProperty(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		 try {
			 String rulePk = request.getParameter(FraudCheckConst.WEB_PARAM_BUSRULE_ID);
			 List<BusPropertyForm> formList = busRuleProcessor.queryBusRuleProperty(Long.parseLong(rulePk));

			 //TODO _solution is in discussion
			 String jsonRules = objectConvertXmlHelper.createObjectXml(formList, formList.size(), formList.size());
			 LogSupport.debug("rules content is " + jsonRules);
			 //TODO_ravindra_review_comments - use unmarshaller, refer - http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/mvc.html#mvc-ann-responsebody 
	         //TODO _rudy_comments -As I know, front end may use the json string in particular format. Gabriel may give you the details.
			 return jsonRules;
         } catch (Exception e) {
        	 LogSupport.error("Exception occurs when fetching properties of business rule...", e);
        	 return null;
         }
	}
	
	
	/**
	 * initialize the attributes for adding a new business.
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addBusRule.html", method = RequestMethod.GET)
	public ModelAndView showAddBusRule(ModelMap model, HttpServletRequest request, 
			HttpServletResponse response) {
		//initialize a form for adding values.
		BusRuleForm busRuleForm = new BusRuleForm();
		model.addAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_FORM, busRuleForm);
		//go to adding business rule page.
		return new ModelAndView("busRule/addBusRule");
	}

	/**
	 * add a new business rule.
	 * @param model
	 * @param request
	 * @param response
	 * @param busRuleForm
	 * @return ModelAndView
	 * @ 
	 * @throws FraudEngineApplicationException 
	 */
	@RequestMapping(value = "/addBusRule.html", method = RequestMethod.POST)
	public ModelAndView addBusRule(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, BusRuleForm busRuleForm) throws FraudEngineApplicationException {
		ValidateErrors errors = busRuleForm.validate();
		if (errors.hasErrors()) {
			model.addAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_ERRORS, errors);
			LogSupport.debug("invalid input found in busRuleForm when doing backend validation." + errors);
			return new ModelAndView("busRule/addBusRule");
		}
		busRuleProcessor.addBusRule(busRuleForm);
		
		return new ModelAndView("redirect:listBusRule.html");		
	}
	
	/**
	 * delete multiple business rules
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws FraudEngineApplicationException 
	 * @ 
	 */
	@RequestMapping(value = "/deleteBusRules.html", method = RequestMethod.GET)
	public ModelAndView deleteBusRules(ModelMap model, HttpServletRequest request, 
			HttpServletResponse response) throws FraudEngineApplicationException  {

		String rulePKs = request.getParameter(FraudCheckConst.WEB_PARAM_BUSRULE_RULEPKS);
		boolean result = busRuleProcessor.deleteBusRules(rulePKs);
		if (result == true) {
			model.addAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_MESSAGE, FraudCheckConst.OPERATOR_SUCCESS);
		}
		return new ModelAndView("rule/listBusRules");
	}

	/**
	 * initialize the attributes for updating a business rule.
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 * @throws FraudEngineApplicationException 
	 */
	@RequestMapping(value = "/updateBusRule.html", method = RequestMethod.GET)
	public ModelAndView showUpdateBusRule(ModelMap model, HttpServletRequest request, 
			HttpServletResponse response) throws FraudEngineApplicationException {

		String ruleIdStr = request.getParameter(FraudCheckConst.WEB_PARAM_BUSRULE_RULEPKS);
		Long ruleId = null;
		ruleId = Long.parseLong(ruleIdStr);
		BusRuleForm busRuleForm = busRuleProcessor.findBusRule(ruleId);
		model.addAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_FORM, busRuleForm);
		return new ModelAndView("rule/updateRule");
	
	}

	/**
	 * update a business rule.
	 * @param model
	 * @param request
	 * @param response
	 * @param busRuleForm
	 * @return ModelAndView
	 * @throws FraudEngineApplicationException 
	 * @ 
	 */
	@RequestMapping(value = "/updateBusRule.html", method = RequestMethod.POST)
	public ModelAndView updateBusRule(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, BusRuleForm busRuleForm) throws FraudEngineApplicationException  {

		//validation
		//TODO _rudy_comment -validation need to be finished. we are now using self-defined validate method in form.
		ValidateErrors errors = busRuleForm.validate();
		if (errors.hasErrors()) {
			model.addAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_ERRORS, errors);
			LogSupport.debug("invalid input found in busRuleForm when doing backend validation." + errors);
			return new ModelAndView("busRule/updateBusRule");
		}
		//update the valid rule
		busRuleProcessor.updateBusRule(busRuleForm);
		model.addAttribute(FraudCheckConst.WEB_PARAM_BUSRULE_MESSAGE, FraudCheckConst.OPERATOR_SUCCESS);
		return new ModelAndView("rule/updateRule");
	}
	
	// add for mockito unit test. no use in business.
	public BusRuleProcessor getBusRuleProcessor() {
		return busRuleProcessor;
	}

	// add for mockito unit test. no use in business.
	public void setBusRuleProcessor(BusRuleProcessor busRuleProcessor) {
		this.busRuleProcessor = busRuleProcessor;
	}
}
