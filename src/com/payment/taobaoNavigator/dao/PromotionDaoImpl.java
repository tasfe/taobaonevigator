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

		return null;
	}

	@Override
	public PromotionEntity getPromotionById(Integer id) {

		return null;
	}

	@Override
	public PromotionEntity getPromotionByName(String name) {

		return null;
	}

	@Override
	public PromotionEntity createPromotion(PromotionEntity promotion) {

		return null;
	}

	@Override
	public PromotionEntity updatePromotion(PromotionEntity promotion) {

		return null;
	}

	@Override
	public boolean deletePromotionById(Integer id) {

		return false;
	}

	@Override
	public boolean deletePromotionByIds(List<Integer> ids) {

		return false;
	}

	@Override
	public boolean deletePromotionByName(String name) {

		return false;
	}

	@Override
	public boolean deletePromotionByNames(List<String> names) {

		return false;
	}

	@Override
	public boolean clearPromotions() {

		return false;
	}

}
