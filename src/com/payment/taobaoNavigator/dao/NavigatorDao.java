package com.payment.taobaoNavigator.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.payment.taobaoNavigator.entity.CatagoryEntity;

@Repository
public interface NavigatorDao {
	public List<CatagoryEntity> findNavigatorByParentId(int pid);
}
