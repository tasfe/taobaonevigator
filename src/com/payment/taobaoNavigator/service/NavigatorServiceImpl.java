package com.payment.taobaoNavigator.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.dao.NavigatorDao;
import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;

@Transactional
@Service
public class NavigatorServiceImpl implements NavigatorService {
	
	@Autowired
	@Qualifier("navigatorDaoImpl")
	private NavigatorDao navigatorDaoImpl;

	@Override
	public List<CatagoryEntity> findCatagoryByPid(int pid) {	
		return navigatorDaoImpl.findNavigatorByParentId(pid);
	}

	@Override
	public Map<CatagoryEntity, List<ProductEntity>> getHotProducts(
			int parentId, int maxNumber) {
		
		return navigatorDaoImpl.getHotProducts(parentId, maxNumber);
	}

	@Override
	public List<ProductEntity> getHotProduct(int catagoryId, int maxNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public List<ProductEntity> getHotProduct(
//			int catagoryId, int maxNumber) {
//		
//		return navigatorDaoImpl.getHotProduct(String.valueOf(catagoryId), maxNumber);
//	}
	
	
	
}
