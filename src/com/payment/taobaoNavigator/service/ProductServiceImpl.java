package com.payment.taobaoNavigator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.dao.ProductDao;
import com.payment.taobaoNavigator.entity.ProductEntity;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;

	@Override
	public List<ProductEntity> getLatestProducts(Integer limited) {
		// invoke dao
		List<ProductEntity> products = productDao.getLatestProducts(limited);

		if (products == null || products.isEmpty()) {
			return new ArrayList<ProductEntity>();
		}

		return products;
	}

	@Override
	public List<ProductEntity> getHottestProducts(Integer limited) {
		// invoke dao
		List<ProductEntity> products = productDao.getHottestProducts(limited);

		if (products == null || products.isEmpty()) {
			return new ArrayList<ProductEntity>();
		}

		return products;
	}

	// add for mockito test
	public void setProductDao(ProductDao ProductDao) {
		this.productDao = ProductDao;
	}

}
