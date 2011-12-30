package com.walmart.fraudengine.rules;

import java.util.List;

import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.request.PaymentMethod;

public class RuleEngineOrder {
	private String orderScore ;
	private Order originalOrder;

	private boolean hitRule = false;

	public RuleEngineOrder(Order order) {
		setOriginalOrder(order);
	}

	public double calculateAmountSubtotal() {
		double sum = 0;
		if(null != originalOrder.getPriceInfo()){
		sum = originalOrder.getPriceInfo().getTotalAmount();
		}
		return sum;
	}
	
	//currently , we don't support multiply payment method
	public String queryPaymentMethod(){
		List<PaymentMethod> paymentMethodList = originalOrder.getPaymentMethods().getPaymentMethod();
		if(paymentMethodList==null||paymentMethodList.isEmpty())return "";
		return paymentMethodList.iterator().next().getPaymentType();
	}

	public String getOrderScore() {
		return orderScore;
	}

	public boolean isHitRule() {
		return hitRule;
	}

	public void setHitRule(boolean hitRule) {
		this.hitRule = hitRule;
	}

	public void setOrderScore(String orderScore) {
		this.orderScore = orderScore;
	}

	public Order getOriginalOrder() {
		return originalOrder;
	}

	public void setOriginalOrder(Order originalOrder) {
		this.originalOrder = originalOrder;
	}
}
