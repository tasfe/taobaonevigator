package com.walmart.fraudengine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity class for table STATUS_LK.
 */
@Entity
@Table(name = "STATUS_LK")
public class StatusLkEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String BY_NAME = "BY_NAME";

	@Id
	@Column(name = "STATUS_PK")
	@SequenceGenerator(name = "StatusLkEntityGenerator", sequenceName = "STATUS_LK_SEQ")
	@GeneratedValue(generator = "StatusLkEntityGenerator")
	private Long statusPk;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	public Long getStatusPk() {

		return this.statusPk;
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