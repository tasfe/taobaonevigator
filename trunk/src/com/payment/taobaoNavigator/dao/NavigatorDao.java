package com.payment.taobaoNavigator.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.payment.taobaoNavigator.entity.BaseEntity;

@Repository
public interface NavigatorDao {
	public List<BaseEntity> findNavigatorByParentId(int pid);
}
