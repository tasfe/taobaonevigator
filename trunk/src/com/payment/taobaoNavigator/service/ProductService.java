package com.payment.taobaoNavigator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.entity.ProductEntity;

@Service
public interface ProductService {
	
	/**
	 * get the latest product information
	 * 
	 * @param limited
	 * 			a number which the result should be less than.
	 * 
	 * @return List<ProductEntity>
	 * 			a list of product object
	 */
	public List<ProductEntity> getLatestProducts(Integer limited);
	
	/**
	 * get the hottest product information
	 * 
	 * @param limited
	 * 			a number which the result should be less than.
	 * 
	 * @return List<ProductEntity>
	 * 			a list of product object
	 */
	public List<ProductEntity> getHottestProducts(Integer limited);
	
}
