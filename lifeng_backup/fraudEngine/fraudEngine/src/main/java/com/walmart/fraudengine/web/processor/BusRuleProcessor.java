package com.walmart.fraudengine.web.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.walmart.fraudengine.dao.BusRuleDao;
import com.walmart.fraudengine.dao.FraudEngineDao;
import com.walmart.fraudengine.exception.ErrorCode;
import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRulePriorityEntity;
import com.walmart.fraudengine.model.BusRulePropertyEntity;
import com.walmart.fraudengine.model.StatusLkEntity;
import com.walmart.fraudengine.util.LogSupport;
import com.walmart.fraudengine.web.form.BusPropertyForm;
import com.walmart.fraudengine.web.form.BusRuleForm;

/**
 * This processor class is designed to process the business logic regarding to
 * business rules. such as add/delete/modify/query the rules / rule priority /
 * rule property.
 */
@Service
public class BusRuleProcessor {

	@Autowired
	@Qualifier("busRuleDaoJpa")
	private BusRuleDao busRuleDao;

	@Autowired
	private FraudEngineDao fraudEngineDaoJpa;

	/**
	 * find business rules for paginated list.
	 * 
	 * @param startIndex
	 * @param size
	 * @param sortMap
	 * @return List<BusRuleForm>
	 * @throws FraudEngineApplicationException
	 */
	public List<BusRuleForm> findBusRules(Long startIndex, Long size, Map<String, String> sortMap) throws FraudEngineApplicationException {

		List<?> list = busRuleDao.findForBusRuleForm(startIndex, size, sortMap);
		if (list == null) { // busRuleDao.findForBusRuleForm will return null if
							// nothing found.
			return null;
		}
		Iterator<?> iter = list.iterator();
		List<BusRuleForm> formList = null;
		while (iter.hasNext()) {
			// it's unsafe to get data from columns one by one. query a view
			// instead
			// and fetch data into view's entity will be robust. TODO
			Object[] columns = (Object[]) iter.next();
			BusRuleForm form = new BusRuleForm();
			form.setRulePK(Long.valueOf(columns[0].toString()));
			form.setDescription(columns[1].toString());
			form.setFileName(columns[2].toString());
			form.setPriority(Integer.valueOf(columns[3].toString()));
			form.setStartDate((Timestamp) columns[4]);
			form.setEndDate((Timestamp) columns[5]);
			form.setHit(Integer.valueOf(columns[6].toString()));
			if (formList == null) {
				formList = new ArrayList<BusRuleForm>();
			}
			formList.add(form);
		}
		return formList;
	}

	/**
	 * find business rules with specific rule pk.
	 * 
	 * @param ruleId
	 * @return BusRuleForm
	 * @throws FraudEngineApplicationException
	 */
	public BusRuleForm findBusRule(Long ruleId) throws FraudEngineApplicationException {

		BusRuleEntity busRuleEntity = fraudEngineDaoJpa.find(ruleId, BusRuleEntity.class);
		if (busRuleEntity == null) {
			LogSupport.debug("can not find BusRuleEntity with pk=" + ruleId);
			return null;
		}
		// get status
		StatusLkEntity statusLkEntity = busRuleEntity.getStatusLk();
		if (statusLkEntity == null) {
			LogSupport.error("Rule without status found.");
			throw new FraudEngineApplicationException(ErrorCode.RULE_WITH_NO_STATUS);
		}
		// get priority
		BusRulePriorityEntity busRulePriorityEntity = busRuleDao.findBusRulePriorityByRuleId(ruleId);
		// a rule must have priority
		if (busRulePriorityEntity == null) {
			LogSupport.error("Rule without priority number found.");
			throw new FraudEngineApplicationException(ErrorCode.RULE_WITN_NO_PRIORIY);
		}

		// set data from entity to form
		BusRuleForm busRuleForm = new BusRuleForm();
		busRuleForm.setStatus(statusLkEntity.getName());
		busRuleForm.setPriority(busRulePriorityEntity.getPriority());
		busRuleForm.setRulePK(ruleId);
		busRuleForm.setDescription(busRuleEntity.getRuleDescription());
		busRuleForm.setFileName(busRuleEntity.getRuleFileName());
		busRuleForm.setStartDate(busRuleEntity.getStartDate());
		busRuleForm.setEndDate(busRuleEntity.getEndDate());

		// get properties
		List<BusRulePropertyEntity> propertyEntities = busRuleDao.findBusRulePropertyByRuleId(ruleId);
		if (propertyEntities == null || propertyEntities.isEmpty()) {
			LogSupport.debug("no property found with ruleId=" + ruleId);
		} else {
			Map<String, String> properties = new LinkedHashMap<String, String>();
			for (BusRulePropertyEntity property : propertyEntities) {
				properties.put(property.getPropertyName(), property.getPropertyValue());
			}
			busRuleForm.setProperties(properties);
		}

		return busRuleForm;

	}

