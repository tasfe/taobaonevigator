package com.payment.taobaoNavigator.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.payment.taobaoNavigator.entity.ClickCountEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;

@Repository
public interface LatestProductDao {
	public List<ProductEntity> getLatestProductions(int max);
	public List<ProductEntity> getHotSells(int max);
}
