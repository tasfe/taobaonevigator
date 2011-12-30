package com.walmart.fraudengine.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table SCHEDULER_MASTER.
 */
@Entity
@Table(name = "SCHEDULER_MASTER")
public class SchedulerMasterEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SCHEDULER_MASTER_PK")
	@SequenceGenerator(name = "SchedulerMasterEntityGenerator", sequenceName = "SCHEDULE_MASTER_SEQ")
	@GeneratedValue(generator = "SchedulerMasterEntityGenerator")
	private Long schedulerMasterPk;

	@Column(name = "SCHEDULER_NAME")
	private String schedulerName;

	@Column(name = "FREQUENCY")
	private Integer frequency;

	@Column(name = "START_DATE")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "LAST_RUN_DATE_TIME")
	private Timestamp lastRunDateTime;

	@Column(name = "STATUS")
	private String status;

	public Long getSchedulerMasterPk() {

		return this.schedulerMasterPk;
	}

	public Timestamp getEndDate() {

		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {

		this.endDate = endDate;
	}

	public Integer getFrequency() {

		return this.frequency;
	}

	public void setFrequency(Integer frequency) {

		this.frequency = frequency;
	}

	public Timestamp getLastRunDateTime() {

		return this.lastRunDateTime;
	}

	public void setLastRunDateTime(Timestamp lastRunDateTime) {

		this.lastRunDateTime = lastRunDateTime;
	}

	public String getSchedulerName() {

		return this.schedulerName;
	}

	public void setSchedulerName(String schedulerName) {

		this.schedulerName = schedulerName;
	}

	public Timestamp getStartDate() {

		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {

		this.startDate = startDate;
	}

	public String getStatus() {

		return this.status;
	}

	public void setStatus(String status) {

		this.status = status;
	}
}