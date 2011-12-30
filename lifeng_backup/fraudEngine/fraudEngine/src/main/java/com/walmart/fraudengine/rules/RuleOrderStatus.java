package com.walmart.fraudengine.rules;

import com.walmart.fraudengine.util.FraudCheckConst;

/**
 * Return object of the Rule Engine execution results.
 * 
 */
public class RuleOrderStatus {
	/** Order No. of the order to be scored */
	private String orderNo;

	/** Transaction ID of this order scoring session */
	private String transId;

	/** Order score generated by the Rule Engine with ACCEPT as default */
	private String orderScore = FraudCheckConst.ACCEPT_STATUS;
	
	/** Represents why this order is accepted, challenged or denied */
	private String reasonDesc = FraudCheckConst.NO_RULE_HIT_REASON_DESC;
	
	/** ID of the rule who gives the result */
	private String ruleId;
	
	private String reasonCode = FraudCheckConst.ACCEPT_REASON_CODE;

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public RuleOrderStatus() {
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getOrderScore() {
		return orderScore;
	}

	public void setOrderScore(String orderScore) {
		this.orderScore = orderScore;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

}
