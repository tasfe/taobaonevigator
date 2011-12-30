package com.walmart.fraudengine.dao;

import java.util.List;
import java.util.Map;

import com.walmart.fraudengine.model.BaseEntity;

/**
 * Common interface for all DAO objects. All DAO's should extend the
 * <code>GenericDao<code> and manage CRUD operations using methods defined in this DAO.
 */
public interface GenericDao {

	/**
	 * Make an entity instance managed and persistent.
	 * 
	 * @param entity Entity object to be managed by the current persistence context
	 * @return Managed entity
	 */
	public <T extends BaseEntity> T create(T entity);

	/**
	 * Find by primary key. Search for an entity of the specified class and primary key. If the entity instance is
	 * contained in the persistence context, it is returned from there.
	 * 
	 * @param entityId Primary key of entity
	 * @param entityClass Entity class
	 * @return The found entity instance or null if the entity does not exist
	 */
	public <T extends BaseEntity> T find(Object entityId, Class<T> entityClass);

	/**
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * @param entity Detached entity object to be merged into the current persistence context
	 * @return The managed instance that the state was merged to
	 */
	public <T extends BaseEntity> T merge(T entity);

	/**
	 * Soft delete the entity instance.
	 * <p>
	 * <strong>Note: </strong>Hard deletes are not permitted within the Fraud Engine application.
	 * 
	 * @param entityId Primary key of entity
	 * @param entityClass Entity class
	 */
	public <T extends BaseEntity> void delete(T entity);

	/**
	 * Find a list of specified entity objects using NamedQuery.
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param namedQuery
	 * @return @
	 */
	public <T extends BaseEntity> List<T> findByNamedQuery(Class<T> entityClass, String namedQuery);

	/**
	 * Find a list of specified entity objects using NamedQuery which may contain parameters.
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param namedQuery
	 * @param parameters
	 * @return @
	 */
	public <T extends BaseEntity> List<T> findByNamedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);
}