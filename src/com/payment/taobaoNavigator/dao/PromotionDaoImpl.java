package com.payment.taobaoNavigator.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.PromotionEntity;

@Repository
public class PromotionDaoImpl extends BaseDaoImpl implements PromotionDao {

	@Override
	public List<PromotionEntity> getPromotions() {
		String hql = "select p from PromotionEntity p";
		Query query = em.createQuery(hql);
		List<PromotionEntity> promotions = query.getResultList();
		return promotions;
	}

	@Override
	public List<PromotionEntity> getEnabledPromotions() {
		String hql = "select p from PromotionEntity p where p.enabled=1";
		Query query = em.createQuery(hql);
		List<PromotionEntity> promotions = query.getResultList();
		return promotions;
	}

	@Override
	public PromotionEntity getPromotionById(Integer id) {
		String hql = "select p from PromotionEntity p where p.id='" + id + "'";
		Query query = em.createQuery(hql);
		PromotionEntity promotionEntity = (PromotionEntity) query
				.getSingleResult();
		return promotionEntity;
	}

	@Override
	public PromotionEntity getPromotionByName(String name) {
		String hql = "select p from PromotionEntity p where p.name='" + name
				+ "'";
		Query query = em.createQuery(hql);
		PromotionEntity promotionEntity = (PromotionEntity) query
				.getSingleResult();
		return promotionEntity;
	}

	@Override
	@Transactional
	public PromotionEntity createPromotion(PromotionEntity promotion) {
		create(promotion);
		return null;
	}

	@Override
	public PromotionEntity updatePromotion(PromotionEntity promotion) {
		em.merge(promotion);
		return null;
	}

	@Override
	@Transactional
	public boolean deletePromotionById(Integer id) {
		boolean flag = false;
		PromotionEntity promotionEntity = em.find(PromotionEntity.class, id);
		em.remove(promotionEntity);
		flag = true;
		return flag;
	}

	@Override
	@Transactional
	public boolean deletePromotionByIds(List<Integer> ids) {
		boolean flag = false;
		for (Integer integer : ids) {
			PromotionEntity promotionEntity = em.find(PromotionEntity.class,
					integer);
			em.remove(promotionEntity);
		}
		flag = true;
		return flag;
	}

	@Override
	@Transactional
	public boolean deletePromotionByName(String name) {
		boolean flag = false;
		PromotionEntity promotionEntity = this.getPromotionByName(name);
		em.remove(promotionEntity);
		flag = true;
		return flag;
	}

	@Override
	@Transactional
	public boolean deletePromotionByNames(List<String> names) {
		boolean flag = false;
		for (String name : names) {
			this.deletePromotionByName(name);
		}
		flag = true;
		return flag;
	}

	@Override
	@Transactional
	public boolean clearPromotions() {
		boolean flag = false;
		String hql = "delete from PromotionEntity p";
		Query query = em.createQuery(hql);
		query.executeUpdate();
		flag = true;
		return flag;
	}

}