	/**
	 * find the BusRuleProperty by Rule Id
	 * 
	 * @param ruleId
	 * @return
	 * @throws FraudEngineApplicationException
	 */
	public List<BusPropertyForm> queryBusRuleProperty(long ruleId) throws FraudEngineApplicationException {
		List<BusRulePropertyEntity> propertyEntities = busRuleDao.findBusRulePropertyByRuleId(ruleId);
		if (propertyEntities == null || propertyEntities.isEmpty()) {
			return null;
		}
		List<BusPropertyForm> formList = null;
		for (BusRulePropertyEntity entity : propertyEntities) {
			BusPropertyForm form = new BusPropertyForm();
			form.setId(entity.getBusRulePropertyPk());
			form.setName(entity.getPropertyName());
			form.setValue(entity.getPropertyValue());
			if (formList == null) {
				formList = new ArrayList<BusPropertyForm>();
			}
			formList.add(form);
		}
		return formList;
	}

	/**
	 * add new rule using the data from page.
	 * 
	 * @param busRuleForm
	 * @return boolean if add successfully, return true; otherwise return false.
	 */
	public boolean addBusRule(BusRuleForm busRuleForm) throws FraudEngineApplicationException {
		if (busRuleForm == null) {
			LogSupport.debug("The form to be saved is null.");
			return false;
		}
		// get statusLkEntity
		StatusLkEntity statusLkEntity = fraudEngineDaoJpa.findStatusLk(busRuleForm.getStatus());
		if (statusLkEntity == null) {
			LogSupport.debug("There is no status entity can be found with id=" + busRuleForm.getStatus());
			return false;

		} else {
			// save business rule
			BusRuleEntity busRuleEntity = new BusRuleEntity();
			busRuleEntity.setRuleFileName(busRuleForm.getFileName());
			busRuleEntity.setRuleDescription(busRuleForm.getDescription());
			busRuleEntity.setStartDate(busRuleForm.getStartDate());
			busRuleEntity.setEndDate(busRuleForm.getEndDate());
			busRuleEntity.setStatusLk(statusLkEntity);
			BusRuleEntity createdBusRuleEntity = fraudEngineDaoJpa.create(busRuleEntity);

			// save business rule priority
			BusRulePriorityEntity busRulePriorityEntity = new BusRulePriorityEntity();
			busRulePriorityEntity.setBusRule(createdBusRuleEntity);
			busRulePriorityEntity.setPriority(busRuleForm.getPriority());
			fraudEngineDaoJpa.create(busRulePriorityEntity);

			// save business rule property
			Map<String, String> properties = busRuleForm.getProperties();
			if (properties != null && !properties.isEmpty()) {
				for (Map.Entry<String, String> property : properties.entrySet()) {
					BusRulePropertyEntity propertyEntity = new BusRulePropertyEntity();
					propertyEntity.setBusRuleFk(createdBusRuleEntity.getBusRulePk());
					propertyEntity.setPropertyName(property.getKey());
					propertyEntity.setPropertyValue(property.getValue());
					fraudEngineDaoJpa.create(propertyEntity);
				}
			}

			return true;
		}
	}

	/**
	 * a batch delete for business rules with a list of rule pk.
	 * 
	 * @param rulePKs
	 *            "," separated string which contains rulePK
	 * @return boolean identify the delete operation is succeed or not.
	 * @throws FraudEngineApplicationException
	 */
	public boolean deleteBusRules(String rulePKs) throws FraudEngineApplicationException {
		if (rulePKs == null || rulePKs.isEmpty()) {
			LogSupport.warn("input for deleting business rules is null or empty.");
			return false;
		}
		String[] rulePKArray = rulePKs.split(",");
		for (String rulePKStr : rulePKArray) {
			if (!StringUtils.isNumeric(rulePKStr) || StringUtils.isWhitespace(rulePKStr)) {
				LogSupport.error("Invalid business rule pk, pk=" + rulePKStr);
				throw new FraudEngineApplicationException(ErrorCode.RULE_INVALID_ID);
			}
			Long rulePk = Long.valueOf(rulePKStr);

			// check rule with given rulePk
			BusRuleEntity deleteBusRule = fraudEngineDaoJpa.find(rulePk, BusRuleEntity.class);
			if (deleteBusRule == null) {
				continue;
			}
			// delete rule
			fraudEngineDaoJpa.delete(deleteBusRule);
			
			// check the priority
			BusRulePriorityEntity busRulePriority = busRuleDao.findBusRulePriorityByRuleId(rulePk);
			if (busRulePriority == null) {
				LogSupport.error("Rule without priority number found.");
				throw new FraudEngineApplicationException(ErrorCode.RULE_WITN_NO_PRIORIY);
			}
			// delete priority
			fraudEngineDaoJpa.delete(busRulePriority);

			// delete properties
			List<BusRulePropertyEntity> busRuleProperties = busRuleDao.findBusRulePropertyByRuleId(rulePk);
			if (busRuleProperties != null) {
				for (BusRulePropertyEntity property : busRuleProperties) {
					// TODO it's better to use a batch delete in one query
					// TODO - sprint 3 please implement this
					fraudEngineDaoJpa.delete(property);
				}
			}
		}

		return true;
	}

