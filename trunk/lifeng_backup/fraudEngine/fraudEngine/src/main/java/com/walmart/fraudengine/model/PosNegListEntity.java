package com.walmart.fraudengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table POS_NEG_LIST.
 */
@Entity
@Table(name = "POS_NEG_LIST")
public class PosNegListEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FETCH_ALL = "FETCH_ALL";

	@Id
	@Column(name = "POS_NEG_LIST_PK")
	@SequenceGenerator(name = "PosNegListEntityGenerator", sequenceName = "POS_NEG_LIST_SEQ")
	@GeneratedValue(generator = "PosNegListEntityGenerator")
	private Long posNegListPk;

	@Column(name = "FIELD_VALUE", nullable = false)
	private String fieldValue;

	@Column(name = "FIELD_SHORT_DESC", nullable = false)
	private String fieldShortDesc;

	@Column(name = "FIELD_LONG_DESC")
	private String fieldLongDesc;

	@ManyToOne(optional = false)
	@JoinColumn(name = "FIELD_TYPE_FK", insertable = false, updatable = false)
	private FieldTypeLkEntity fieldTypeLk;

	@OneToOne(optional = false)
	@JoinColumn(name = "POS_NEG_REASON_FK", insertable = false, updatable = false)
	private PosNegReasonLkEntity posNegReasonLk;

	public Long getPosNegListPk() {

		return this.posNegListPk;
	}

	public String getFieldShortDesc() {

		return this.fieldShortDesc;
	}

	public void setFieldShortDesc(String fieldShortDesc) {

		this.fieldShortDesc = fieldShortDesc;
	}

	public String getFieldValue() {

		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {

		this.fieldValue = fieldValue;
	}

	public String getFieldLongDesc() {

		return this.fieldLongDesc;
	}

	public void setFieldLongDesc(String fieldLongDesc) {

		this.fieldLongDesc = fieldLongDesc;
	}

	public FieldTypeLkEntity getFieldTypeLk() {

		return this.fieldTypeLk;
	}

	public void setFieldTypeLk(FieldTypeLkEntity fieldTypeLk) {

		this.fieldTypeLk = fieldTypeLk;
	}

	public PosNegReasonLkEntity getPosNegReasonLk() {

		return this.posNegReasonLk;
	}

	public void setPosNegReasonLk(PosNegReasonLkEntity posNegReasonLk) {

		this.posNegReasonLk = posNegReasonLk;
	}
}