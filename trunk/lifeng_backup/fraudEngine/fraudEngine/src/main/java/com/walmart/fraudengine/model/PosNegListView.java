package com.walmart.fraudengine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POS_NEG_LIST_VIEW")
public class PosNegListView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "POS_NEG_LIST_PK")
	private Long posNegListPk;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FIELD_VALUE")
	private String fieldValue;

	@Column(name = "FIELD_SHORT_DESC")
	private String fieldShortDesc;

	@Column(name = "REASON_CODE")
	private String reasonCode;

	@Column(name = "REASON_SHORT_DESC")
	private String reasonShortDesc;

	@Column(name = "STATUS")
	private String status;

	public Long getPosNegListPk() {

		return this.posNegListPk;
	}

	public String getFieldShortDesc() {

		return this.fieldShortDesc;
	}

	public String getFieldValue() {

		return this.fieldValue;
	}

	public String getName() {

		return this.name;
	}

	public String getReasonCode() {

		return this.reasonCode;
	}

	public String getReasonShortDesc() {

		return this.reasonShortDesc;
	}

	public String getStatus() {

		return this.status;
	}
}