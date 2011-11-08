package com.payment.taobaoNavigator.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.payment.taobaoNavigator.entity.BaseEntity;

@Repository
public class NavigatorDaoImpl extends BaseDaoImpl implements NavigatorDao {

	@Override
	public List<BaseEntity> findNavigatorByParentId(int pid) {
		
		return null;
	}

	
	
}
