package com.walmart.fraudengine.processor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.fraudengine.BaseTestCase;
import com.walmart.fraudengine.dao.BusRuleDaoJpa;
import com.walmart.fraudengine.dao.FraudEngineDao;
import com.walmart.fraudengine.dao.FraudEngineDaoJpa;
import com.walmart.fraudengine.jaxb.csc.response.FraudReviewResponse;
import com.walmart.fraudengine.jaxb.oms.request.Order;
import com.walmart.fraudengine.jms.JaxbMessageConverter;
import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRuleHitEntity;
import com.walmart.fraudengine.model.OrderDetailEntity;
import com.walmart.fraudengine.model.OrderStatusEntity;
import com.walmart.fraudengine.model.PosNegListEntity;
import com.walmart.fraudengine.model.PosNegListView;
import com.walmart.fraudengine.model.PosNegReasonLkEntity;
import com.walmart.fraudengine.model.StatusLkEntity;
import com.walmart.fraudengine.rules.RuleOrderStatus;
import com.walmart.fraudengine.util.FraudCheckConst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
public class FraudCheckProcessorTest extends BaseTestCase {
	@Mock
	private Order order;
	@Mock
	private FraudReviewResponse fraudReviewResponse;
	@Mock
	private FraudReviewResponse.Response response;
	@Mock
	private OrderDetailEntity orderDetailEntity;
	@Mock
	private OrderStatusEntity orderStatusEntity;
	@Mock
	private BusRuleHitEntity busRuleHitEntity;
	@Mock
	private PosNegListView posNegResult;
	@Mock
	private PosNegListEntity posNegListEntity;
	@Mock
	private StatusLkEntity statusLkEntity;
	@Mock
	private FraudEngineDaoJpa fraudEngineDaoJpa;
	@Mock
	private FraudEngineDao fraudEngineDao;
	@Mock
	private BusRuleDaoJpa busRuleDaoJpa;
	@Mock
	private PosNegRulesProcessor posNegRulesProcessor;
	@Mock
	private RulesEngineProcessor rulesEngineProcessor;
	@Mock
	private RuleOrderStatus ruleOrderStatus;
	@Mock
	private MessageProcessor messageProcessor;
	@Mock
	private JaxbMessageConverter jaxbMessageConverter;
	@Mock
	private Message message;
	@Autowired
	private FraudCheckProcessor fraudCheckProcessor;

