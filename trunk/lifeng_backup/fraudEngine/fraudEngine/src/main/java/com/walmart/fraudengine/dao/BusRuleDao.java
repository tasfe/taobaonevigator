package com.walmart.fraudengine.dao;

import java.util.List;
import java.util.Map;

import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRuleHitEntity;
import com.walmart.fraudengine.model.BusRulePriorityEntity;
import com.walmart.fraudengine.model.BusRulePropertyEntity;

/**
 * DAO interface for the operations of business rule.
 */
public interface BusRuleDao extends GenericDao {

	/**
	 * get all of the BusRuleEntity objects
	 * 
	 * @return List<BusRuleEntity>
	 */
	public List<BusRuleEntity> findAllBusRule();

	/**
	 * get all of the BusRulePropertyEntity objects
	 * 
	 * @return List<BusRulePropertyEntity>
	 */
	public List<BusRulePropertyEntity> findAllBusRuleProperty();

	/**
	 * get a list of BusRulePropertyEntity objects by specific ruleId
	 * 
	 * @param id
	 * @return List<BusRulePropertyEntity>
	 */
	public List<BusRulePropertyEntity> findBusRulePropertyByRuleId(Long id);

	/**
	 * get BusRuleHitEntity object by specific ruleId
	 * 
	 * @param ruleId
	 * @return BusRuleHitEntity
	 */
	public BusRuleHitEntity findBusRuleHitByRuleId(long ruleId);

	/**
	 * get all of the BusRulePriority entities
	 * 
	 * @return List<BusRulePriorityEntity>
	 */
	public List<BusRulePriorityEntity> findAllBusRulePriority();

	/**
	 * get BusRulePriorityEntity object by specific ruleId
	 * 
	 * @param ruleId
	 * @return
	 */
	public BusRulePriorityEntity findBusRulePriorityByRuleId(Long ruleId);

	/**
	 * @return
	 */
	public int getCountOfBusRule();

	/**
	 * get a list of data for pages
	 * 
	 * @param startIndex
	 * @param size
	 * @param sortMap
	 * @return List
	 */
	public List<?> findForBusRuleForm(Long startIndex, Long size,
			Map<String, String> sortMap);
}