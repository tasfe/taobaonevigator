package com.walmart.fraudengine.dao;

import java.util.Map;

import com.walmart.fraudengine.model.PosNegListView;
import com.walmart.fraudengine.model.StatusLkEntity;

/**
 * Common DAO class for Fraud Engine application.
 */
public interface FraudEngineDao extends GenericDao {

	public StatusLkEntity findStatusLk(String status);

	public PosNegListView findFromView(Map<String, String> fieldMap);
}