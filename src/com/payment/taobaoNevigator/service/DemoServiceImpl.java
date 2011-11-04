package com.payment.taobaoNevigator.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.payment.taobaoNevigator.dao.IDemoDao;

@Service
public class DemoServiceImpl implements IDemoService {
	
	@Autowired
	@Qualifier("demoDaoImpl")
	private IDemoDao demoDao;
	
	@Override
	public void doDemo(){
		System.out.println("do demo in demo service." + new Date());
		demoDao.doDemo();
	}
}
