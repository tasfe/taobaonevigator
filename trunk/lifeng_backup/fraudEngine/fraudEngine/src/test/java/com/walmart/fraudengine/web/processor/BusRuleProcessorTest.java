package com.walmart.fraudengine.web.processor;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.fraudengine.BaseTestCase;
import com.walmart.fraudengine.dao.BusRuleDao;
import com.walmart.fraudengine.dao.FraudEngineDao;
import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.model.BaseEntity;
import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRulePriorityEntity;
import com.walmart.fraudengine.model.BusRulePropertyEntity;
import com.walmart.fraudengine.model.StatusLkEntity;
import com.walmart.fraudengine.web.form.BusPropertyForm;
import com.walmart.fraudengine.web.form.BusRuleForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:fraudEngineMessagingContext.xml",
		"classpath:testFraudEnginePersistance.xml" })
public class BusRuleProcessorTest extends BaseTestCase {

	@Autowired
	private BusRuleProcessor busRuleProcessor;

	@Mock
	private BusRuleDao busRuleDao;

	@Mock
	private FraudEngineDao fraudEngineDaoJpa;

	@Mock
	private BusRuleEntity busRuleEntity;

	@Mock
	private BusRulePriorityEntity busRulePriorityEntity;

	@Mock
	private StatusLkEntity statusLkEntity;

	@Mock
	private BusRulePropertyEntity busRulePropertyEntity;

	@Mock
	private BusRuleForm busRuleForm;

