package com.payment.taobaoNavigator.dao;

import java.util.List;
import java.util.Map;

import com.payment.taobaoNavigator.entity.BaseEntity;

public interface BaseDao {
	public <T extends BaseEntity> T create(T entity);
	public <T extends BaseEntity> T update(T entity);
	public <T extends BaseEntity> void delete(T entity);
	public <T extends BaseEntity> T find(Class<T> entityClass, Object entityPk);
	public <T extends BaseEntity> List<T> findByNamedQuery(Class<T> entityClass, String namedQuery);
	public <T extends BaseEntity> List<T> findByNamedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);
}
