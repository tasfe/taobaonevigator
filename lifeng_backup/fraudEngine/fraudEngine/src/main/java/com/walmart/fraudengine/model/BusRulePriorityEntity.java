package com.walmart.fraudengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table BUS_RULE_PRIORITY.
 */
@Entity
@Table(name = "BUS_RULE_PRIORITY")
public class BusRulePriorityEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FETCH_ALL_ORDER_BY_PRIORITY = "FETCH_ALL_ORDER_BY_PRIORITY";
	public static final String BY_RULE_ID = "BY_RULE_ID";

	@Id
	@Column(name = "BUS_RULE_PRIORITY_PK")
	@SequenceGenerator(name = "BusRulePriorityEntityGenerator", sequenceName = "BUS_RULE_PRIRITY_SEQ")
	@GeneratedValue(generator = "BusRulePriorityEntityGenerator")
	private Long busRulePriorityPk;

	@Column(name = "PRIORITY", nullable = false)
	private Integer priority;

	@OneToOne(optional = false)
	@JoinColumn(name = "BUS_RULE_FK", nullable = false)
	private BusRuleEntity busRule;

	public Long getBusRulePriorityPk() {

		return this.busRulePriorityPk;
	}

	public Integer getPriority() {

		return this.priority;
	}

	public void setPriority(Integer priority) {

		this.priority = priority;
	}

	public BusRuleEntity getBusRule() {

		return this.busRule;
	}

	public void setBusRule(BusRuleEntity busRule) {

		this.busRule = busRule;
	}
}