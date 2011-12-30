package com.walmart.fraudengine.processor;

import static com.walmart.fraudengine.util.FraudCheckConst.EXCLUDE_SOFT_DELETE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.dao.BusRuleDao;
import com.walmart.fraudengine.dao.FraudEngineDao;
import com.walmart.fraudengine.dao.QueryParameter;
import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.request.PaymentMethod;
import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRuleHitEntity;
import com.walmart.fraudengine.model.OrderDetailEntity;
import com.walmart.fraudengine.model.OrderStatusEntity;
import com.walmart.fraudengine.model.PosNegListEntity;
import com.walmart.fraudengine.model.PosNegListView;
import com.walmart.fraudengine.model.StatusLkEntity;
import com.walmart.fraudengine.rules.RuleOrderStatus;
import com.walmart.fraudengine.util.FraudCheckConst;

@Component
public class FraudCheckProcessor {

	@Autowired
	private MessageProcessor messageProcessor;

	@Autowired
	private PosNegRulesProcessor posNegRulesProcessor;

	@Autowired
	private RulesEngineProcessor rulesEngineProcessor;

	@Autowired
	private FraudEngineDao fraudEngineDaoJpa;

	@Autowired
	private BusRuleDao busRuleDaoJpa;

	/**
	 * Description:This method is a oms process.First,we get a order object by
	 * parsing the omsRequestXml.Then we check the order status,if it is
	 * cancel,just return. Else we'll parse the order into a
	 * OrderDetailEntity,then we check the PosNegList. We use negativeListFilter
	 * to check the order and we will get a posNegResult. If the posNegResult is
	 * null,we'll enter the rule engine. If passing the negativeListFilter,we'll
	 * use the result to set the order status of orderDetail object.Meanwhile
	 * we'll add the orderStatus into the orderStatusSet. Then we'll store the
	 * orderDetail into the DB.Finally,we create a xml response to the OMS.
	 * 
	 * @param order
	 * @throws FraudEngineApplicationException
	 */
	@Transactional
	public void omsProcess(Order order) {

		if (FraudCheckConst.CANCELLED_STATUS.equals(order.getStatus())) {
			cancelFraudCheck(order);
		} else {
			OrderDetailEntity orderDetailEntity = fetchOrderDetail(order.getOrderNo());

			if (orderDetailEntity == null) {

				orderDetailEntity = createOrderDetailEntity(order);
				orderDetailEntity = fraudEngineDaoJpa.create(orderDetailEntity);

				final PosNegListView posNegResult = posNegRulesProcessor.posNegRuleCheck(order);

				if (null == posNegResult) {
					RuleOrderStatus ruleOrderStatus = rulesEngineProcessor.rulesEngineCheck(order);

					StatusLkEntity statusLkEntity = fraudEngineDaoJpa.findStatusLk(ruleOrderStatus.getOrderScore());

					OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
					orderStatusEntity.setStatusLk(statusLkEntity);
					orderStatusEntity.setStatusReason(ruleOrderStatus.getReasonDesc());
					orderStatusEntity.setReasonCode(ruleOrderStatus.getReasonCode());
					String ruleId = ruleOrderStatus.getRuleId();
					if (!StringUtils.isEmpty(ruleId)) {
						orderStatusEntity.setBusRule(Long.parseLong(ruleId));
						updateBusRuleHit(Long.parseLong(ruleId));
					}
					orderDetailEntity.getOrderStatuses().add(orderStatusEntity);
					orderStatusEntity.setOrderDetail(orderDetailEntity);

					Long requestId = orderDetailEntity.getOrderDetailPk();
					if (FraudCheckConst.CHALLENGE_STATUS.equalsIgnoreCase(ruleOrderStatus.getOrderScore())) {
						messageProcessor.sendChallengeMessageToCsc(order, FraudCheckConst.CHALLENGE_STATUS,
								ruleOrderStatus.getReasonDesc(), requestId, ruleOrderStatus.getReasonCode());
					}
					messageProcessor.sendFraudCheckResponseToOms(order, ruleOrderStatus.getOrderScore(), orderDetailEntity
							.getOrderDetailPk().toString(), ruleOrderStatus.getReasonDesc());

				} else if (posNegResult.getStatus().equals(FraudCheckConst.ACCEPT_STATUS)
						|| posNegResult.getStatus().equals(FraudCheckConst.DENY_STATUS)
						|| posNegResult.getStatus().equals(FraudCheckConst.CHALLENGE_STATUS)) {

					PosNegListEntity posNegListEntity = fraudEngineDaoJpa.find(posNegResult.getPosNegListPk(), PosNegListEntity.class);

					OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
					orderStatusEntity.setPosNegListFk(posNegResult.getPosNegListPk());
					orderStatusEntity.setStatusLk(posNegListEntity.getPosNegReasonLk().getStatusLk());
					orderDetailEntity.getOrderStatuses().add(orderStatusEntity);
					orderStatusEntity.setOrderDetail(orderDetailEntity);

					if (posNegResult.getStatus().equals(FraudCheckConst.CHALLENGE_STATUS)) {
						messageProcessor.sendChallengeMessageToCsc(order, posNegResult.getStatus(), posNegResult.getReasonShortDesc(),
								orderDetailEntity.getOrderDetailPk(), posNegResult.getReasonCode());
					}
					messageProcessor.sendFraudCheckResponseToOms(order, posNegResult.getStatus(), orderDetailEntity.getOrderDetailPk()
							.toString(), posNegResult.getReasonShortDesc());
				}
			} else {
				// TODO - sprint 3 - send a duplicate message exception to OMS
			}

		}

	}

