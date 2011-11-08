package com.payment.taobaoNavigator.dao;

import java.util.List;
import java.util.Map;

import com.payment.taobaoNavigator.entity.BaseEntity;

public interface IBaseDao {
	public <T extends BaseEntity> T create(T entity);
	public <T extends BaseEntity> T update(T entity);
	public <T extends BaseEntity> T delete(T entity);
	public <T extends BaseEntity> T find(Object entityPk, Class<T> entityClass);
	public <T extends BaseEntity> List<T> findByNamedQuery(Class<T> entityClass, String namedQuery);
	public <T extends BaseEntity> List<T> findByNamedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);
}
