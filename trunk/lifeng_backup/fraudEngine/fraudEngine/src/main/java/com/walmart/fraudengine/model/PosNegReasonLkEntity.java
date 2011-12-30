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
 * Entity class for table POS_NEG_REASON_LK. This is a static lookup table which identifies the set of reason codes that
 * can be used to flag a field as Accept, Deny, or Challenge during positive-negative fraud check.
 */
@Entity
@Table(name = "POS_NEG_REASON_LK")
public class PosNegReasonLkEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "POS_NEG_REASON_PK")
	@SequenceGenerator(name = "PosNegReasonLkEntityGenerator", sequenceName = "POS_NEG_RERSONE_LK_SEQ")
	@GeneratedValue(generator = "PosNegReasonLkEntityGenerator")
	private Long posNegReasonPk;

	@Column(name = "REASON_CODE", nullable = false)
	private String reasonCode;

	@Column(name = "REASON_SHORT_DESC", nullable = false)
	private String reasonShortDesc;

	@Column(name = "REASON_LONG_DESC", nullable = false)
	private String reasonLongDesc;

	@OneToOne(optional = false)
	@JoinColumn(name = "STATUS_FK", nullable = false)
	private StatusLkEntity statusLk;

	public Long getPosNegReasonPk() {

		return this.posNegReasonPk;
	}

	public String getReasonCode() {

		return this.reasonCode;
	}

	public void setReasonCode(String reasonCode) {

		this.reasonCode = reasonCode;
	}

	public String getReasonShortDesc() {

		return this.reasonShortDesc;
	}

	public void setReasonShortDesc(String reasonShortDesc) {

		this.reasonShortDesc = reasonShortDesc;
	}

	public String getReasonLongDesc() {

		return this.reasonLongDesc;
	}

	public void setReasonLongDesc(String reasonLongDesc) {

		this.reasonLongDesc = reasonLongDesc;
	}

	public StatusLkEntity getStatusLk() {

		return this.statusLk;
	}

	public void setStatusLk(StatusLkEntity statusLk) {

		this.statusLk = statusLk;
	}
}