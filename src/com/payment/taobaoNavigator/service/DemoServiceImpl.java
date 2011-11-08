package com.payment.taobaoNavigator.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.payment.taobaoNavigator.dao.DemoDao;

@Service
public class DemoServiceImpl implements DemoService {
	
	@Autowired
	@Qualifier("demoDaoImpl")
	private DemoDao demoDao;
	
	@Override
	public void doDemo(){
		System.out.println("do demo in demo service." + new Date());
		demoDao.doDemo();
	}
}
