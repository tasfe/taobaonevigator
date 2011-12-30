package com.walmart.fraudengine.dao;

import static com.walmart.fraudengine.util.FraudCheckConst.EXCLUDE_SOFT_DELETE;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.fraudengine.model.PosNegListView;
import com.walmart.fraudengine.model.StatusLkEntity;

/**
 * Common DAO class for Fraud Engine application.
 */
@Repository
public class FraudEngineDaoJpa extends GenericDaoJpa implements FraudEngineDao {

	public static final String ORDER_STATUS_NAME = "name";
	

	@Override
	@Transactional
	public StatusLkEntity findStatusLk(String status) {

		final Map<String, Object> parameters = QueryParameter.with(ORDER_STATUS_NAME, status)
				.and(SOFT_DELETE, EXCLUDE_SOFT_DELETE).parameters();
		List<StatusLkEntity> statusLkEntityList = this.findByNamedQuery(StatusLkEntity.class, StatusLkEntity.BY_NAME,
				parameters);

		if (statusLkEntityList != null && statusLkEntityList.iterator().hasNext()) {
			return statusLkEntityList.iterator().next();
		}

		return null;
	}


	@Override
	@Transactional
	public PosNegListView findFromView(Map<String, String> fieldNameValues) {
		StringBuffer sql = new StringBuffer();
    	sql.append(" select o from PosNegListView o where 1!=1 ");
    	if ( fieldNameValues != null ) {
	    	for ( int i = 0; i < fieldNameValues.size(); i++) {
	    		sql.append(" or ( name = ? and fieldValue = ? ) ");
	    	}
    	}
    	sql.append(" order by reasonCode asc");
    	Query query = em.createQuery(sql.toString());

    	//bind parameters
    	if ( fieldNameValues != null ) {
    		Iterator<Entry<String,String>> iter = fieldNameValues.entrySet().iterator();
    		for(int i=1;iter.hasNext();){
    			Entry<String,String> entry = iter.next();
    			query.setParameter(i++, entry.getKey());
    			query.setParameter(i++, entry.getValue());
    		}
    	}
    	//select the top record
    	query.setMaxResults(1);
    	List<PosNegListView> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.iterator().next();
	}
	
}