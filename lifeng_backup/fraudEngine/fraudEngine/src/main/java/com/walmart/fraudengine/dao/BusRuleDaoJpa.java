package com.walmart.fraudengine.dao;

import static com.walmart.fraudengine.util.FraudCheckConst.EXCLUDE_SOFT_DELETE;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.exception.ErrorCode;
import com.walmart.fraudengine.exception.FraudEngineApplicationException;
import com.walmart.fraudengine.model.BusRuleEntity;
import com.walmart.fraudengine.model.BusRuleHitEntity;
import com.walmart.fraudengine.model.BusRulePriorityEntity;
import com.walmart.fraudengine.model.BusRulePropertyEntity;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.LogSupport;
import com.walmart.fraudengine.web.form.BusRuleForm;
import com.walmart.fraudengine.web.util.NameQuerySqlHelper;

/**
 * DAO implementation class for the operations of business rule
 * 
 * @author
 * @version
 */
@Repository
public class BusRuleDaoJpa extends GenericDaoJpa implements BusRuleDao {

	protected static final String BUS_RULE_ID = "bizRuleId";

	@Autowired
	private NameQuerySqlHelper nameQuerySqlHelper;

	/**
	 * get all of the BusRuleEntity objects
	 * 
	 * @return List<BusRuleEntity>
	 */
	@Override
	@Transactional
	public List<BusRuleEntity> findAllBusRule() {
		final Map<String, Object> parameters = QueryParameter.with(SOFT_DELETE, EXCLUDE_SOFT_DELETE)
				.and(STATUS, FraudCheckConst.STATUS_ACTIVE).parameters();
		return this.findByNamedQuery(BusRuleEntity.class, BusRuleEntity.FETCH_ALL_BY_STATUS, parameters);
	}

	/**
	 * get all of the BusRulePropertyEntity objects
	 * 
	 * @return List<BusRulePropertyEntity>
	 */
	@Override
	@Transactional
	public List<BusRulePropertyEntity> findAllBusRuleProperty() {
		final Map<String, Object> parameters = QueryParameter.with(SOFT_DELETE, EXCLUDE_SOFT_DELETE).parameters();
		return this.findByNamedQuery(BusRulePropertyEntity.class, BusRulePropertyEntity.FETCH_ALL, parameters);
	}

	/**
	 * get a list of BusRulePropertyEntity objects by specific ruleId
	 * 
	 * @param id
	 * @return List<BusRulePropertyEntity>
	 */
	@Override
	@Transactional
	public List<BusRulePropertyEntity> findBusRulePropertyByRuleId(Long id) {
		final Map<String, Object> parameters = QueryParameter.with(BUS_RULE_ID, id).and(SOFT_DELETE, EXCLUDE_SOFT_DELETE).parameters();
		return this.findByNamedQuery(BusRulePropertyEntity.class, BusRulePropertyEntity.BY_RULE_ID, parameters);
	}

	/**
	 * get BusRuleHitEntity object by specific ruleId
	 * 
	 * @param ruleId
	 * @return BusRuleHitEntity
	 */
	@Override
	@Transactional
	public BusRuleHitEntity findBusRuleHitByRuleId(long ruleId) {
		BusRuleHitEntity busRuleHitEntity = null;
		final Map<String, Object> parameters = QueryParameter.with(BUS_RULE_ID, ruleId).and(SOFT_DELETE, EXCLUDE_SOFT_DELETE).parameters();
		List<BusRuleHitEntity> list = this.findByNamedQuery(BusRuleHitEntity.class, BusRuleHitEntity.BY_RULE_ID, parameters);
		if (!list.isEmpty()) {
			busRuleHitEntity = (BusRuleHitEntity) list.get(0);
			LogSupport.debug("BusRuleHitEntity found when ruleId=" + ruleId);
			LogSupport.debug(busRuleHitEntity.toString());
		} else {
			LogSupport.debug("no BusRuleHitEntity found when ruleId=" + ruleId);
		}
		return busRuleHitEntity;
	}

