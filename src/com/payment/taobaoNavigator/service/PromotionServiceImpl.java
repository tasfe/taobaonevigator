package com.payment.taobaoNavigator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.dao.PromotionDao;
import com.payment.taobaoNavigator.form.PromotionForm;

@Service
public class PromotionServiceImpl implements PromotionService {
	
	@Autowired
	@Qualifier("promotionDaoImpl")
	private PromotionDao promotionDao;

	@Override
	public List<PromotionForm> getPromotions() {

		return null;
	}

	@Override
	public List<PromotionForm> getEnabledPromotions() {

		return null;
	}

	@Override
	public PromotionForm getPromotionById(Integer id) {

		return null;
	}

	@Override
	public PromotionForm getPromotionByName(String name) {

		return null;
	}

	@Override
	public PromotionForm createPromotion(PromotionForm promotion) {

		return null;
	}

	@Override
	public PromotionForm updatePromotion(PromotionForm promotion) {

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
