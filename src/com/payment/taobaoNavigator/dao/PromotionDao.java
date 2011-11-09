package com.payment.taobaoNavigator.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.payment.taobaoNavigator.entity.PromotionEntity;

@Repository
public interface PromotionDao {
	
	public List<PromotionEntity> getPromotions();
	
	public List<PromotionEntity> getEnabledPromotions();
	
	public PromotionEntity getPromotionById(Integer id);
	
	public PromotionEntity getPromotionByName(String name);
	
	public PromotionEntity createPromotion(PromotionEntity promotion);
	
	public PromotionEntity updatePromotion(PromotionEntity promotion);
	
	public boolean deletePromotionById(Integer id);
	
	public boolean deletePromotionByIds(List<Integer> ids);
	
	public boolean deletePromotionByName(String name);
	
	public boolean deletePromotionByNames(List<String> names);
	
	public boolean clearPromotions();
}