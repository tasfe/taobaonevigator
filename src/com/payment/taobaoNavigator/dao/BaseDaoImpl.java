package com.payment.taobaoNavigator.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.BaseEntity;

@Repository
public abstract class BaseDaoImpl implements BaseDao{
	
	@PersistenceContext(unitName = "taobaonavigator")
	protected EntityManager em;
	
	@Override
	@Transactional
	public <T extends BaseEntity> T create(T entity) {

		em.persist(entity);
		em.flush();
		em.refresh(entity);
		
		return entity;
	}
	
	@Override
	@Transactional
	public <T extends BaseEntity> T update(T entity) {
		return (T) this.em.merge(entity);
	}

	@Override
	@Transactional
	public <T extends BaseEntity> void delete(T entity) {
		 em.remove(entity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public <T extends BaseEntity> T find(Class<T> entityClass, Object entityPk) {

		return (T) this.em.find(entityClass, entityPk);
	}


	@Override
	public <T extends BaseEntity> List<T> findByNamedQuery(
			Class<T> entityClass, String namedQuery) {
		
		return findByNamedQuery(entityClass, namedQuery, null);
	}

	@Override
	public <T extends BaseEntity> List<T> findByNamedQuery(
			Class<T> entityClass, String queryName,
			Map<String, Object> parameters) {
		Query query = em.createNamedQuery(entityClass.getSimpleName() + "." + queryName);
		if (parameters != null) {
			for (Entry<String, Object> param : parameters.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		query.setHint("org.hibernate.cacheable", true);
		final List<T> resultList = query.getResultList();
		
		if (resultList == null) {
			return new LinkedList<T>();
		}

		return resultList;
	}

	@Override
	public Query createCachedQuery(String queryCommand) {
		Query query = em.createQuery(queryCommand);
		query.setHint("org.hibernate.cacheable", true);
		return query;
	}
	
}