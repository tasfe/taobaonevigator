package com.walmart.fraudengine.processor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.jaxb.csc.request.FraudReviewRequest;
import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jaxb.oms.request.Extn;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS.MetaInfo;
import com.walmart.fraudengine.jaxb.oms.response.FraudReviewResponseOMS.Response;
import com.walmart.fraudengine.jms.producer.MessageProducer;
import com.walmart.fraudengine.model.OrderDetailEntity;
import com.walmart.fraudengine.util.FraudCheckConst;

/**
 * TODO add comments and format codes.
 * 
 *
 */
@Component("messageProcessor")
public class MessageProcessor {

	@Autowired
	private MessageProducer cscMessageProducer;
	
	@Autowired
	private MessageProducer omsMessageProducer;
		
	private FraudReviewRequest createCscRequestXml(Order order, String status,
			String reasonDesc, Long requestId, String scoreReference){
		FraudReviewRequest fraudReviewRequest = new FraudReviewRequest();
		FraudReviewRequest.Request request = new FraudReviewRequest.Request();
		FraudReviewRequest.MetaInfo metaInfo = new FraudReviewRequest.MetaInfo();
		request.setOrderId(order.getOrderNo());
		request.setScoreReference(scoreReference);
		request.setChallengeCode(status);
		request.setChallengeDescription(reasonDesc);
		request.setRequestId(requestId.toString());
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		request.setRequestTime(now);
		fraudReviewRequest.setRequest(request);
		metaInfo.setServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewRequest.setMetaInfo(metaInfo);
		return fraudReviewRequest;
	}

	private FraudReviewResponseOMS createOmsResp(String orderNo, String status,
			String orderDetialPk, String transactionId,
			String statusReason){
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction(status);
		response.setAgentId(FraudCheckConst.CREATE_BY); // order status created
		response.setDescription(statusReason); // order status
		response.setOrderId(orderNo);
		response.setRequestId(orderDetialPk); // order detail pk
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		response.setTime(calendar); // create timestamp
		response.setTransactionId(transactionId);
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		return fraudReviewResponseOMS;
	}
	
	private FraudReviewResponseOMS createOmsAcceptRespBySwitchOff(OrderDetailEntity orderDetailEntity){
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction(FraudCheckConst.ACCEPT_STATUS);
		response.setAgentId(FraudCheckConst.CREATE_BY); // order status created
															// by
		response.setDescription(FraudCheckConst.ACCEPT_REASON_BY_SWITCH_OFF); // order status
																// reason code
		response.setOrderId(orderDetailEntity.getOmsOrderId());
		response.setRequestId(orderDetailEntity.getOrderDetailPk().toString()); // order detail pk
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		final Timestamp createdDate = orderDetailEntity.getCreatedDate();
		if ( createdDate != null )
		{
			calendar.setTime(createdDate);
			response.setTime(calendar); // create timestamp
		}
		response.setTransactionId(orderDetailEntity.getTransactionId());
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		return fraudReviewResponseOMS;
	}
	
	private FraudReviewRequest createCscCancelXml(Order order, String status, Long requestId){
		FraudReviewRequest fraudReviewRequest = new FraudReviewRequest();
		FraudReviewRequest.Request request = new FraudReviewRequest.Request();
		FraudReviewRequest.MetaInfo metaInfo = new FraudReviewRequest.MetaInfo();
		request.setOrderId(order.getOrderNo());
		request.setChallengeCode(status);
		request.setRequestId(requestId.toString());
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		request.setRequestTime(now);
		fraudReviewRequest.setRequest(request);
		metaInfo.setServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewRequest.setMetaInfo(metaInfo);
		return fraudReviewRequest;
	}
	
	private FraudReviewResponseOMS createOmsRespXmlByCscResp(
			FraudReviewResponse fraudReviewResponse,
			OrderDetailEntity orderDetailEntity) {

		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction(fraudReviewResponse.getResponse().getAction());
		response.setAgentId(fraudReviewResponse.getResponse().getAgentId());
		response.setDescription(fraudReviewResponse.getResponse()
				.getAgentDescription());
		response.setOrderId(fraudReviewResponse.getResponse().getOrderId());
		response.setRequestId(fraudReviewResponse.getResponse().getRequestId());
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		final Timestamp createdDate = orderDetailEntity.getLastModifiedDate();
		if ( createdDate != null )
		{
			calendar.setTime(createdDate);
			response.setTime(calendar); // create timestamp
		}
		response.setTransactionId(orderDetailEntity.getTransactionId());
		metaInfo.setFraudServerName(fraudReviewResponse.getMetaInfo()
				.getCSCServerName());
		fraudReviewResponseOMS.setResponse(response);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		return fraudReviewResponseOMS;
	}

