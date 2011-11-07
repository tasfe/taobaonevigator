package com.payment.taobaoNavigator.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

@Repository
public class DemoDaoImpl extends AbstractBaseDao implements IDemoDao {
	@Override
	public void doDemo(){
		System.out.println("do demo in demo dao." + new Date());
	}
}
