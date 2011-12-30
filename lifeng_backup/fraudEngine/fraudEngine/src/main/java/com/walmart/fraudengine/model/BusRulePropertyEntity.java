package com.walmart.fraudengine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the BUS_RULE_PROPERTY database table.
 */
@Entity
@Table(name = "BUS_RULE_PROPERTY")
public class BusRulePropertyEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String BY_RULE_ID = "BY_RULE_ID";
	public static final String FETCH_ALL = "FETCH_ALL";

	@Id
	@Column(name = "BUS_RULE_PROPERTY_PK")
	@SequenceGenerator(name = "BusRulePropertyEntityGenerator", sequenceName = "BUS_RULE_PROPERTY_SEQ")
	@GeneratedValue(generator = "BusRulePropertyEntityGenerator")
	private Long busRulePropertyPk;

	@Column(name = "BUS_RULE_FK")
	private long busRuleFk;

	@Column(name = "PROPERTY_VALUE")
	private String propertyValue;
	
	@Column(name = "PROPERTY_NAME")
	private String propertyName;

	public Long getBusRulePropertyPk() {

		return this.busRulePropertyPk;
	}

	public long getBusRuleFk() {

		return busRuleFk;
	}

	public void setBusRuleFk(long busRuleFk) {

		this.busRuleFk = busRuleFk;
	}

	public String getPropertyValue() {

		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {

		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}