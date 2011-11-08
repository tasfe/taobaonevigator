package com.payment.taobaoNavigator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.dao.NavigatorDao;
import com.payment.taobaoNavigator.entity.CatagoryEntity;

@Service
public class NavigatorServiceImpl implements NavigatorService {
	
	@Autowired
	@Qualifier("navigatorDaoImpl")
	private NavigatorDao navigatorDaoImpl;

	@Override
	public List<CatagoryEntity> findCatagoryByPid(int pid) {	
		return navigatorDaoImpl.findNavigatorByParentId(pid);
	}
	
	
}
