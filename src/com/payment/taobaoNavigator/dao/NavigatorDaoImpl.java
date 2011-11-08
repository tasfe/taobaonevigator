package com.payment.taobaoNavigator.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.payment.taobaoNavigator.entity.BaseEntity;
import com.payment.taobaoNavigator.entity.CatagoryEntity;

@Repository
public class NavigatorDaoImpl extends BaseDaoImp implements NavigatorDao {

	@Override
	public List<CatagoryEntity> findNavigatorByParentId(int pid) {
		Map parameters=new HashMap();
		parameters.put("pid", pid);
		return this.findByNamedQuery(CatagoryEntity.class, CatagoryEntity.BYPARENTID, parameters);
		
	}

	
	
}