	/**
	 * get all of the BusRulePriority entities
	 * 
	 * @return List<BusRulePriorityEntity>
	 */
	@Override
	@Transactional
	public List<BusRulePriorityEntity> findAllBusRulePriority() {
		final Map<String, Object> parameters = QueryParameter.with(SOFT_DELETE, EXCLUDE_SOFT_DELETE)
				.and(STATUS, FraudCheckConst.STATUS_ACTIVE).parameters();
		return this.findByNamedQuery(BusRulePriorityEntity.class, BusRulePriorityEntity.FETCH_ALL_ORDER_BY_PRIORITY, parameters);
	}

	@Override
	@Transactional
	public BusRulePriorityEntity findBusRulePriorityByRuleId(Long ruleId) {
		BusRulePriorityEntity busRulePriorityEntity = null;
		final Map<String, Object> parameters = QueryParameter.with(BUS_RULE_ID, ruleId).and(SOFT_DELETE, EXCLUDE_SOFT_DELETE).parameters();
		List<BusRulePriorityEntity> list = this.findByNamedQuery(BusRulePriorityEntity.class, BusRulePriorityEntity.BY_RULE_ID, parameters);
		if (!list.isEmpty()) {
			busRulePriorityEntity = (BusRulePriorityEntity) list.get(0);
			LogSupport.debug("BusRulePriorityEntity found when ruleId=" + ruleId);
			LogSupport.debug(busRulePriorityEntity.toString());
		} else {
			LogSupport.debug("no BusRulePriorityEntity found when ruleId=" + ruleId);
		}
		return busRulePriorityEntity;
	}

	@Override
	@Transactional
	public int getCountOfBusRule() {
		// construct a query string to retrieve all the objects
		StringBuffer hql = new StringBuffer();
		hql.append(nameQuerySqlHelper.getSqlByName(BusRuleForm.class.getSimpleName() + QUERY_DOT + BusRuleForm.FETCH_ALL));
		// setup query
		Query query = this.em.createNativeQuery(hql.toString());
		// execute
		List<?> resultList = query.getResultList();
		return resultList == null ? 0 : resultList.size();
	}

	@Transactional
	public List<?> findForBusRuleForm(Long startIndex, Long size, Map<String, String> sortMap) {
		LogSupport.debug("finding scrolling data for business rule form. startIndex=" + startIndex + " and size=" + size);
		// verify the parameters
		if ((startIndex != null && startIndex < 0) || (size != null && size < 0)) {
			FraudEngineApplicationException dbException = new FraudEngineApplicationException(ErrorCode.SIZE_OR_INDEX_NEGATIVE);
			LogSupport.debug("wrong parameter.", dbException);
		}
		// construct a query string to retrieve all the objects
		StringBuffer hql = new StringBuffer();
		try {
			hql.append(nameQuerySqlHelper.getSqlByName(BusRuleForm.class.getSimpleName() + QUERY_DOT + BusRuleForm.FETCH_ALL));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		hql.append(ORDER_BY);
		if (sortMap != null) {
			for (Map.Entry<String, String> sort : sortMap.entrySet()) {
				hql.append(sort.getKey() + " ");
				hql.append(sort.getValue() + ",");
			}
		}
		hql.append(DEFAULT_ORDER_COLUMN);
		// setup query
		Query query = this.em.createNativeQuery(hql.toString());
		if (startIndex != null) {
			query.setFirstResult(startIndex.intValue());
		}
		if (size != null) {
			query.setMaxResults(size.intValue());
		}
		// execute
		List<?> resultList = query.getResultList();
		// log
		if (resultList != null && resultList.size() > 0) {
			LogSupport.debug("got scrolling data for business rule form successfully");
		} else {
			LogSupport.debug("can't get scrolling data business rule form.");
		}
		return resultList;
	}

}
