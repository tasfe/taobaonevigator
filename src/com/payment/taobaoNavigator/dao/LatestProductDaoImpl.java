package com.payment.taobaoNavigator.dao;

import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.ClickCountEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;

@Repository
@Transactional
public class LatestProductDaoImpl extends BaseDaoImpl implements
		LatestProductDao {

	@Override
	public List<ProductEntity> getLatestProductions(int max) {
		String hql = "select p  from ProductEntity p order by p.createdDate desc ";
		Query query = this.createCachedQuery(hql);
		query.setMaxResults(max);
		List<ProductEntity> productEntity = query.getResultList();
		return productEntity;
	}

	@Override
	public List<ProductEntity> getHotSells(int max) {
		String hql = "select p from ClickCountEntity c  join c.product p order by c.count desc";
		Query query = this.createCachedQuery(hql);
		query.setMaxResults(max);
		List<ProductEntity> productEntity = query.getResultList();
		return productEntity;
	}
}
