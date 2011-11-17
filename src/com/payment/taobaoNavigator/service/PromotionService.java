package com.payment.taobaoNavigator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.entity.PromotionEntity;

@Service
public interface PromotionService {

	//TODO add javadoc
	public List<PromotionEntity> getPromotions();

	//TODO add javadoc
	public List<PromotionEntity> getEnabledPromotions();
	
	//TODO add javadoc
	public PromotionEntity getPromotionById(Integer id);

	//TODO add javadoc
	public PromotionEntity getPromotionByName(String name);

	//TODO add javadoc
	public PromotionEntity createPromotion(PromotionEntity promotion);
	
	//TODO add javadoc
	public PromotionEntity updatePromotion(PromotionEntity promotion);

	//TODO add javadoc
	public boolean deletePromotionById(Integer id);

	//TODO add javadoc
	public boolean deletePromotionByIds(List<Integer> ids);

	//TODO add javadoc
	public boolean deletePromotionByName(String name);

	//TODO add javadoc
	public boolean deletePromotionByNames(List<String> names);

	//TODO add javadoc
	public boolean clearPromotions();

}
