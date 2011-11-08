package com.payment.taobaoNavigator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.form.PromotionForm;

@Service
public interface PromotionService {
	
public List<PromotionForm> getPromotions();
	
	public List<PromotionForm> getEnabledPromotions();
	
	public PromotionForm getPromotionById(Integer id);
	
	public PromotionForm getPromotionByName(String name);
	
	public PromotionForm createPromotion(PromotionForm promotion);
	
	public PromotionForm updatePromotion(PromotionForm promotion);
	
	public boolean deletePromotionById(Integer id);
	
	public boolean deletePromotionByIds(List<Integer> ids);
	
	public boolean deletePromotionByName(String name);
	
	public boolean deletePromotionByNames(List<String> names);
	
	public boolean clearPromotions();
	
}