	/**
	 * Description:This method is used to send a xml response message to OMS by
	 * using the cscResponseXml.
	 * 
	 * @param fraudReviewResponse
	 */
	@Transactional
	public void cscProcess(FraudReviewResponse fraudReviewResponse) {
		if (!FraudCheckConst.CSC_CANCELLED.equalsIgnoreCase(fraudReviewResponse.getResponse().getAction())) {
			// TODO - sprint 3 - fix xsd
			Long orderDetailId = Long.valueOf(fraudReviewResponse.getResponse().getRequestId());
			OrderDetailEntity orderDetailEntity = fraudEngineDaoJpa.find(orderDetailId, OrderDetailEntity.class);
			Set<OrderStatusEntity> orderStatuses = orderDetailEntity.getOrderStatuses();
			for (OrderStatusEntity orderStatusEntity : orderStatuses) {
				if (FraudCheckConst.CANCELLED_STATUS.equalsIgnoreCase(orderStatusEntity.getStatusLk().getName()))
					return;
			}

			StatusLkEntity newStatusLkEntity = fraudEngineDaoJpa.findStatusLk(fraudReviewResponse.getResponse().getAction());
			OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
			orderStatusEntity.setStatusReason(fraudReviewResponse.getResponse().getAgentDescription());
			orderStatusEntity.setReasonCode(fraudReviewResponse.getResponse().getReasonCode());
			orderStatusEntity.setStatusLk(newStatusLkEntity);
			orderDetailEntity.getOrderStatuses().add(orderStatusEntity);
			orderStatusEntity.setOrderDetail(orderDetailEntity);

			messageProcessor.sendCscResponseToOms(fraudReviewResponse, orderDetailEntity);
		}
	}

	/**
	 * used when bus_rule has been hit , we should update the rule 's hit record
	 * this method will not be called directly , only used in rule engine
	 * order_status update
	 * 
	 * @param ruleId
	 * @return
	 */
	private void updateBusRuleHit(Long ruleId) {

		BusRuleHitEntity oldBusRuleHitEntity = busRuleDaoJpa.findBusRuleHitByRuleId(ruleId);

		if (oldBusRuleHitEntity == null) {
			// to add a new rule hit record
			BusRuleEntity busRuleEntity = fraudEngineDaoJpa.find(ruleId, BusRuleEntity.class);
			BusRuleHitEntity newBusRuleHitEntity = new BusRuleHitEntity();
			newBusRuleHitEntity.setBusRule(busRuleEntity);
			newBusRuleHitEntity.setHitCount(1L);
			busRuleDaoJpa.create(newBusRuleHitEntity);
		} else {
			Long hitCount = oldBusRuleHitEntity.getHitCount() + 1;
			oldBusRuleHitEntity.setHitCount(hitCount);
		}
	}

