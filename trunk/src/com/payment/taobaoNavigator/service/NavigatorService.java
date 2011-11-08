package com.payment.taobaoNavigator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.entity.CatagoryEntity;


public interface NavigatorService {
	public List<CatagoryEntity> findCatagoryByPid(int pid);
}