	/**
	 * Description:This method is used to get TransactionId from the order.
	 * 
	 * @param <order>
	 *            Order
	 * @return <<return transactionId>>
	 */
	public String getTransactionId(Order order){
		String transactionId = null;
		Extn extn = order.getExtn();
		if (extn != null)
			transactionId = extn.getFraudLastTransactionID();
		return transactionId;
	}
	
	public void sendCancelMessageToCsc(Order order, Long requestId){
		FraudReviewRequest cscCancel = createCscCancelXml(order, FraudCheckConst.CANCELLED_STATUS, requestId);
		cscMessageProducer.sendMessage(cscCancel);
	}
	
	public void sendChallengeMessageToCsc(Order order, String status,
			String reasonDesc, Long requestId, String scoreReference) {
		FraudReviewRequest cscRequest = createCscRequestXml(order,
				FraudCheckConst.CHALLENGE_STATUS, reasonDesc, requestId,
				scoreReference);
		cscMessageProducer.sendMessage(cscRequest);
	}
	
	public void sendErrorMessageToCsc(FraudReviewResponse fraudReviewResponse, FraudEngineApplicationException e) {
		FraudReviewRequest fraudReviewRequest = new FraudReviewRequest();
		FraudReviewRequest.Request request = new FraudReviewRequest.Request();
		FraudReviewRequest.MetaInfo metaInfo = new FraudReviewRequest.MetaInfo();
		request.setOrderId(fraudReviewResponse.getResponse().getOrderId());
		request.setScoreReference(FraudCheckConst.NULL_VALUE);
		request.setChallengeCode(FraudCheckConst.ERROR_MESSAGE);
		request.setChallengeDescription(FraudCheckConst.NULL_VALUE);
		request.setRequestId(fraudReviewResponse.getResponse().getRequestId());
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		request.setRequestTime(now);
		fraudReviewRequest.setRequest(request);
		metaInfo.setServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewRequest.setMetaInfo(metaInfo);
		cscMessageProducer.sendMessage(fraudReviewRequest);
	}
	
	public void sendFraudCheckResponseToOms(Order order, String status,
			String orderDetialPk,String statusReason) {
		FraudReviewResponseOMS orderResponse = createOmsResp(order.getOrderNo(), status,
				orderDetialPk, getTransactionId(order), statusReason);
		omsMessageProducer.sendMessage(orderResponse);
	}
	
	public void sendAcceptResponseBySwitchOffToOms(OrderDetailEntity orderDetailEntity) {
		FraudReviewResponseOMS orderResponse = createOmsAcceptRespBySwitchOff(orderDetailEntity);
		omsMessageProducer.sendMessage(orderResponse);
	}
	
	public void sendErrorMessageToOms(Order order, FraudEngineApplicationException e) {
		FraudReviewResponseOMS fraudReviewResponseOMS = new FraudReviewResponseOMS();
		Response response = new Response();
		MetaInfo metaInfo = new MetaInfo();
		response.setAction(FraudCheckConst.ERROR_MESSAGE);
		response.setAgentId(FraudCheckConst.CREATE_BY); // order status created
		response.setDescription(e.getErrorCodeEnum()+""); // error code
		response.setOrderId(order.getOrderNo());
		response.setRequestId(FraudCheckConst.NULL_VALUE); // order detail pk
		Calendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		response.setTime(calendar); // create timestamp
		if(null != order.getExtn()){
		response.setTransactionId(order.getExtn().getFraudLastTransactionID());
		}
		metaInfo.setFraudServerName(FraudCheckConst.CSC_SERVER_NAME);
		fraudReviewResponseOMS.setMetaInfo(metaInfo);
		fraudReviewResponseOMS.setResponse(response);
		omsMessageProducer.sendMessage(fraudReviewResponseOMS);
	}
	
	public void sendCscResponseToOms(FraudReviewResponse fraudReviewResponse,
			OrderDetailEntity orderDetailEntity) {
		FraudReviewResponseOMS orderResponse = createOmsRespXmlByCscResp(
				fraudReviewResponse, orderDetailEntity);
		omsMessageProducer.sendMessage(orderResponse);
	}


	public MessageProducer getCscQueueSender() {
		return cscMessageProducer;
	}

	public void setCscQueueSender(MessageProducer cscQueueSender) {
		this.cscMessageProducer = cscQueueSender;
	}

	public MessageProducer getOmsQueueSender() {
		return omsMessageProducer;
	}

	public void setOmsQueueSender(MessageProducer omsQueueSender) {
		this.omsMessageProducer = omsQueueSender;
	}
}