	@Test
	public void testOmsProcessCancelPostToCsc() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);
		when(jaxbMessageConverter.fromMessage(message)).thenReturn(order);
		when(order.getStatus()).thenReturn(FraudCheckConst.CANCELLED_STATUS);
		when(order.getOrderNo()).thenReturn("12345");
		final Map<String, Object> parameter = new HashMap<String, Object>();
		when(fraudEngineDao.findByNamedQuery(OrderDetailEntity.class, null, parameter)).thenReturn(null);
		when(fraudEngineDao.findStatusLk(FraudCheckConst.CANCELLED_STATUS)).thenReturn(statusLkEntity);
		when(fraudEngineDao.create(orderDetailEntity)).thenReturn(orderDetailEntity);
		fraudCheckProcessor.omsProcess(order);
		verify(messageProcessor).sendCancelMessageToCsc(order, null);

	}

	@Test
	public void testOmsProcessNoInfoRuleEngineAcceptPostToOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setPosNegRulesProcessor(posNegRulesProcessor);
		fraudCheckProcessor.setRulesEngineProcessor(rulesEngineProcessor);
		fraudCheckProcessor.setBusRuleDaoJpa(busRuleDaoJpa);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);

		when(jaxbMessageConverter.fromMessage(message)).thenReturn(order);
		when(order.getOrderNo()).thenReturn("12345");
		when(fraudEngineDao.create(any(OrderDetailEntity.class))).thenReturn(orderDetailEntity);
		when(posNegRulesProcessor.posNegRuleCheck(order)).thenReturn(null);
		when(rulesEngineProcessor.rulesEngineCheck(order)).thenReturn(ruleOrderStatus);
		when(fraudEngineDaoJpa.findStatusLk(anyString())).thenReturn(mock(StatusLkEntity.class));
		when(ruleOrderStatus.getRuleId()).thenReturn("10");
		when(orderDetailEntity.getOrderDetailPk()).thenReturn(12L);
		when(ruleOrderStatus.getOrderScore()).thenReturn(FraudCheckConst.ACCEPT_STATUS);
		when(busRuleDaoJpa.findBusRuleHitByRuleId(Long.parseLong(ruleOrderStatus.getRuleId()))).thenReturn(busRuleHitEntity);
		when(busRuleDaoJpa.create(busRuleHitEntity)).thenReturn(busRuleHitEntity);
		final BusRuleEntity busRuleEntity = mock(BusRuleEntity.class);
		when(fraudEngineDao.find(Long.parseLong(ruleOrderStatus.getRuleId()), BusRuleEntity.class)).thenReturn(busRuleEntity);

		fraudCheckProcessor.omsProcess(order);
		verify(messageProcessor).sendFraudCheckResponseToOms(order, FraudCheckConst.ACCEPT_STATUS, "12", null);
	}

	@Test
	public void testOmsProcessNoInfoRuleEngineDenyPostToOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setPosNegRulesProcessor(posNegRulesProcessor);
		fraudCheckProcessor.setRulesEngineProcessor(rulesEngineProcessor);
		fraudCheckProcessor.setBusRuleDaoJpa(busRuleDaoJpa);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);

		when(jaxbMessageConverter.fromMessage(message)).thenReturn(order);
		when(order.getOrderNo()).thenReturn("12345");
		when(fraudEngineDao.create(any(OrderDetailEntity.class))).thenReturn(orderDetailEntity);
		when(posNegRulesProcessor.posNegRuleCheck(order)).thenReturn(null);
		when(rulesEngineProcessor.rulesEngineCheck(order)).thenReturn(ruleOrderStatus);
		when(fraudEngineDaoJpa.findStatusLk(anyString())).thenReturn(mock(StatusLkEntity.class));
		when(ruleOrderStatus.getRuleId()).thenReturn("10");
		when(orderDetailEntity.getOrderDetailPk()).thenReturn(13L);
		when(ruleOrderStatus.getOrderScore()).thenReturn(FraudCheckConst.DENY_STATUS);
		when(busRuleDaoJpa.findBusRuleHitByRuleId(Long.parseLong(ruleOrderStatus.getRuleId()))).thenReturn(busRuleHitEntity);
		when(busRuleDaoJpa.create(busRuleHitEntity)).thenReturn(busRuleHitEntity);
		final BusRuleEntity busRuleEntity = mock(BusRuleEntity.class);
		when(fraudEngineDao.find(Long.parseLong(ruleOrderStatus.getRuleId()), BusRuleEntity.class)).thenReturn(busRuleEntity);

		fraudCheckProcessor.omsProcess(order);
		verify(messageProcessor).sendFraudCheckResponseToOms(order, FraudCheckConst.DENY_STATUS, "13", null);
	}

	@Test
	public void testOmsProcessNoInfoRuleEngineChallengePostToCscAndOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setPosNegRulesProcessor(posNegRulesProcessor);
		fraudCheckProcessor.setRulesEngineProcessor(rulesEngineProcessor);
		fraudCheckProcessor.setBusRuleDaoJpa(busRuleDaoJpa);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);

		when(jaxbMessageConverter.fromMessage(message)).thenReturn(order);
		when(order.getOrderNo()).thenReturn("12345");
		when(fraudEngineDao.create(any(OrderDetailEntity.class))).thenReturn(orderDetailEntity);
		when(posNegRulesProcessor.posNegRuleCheck(order)).thenReturn(null);
		when(rulesEngineProcessor.rulesEngineCheck(order)).thenReturn(ruleOrderStatus);
		when(fraudEngineDaoJpa.findStatusLk(anyString())).thenReturn(mock(StatusLkEntity.class));
		when(ruleOrderStatus.getRuleId()).thenReturn("10");
		when(orderDetailEntity.getOrderDetailPk()).thenReturn(14L);
		when(ruleOrderStatus.getOrderScore()).thenReturn(FraudCheckConst.CHALLENGE_STATUS);
		when(busRuleDaoJpa.findBusRuleHitByRuleId(Long.parseLong(ruleOrderStatus.getRuleId()))).thenReturn(busRuleHitEntity);
		when(busRuleDaoJpa.create(busRuleHitEntity)).thenReturn(busRuleHitEntity);
		final BusRuleEntity busRuleEntity = mock(BusRuleEntity.class);
		when(fraudEngineDao.find(Long.parseLong(ruleOrderStatus.getRuleId()), BusRuleEntity.class)).thenReturn(busRuleEntity);

		fraudCheckProcessor.omsProcess(order);
		verify(messageProcessor).sendFraudCheckResponseToOms(order, FraudCheckConst.CHALLENGE_STATUS, "14", null);
		verify(messageProcessor).sendChallengeMessageToCsc(order, FraudCheckConst.CHALLENGE_STATUS, null, 14L, null);
	}

	@Test
	public void testOmsProcessAcceptPostToOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setPosNegRulesProcessor(posNegRulesProcessor);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);

		when(jaxbMessageConverter.fromMessage(message)).thenReturn(order);
		when(order.getOrderNo()).thenReturn("12345");
		when(fraudEngineDao.create(any(OrderDetailEntity.class))).thenReturn(orderDetailEntity);
		when(orderDetailEntity.getOrderDetailPk()).thenReturn(15L);
		when(posNegRulesProcessor.posNegRuleCheck(order)).thenReturn(posNegResult);
		when(posNegResult.getStatus()).thenReturn(FraudCheckConst.ACCEPT_STATUS);
		when(posNegResult.getPosNegListPk()).thenReturn(1L);
		when(fraudEngineDao.find(posNegResult.getPosNegListPk(), PosNegListEntity.class)).thenReturn(posNegListEntity);
		when(posNegListEntity.getPosNegReasonLk()).thenReturn(mock(PosNegReasonLkEntity.class));

		fraudCheckProcessor.omsProcess(order);
		verify(messageProcessor).sendFraudCheckResponseToOms(order, FraudCheckConst.ACCEPT_STATUS, "15", null);
	}

	@Test
	public void testOmsProcessDenyPostToOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setPosNegRulesProcessor(posNegRulesProcessor);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);

		when(jaxbMessageConverter.fromMessage(message)).thenReturn(order);
		when(order.getOrderNo()).thenReturn("12345");
		when(fraudEngineDao.create(any(OrderDetailEntity.class))).thenReturn(orderDetailEntity);
		when(orderDetailEntity.getOrderDetailPk()).thenReturn(16L);
		when(posNegRulesProcessor.posNegRuleCheck(order)).thenReturn(posNegResult);
		when(posNegResult.getStatus()).thenReturn(FraudCheckConst.DENY_STATUS);
		when(posNegResult.getPosNegListPk()).thenReturn(1L);
		when(fraudEngineDao.find(posNegResult.getPosNegListPk(), PosNegListEntity.class)).thenReturn(posNegListEntity);
		when(posNegListEntity.getPosNegReasonLk()).thenReturn(mock(PosNegReasonLkEntity.class));

		fraudCheckProcessor.omsProcess(order);
		verify(messageProcessor).sendFraudCheckResponseToOms(order, FraudCheckConst.DENY_STATUS, "16", null);

	}

	@Test
	public void testOmsProcessChallengePostToCscAndOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setPosNegRulesProcessor(posNegRulesProcessor);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);

		when(jaxbMessageConverter.fromMessage(message)).thenReturn(order);
		when(order.getOrderNo()).thenReturn("12345");
		when(fraudEngineDao.create(any(OrderDetailEntity.class))).thenReturn(orderDetailEntity);
		when(orderDetailEntity.getOrderDetailPk()).thenReturn(17L);
		when(posNegRulesProcessor.posNegRuleCheck(order)).thenReturn(posNegResult);
		when(posNegResult.getStatus()).thenReturn(FraudCheckConst.CHALLENGE_STATUS);
		when(posNegResult.getPosNegListPk()).thenReturn(1L);
		when(fraudEngineDao.find(posNegResult.getPosNegListPk(), PosNegListEntity.class)).thenReturn(posNegListEntity);
		when(posNegListEntity.getPosNegReasonLk()).thenReturn(mock(PosNegReasonLkEntity.class));

		fraudCheckProcessor.omsProcess(order);
		verify(messageProcessor).sendFraudCheckResponseToOms(order, FraudCheckConst.CHALLENGE_STATUS, "17", null);
		verify(messageProcessor).sendChallengeMessageToCsc(order, FraudCheckConst.CHALLENGE_STATUS, null, 17L, null);

	}

	@Test
	public void testCscProcessAcceptPostToOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);
		when(jaxbMessageConverter.fromMessage(message)).thenReturn(fraudReviewResponse);
		when(fraudReviewResponse.getResponse()).thenReturn(response);
		when(response.getAction()).thenReturn(FraudCheckConst.ACCEPT_STATUS);
		when(response.getRequestId()).thenReturn("9001");
		when(fraudEngineDao.find(Long.valueOf(response.getRequestId()), OrderDetailEntity.class)).thenReturn(orderDetailEntity);
		when(fraudEngineDao.findStatusLk(response.getAction())).thenReturn(statusLkEntity);

		fraudCheckProcessor.cscProcess(fraudReviewResponse);
		verify(messageProcessor).sendCscResponseToOms(fraudReviewResponse, orderDetailEntity);

	}

	@Test
	public void testCscProcessDenyPostToOms() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);
		when(jaxbMessageConverter.fromMessage(message)).thenReturn(fraudReviewResponse);
		when(fraudReviewResponse.getResponse()).thenReturn(response);
		when(response.getAction()).thenReturn(FraudCheckConst.DENY_STATUS);
		when(response.getRequestId()).thenReturn("9002");
		when(response.getReasonCode()).thenReturn("8000");
		when(fraudEngineDao.find(Long.valueOf(response.getRequestId()), OrderDetailEntity.class)).thenReturn(orderDetailEntity);
		when(fraudEngineDao.findStatusLk(response.getAction())).thenReturn(statusLkEntity);

		fraudCheckProcessor.cscProcess(fraudReviewResponse);
		verify(messageProcessor).sendCscResponseToOms(fraudReviewResponse, orderDetailEntity);

	}

	@Test
	public void testCscProcessDenyOrAcceptAndDBHasCancelledStatus() throws Exception {
		fraudCheckProcessor.setFraudEngineDaoJpa(fraudEngineDao);
		when(jaxbMessageConverter.fromMessage(message)).thenReturn(fraudReviewResponse);
		when(fraudReviewResponse.getResponse()).thenReturn(response);
		when(response.getAction()).thenReturn(FraudCheckConst.DENY_STATUS);
		when(response.getRequestId()).thenReturn("9002");
		when(response.getReasonCode()).thenReturn("8000");
		when(fraudEngineDao.find(Long.valueOf(response.getRequestId()), OrderDetailEntity.class)).thenReturn(orderDetailEntity);
		when(orderStatusEntity.getStatusLk()).thenReturn(statusLkEntity);
		when(statusLkEntity.getName()).thenReturn(FraudCheckConst.CANCELLED_STATUS);

		fraudCheckProcessor.cscProcess(fraudReviewResponse);

	}

	@Test
	public void testCscCancelProcess() throws Exception {
		fraudCheckProcessor.setMessageProcessor(messageProcessor);
		when(jaxbMessageConverter.fromMessage(message)).thenReturn(fraudReviewResponse);
		when(fraudReviewResponse.getResponse()).thenReturn(response);
		when(response.getAction()).thenReturn(FraudCheckConst.CSC_CANCELLED);

		fraudCheckProcessor.cscProcess(fraudReviewResponse);
		verifyNoMoreInteractions(messageProcessor);
	}
}
