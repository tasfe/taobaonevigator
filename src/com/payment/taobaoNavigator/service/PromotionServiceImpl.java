package com.payment.taobaoNavigator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.dao.PromotionDao;
import com.payment.taobaoNavigator.entity.PromotionEntity;

@Transactional
@Service
public class PromotionServiceImpl implements PromotionService {
	
	@Autowired
	@Qualifier("promotionDaoImpl")
	private PromotionDao promotionDao;

	@Override
	public List<PromotionEntity> getPromotions() {
		List<PromotionEntity> promotionEntities = promotionDao.getPromotions();
		if (promotionEntities == null || promotionEntities.isEmpty()) {
			return new ArrayList<PromotionEntity>();
		}

		return promotionEntities;
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
	
	//add for mockito test
	public void setPromotionDao(PromotionDao promotionDao) {
		this.promotionDao = promotionDao;
	}

}
