package com.payment.taobaoNavigator.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;

@Repository
public interface NavigatorDao {
	public List<CatagoryEntity> findNavigatorByParentId(int pid);
	public CatagoryEntity findNavigatorById(int id);
	public CatagoryEntity createNavigator(CatagoryEntity entity);
	public CatagoryEntity updateNavigator(CatagoryEntity entity);
	public void deleteNavigator(CatagoryEntity entity);
	
	public Map<CatagoryEntity ,List<ProductEntity>>getHotProducts(int pid,int maxNumber);
//	public List<ProductEntity> getHotProduct(String catagoryId,int maxNumber);
	public List<CatagoryEntity> getAllChildCatagory(int i);
	public List<ProductEntity> getHotProduct(int pid,int maxNumber);
}
