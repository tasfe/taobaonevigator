package com.walmart.fraudengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table FIELD_TYPE_LK. This is a static lookup table which identifies the set of fields which can be
 * used for positive-negative fraud check.
 */
@Entity
@Table(name = "FIELD_TYPE_LK")
public class FieldTypeLkEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FIELD_TYPE_PK")
	@SequenceGenerator(name = "FieldTypeLkEntityGenerator", sequenceName = "FIELD_TYPE_LK_SEQ")
	@GeneratedValue(generator = "FieldTypeLkEntityGenerator")
	private Long fieldTypePk;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	public Long getFieldTypePk() {

		return this.fieldTypePk;
	}

	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {

		return this.description;
	}

	public void setDescription(String description) {

		this.description = description;
	}
}