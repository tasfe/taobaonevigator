package com.payment.taobaoNavigator.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.dao.NavigatorDao;
import com.payment.taobaoNavigator.entity.CatagoryEntity;
import com.payment.taobaoNavigator.entity.ProductEntity;

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
	
	
	
}
