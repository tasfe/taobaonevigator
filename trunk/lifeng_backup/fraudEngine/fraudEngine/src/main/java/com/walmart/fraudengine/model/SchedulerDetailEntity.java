package com.walmart.fraudengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table SCHEDULER_DETAIL.
 */
@Entity
@Table(name = "SCHEDULER_DETAIL")
public class SchedulerDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SCHEDULER_DETAILS_PK")
	@SequenceGenerator(name = "SchedulerDetailEntityGenerator", sequenceName = "SCHEDULE_DETAIL_SEQ")
	@GeneratedValue(generator = "SchedulerDetailEntityGenerator")
	private Long schedulerDetailsPk;

	@Column(name = "OMS_ORDER_ID", nullable = false)
	private String omsOrderId;

	@Column(name = "TRANSACTION_ID", nullable = false)
	private String transactionId;

	@Column(name = "TRANSACTION_SENT_TO")
	private String transactionSentTo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SCHEDULER_MASTER_FK")
	private SchedulerMasterEntity schedulerMaster;

	public Long getSchedulerDetailsPk() {

		return this.schedulerDetailsPk;
	}

	public String getOmsOrderId() {

		return this.omsOrderId;
	}

	public void setOmsOrderId(String omsOrderId) {

		this.omsOrderId = omsOrderId;
	}

	public String getTransactionId() {

		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {

		this.transactionId = transactionId;
	}

	public String getTransactionSentTo() {

		return this.transactionSentTo;
	}

	public void setTransactionSentTo(String transactionSentTo) {

		this.transactionSentTo = transactionSentTo;
	}

	public SchedulerMasterEntity getSchedulerMaster() {

		return this.schedulerMaster;
	}

	public void setSchedulerMaster(SchedulerMasterEntity schedulerMaster) {

		this.schedulerMaster = schedulerMaster;
	}
}