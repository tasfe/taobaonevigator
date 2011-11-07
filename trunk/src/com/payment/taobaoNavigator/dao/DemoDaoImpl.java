package com.payment.taobaoNavigator.dao;

import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.payment.taobaoNavigator.entity.CatagoryEntity;

@Repository
public class DemoDaoImpl extends AbstractBaseDao implements IDemoDao {
	
	@javax.persistence.PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public void doDemo(){
		System.out.println("do demo in demo dao." + new Date());
		CatagoryEntity catagory = em.find(CatagoryEntity.class, 1);
		System.out.println("catagory : name = " + catagory.getName());
		catagory.setName("newName");
		CatagoryEntity mergedCatagory = em.merge(catagory);
		System.out.println("catagory : name = " + mergedCatagory.getName());
	}
}