	private void cancelFraudCheck(Order order) {
		OrderDetailEntity orderDetailEntity = fetchOrderDetail(order.getOrderNo());
		if (orderDetailEntity == null) {
			orderDetailEntity = createOrderDetailEntity(order);

			OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
			StatusLkEntity statusLkEntity = fraudEngineDaoJpa.findStatusLk(FraudCheckConst.CANCELLED_STATUS);
			orderStatusEntity.setStatusLk(statusLkEntity);
			orderStatusEntity.setStatusReason(FraudCheckConst.CANCELLED_REASON);

			orderDetailEntity.getOrderStatuses().add(orderStatusEntity);
			orderStatusEntity.setOrderDetail(orderDetailEntity);

			fraudEngineDaoJpa.create(orderDetailEntity);
		} else {
			OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
			StatusLkEntity statusLkEntity = fraudEngineDaoJpa.findStatusLk(FraudCheckConst.CANCELLED_STATUS);
			orderStatusEntity.setStatusLk(statusLkEntity);
			orderStatusEntity.setStatusReason(FraudCheckConst.CANCELLED_REASON);

			orderDetailEntity.getOrderStatuses().add(orderStatusEntity);
			orderStatusEntity.setOrderDetail(orderDetailEntity);
		}

		messageProcessor.sendCancelMessageToCsc(order, orderDetailEntity.getOrderDetailPk());
	}

	private OrderDetailEntity fetchOrderDetail(String orderNo) {
		final Map<String, Object> parameters = QueryParameter.with("omsOrderId", orderNo).and("softDelete", EXCLUDE_SOFT_DELETE)
				.parameters();
		List<OrderDetailEntity> ordreDetails = fraudEngineDaoJpa.findByNamedQuery(OrderDetailEntity.class,
				OrderDetailEntity.BY_OMS_ORDER_ID, parameters);

		if (!ordreDetails.isEmpty()) {
			return ordreDetails.iterator().next();
		}
		return null;
	}

	private OrderDetailEntity createOrderDetailEntity(Order order) {
		OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
		orderDetailEntity.setCustomerEmail(order.getCustomerEMailID());
		orderDetailEntity.setCustomerId(order.getCustomerContactID());
		if (null != order.getExtn()) {
			orderDetailEntity.setIpAddress(order.getExtn().getCustomerIPAddr());
			orderDetailEntity.setTransactionId(order.getExtn().getFraudLastTransactionID());
		}
		if (null != order.getOrderLines()) {
			if (null != order.getOrderLines().getOrderLine())
				orderDetailEntity.setNumberOfLines(order.getOrderLines().getOrderLine().size());
		}
		orderDetailEntity.setOmsOrderId(order.getOrderNo());
		if (null != order.getPriceInfo()) {
			orderDetailEntity.setOrderAmount(new BigDecimal(order.getPriceInfo().getTotalAmount()));
		}
		orderDetailEntity.setOrderPriority(new BigDecimal(1));
		orderDetailEntity.setOrderReceivedDate(new Timestamp(System.currentTimeMillis()));
		orderDetailEntity.setOrderRetryCount(0);
		if (null != order.getPaymentMethods()) {
			List<PaymentMethod> paymentMethodList = order.getPaymentMethods().getPaymentMethod();
			for (PaymentMethod paymentMethod : paymentMethodList) {
				orderDetailEntity.setPaymentType(paymentMethod.getPaymentType());
			}
		}
		return orderDetailEntity;
	}
	

	public void setMessageProcessor(MessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	public void setPosNegRulesProcessor(PosNegRulesProcessor posNegRulesProcessor) {
		this.posNegRulesProcessor = posNegRulesProcessor;
	}

	public void setRulesEngineProcessor(RulesEngineProcessor rulesEngineProcessor) {
		this.rulesEngineProcessor = rulesEngineProcessor;
	}

	public void setFraudEngineDaoJpa(FraudEngineDao fraudEngineDaoJpa) {
		this.fraudEngineDaoJpa = fraudEngineDaoJpa;
	}

	public void setBusRuleDaoJpa(BusRuleDao busRuleDaoJpa) {
		this.busRuleDaoJpa = busRuleDaoJpa;
	}

}
