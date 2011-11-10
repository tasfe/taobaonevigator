package com.payment.taobaoNavigator.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.PromotionEntity;

@Repository
@Transactional
public class PromotionDaoImpl extends BaseDaoImpl implements PromotionDao {

	@Override
	public List<PromotionEntity> getPromotions() {
		String hql = "select p from PromotionEntity p";
		Query query = this.createCachedQuery(hql);
		List<PromotionEntity> promotions = query.getResultList();
		return promotions;
	}

	@Override
	public List<PromotionEntity> getEnabledPromotions() {
		String hql = "select p from PromotionEntity p where p.enabled = 1";
		Query query = this.createCachedQuery(hql);
		List<PromotionEntity> promotions = query.getResultList();
		return promotions;
	}

	@Override
	public PromotionEntity getPromotionById(Integer id) {
		String hql = "select p from PromotionEntity p where p.id = :id";
		Query query = this.createCachedQuery(hql);
		query.setParameter("id", id);
		PromotionEntity promotionEntity = (PromotionEntity) query
				.getSingleResult();
		return promotionEntity;
	}

	@Override
	public PromotionEntity getPromotionByName(String name) {
		String hql = "select p from PromotionEntity p where p.name = :name";
		Query query = this.createCachedQuery(hql);
		query.setParameter("name", name);
		PromotionEntity promotionEntity = (PromotionEntity) query
				.getSingleResult();
		return promotionEntity;
	}

	@Override
	public PromotionEntity createPromotion(PromotionEntity promotion) {
		PromotionEntity createdPromotion = this.create(promotion);
		return createdPromotion;
	}

	@Override
	public PromotionEntity updatePromotion(PromotionEntity promotion) {
		PromotionEntity updatedPromotion = this.update(promotion);
		return updatedPromotion;
	}

	@Override
	public void deletePromotionById(Integer id) {
		PromotionEntity promotionEntity = this.find(PromotionEntity.class, id);
		this.delete(promotionEntity);
	}

	@Override
	public void deletePromotionByIds(List<Integer> ids) {
		for (Integer id : ids) {
			PromotionEntity promotionEntity = this.find(PromotionEntity.class, id);
			this.delete(promotionEntity);
		}
	}

	@Override
	public void deletePromotionByName(String name) {
		PromotionEntity promotionEntity = this.getPromotionByName(name);
		this.delete(promotionEntity);
	}

	@Override
	public void deletePromotionByNames(List<String> names) {
		for (String name : names) {
			this.deletePromotionByName(name);
		}
	}

	@Override
	public void clearPromotions() {
		String hql = "delete from PromotionEntity p";
		Query query = em.createQuery(hql);
		query.executeUpdate();
	}

}
