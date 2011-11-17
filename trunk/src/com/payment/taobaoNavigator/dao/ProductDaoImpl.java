package com.payment.taobaoNavigator.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.ProductEntity;

@Repository
@Transactional
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

	@Override
	public List<ProductEntity> getLatestProducts(Integer limited) {
		String jpql = "SELECT p FROM ProductEntity p ORDER BY p.createdDate DESC ";
		Query query = this.createCachedQuery(jpql);
		if (limited != null) {
			query.setMaxResults(limited);
		}
		List<ProductEntity> productEntity = query.getResultList();
		return productEntity;
	}

	@Override
	public List<ProductEntity> getHottestProducts(Integer limited) {
		String jpql = "SELECT p FROM ClickCountEntity c JOIN c.product p ORDER BY c.count DESC";
		Query query = this.createCachedQuery(jpql);
		if (limited != null) {
			query.setMaxResults(limited);
		}
		List<ProductEntity> productEntity = query.getResultList();
		return productEntity;
	}
}
