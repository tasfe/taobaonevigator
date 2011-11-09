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
	
	public void deletePromotionById(Integer id);
	
	public void deletePromotionByIds(List<Integer> ids);
	
	public void deletePromotionByName(String name);
	
	public void deletePromotionByNames(List<String> names);
	
	public void clearPromotions();
}
