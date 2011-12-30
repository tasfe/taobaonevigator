package com.walmart.fraudengine.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table BUS_RULE.
 */
@Entity
@Table(name = "BUS_RULE")
public class BusRuleEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FETCH_ALL = "FETCH_ALL";
	public static final String FETCH_ALL_BY_STATUS = "FETCH_ALL_BY_STATUS";
	public static final String FETCH_ALL_ORDER_BY_FILE_NAME = "FETCH_ALL_ORDER_BY_FILE_NAME";
	public static final String BY_FILE_NAME_AND_DESC = "BY_FILE_NAME_AND_DESC";

	@Id
	@Column(name = "BUS_RULE_PK")
	@SequenceGenerator(name = "BusRuleEntityGenerator", sequenceName = "BUS_RULE_SEQ ")
	@GeneratedValue(generator = "BusRuleEntityGenerator")
	private Long busRulePk;

	@Column(name = "RULE_FILE_NAME", nullable = false)
	private String ruleFileName;

	@Lob()
	@Column(name = "RULE", nullable = false)
	private String rule;

	@Column(name = "RULE_DESCRIPTION", nullable = false)
	private String ruleDescription;

	@Column(name = "START_DATE", nullable = false)
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "REASON_CODE")
	private String reasonCode;

	@OneToOne(optional = false)
	@JoinColumn(name = "STATUS_FK", nullable = false)
	private StatusLkEntity statusLk;

	public Long getBusRulePk() {

		return this.busRulePk;
	}

	public String getReasonCode() {

		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {

		this.reasonCode = reasonCode;
	}

	public String getRuleFileName() {

		return this.ruleFileName;
	}

	public void setRuleFileName(String ruleFileName) {

		this.ruleFileName = ruleFileName;
	}

	public String getRule() {

		return this.rule;
	}

	public void setRule(String rule) {

		this.rule = rule;
	}

	public String getRuleDescription() {

		return this.ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {

		this.ruleDescription = ruleDescription;
	}

	public Timestamp getStartDate() {

		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {

		this.startDate = startDate;
	}

	public Timestamp getEndDate() {

		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {

		this.endDate = endDate;
	}

	public StatusLkEntity getStatusLk() {

		return this.statusLk;
	}

	public void setStatusLk(StatusLkEntity statusLk) {

		this.statusLk = statusLk;
	}
}