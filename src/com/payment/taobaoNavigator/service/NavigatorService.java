package com.payment.taobaoNavigator.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;


public interface NavigatorService {
	public List<CatagoryEntity> findCatagoryByPid(int pid);
	public Map<CatagoryEntity, List<ProductEntity>> getHotProducts(int parentId,int maxNumber);
	public List<ProductEntity> getHotProduct(int catagoryId, int maxNumber);
}