	/**
	 * update a business rule
	 * 
	 * @param busRuleForm
	 * @throws FraudEngineApplicationException
	 */
	public boolean updateBusRule(BusRuleForm busRuleForm) throws FraudEngineApplicationException {

		if (busRuleForm == null) {
			LogSupport.debug("The form to be updated is null.");
			return false;
		}

		// get statusLkEntity
		StatusLkEntity statusLkEntity = fraudEngineDaoJpa.findStatusLk(busRuleForm.getStatus());
		if (statusLkEntity == null) {
			LogSupport.debug("There is no status entity can be found with id=" + busRuleForm.getStatus());
			return false;
		}

		// get priority
		BusRulePriorityEntity originalPriority = busRuleDao.findBusRulePriorityByRuleId(busRuleForm.getRulePK());
		if (originalPriority == null) {
			// a rule must have priority
			LogSupport.error("Rule without priority number found.");
			throw new FraudEngineApplicationException(ErrorCode.RULE_WITN_NO_PRIORIY);
		}

		// update business rule
		BusRuleEntity busRuleEntity = fraudEngineDaoJpa.find(busRuleForm.getRulePK(), BusRuleEntity.class);
		busRuleEntity.setRuleFileName(busRuleForm.getFileName());
		busRuleEntity.setRuleDescription(busRuleForm.getDescription());
		busRuleEntity.setStartDate(busRuleForm.getStartDate());
		busRuleEntity.setEndDate(busRuleForm.getEndDate());
		busRuleEntity.setStatusLk(statusLkEntity);
		BusRuleEntity updatedBusRuleEntity = fraudEngineDaoJpa.merge(busRuleEntity);

		// update priority
		// TODO it seems we need to make a cascade update to other priorities.
		if (originalPriority.getPriority().compareTo(busRuleForm.getPriority()) != 0) {
			// delete original priority and save new value.
			fraudEngineDaoJpa.delete(originalPriority);
			BusRulePriorityEntity newPriority = new BusRulePriorityEntity();
			newPriority.setBusRule(updatedBusRuleEntity);
			newPriority.setPriority(busRuleForm.getPriority());
			fraudEngineDaoJpa.create(newPriority);
		}

		// update properties
		List<BusRulePropertyEntity> originalProperties = busRuleDao.findBusRulePropertyByRuleId(updatedBusRuleEntity.getBusRulePk());
		Map<String, String> newProperties = busRuleForm.getProperties();
		if (isPropertiesModified(originalProperties, newProperties)) {
			// delete original properties if any update
			if (originalProperties != null) {
				for (BusRulePropertyEntity property : originalProperties) {
					fraudEngineDaoJpa.delete(property);
				}
			}
			// save new properties
			if (newProperties != null && !newProperties.isEmpty()) {
				for (Map.Entry<String, String> property : newProperties.entrySet()) {
					BusRulePropertyEntity propertyEntity = new BusRulePropertyEntity();
					propertyEntity.setBusRuleFk(updatedBusRuleEntity.getBusRulePk());
					propertyEntity.setPropertyName(property.getKey());
					propertyEntity.setPropertyValue(property.getValue());
					fraudEngineDaoJpa.create(propertyEntity);
				}
			}
		}

		return true;
	}

	/**
	 * to determine that whether the properties has changed or not.
	 * 
	 * @param original
	 * @param updated
	 * @return boolean
	 */
	private boolean isPropertiesModified(List<BusRulePropertyEntity> original, Map<String, String> updated) {
		if ((original == null && updated != null) || (original != null && updated == null)) {
			return true;
		}
		if (original == null && updated == null) {
			return false;
		}
		if (original.size() != updated.size()) {
			return true;
		}
		for (int i = 0; i < original.size(); i++) {
			BusRulePropertyEntity property = original.get(i);
			/* add property name for each property. see modification below.
			if (!(property != null && property.getPropertyValue() != null && property.getPropertyValue().equals(updated.get(i)))) {
				return true;
			}
			*/
			if (property != null && property.getPropertyName() != null) {
				String propertyName = property.getPropertyName();
				if (!updated.containsKey(propertyName)) {
					return true;
				}
				String propertyValue = property.getPropertyValue();
				if (propertyValue != null && !propertyValue.equals(updated.get(propertyName))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * get how many business rules there are.
	 * 
	 * @return Long
	 * @throws FraudEngineApplicationException
	 */
	public Long getBusRuleCount() throws FraudEngineApplicationException {
		int count = busRuleDao.getCountOfBusRule();
		return Integer.valueOf(count).longValue();
	}

	// add for mockito unit test. no use in business.
	public void setBusRuleDao(BusRuleDao busRuleDao) {
		this.busRuleDao = busRuleDao;
	}

	// add for mockito unit test. no use in business.
	public void setFraudEngineDaoJpa(FraudEngineDao fraudEngineDaoJpa) {
		this.fraudEngineDaoJpa = fraudEngineDaoJpa;
	}
}
