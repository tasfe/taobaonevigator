package com.walmart.fraudengine.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.fraudengine.dao.FraudEngineDao;
import com.walmart.fraudengine.jaxb.oms.request.Extn;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.request.PersonInfoBillTo;
import com.walmart.fraudengine.jaxb.oms.request.PersonInfoShipTo;
import com.walmart.fraudengine.model.PosNegListView;
import com.walmart.fraudengine.util.FraudCheckConst;

@Component
public class PosNegRulesProcessor {
	
	@Autowired
	private FraudEngineDao fraudEngineDaoJpa;

	/**
	 * This function will check the Order attribute which define in the
	 * FIELD_TYPE_LK table and get the result.
	 * The rule priority :
	 * 		Accept > Challenge > Deny > No_Infomation 
	 * 
	 * Currently , we use the Database as the DataSource. 
	 * In the future, we will read Neg/Pos data from the CacheManager.  
	 * 
	 * @param order
	 * @return PosNegListView
	 */
	public PosNegListView posNegRuleCheck(Order order) {
			Map<String, String> fieldNameValue = new HashMap<String, String>();
			PersonInfoBillTo personInfoBill = order.getPersonInfoBillTo();
			Extn extn =  order.getExtn();
			PersonInfoShipTo personInfoShip = order.getPersonInfoShipTo();

			fieldNameValue.put(FraudCheckConst.CUSTOEMR_ID, order.getCustomerContactID());
			fieldNameValue.put(FraudCheckConst.CUSTOMER_EMAIL, order.getCustomerEMailID());
			fieldNameValue.put(FraudCheckConst.CUSTOMER_ADDRESS,getBillAddress(personInfoBill));
			fieldNameValue.put(FraudCheckConst.IP_ADDRESS,extn.getCustomerIPAddr());
			fieldNameValue.put(FraudCheckConst.SHIPPING_ADDRESS,getShipAddress(personInfoShip));
			fieldNameValue.put(FraudCheckConst.SHIPPING_PHONE,personInfoShip.getMobilePhone());
			fieldNameValue.put(FraudCheckConst.SHIPPING_PHONE,personInfoShip.getDayPhone());
			fieldNameValue.put(FraudCheckConst.SHIPPING_PHONE,personInfoShip.getEveningPhone());
			fieldNameValue.put(FraudCheckConst.SHIPPING_EMAIL,personInfoShip.getEMailID());
			
			return fraudEngineDaoJpa.findFromView(fieldNameValue);
	}

	/**
	 * 	abstract the concat String method 
	 */
	private String getShipAddress(PersonInfoShipTo personInfoShip) {
		StringBuilder shipAddress = new StringBuilder();
		if(personInfoShip.getAddressLine1()!=null)
			shipAddress.append(StringUtils.deleteWhitespace(personInfoShip.getAddressLine1()));
		if(personInfoShip.getAddressLine2()!=null)
			shipAddress.append(StringUtils.deleteWhitespace(personInfoShip.getAddressLine2()));
		if(personInfoShip.getAddressLine3()!=null)
			shipAddress.append(StringUtils.deleteWhitespace(personInfoShip.getAddressLine3()));
		if(personInfoShip.getAddressLine4()!=null)
			shipAddress.append(StringUtils.deleteWhitespace(personInfoShip.getAddressLine4()));
		if(personInfoShip.getAddressLine5()!=null)
			shipAddress.append(StringUtils.deleteWhitespace(personInfoShip.getAddressLine5()));
		return shipAddress.toString();
	}
	/**
	 * 	abstract the concat String method 
	 */
	private String getBillAddress(PersonInfoBillTo personInfoBill) {
		StringBuilder billAddress = new StringBuilder();
		if(personInfoBill.getAddressLine1()!=null)
			billAddress.append(StringUtils.deleteWhitespace(personInfoBill.getAddressLine1()));
		if(personInfoBill.getAddressLine2()!=null)
			billAddress.append(StringUtils.deleteWhitespace(personInfoBill.getAddressLine2()));
		if(personInfoBill.getAddressLine3()!=null)
			billAddress.append(StringUtils.deleteWhitespace(personInfoBill.getAddressLine3()));
		if(personInfoBill.getAddressLine4()!=null)
			billAddress.append(StringUtils.deleteWhitespace(personInfoBill.getAddressLine4()));
		if(personInfoBill.getAddressLine5()!=null)
			billAddress.append(StringUtils.deleteWhitespace(personInfoBill.getAddressLine5()));
		return billAddress.toString();
	}
	
    
    
}
