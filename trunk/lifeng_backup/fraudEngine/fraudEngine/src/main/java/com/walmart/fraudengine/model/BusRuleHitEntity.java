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
 * Entity class for table BUS_RULE_HIT.
 */
@Entity
@Table(name = "BUS_RULE_HIT")
public class BusRuleHitEntity extends BaseEntity {

	public final static String BY_RULE_ID = "BY_RULE_ID";

	private static final long serialVersionUID = 1;

	@Id
	@Column(name = "BUS_RULE_HIT_PK")
	@SequenceGenerator(name = "BusRuleHitEntityGenerator", sequenceName = "BUS_RULE_HIT_SEQ  ")
	@GeneratedValue(generator = "BusRuleHitEntityGenerator")
	private Long busRuleHitPk;

	@Column(name = "HIT_COUNT", nullable = false)
	private Long hitCount;

	@OneToOne(optional = false)
	@JoinColumn(name = "BUS_RULE_FK", nullable = false)
	private BusRuleEntity busRule;

	public Long getBusRuleHitPk() {

		return this.busRuleHitPk;
	}

	public Long getHitCount() {

		return this.hitCount;
	}

	public void setHitCount(Long hitCount) {

		this.hitCount = hitCount;
	}

	public BusRuleEntity getBusRule() {

		return this.busRule;
	}

	public void setBusRule(BusRuleEntity busRule) {

		this.busRule = busRule;
	}
}