	@Before
	public void setUp() throws Exception {
		busRuleProcessor.setBusRuleDao(busRuleDao);
		busRuleProcessor.setFraudEngineDaoJpa(fraudEngineDaoJpa);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindBusRulesNull() {
		// preparation
		when(busRuleDao.findForBusRuleForm(anyLong(), anyLong(), anyMapOf(String.class, String.class))).thenReturn(null);
		// execution
		try {
			busRuleProcessor.findBusRules(1L, 1L, null);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		// assertion
		verify(busRuleDao).findForBusRuleForm(anyLong(), anyLong(), anyMapOf(String.class, String.class));
	}

	@Test
	public void testFindBusRulesNotNull() {
		// preparation
		List list = null;
		int listSize = 5;
		for (int i = 0; i < listSize; i++) {
			Object[] column = new Object[7];
			// rulePK
			column[0] = i;
			// description
			column[1] = "description" + i;
			// filename
			column[2] = "filename" + i;
			// priority
			column[3] = i;
			// start date
			column[4] = new Timestamp(i);
			// end date
			column[5] = new Timestamp(i + 1);
			// hit
			column[6] = i;

			// add column to list
			if (list == null) {
				list = new ArrayList<Object[]>();
			}
			list.add(column);
		}
		when(busRuleDao.findForBusRuleForm(anyLong(), anyLong(), anyMapOf(String.class, String.class))).thenReturn(list);

		// execution
		List<BusRuleForm> result = null;
		try {
			result = busRuleProcessor.findBusRules(1L, 1L, null);
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		Assert.assertNotNull(result);
		Assert.assertEquals(5, result.size());
		verify(busRuleDao).findForBusRuleForm(anyLong(), anyLong(), anyMapOf(String.class, String.class));
	}

	@Test
	public void testFindBusRuleNull() {
		// preparation
		when(fraudEngineDaoJpa.find(anyLong(), eq(BusRuleEntity.class))).thenReturn(null);
		// execution
		BusRuleForm form = null;
		try {
			form = busRuleProcessor.findBusRule(1L);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		// assertion
		Assert.assertNull(form);
		verify(fraudEngineDaoJpa).find(anyLong(), eq(BusRuleEntity.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
	}

	@Test
	public void testFindBusRuleStatusNullException() {
		// preparation
		when(fraudEngineDaoJpa.find(anyLong(), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(busRuleEntity.getStatusLk()).thenReturn(null);
		// execution
		BusRuleForm form = null;
		boolean exceptionOccured = false;
		try {
			form = busRuleProcessor.findBusRule(1L);
		} catch (FraudEngineApplicationException e) {
			Assert.assertNotNull(e);
			exceptionOccured = true;
		}
		// assertion
		Assert.assertNull(form);
		Assert.assertEquals(true, exceptionOccured);
		verify(fraudEngineDaoJpa).find(anyLong(), eq(BusRuleEntity.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
	}

	@Test
	public void testFindBusRulePriorityNullException() {
		// preparation
		when(fraudEngineDaoJpa.find(anyLong(), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(busRuleDao.findBusRulePriorityByRuleId(anyLong())).thenReturn(null);
		when(busRuleEntity.getStatusLk()).thenReturn(statusLkEntity);
		// execution
		BusRuleForm form = null;
		boolean exceptionOccured = false;
		try {
			form = busRuleProcessor.findBusRule(1L);
		} catch (FraudEngineApplicationException e) {
			Assert.assertNotNull(e);
			exceptionOccured = true;
		}
		// assertion
		Assert.assertNull(form);
		Assert.assertEquals(true, exceptionOccured);
		verify(fraudEngineDaoJpa).find(anyLong(), eq(BusRuleEntity.class));
		verify(busRuleDao).findBusRulePriorityByRuleId(anyLong());
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testFindBusRulePropertyNull() {
		// preparation
		when(fraudEngineDaoJpa.find(anyLong(), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(busRuleDao.findBusRulePropertyByRuleId(anyLong())).thenReturn(null);
		when(busRuleDao.findBusRulePriorityByRuleId(anyLong())).thenReturn(busRulePriorityEntity);
		when(busRuleEntity.getStatusLk()).thenReturn(statusLkEntity);	
		when(busRuleEntity.getRuleDescription()).thenReturn("description");
		when(busRuleEntity.getRuleFileName()).thenReturn("filename");
		when(busRuleEntity.getStartDate()).thenReturn(new Timestamp(1L));
		when(busRuleEntity.getEndDate()).thenReturn(new Timestamp(1L));
		when(statusLkEntity.getName()).thenReturn("ACTIVE");
		when(busRulePriorityEntity.getPriority()).thenReturn(1);
		
		// execution
		BusRuleForm form = null;
		try {
			form = busRuleProcessor.findBusRule(1L);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		// assertion
		Assert.assertNotNull(form);
		Assert.assertNull(form.getProperties());
		verify(fraudEngineDaoJpa).find(anyLong(), eq(BusRuleEntity.class));
		verify(busRuleDao).findBusRulePriorityByRuleId(anyLong());
		verify(busRuleDao).findBusRulePropertyByRuleId(anyLong());
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testFindBusRulePropertyNotNull() {
		// preparation
		when(fraudEngineDaoJpa.find(anyLong(), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		List<BusRulePropertyEntity> propertyEntities = new ArrayList<BusRulePropertyEntity>();
		propertyEntities.add(busRulePropertyEntity);
		when(busRuleDao.findBusRulePropertyByRuleId(anyLong())).thenReturn(propertyEntities);
		when(busRuleDao.findBusRulePriorityByRuleId(anyLong())).thenReturn(busRulePriorityEntity);
		when(busRuleEntity.getStatusLk()).thenReturn(statusLkEntity);
		when(busRuleEntity.getRuleDescription()).thenReturn("description");
		when(busRuleEntity.getRuleFileName()).thenReturn("filename");
		when(busRuleEntity.getStartDate()).thenReturn(new Timestamp(1L));
		when(busRuleEntity.getEndDate()).thenReturn(new Timestamp(1L));
		when(statusLkEntity.getName()).thenReturn("ACTIVE");
		when(busRulePriorityEntity.getPriority()).thenReturn(1);
		when(busRulePropertyEntity.getPropertyValue()).thenReturn("I'm property");	

		// execution
		BusRuleForm form = null;
		try {
			form = busRuleProcessor.findBusRule(1L);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		// assertion
		Assert.assertNotNull(form);
		Assert.assertNotNull(form.getProperties());
		verify(fraudEngineDaoJpa).find(anyLong(), eq(BusRuleEntity.class));
		verify(busRuleDao).findBusRulePriorityByRuleId(anyLong());
		verify(busRuleDao).findBusRulePropertyByRuleId(anyLong());
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testQueryBusRulePropertyNull() {
		// preparation
		when(busRuleDao.findBusRulePropertyByRuleId(anyLong())).thenReturn(null);
		// execution
		List<BusPropertyForm> result = null;
		try {
			result = busRuleProcessor.queryBusRuleProperty(1L);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		// assertion
		Assert.assertNull(result);
		verify(busRuleDao).findBusRulePropertyByRuleId(anyLong());
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testQueryBusRulePropertyNotNull() {
		// preparation
		List<BusRulePropertyEntity> propertyEntities = new ArrayList<BusRulePropertyEntity>();
		when(busRulePropertyEntity.getBusRulePropertyPk()).thenReturn(1L);
		when(busRulePropertyEntity.getPropertyName()).thenReturn("I'm property's name");
		when(busRulePropertyEntity.getPropertyValue()).thenReturn("I'm property's value");
		propertyEntities.add(busRulePropertyEntity);
		propertyEntities.add(busRulePropertyEntity);
		when(busRuleDao.findBusRulePropertyByRuleId(anyLong())).thenReturn(propertyEntities);
		
		// execution
		List<BusPropertyForm> result = null;
		try {
			result = busRuleProcessor.queryBusRuleProperty(1L);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		
		// assertion
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		verify(busRuleDao).findBusRulePropertyByRuleId(anyLong());
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testAddBusRuleFormNull() {
		// preparation

		// execution
		boolean added = false;
		try {
			added = busRuleProcessor.addBusRule(null);
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		Assert.assertEquals(false, added);
		verifyZeroInteractions(fraudEngineDaoJpa);
		verifyZeroInteractions(busRuleDao);
	}

	@Test
	public void testAddBusRuleInvalidStatus() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(null);
		when(busRuleForm.getStatus()).thenReturn("InvalidStatus");
		
		// execution
		boolean added = false;
		try {
			added = busRuleProcessor.addBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		Assert.assertEquals(false, added);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyZeroInteractions(busRuleDao);
	}

	@Test
	public void testAddBusRuleSuccess() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(statusLkEntity);
		when(fraudEngineDaoJpa.create(argThat(new BusRuleEntityMatcher()))).thenReturn(busRuleEntity);
		when(fraudEngineDaoJpa.create(argThat(new BusRulePriorityEntityMatcher()))).thenReturn(null);
		when(fraudEngineDaoJpa.create(argThat(new BusRulePropertyEntityMatcher()))).thenReturn(null);
		when(busRuleForm.getFileName()).thenReturn("filename");
		when(busRuleForm.getDescription()).thenReturn("description");
		when(busRuleForm.getStartDate()).thenReturn(new Timestamp(1L));
		when(busRuleForm.getEndDate()).thenReturn(new Timestamp(1L));
		when(busRuleForm.getFileName()).thenReturn("filename");
		when(busRuleForm.getPriority()).thenReturn(1);
		Map<String, String> properties = new LinkedHashMap<String, String>();
		properties.put("property1_name", "property1_value");
		properties.put("property2_name", "property2_value");
		when(busRuleForm.getProperties()).thenReturn(properties);
		when(busRuleEntity.getBusRulePk()).thenReturn(1L);

		// execution
		boolean added = false;
		try {
			added = busRuleProcessor.addBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		Assert.assertEquals(true, added);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));
		verify(fraudEngineDaoJpa, times(4)).create(any(BaseEntity.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyZeroInteractions(busRuleDao);
	}

	@Test
	public void testDeleteBusRulesPKsNull() {
		// preparation

		// execution
		try {
			busRuleProcessor.deleteBusRules(null);
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		verifyZeroInteractions(fraudEngineDaoJpa);
		verifyZeroInteractions(busRuleDao);
	}

	@Test
	public void testDeleteBusRulesPKsEmpty() {
		// preparation

		// execution
		try {
			busRuleProcessor.deleteBusRules("");
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		verifyZeroInteractions(fraudEngineDaoJpa);
		verifyZeroInteractions(busRuleDao);
	}

	@Test
	public void testDeleteBusRulesPKsNotNumericException() {
		// preparation

		// execution
		boolean exceptionOccured = false;
		try {
			busRuleProcessor.deleteBusRules("aa");
		} catch (FraudEngineApplicationException e) {
			Assert.assertNotNull(e);
			exceptionOccured = true;
		}

		// assertion
		Assert.assertEquals(true, exceptionOccured);
		verifyZeroInteractions(fraudEngineDaoJpa);
		verifyZeroInteractions(busRuleDao);
	}

	@Test
	public void testDeleteBusRulesPriorityNotFoundException() {
		// preparation
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(null);
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRuleEntityMatcher()));
		
		// execution
		boolean exceptionOccured = false;
		try {
			busRuleProcessor.deleteBusRules("1,2");
		} catch (FraudEngineApplicationException e) {
			Assert.assertNotNull(e);
			exceptionOccured = true;
		}

		// assertion
		Assert.assertEquals(true, exceptionOccured);
		verify(fraudEngineDaoJpa).find(any(Long.class), eq(BusRuleEntity.class));
		verify(fraudEngineDaoJpa).delete(argThat(new BusRuleEntityMatcher()));
		verify(busRuleDao).findBusRulePriorityByRuleId(anyLong());
		verifyNoMoreInteractions(busRuleDao);
		verifyZeroInteractions(fraudEngineDaoJpa);
	}

	@Test
	public void testDeleteBusRulesNoneProperties() {
		// preparation
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(busRulePriorityEntity);
		when(busRuleDao.findBusRulePropertyByRuleId(any(Long.class))).thenReturn(null);
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRulePriorityEntity.class))).thenReturn(busRulePriorityEntity);
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRuleEntityMatcher()));
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRulePriorityEntityMatcher()));
		when(busRulePriorityEntity.getBusRulePriorityPk()).thenReturn(1L);
		// execution
		try {
			busRuleProcessor.deleteBusRules("1,2");
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		verify(busRuleDao, times(2)).findBusRulePriorityByRuleId(any(Long.class));
		verify(fraudEngineDaoJpa, times(2)).find(any(Long.class), eq(BusRuleEntity.class));
		verify(fraudEngineDaoJpa, times(2)).delete(argThat(new BusRuleEntityMatcher()));
		verify(fraudEngineDaoJpa, times(2)).delete(argThat(new BusRulePriorityEntityMatcher()));
		verify(busRuleDao, times(2)).findBusRulePropertyByRuleId(any(Long.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	// have priority and properties
	public void testDeleteBusRules() {
		// preparation
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(busRulePriorityEntity);
		List<BusRulePropertyEntity> busRuleProperties = new ArrayList<BusRulePropertyEntity>();
		busRuleProperties.add(busRulePropertyEntity);// one
		busRuleProperties.add(busRulePropertyEntity);// two
		when(busRuleDao.findBusRulePropertyByRuleId(any(Long.class))).thenReturn(busRuleProperties);		
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRulePriorityEntity.class))).thenReturn(busRulePriorityEntity);
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRulePropertyEntity.class))).thenReturn(busRulePropertyEntity);
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRuleEntityMatcher()));
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRulePriorityEntityMatcher()));
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRulePropertyEntityMatcher()));
		when(busRulePropertyEntity.getBusRulePropertyPk()).thenReturn(1L);
		when(busRulePriorityEntity.getBusRulePriorityPk()).thenReturn(1L);

		// execution
		try {
			busRuleProcessor.deleteBusRules("1,2");
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		verify(fraudEngineDaoJpa, times(2)).find(any(Long.class), eq(BusRuleEntity.class));
		verify(fraudEngineDaoJpa, times(2)).delete(argThat(new BusRuleEntityMatcher()));
		verify(fraudEngineDaoJpa, times(2)).delete(argThat(new BusRulePriorityEntityMatcher()));
		verify(fraudEngineDaoJpa, times(4)).delete(argThat(new BusRulePropertyEntityMatcher()));
		verify(busRuleDao, times(2)).findBusRulePropertyByRuleId(any(Long.class));
		verify(busRuleDao, times(2)).findBusRulePriorityByRuleId(any(Long.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);		
	}

	@Test
	public void testUpdateBusRuleFormIsNull() {
		// preparation

		// execution
		boolean result = false;
		try {
			result = busRuleProcessor.updateBusRule(null);
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		Assert.assertEquals(false, result);
		verifyZeroInteractions(fraudEngineDaoJpa);
		verifyZeroInteractions(busRuleDao);
	}

	@Test
	public void testUpdateBusRuleInvalidStatus() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(null);
		when(busRuleForm.getStatus()).thenReturn("invalidstatus");

		// execution
		boolean result = false;
		try {
			result = busRuleProcessor.updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();
		}
		// assertion
		Assert.assertEquals(false, result);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));
		verifyZeroInteractions(busRuleDao);
		verifyNoMoreInteractions(fraudEngineDaoJpa);
	}

	@Test
	public void testUpdateBusRuleNoPriorityFoundException() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(statusLkEntity);
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(null);

		// execution
		boolean result = false;
		boolean exceptionOccured = false;
		try {
			result = busRuleProcessor.updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			Assert.assertNotNull(e);
			exceptionOccured = true;
		}
		// assertion
		Assert.assertEquals(false, result);
		Assert.assertEquals(true, exceptionOccured);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));
		verify(busRuleDao).findBusRulePriorityByRuleId(any(Long.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testUpdateBusRulePriorityChangedPropertiesChanged() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(statusLkEntity);
		when(fraudEngineDaoJpa.merge(any(BusRuleEntity.class))).thenReturn(busRuleEntity);		
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRulePriorityEntityMatcher()));
		doNothing().when(fraudEngineDaoJpa).delete(argThat(new BusRulePropertyEntityMatcher()));
		when(fraudEngineDaoJpa.create(argThat(new BusRulePriorityEntityMatcher()))).thenReturn(null);
		when(fraudEngineDaoJpa.create(argThat(new BusRulePropertyEntityMatcher()))).thenReturn(null);
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(busRulePriorityEntity);
		when(busRuleForm.getFileName()).thenReturn("filename");
		when(busRuleForm.getDescription()).thenReturn("description");
		when(busRuleForm.getStartDate()).thenReturn(new Timestamp(1L));
		when(busRuleForm.getEndDate()).thenReturn(new Timestamp(2L));
		when(busRuleEntity.getBusRulePk()).thenReturn(1L);
		// priority changed
		when(busRulePriorityEntity.getPriority()).thenReturn(2);
		when(busRulePriorityEntity.getBusRulePriorityPk()).thenReturn(1L);
		when(busRuleForm.getPriority()).thenReturn(1);
		// properties changed
		List<BusRulePropertyEntity> properties = new ArrayList<BusRulePropertyEntity>();
		properties.add(busRulePropertyEntity);
		when(busRulePropertyEntity.getPropertyName()).thenReturn("property1_name");
		when(busRulePropertyEntity.getPropertyValue()).thenReturn("old_value");
		when(busRuleDao.findBusRulePropertyByRuleId(any(Long.class))).thenReturn(properties);
		Map<String, String> newProperties = new LinkedHashMap<String, String>();
		newProperties.put("property1_name", "property1_value");
		when(busRuleForm.getProperties()).thenReturn(newProperties);

		// execution
		boolean result = false;
		try {
			result = busRuleProcessor.updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();

		}
		// assertion
		Assert.assertEquals(true, result);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));	
		verify(fraudEngineDaoJpa).find(any(Long.class), eq(BusRuleEntity.class));
		verify(fraudEngineDaoJpa).merge(any(BusRuleEntity.class));
		verify(fraudEngineDaoJpa).delete(argThat(new BusRulePriorityEntityMatcher()));
		verify(fraudEngineDaoJpa).delete(argThat(new BusRulePropertyEntityMatcher()));
		verify(fraudEngineDaoJpa).create(argThat(new BusRulePriorityEntityMatcher()));
		verify(fraudEngineDaoJpa).create(argThat(new BusRulePropertyEntityMatcher()));
		verify(busRuleDao).findBusRulePriorityByRuleId(any(Long.class));
		verify(busRuleDao).findBusRulePropertyByRuleId(any(Long.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testUpdateBusRulePriorityNotChangedPropertiesChanged() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(statusLkEntity);		
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(fraudEngineDaoJpa.merge(any(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(fraudEngineDaoJpa.create(argThat(new BusRulePriorityEntityMatcher()))).thenReturn(null);
		when(fraudEngineDaoJpa.create(argThat(new BusRulePropertyEntityMatcher()))).thenReturn(null);
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(busRulePriorityEntity);
		when(busRuleForm.getFileName()).thenReturn("filename");
		when(busRuleForm.getDescription()).thenReturn("description");
		when(busRuleForm.getStartDate()).thenReturn(new Timestamp(1L));
		when(busRuleForm.getEndDate()).thenReturn(new Timestamp(2L));		
		// priority not changed
		when(busRulePriorityEntity.getPriority()).thenReturn(1);
		when(busRuleForm.getPriority()).thenReturn(1);
		// properties changed
		List<BusRulePropertyEntity> properties = new ArrayList<BusRulePropertyEntity>();
		properties.add(busRulePropertyEntity);
		when(busRulePropertyEntity.getPropertyName()).thenReturn("property1_name");
		when(busRulePropertyEntity.getPropertyValue()).thenReturn("old_value");
		when(busRuleDao.findBusRulePropertyByRuleId(any(Long.class))).thenReturn(properties);
		Map<String, String> newProperties = new LinkedHashMap<String, String>();
		newProperties.put("property1_name", "property1_value");
		when(busRuleForm.getProperties()).thenReturn(newProperties);
		when(busRuleEntity.getBusRulePk()).thenReturn(1L);

		// execution
		boolean result = false;
		try {
			result = busRuleProcessor.updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();

		}
		// assertion
		Assert.assertEquals(true, result);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));		
		verify(fraudEngineDaoJpa).find(any(Long.class), eq(BusRuleEntity.class));
		verify(fraudEngineDaoJpa).merge(any(BusRuleEntity.class));		
		verify(fraudEngineDaoJpa).delete(any(BusRulePropertyEntity.class));
		verify(fraudEngineDaoJpa).create(argThat(new BusRulePropertyEntityMatcher()));
		verify(busRuleDao).findBusRulePriorityByRuleId(any(Long.class));
		verify(busRuleDao).findBusRulePropertyByRuleId(any(Long.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testUpdateBusRulePriorityChangedPropertiesNotChanged() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(statusLkEntity);
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(fraudEngineDaoJpa.create(argThat(new BusRulePriorityEntityMatcher()))).thenReturn(null);
		when(fraudEngineDaoJpa.create(argThat(new BusRulePropertyEntityMatcher()))).thenReturn(null);
		when(fraudEngineDaoJpa.merge(any(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(busRulePriorityEntity);		
		when(busRuleForm.getFileName()).thenReturn("filename");
		when(busRuleForm.getDescription()).thenReturn("description");
		when(busRuleForm.getStartDate()).thenReturn(new Timestamp(1L));
		when(busRuleForm.getEndDate()).thenReturn(new Timestamp(2L));	
		// priority changed
		when(busRulePriorityEntity.getPriority()).thenReturn(2);
		when(busRuleForm.getPriority()).thenReturn(1);
		// properties changed
		List<BusRulePropertyEntity> properties = new ArrayList<BusRulePropertyEntity>();
		properties.add(busRulePropertyEntity);
		when(busRulePropertyEntity.getPropertyValue()).thenReturn("value");
		when(busRuleDao.findBusRulePropertyByRuleId(any(Long.class))).thenReturn(properties);
		Map<String, String> newProperties = new LinkedHashMap<String, String>();
		newProperties.put("property1_name", "property1_value");
		when(busRuleForm.getProperties()).thenReturn(newProperties);
		when(busRuleEntity.getBusRulePk()).thenReturn(1L);

		

		// execution
		boolean result = false;
		try {
			result = busRuleProcessor.updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();

		}
		// assertion
		Assert.assertEquals(true, result);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));		
		verify(fraudEngineDaoJpa).find(any(Long.class), eq(BusRuleEntity.class));
		verify(fraudEngineDaoJpa).merge(any(BusRuleEntity.class));
		verify(fraudEngineDaoJpa).delete(any(BusRulePriorityEntity.class));
		verify(fraudEngineDaoJpa).create(argThat(new BusRulePriorityEntityMatcher()));
		verify(busRuleDao).findBusRulePropertyByRuleId(any(Long.class));
		verify(busRuleDao).findBusRulePriorityByRuleId(any(Long.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testUpdateBusRulePriorityNotChangedPropertiesNotChanged() {
		// preparation
		when(fraudEngineDaoJpa.findStatusLk(any(String.class))).thenReturn(statusLkEntity);
		when(fraudEngineDaoJpa.merge(any(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(fraudEngineDaoJpa.find(any(Long.class), eq(BusRuleEntity.class))).thenReturn(busRuleEntity);
		when(busRuleDao.findBusRulePriorityByRuleId(any(Long.class))).thenReturn(busRulePriorityEntity);
		when(busRuleForm.getFileName()).thenReturn("filename");
		when(busRuleForm.getDescription()).thenReturn("description");
		when(busRuleForm.getStartDate()).thenReturn(new Timestamp(1L));
		when(busRuleForm.getEndDate()).thenReturn(new Timestamp(2L));		
		// priority changed
		when(busRulePriorityEntity.getPriority()).thenReturn(1);
		when(busRuleForm.getPriority()).thenReturn(1);
		// properties changed
		List<BusRulePropertyEntity> properties = new ArrayList<BusRulePropertyEntity>();
		properties.add(busRulePropertyEntity);
		when(busRulePropertyEntity.getPropertyValue()).thenReturn("value");
		when(busRuleDao.findBusRulePropertyByRuleId(any(Long.class))).thenReturn(properties);
		Map<String, String> newProperties = new LinkedHashMap<String, String>();
		newProperties.put("property1_name", "property1_value");
		when(busRuleForm.getProperties()).thenReturn(newProperties);
		when(busRuleEntity.getBusRulePk()).thenReturn(1L);

		// execution
		boolean result = false;
		try {
			result = busRuleProcessor.updateBusRule(busRuleForm);
		} catch (FraudEngineApplicationException e) {
			fail();

		}
		// assertion
		Assert.assertEquals(true, result);
		verify(fraudEngineDaoJpa).findStatusLk(any(String.class));
		verify(fraudEngineDaoJpa).find(any(Long.class), eq(BusRuleEntity.class));
		verify(fraudEngineDaoJpa).merge(any(BusRuleEntity.class));
		verify(busRuleDao).findBusRulePriorityByRuleId(any(Long.class));
		verify(busRuleDao).findBusRulePropertyByRuleId(any(Long.class));
		verifyNoMoreInteractions(fraudEngineDaoJpa);
		verifyNoMoreInteractions(busRuleDao);
	}

	@Test
	public void testGetBusRuleCount() {
		// preparation
		when(busRuleDao.getCountOfBusRule()).thenReturn(3);

		// execution
		long count = -1L;
		try {
			count = busRuleProcessor.getBusRuleCount();
		} catch (FraudEngineApplicationException e) {
			fail();
		}

		// assertion
		Assert.assertEquals(3L, count);
		verify(busRuleDao).getCountOfBusRule();
		verifyZeroInteractions(fraudEngineDaoJpa);
	}

}

class BusRuleEntityMatcher extends ArgumentMatcher<BusRuleEntity> {

	@Override
	public boolean matches(Object argument) {
		return (argument instanceof BusRuleEntity);
	}

}

class BusRulePriorityEntityMatcher extends ArgumentMatcher<BusRulePriorityEntity> {

	@Override
	public boolean matches(Object argument) {
		return (argument instanceof BusRulePriorityEntity);
	}

}

class BusRulePropertyEntityMatcher extends ArgumentMatcher<BusRulePropertyEntity> {

	@Override
	public boolean matches(Object argument) {
		return (argument instanceof BusRulePropertyEntity);
	}

}
